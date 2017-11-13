package com.abdelrhman.xkcd.data.remote;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class XKCDServiceTest {

    private XKCDService service;

    @Before
    public void setup() {
        service = new XKCDService("https://xkcd.com/", false);
    }

    @Test
    public void getComic() throws IOException, JSONException {
        long id = 1000;
        long serviceId = service.getComic(id).blockingFirst().getNum();
        Assert.assertEquals(id, serviceId);
        Assert.assertEquals(getComicById(id), serviceId);
    }

    @Test
    public void getLatest() throws IOException, JSONException {
        long latestId = service.getLatest().blockingFirst().getNum();
        Assert.assertEquals(getLatestId(), latestId);
    }


    private long getLatestId() throws IOException, JSONException {
        Request request = new Request.Builder()
                .get()
                .url("https://xkcd.com/info.0.json")
                .build();
        String response = new OkHttpClient().newCall(request).execute().body().string();
        return new JSONObject(response).getLong("num");
    }

    private long getComicById(long id) throws IOException, JSONException {
        Request request = new Request.Builder()
                .get()
                .url(String.format("https://xkcd.com//%d/info.0.json", id))
                .build();
        String response = new OkHttpClient().newCall(request).execute().body().string();
        return new JSONObject(response).getLong("num");
    }


}