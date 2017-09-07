package com.abdelrhman.xkcd.data;

import io.reactivex.Flowable;

public interface IDataManager {
    Flowable<Comic> getLatest();

    Flowable<Comic> getComic(long id);
}
