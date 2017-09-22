package com.abdelrhman.xkcd.data.local;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import com.abdelrhman.xkcd.data.Comic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class LocalDataManagerTest {

    private LocalDataSource localDataManager;
    private int id;

    @Before
    public void setup(){
        ComicDataBase dataBase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getTargetContext(),
                ComicDataBase.class)
                .build();
        localDataManager = new LocalDataSource(dataBase.comicDao());
    }

    @Test
    public void getLatest(){
        Comic[] comics = new Comic[5];
        for (int i = 0; i < comics.length; i++) {
            comics[i] = new Comic(1234 - i, "Test", "Test", "Test", "Test", new Date());
            localDataManager.add(comics[i]);
        }
        Comic comic = localDataManager.getLatest().blockingFirst();
        Assert.assertEquals(comics[0].getId(), comic.getId());
        Assert.assertEquals(comics[0].getDate(), comic.getDate());
        Assert.assertEquals(comics[0].getImageUrl(), comic.getImageUrl());
        Assert.assertEquals(comics[0].getText(), comic.getText());
        Assert.assertEquals(comics[0].getTitle(), comic.getTitle());
    }

    @Test
    public void getItem(){
        id = 11;
        Comic comic = new Comic(id, "Test", "Test", "Test", "Test", new Date());
        localDataManager.add(comic);
        Comic comic1 = localDataManager.getComic(id).blockingFirst();
        Assert.assertEquals(comic.getId(), comic1.getId());
        Assert.assertEquals(comic.getDate(), comic1.getDate());
        Assert.assertEquals(comic.getImageUrl(), comic1.getImageUrl());
        Assert.assertEquals(comic.getText(), comic1.getText());
        Assert.assertEquals(comic.getTitle(), comic1.getTitle());
    }

}