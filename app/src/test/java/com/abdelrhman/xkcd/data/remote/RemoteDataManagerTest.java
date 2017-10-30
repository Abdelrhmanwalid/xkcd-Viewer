package com.abdelrhman.xkcd.data.remote;

import com.abdelrhman.xkcd.data.Comic;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import static org.junit.Assert.assertEquals;

public class RemoteDataManagerTest {
    @Inject
    private RemoteDataSource remoteDataManager;

    @Before
    public void setup() {
        XKCDService service = new XKCDService("https://xkcd.com/", false);
        remoteDataManager = new RemoteDataSource(service);
    }

    @Test
    public void testLatest() throws IOException, JSONException {
        Comic comic = remoteDataManager.getLatest().blockingFirst();
        assertEquals(getLatestId(), comic.getId());
    }

    @Test
    public void testItem() {
        long id = 1885;
        Comic comic = remoteDataManager.getComic(id).blockingFirst();
        assertEquals(id, comic.getId());
    }

    private long getLatestId() throws IOException, JSONException {
        Request request = new Request.Builder()
                .get()
                .url("https://xkcd.com/info.0.json")
                .build();
        String response = new OkHttpClient().newCall(request).execute().body().string();
        return new JSONObject(response).getLong("num");
    }
}