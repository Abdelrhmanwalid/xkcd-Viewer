package com.abdelrhman.xkcd.data;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import com.abdelrhman.xkcd.data.local.ComicDataBase;
import com.abdelrhman.xkcd.data.local.LocalDataSource;
import com.abdelrhman.xkcd.data.remote.RemoteDataSource;
import com.abdelrhman.xkcd.data.remote.XKCDService;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ComicRepositoryTest {

    private long latestID = -1;
    private ComicRepository repository;
    private LocalDataSource localDataManager;

    @Before()
    public void setup() throws IOException, JSONException {
        ComicDataBase dataBase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getTargetContext(),
                ComicDataBase.class)
                .build();
        localDataManager = new LocalDataSource(dataBase.comicDao());
        XKCDService service = new XKCDService("https://xkcd.com/", true);
        RemoteDataSource remoteDataManager = new RemoteDataSource(service);
        repository = new ComicRepository(localDataManager, remoteDataManager);
    }

    private long getLatestId() throws IOException, JSONException {
        if (latestID == -1) {
            Request request = new Request.Builder()
                    .get()
                    .url("https://xkcd.com/info.0.json")
                    .build();
            String response = new OkHttpClient().newCall(request).execute().body().string();
            latestID = new JSONObject(response).getLong("num");
        }
        return latestID;
    }

    @Test
    public void getLatest() throws IOException, JSONException {
        long latestId = getLatestId();
        Comic comic = repository.getLatest().blockingFirst();
        Assert.assertNotNull(comic);
        Assert.assertEquals(latestId, comic.getId());
    }

    @Test
    public void getItem() {
        long id = 1886;
        Comic comic = repository.getComic(id).blockingFirst();
        Assert.assertNotNull(comic);
        Assert.assertEquals(id, comic.getId());
    }

    @Test
    public void getItemOffline() {
        long id = 1880;
        Comic comic = new Comic(id, "Test", "Test", "Test", "Test", new Date());
        localDataManager.add(comic);
        Comic comic1 = repository.getComic(id).blockingFirst();
        Assert.assertEquals(comic.getId(), comic1.getId());
        Assert.assertEquals(comic.getDate(), comic1.getDate());
        Assert.assertEquals(comic.getImageUrl(), comic1.getImageUrl());
        Assert.assertEquals(comic.getText(), comic1.getText());
        Assert.assertEquals(comic.getTitle(), comic1.getTitle());
    }

}