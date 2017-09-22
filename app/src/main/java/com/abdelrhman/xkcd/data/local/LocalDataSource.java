package com.abdelrhman.xkcd.data.local;

import com.abdelrhman.xkcd.data.Comic;
import com.abdelrhman.xkcd.data.Converters;
import com.abdelrhman.xkcd.data.DataSource;
import com.abdelrhman.xkcd.data.local.dao.ComicDao;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;


@Singleton
public class LocalDataSource implements DataSource {
    private final ComicDao comicDao;

    @Inject
    public LocalDataSource(ComicDao comicDao) {
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
