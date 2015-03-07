package com.jaeger.reading.model;

import org.litepal.crud.DataSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Book extends DataSupport implements Serializable {
    private int id;
    private String name;
    private String author;
    private int pages;
    private int readPages;
    private String coverFile;
    private Date addDate;
    private Date finishDate;
    private boolean isFinish;

    private List<Excerpt> excerptList = new ArrayList<>();

    public Book() {

    }

    public Book(String name, String author, int pages, int readPages, String coverFile) {
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.readPages = readPages;
        this.coverFile = coverFile;
        this.addDate = new Date(System.currentTimeMillis());
        this.isFinish = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setReadPages(int readPages) {
        this.readPages = readPages;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCoverFile(String coverFile) {
        this.coverFile = coverFile;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public void setExcerptList(List<Excerpt> excerptList) {
        this.excerptList = excerptList;
    }

    public void setFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public List<Excerpt> getExcerptList() {
        return excerptList;
    }

    public String getName() {
        return name;
    }

    public Date getAddDate() {
        return addDate;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }

    public int getId() {
        return id;
    }

    public int getReadPages() {
        return readPages;
    }

    public String getCoverFile() {
        return coverFile;
    }

}
