package com.abdelrhman.xkcd.data.remote.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComicDto {
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("num")
    @Expose
    private int num;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("news")
    @Expose
    private String news;
    @SerializedName("safe_title")
    @Expose
    private String safeTitle;
    @SerializedName("transcript")
    @Expose
    private String transcript;
    @SerializedName("alt")
    @Expose
    private String alt;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("day")
    @Expose
    private String day;

    public String getMonth() {
        return month;
    }

    public ComicDto setMonth(String month) {
        this.month = month;
        return this;
    }

    public int getNum() {
        return num;
    }

    public ComicDto setNum(int num) {
        this.num = num;
        return this;
    }

    public String getLink() {
        return link;
    }

    public ComicDto setLink(String link) {
        this.link = link;
        return this;
    }

    public String getYear() {
        return year;
    }

    public ComicDto setYear(String year) {
        this.year = year;
        return this;
    }

    public String getNews() {
        return news;
    }

    public ComicDto setNews(String news) {
        this.news = news;
        return this;
    }

    public String getSafeTitle() {
        return safeTitle;
    }

    public ComicDto setSafeTitle(String safeTitle) {
        this.safeTitle = safeTitle;
        return this;
    }

    public String getTranscript() {
        return transcript;
    }

    public ComicDto setTranscript(String transcript) {
        this.transcript = transcript;
        return this;
    }

    public String getAlt() {
        return alt;
    }

    public ComicDto setAlt(String alt) {
        this.alt = alt;
        return this;
    }

    public String getImg() {
        return img;
    }

    public ComicDto setImg(String img) {
        this.img = img;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ComicDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDay() {
        return day;
    }

    public ComicDto setDay(String day) {
        this.day = day;
        return this;
    }
}
