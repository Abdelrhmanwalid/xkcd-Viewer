package com.abdelrhman.xkcd.data;

import com.abdelrhman.xkcd.data.local.LocalDataManager;
import com.abdelrhman.xkcd.data.remote.RemoteDataManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class ComicRepository implements IDataManager {

    private LocalDataManager localDataManager;
    private RemoteDataManager remoteDataManager;

    @Inject
    public ComicRepository(LocalDataManager localDataManager, RemoteDataManager remoteDataManager) {
        this.localDataManager = localDataManager;
        this.remoteDataManager = remoteDataManager;
    }

    @Override
    public Flowable<Comic> getLatest() {
        return localDataManager.getLatest()
                .mergeWith(remoteDataManager.getLatest().doOnNext(localDataManager::add));
    }

    @Override
    public Flowable<Comic> getComic(long id) {
        return localDataManager.getComic(id)
                .ambWith(remoteDataManager.getComic(id).doOnNext(localDataManager::add));
    }

}
