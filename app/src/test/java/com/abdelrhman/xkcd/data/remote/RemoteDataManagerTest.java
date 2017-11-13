package com.abdelrhman.xkcd.data.remote;

import com.abdelrhman.xkcd.data.Comic;
import com.abdelrhman.xkcd.data.remote.dto.ComicDto;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Flowable;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static org.junit.Assert.assertEquals;

// TODO: test with a fake service and create tests for the service
public class RemoteDataManagerTest {
    @Inject
    private RemoteDataSource remoteDataManager;

    private int latestID = 90;

    @Before
    public void setup() {
        RemoteService service = new FakeService();
        remoteDataManager = new RemoteDataSource(service);
    }

    @Test
    public void testLatest() throws IOException, JSONException {
        Comic comic = remoteDataManager.getLatest().blockingFirst();
        assertEquals(latestID, comic.getId());
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

    private class FakeService implements RemoteService {

        ComicDto dto;
        ComicDto latest;

        FakeService() {
            dto = new ComicDto()
                    .setAlt("alt")
                    .setYear("2017")
                    .setTitle("title")
                    .setSafeTitle("safetitle")
                    .setTranscript("transcript")
                    .setMonth("11")
                    .setDay("11")
                    .setNews("news")
                    .setLink("link")
                    .setImg("image");

            latest = new ComicDto()
                    .setNum(latestID)
                    .setAlt("alt")
                    .setYear("2017")
                    .setTitle("title")
                    .setSafeTitle("safetitle")
                    .setTranscript("transcript")
                    .setMonth("11")
                    .setDay("11")
                    .setNews("news")
                    .setLink("link")
                    .setImg("image");
        }

        @Override
        public Flowable<ComicDto> getLatest() {
            return Flowable.just(latest);
        }

        @Override
        public Flowable<ComicDto> getComic(long id) {
            return Flowable.just(dto.setNum((int) id));
        }
    }
}