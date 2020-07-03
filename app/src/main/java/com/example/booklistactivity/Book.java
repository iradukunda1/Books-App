package com.example.booklistactivity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import static com.squareup.picasso.Picasso.*;

public class Book implements Parcelable {

    public String id;
    public String title;
    public String authors;
    public String subtitle;
    public String publisher;
    public String publishedDate;
    public String description;
//    public String thumbnail;

    public Book(String id, String title, String[] authors, String subtitle, String publisher,
                String publishedDate, String description) {

        this.id = id;
        this.title = title;
        this.authors = TextUtils.join(", ", authors);
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
//        this.thumbnail = thumbnail;
    }

    protected Book(Parcel in) {
        id = in.readString();
        title = in.readString();
        authors = in.readString();
        subtitle = in.readString();
        publisher = in.readString();
        publishedDate = in.readString();
        description = in.readString();
//        thumbnail = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(authors);
        parcel.writeString(subtitle);
        parcel.writeString(publisher);
        parcel.writeString(publishedDate);
        parcel.writeString(description);
//        parcel.writeString(thumbnail);
    }

//    @BindingAdapter({"android:imageUrl"})
//    public static void loadImage(ImageView view, String imageUrl) {
//        Picasso.with(view.getContext())
//                .load(imageUrl)
//                .placeholder(R.drawable.book_open)
//                .into(view);
//    }
}
