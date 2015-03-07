package com.jaeger.reading.model;


import android.graphics.Bitmap;

import java.io.Serializable;

public class BookInfo implements Serializable{
    private String title;
    private String author;
    private String id;
    private int pages;
    private String coverImgUrl;
    private Bitmap cover;


    public BookInfo(String id, String title, String author, int pages, String coverImgUrl, Bitmap cover) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.coverImgUrl = coverImgUrl;
        this.cover = cover;
    }

    public BookInfo(String id, String title, String author, int pages, String coverImgUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.coverImgUrl = coverImgUrl;
    }
    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Bitmap getCover() {
        return cover;
    }

    public void setCover(Bitmap cover) {
        this.cover = cover;
    }

    public void setId(String id) {
        this.id = id;
    }
}
