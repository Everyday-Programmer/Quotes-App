package com.example.quotesapp;

public class Quote {
    String quote, author;

    public Quote(String quote, String author) {
        this.quote = quote;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public String getQuote() {
        return quote;
    }
}