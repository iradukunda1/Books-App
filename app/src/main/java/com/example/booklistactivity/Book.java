package com.example.booklistactivity;

public class Book {

    public String id;
    public String title;
    public String[] authors;
    public String subtitle;
    public String publisher;
    public String publishedDate;

    public Book(String id, String title, String[] authors, String subtitle, String publisher, String publishedDate) {

        this.id = id;
        this.title = title;
        this.authors = authors;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
    }
}
