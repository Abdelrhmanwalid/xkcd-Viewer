package com.abdelrhman.xkcd.data;

import java.util.Date;

public class Comic {
    private final long id;
    private final String imageUrl;
    private final String url;
    private final String title;
    private final String text;
    private final Date date;

    public Comic(long id, String imageUrl, String url, String title, String text, Date date) {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }
}
