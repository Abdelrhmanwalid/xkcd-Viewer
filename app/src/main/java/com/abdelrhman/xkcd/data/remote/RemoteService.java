package com.abdelrhman.xkcd.data.remote;

import com.abdelrhman.xkcd.data.remote.dto.ComicDto;

import io.reactivex.Flowable;

public interface RemoteService {
    Flowable<ComicDto> getLatest();

    Flowable<ComicDto> getComic(long id);
}
