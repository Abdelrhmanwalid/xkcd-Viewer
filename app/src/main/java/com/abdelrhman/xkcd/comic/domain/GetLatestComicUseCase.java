package com.abdelrhman.xkcd.comic.domain;

import com.abdelrhman.xkcd.UseCase;
import com.abdelrhman.xkcd.data.Comic;
import com.abdelrhman.xkcd.data.ComicRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class GetLatestComicUseCase extends UseCase<GetLatestComicUseCase.RequestValue, GetLatestComicUseCase.ResponseValue> {

    private ComicRepository repository;

    @Inject
    public GetLatestComicUseCase(ComicRepository repository) {
        super(Schedulers.io(), AndroidSchedulers.mainThread());
        this.repository = repository;
    }

    @Override
    protected Flowable<ResponseValue> execute(RequestValue requestValue) {
        return repository.getLatest().map(ResponseValue::new);
    }

    public static class RequestValue implements UseCase.RequestValue {

    }

    public class ResponseValue implements UseCase.ResponseValue {
        private Comic comic;

        public ResponseValue(Comic comic) {
            this.comic = comic;
        }

        public Comic getComic() {
            return comic;
        }
    }

}
