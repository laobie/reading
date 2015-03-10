package com.jaeger.reading.model;


import android.graphics.Bitmap;

import java.io.Serializable;

public class BookInfo implements Serializable{
    private String title;       //标题
    private String author;      //作者
    private String id;          //豆瓣图书库中id
    private int pages;          //页数
    private String coverImgUrl; //封面图片地址
    private Bitmap cover;       //封面图片
    private String summary;     //简介

    public BookInfo(String id, String title, String author, int pages, String coverImgUrl, Bitmap cover, String summary) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.coverImgUrl = coverImgUrl;
        this.cover = cover;
        this.summary = summary;
    }

    public BookInfo(String id, String title, String author, int pages, String coverImgUrl, String summary) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.coverImgUrl = coverImgUrl;
        this.summary = summary;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setCover(Bitmap cover) {
        this.cover = cover;
    }

    public void setId(String id) {
        this.id = id;
    }
}
