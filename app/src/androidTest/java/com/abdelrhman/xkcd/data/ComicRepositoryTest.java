package com.abdelrhman.xkcd.data;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import com.abdelrhman.xkcd.data.local.ComicDataBase;
import com.abdelrhman.xkcd.data.local.LocalDataSource;
import com.abdelrhman.xkcd.data.remote.RemoteDataSource;
import com.abdelrhman.xkcd.data.remote.XKCDService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class ComicRepositoryTest {

    private ComicRepository repository;
    private LocalDataSource localDataManager;

    @Before()
    public void setup() {
        ComicDataBase dataBase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getTargetContext(),
                ComicDataBase.class)
                .build();
        localDataManager = new LocalDataSource(dataBase.comicDao());
        XKCDService service = new XKCDService("https://xkcd.com/", false);
        RemoteDataSource remoteDataManager = new RemoteDataSource(service);
        repository = new ComicRepository(localDataManager, remoteDataManager);
    }

    @Test
    public void getLatest() {
        long latestId = 1886;
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
    public void getItemOffline(){
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