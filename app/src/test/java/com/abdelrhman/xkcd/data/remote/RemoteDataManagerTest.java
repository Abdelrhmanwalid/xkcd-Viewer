package com.abdelrhman.xkcd.data.remote;

import com.abdelrhman.xkcd.data.Comic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RemoteDataManagerTest {
    private RemoteDataManager remoteDataManager;

    @Before
    public void setup() {
        remoteDataManager = new RemoteDataManager("https://xkcd.com/");
    }

    @Test
    public void testLatest() {
        Comic comic = remoteDataManager.getLatest().blockingFirst();
        assertEquals(1886, comic.getId());
    }

    @Test
    public void testItem() {
        long id = 1885;
        Comic comic = remoteDataManager.getComic(id).blockingFirst();
        assertEquals(id, comic.getId());
    }
}