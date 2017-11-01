package com.abdelrhman.xkcd.comic.domain;

import com.abdelrhman.xkcd.UseCase;
import com.abdelrhman.xkcd.data.Comic;
import com.abdelrhman.xkcd.data.ComicRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GetComicUseCase extends UseCase<GetComicUseCase.RequestValue, GetComicUseCase.ResponseValue> {

    private ComicRepository repository;

    @Inject
    public GetComicUseCase(ComicRepository repository) {
        super(Schedulers.io(), AndroidSchedulers.mainThread());
        this.repository = repository;
    }

    @Override
    protected Flowable<ResponseValue> execute(RequestValue requestValue) {
        return repository.getComic(requestValue.getComicId())
                .map(ResponseValue::new);
    }

    public static class RequestValue implements UseCase.RequestValue {
        private long comicId;

        public RequestValue(long comicId) {
            this.comicId = comicId;
        }

        public long getComicId() {
            return comicId;
        }
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
