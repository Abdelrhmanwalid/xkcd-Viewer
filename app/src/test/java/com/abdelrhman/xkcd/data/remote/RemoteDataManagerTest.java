package com.abdelrhman.xkcd.data.remote;

import com.abdelrhman.xkcd.data.Comic;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

public class RemoteDataManagerTest {
    @Inject
    private RemoteDataManager remoteDataManager;

    @Before
    public void setup() {
        XKCDService service = new XKCDService("https://xkcd.com/");
        remoteDataManager = new RemoteDataManager(service);
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