package com.abdelrhman.xkcd.data.local;

import com.abdelrhman.xkcd.data.Comic;
import com.abdelrhman.xkcd.data.IDataManager;
import com.abdelrhman.xkcd.data.local.dao.ComicDao;

import io.reactivex.Flowable;


public class LocalDataManager implements IDataManager {
    private final ComicDao comicDao;

    public LocalDataManager(ComicDataBase dataBase) {
        comicDao = dataBase.comicDao();
    }


    @Override
    public Flowable<Comic> getLatest() {
        return comicDao.getLatest().
                map(comicItem -> ((Comic) comicItem));
    }

    @Override
    public Flowable<Comic> getComic(long id) {
        return comicDao.getComic(id)
                .map(comicItem -> ((Comic) comicItem));
    }


    public void add(Comic comic) {
        comicDao.insertAll(new ComicItem(comic));
    }
}
