package com.abdelrhman.xkcd.data.remote;

import com.abdelrhman.xkcd.data.Comic;
import com.abdelrhman.xkcd.data.Converters;
import com.abdelrhman.xkcd.data.IDataManager;

import javax.inject.Inject;

import io.reactivex.Flowable;


public class RemoteDataManager implements IDataManager {
    private final XKCDService service;

    @Inject
    public RemoteDataManager(XKCDService service) {
        this.service = service;
    }

    @Override
    public Flowable<Comic> getLatest() {
        return service.getLatest()
                .map(dto -> (Converters.comicDtoToComic(dto)));
    }

    @Override
    public Flowable<Comic> getComic(long id) {
        return service.getComic(id)
                .map(dto -> (Converters.comicDtoToComic(dto)));
    }

}
