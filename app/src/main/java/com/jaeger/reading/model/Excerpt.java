package com.jaeger.reading.model;

import com.jaeger.reading.model.Book;

import java.util.Date;

/**
 * Created by Jaeger on 1/26/026.
 */
public class Excerpt {
    private Book book;
    private Date addDate;
    private String excerptStr;

    public void setBook(Book book) {
        this.book = book;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public void setExcerptStr(String excerptStr) {
        this.excerptStr = excerptStr;
    }

    public Book getBook() {
        return book;
    }

    public Date getAddDate() {
        return addDate;
    }

    public String getExcerptStr() {
        return excerptStr;
    }
}
