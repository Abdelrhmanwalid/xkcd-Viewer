package com.abdelrhman.xkcd.data.remote;

import com.abdelrhman.xkcd.data.Comic;
import com.abdelrhman.xkcd.data.IDataManager;
import com.abdelrhman.xkcd.data.local.ComicItem;

import io.reactivex.Flowable;


public class RemoteDataManager implements IDataManager {
    private final XKCDService service;

    public RemoteDataManager(String baseUrl) {
        service = new XKCDService(baseUrl);
    }

    @Override
    public Flowable<Comic> getLatest() {
        return service.getLatest()
                .map(dto -> ((Comic) new ComicItem(dto)));
    }

    @Override
    public Flowable<Comic> getComic(long id) {
        return service.getComic(id)
                .map(dto -> ((Comic) new ComicItem(dto)));
    }

}
