package com.example.booklistactivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class booksAdapter extends RecyclerView.Adapter<booksAdapter.BookViewHolder> {
    ArrayList<Book> books;

    public booksAdapter(ArrayList<Book> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.book_list_item, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.bind(book);
    }

    //Get books numbers
    @Override
    public int getItemCount() {

        return books.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvAuthors;
        TextView tvPublisher;
        TextView tvPublishedDate;

        public BookViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvAuthors = (TextView) itemView.findViewById(R.id.tvAuthors);
            tvPublisher = (TextView) itemView.findViewById(R.id.tvPublisher);
            tvPublishedDate = (TextView) itemView.findViewById(R.id.tvPublishedDate);
            itemView.setOnClickListener(this);
        }

        //Set data into each row display to book_list_item.xml
        public void bind(Book book) {
            tvTitle.setText(book.title);
//            String authors = "";
//            int i = 0;
//            for (String author : book.authors) {
//                authors += author;
//                i++;
//                if (i < book.authors.length) {
//                    authors += ", ";
//                }
//            }
            tvAuthors.setText(book.authors);
            tvPublisher.setText(book.publisher);
            tvPublishedDate.setText(book.publishedDate);
        }

        //Get selected book
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Book getSelectedBook = books.get(position);
            Intent intent = new Intent(view.getContext(), BookDetail.class);
            intent.putExtra("Book", getSelectedBook);
            view.getContext().startActivities(new Intent[]{intent});
        }
    }
}
