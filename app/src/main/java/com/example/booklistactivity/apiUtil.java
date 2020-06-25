package com.example.booklistactivity;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class apiUtil {
    private apiUtil() {
    }

    public static final String BASE_URL_API = "https://www.googleapis.com/books/v1/volumes/";
    public static final String QUERY_PARAMETER_KEY = "q";
    public static final String key = "key";
    public static final String API_KEY = "AIzaSyCm9Veij2ioBFTevNEewzrW020w2e4JNms";

    //Function used to fetch our data
    public static URL buildURL(String title) {
        URL url = null;
        Uri uri = Uri.parse(BASE_URL_API).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_KEY, title)
//                .appendQueryParameter(key, API_KEY)
                .build();
        try {
            url = new URL(uri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    //Function which used to get Json from url
    public static String getJson(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");
            boolean hasData = scanner.hasNext();
            if (hasData) return scanner.next();
            return null;
        } catch (Exception e) {
            Log.d("Error", e.toString());
            return null;
        } finally {
            connection.disconnect();
        }
    }

    //Function which store books array data
    public static ArrayList<Book> getBooksFromJson(String json) {
        final String ID = "id";
        final String TITLE = "title";
        final String SUBTITLE = "subtitle";
        final String AUTHORS = "authors";
        final String PUBLISHER = "publisher";
        final String PUBLISHED_DATE = "publishedDate";
        final String ITEMS = "items";
        final String VOLUME_INFO = "volumeInfo";

        ArrayList<Book> books = new ArrayList<Book>();
        try {
            JSONObject jsonBooks = new JSONObject(json);
            JSONArray arrayBooks = jsonBooks.getJSONArray(ITEMS);
            int numberOfBooks = arrayBooks.length();

            for (int book = 0; book < numberOfBooks; book++) {
                JSONObject bookJSON = arrayBooks.getJSONObject(book);
                JSONObject volumeInfoJSON = bookJSON.getJSONObject(VOLUME_INFO);

                int numberOfAuthor = volumeInfoJSON.getJSONArray(AUTHORS).length();
                String[] authors = new String[numberOfAuthor];
                for (int author = 0; author < numberOfAuthor; author++) {
                    authors[author] = volumeInfoJSON.getJSONArray(AUTHORS).get(author).toString();
                }
                Book bookObject = new Book(
                        bookJSON.getString(ID),
                        volumeInfoJSON.getString(TITLE),
                        authors,
                        (volumeInfoJSON.isNull(SUBTITLE) ? "" : volumeInfoJSON.getString(SUBTITLE)),
                        volumeInfoJSON.getString(PUBLISHER),
                        volumeInfoJSON.getString(PUBLISHED_DATE)
                );
                books.add(bookObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return books;
    }

}
