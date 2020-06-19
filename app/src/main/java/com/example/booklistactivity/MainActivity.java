package com.example.booklistactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.util.Log;
import android.os.Bundle;
import java.net.URL;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            URL bookUrl = apiUtil.buildURL("cooking");
            new bookQueryTasks().execute(bookUrl);
        }
        catch (Exception e){
            Log.d("Error", e.getMessage());
        }
    }
    public class bookQueryTasks extends AsyncTask<URL,Void,String> {

        @Override
        protected String doInBackground(URL... urls) {

           URL searchURL =  urls[0];
           String result = null;
           try{
               result = apiUtil.getJson(searchURL);
           }
           catch (Exception e){
               Log.d("Error",e.getMessage());
           }
           return result;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView textViewResult = (TextView) findViewById(R.id.tvResponse);
            textViewResult.setText(result);
        }

    }
}