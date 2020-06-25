package com.example.booklistactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;
import android.os.Bundle;

import java.net.URL;
import java.util.ArrayList;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "Books App";
    private ProgressBar mLoadingProgressBar;

    private RecyclerView rvBooks;

    //executed before opening app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingProgressBar = (ProgressBar) findViewById(R.id.pb_loading);
        rvBooks = (RecyclerView) findViewById(R.id.rv_books);
        LinearLayoutManager booksLayoutManagement = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rvBooks.setLayoutManager(booksLayoutManagement);
        try {
            URL bookUrl = apiUtil.buildURL("android");
            new bookQueryTasks().execute(bookUrl);
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_item_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.search_action);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            URL BUILDURL = apiUtil.buildURL(query);
            new bookQueryTasks().execute(BUILDURL);
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    public class bookQueryTasks extends AsyncTask<URL, Void, String> {
        // executed before during  loading
        @Override
        protected String doInBackground(URL... urls) {

            URL searchURL = urls[0];
            String result = null;
            try {
                result = apiUtil.getJson(searchURL);
            } catch (Exception e) {
                Log.d("Error", e.getMessage());
            }
            return result;
        }

        //executed after loading of the app


        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, ">>>*** Start On post execution ***<<<" + Thread.currentThread().getId() + "***<<<");
//            TextView textViewResult = (TextView) findViewById(R.id.tvResponse);
            TextView tvError = (TextView) findViewById(R.id.tv_error);
            mLoadingProgressBar.setVisibility(View.INVISIBLE);
            if (result == null) {
                tvError.setVisibility(View.VISIBLE);
                rvBooks.setVisibility(View.INVISIBLE);
            } else {
                tvError.setVisibility(View.INVISIBLE);
                rvBooks.setVisibility(View.VISIBLE);
            }
            ArrayList<Book> Books = apiUtil.getBooksFromJson(result);
            booksAdapter adapter = new booksAdapter(Books);
            rvBooks.setAdapter(adapter);
//            String resultString = "";
//            for (Book book : Books) {
//                resultString = resultString + book.title + "\n" +
//                        book.publishedDate + "\n\n";
//            }
//            textViewResult.setText(resultString);
            Log.i(TAG, ">>>*** End of On post execution ***<<<" + Thread.currentThread().getId() + "***<<<");

        }

        //activity or progress to be displayed during onDoingBackground task
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgressBar.setVisibility(View.VISIBLE);
        }

    }
}