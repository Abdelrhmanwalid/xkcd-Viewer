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

    private void saveOffline(Comic comic) {
        localDataManager.add(comic);
    }

    @Override
    public Flowable<Comic> getLatest() {
        Flowable<Comic> local = localDataManager.getLatest();
        Flowable<Comic> remote = remoteDataManager.getLatest()
                .doOnNext(this::saveOffline);
        return local.mergeWith(remote);
    }

    @Override
    public Flowable<Comic> getComic(long id) {
        Flowable<Comic> local = localDataManager.getComic(id);
        Flowable<Comic> remote = remoteDataManager.getComic(id)
                .doOnNext(this::saveOffline);
        return local.ambWith(remote);
    }

}
