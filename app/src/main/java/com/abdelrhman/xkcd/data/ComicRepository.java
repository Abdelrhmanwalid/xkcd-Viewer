package com.abdelrhman.xkcd.data;

import com.abdelrhman.xkcd.data.local.LocalDataSource;
import com.abdelrhman.xkcd.data.remote.RemoteDataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class ComicRepository implements DataSource {

    private LocalDataSource localDataManager;
    private RemoteDataSource remoteDataManager;

    @Inject
    public ComicRepository(LocalDataSource localDataManager, RemoteDataSource remoteDataManager) {
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
