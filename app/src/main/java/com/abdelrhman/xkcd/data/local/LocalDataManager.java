package com.abdelrhman.xkcd.data.local;

import com.abdelrhman.xkcd.data.Comic;
import com.abdelrhman.xkcd.data.Converters;
import com.abdelrhman.xkcd.data.IDataManager;
import com.abdelrhman.xkcd.data.local.dao.ComicDao;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;


@Singleton
public class LocalDataManager implements IDataManager {
    private final ComicDao comicDao;

    @Inject
    public LocalDataManager(ComicDao comicDao) {
        this.comicDao = comicDao;
    }


    @Override
    public Flowable<Comic> getLatest() {
        return comicDao.getLatest().
                map(comicItem -> (Converters.LocalComicToComic(comicItem)));
    }

    @Override
    public Flowable<Comic> getComic(long id) {
        return comicDao.getComic(id)
                .map(comicItem -> (Converters.LocalComicToComic(comicItem)));
    }


    public void add(Comic comic) {
        comicDao.insertAll(Converters.comicToLocalComic(comic));
    }
}
