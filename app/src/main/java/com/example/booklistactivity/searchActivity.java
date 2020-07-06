package com.example.booklistactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;

public class searchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final EditText etTitle = (EditText) findViewById(R.id.etTitle);
        final EditText etAuthor = (EditText) findViewById(R.id.etAuthor);
        final EditText etPublisher = (EditText) findViewById(R.id.etPublisher);
        final EditText etIsbn = (EditText) findViewById(R.id.etIsbn);
        final Button button = (Button) findViewById(R.id.searchBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title= etTitle.getText().toString().trim();
                String author= etAuthor.getText().toString().trim();
                String publisher= etPublisher.getText().toString().trim();
                String isbn= etIsbn.getText().toString().trim();
                if(title.isEmpty() && author.isEmpty() && publisher.isEmpty() && isbn.isEmpty()){
                    String message = getString(R.string.no_searchData);
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }else{
                    URL queryURL = apiUtil.buildUrl(title,author,publisher,isbn);
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("Query",queryURL);
                    startActivity(intent);
                }
            }
        });
    }
}