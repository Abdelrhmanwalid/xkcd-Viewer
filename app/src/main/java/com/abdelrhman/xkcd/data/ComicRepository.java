package com.abdelrhman.xkcd.data;

import com.abdelrhman.xkcd.data.local.LocalDataSource;
import com.abdelrhman.xkcd.data.remote.RemoteDataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class ComicRepository implements DataSource {

    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;

    @Inject
    public ComicRepository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private void saveOffline(Comic comic) {
        localDataSource.add(comic);
    }

    @Override
    public Flowable<Comic> getLatest() {
        Flowable<Comic> local = localDataSource.getLatest();
        Flowable<Comic> remote = remoteDataSource.getLatest()
                .doOnNext(this::saveOffline);
        return local.mergeWith(remote);
    }

    @Override
    public Flowable<Comic> getComic(long id) {
        Flowable<Comic> local = localDataSource.getComic(id);
        Flowable<Comic> remote = remoteDataSource.getComic(id)
                .doOnNext(this::saveOffline);
        return local.ambWith(remote);
    }

}
