package com.abdelrhman.xkcd.data.local;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;

import java.util.Date;

@Entity(tableName = "comics", indices = {@Index("id")}, primaryKeys = "id")
public class LocalComic {

    private long id;
    private String imageUrl;
    private String url;
    private String title;
    private String text;
    private Date date;

    public LocalComic(long id, String imageUrl, String url, String title, String text, Date date) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.url = url;
        this.title = title;
        this.text = text;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public LocalComic setId(long id) {
        this.id = id;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public LocalComic setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public LocalComic setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public LocalComic setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getText() {
        return text;
    }

    public LocalComic setText(String text) {
        this.text = text;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public LocalComic setDate(Date date) {
        this.date = date;
        return this;
    }
}
