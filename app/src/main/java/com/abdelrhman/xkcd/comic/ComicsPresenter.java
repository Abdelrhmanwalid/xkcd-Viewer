package com.abdelrhman.xkcd.comic;

import com.abdelrhman.xkcd.comic.domain.GetLatestComicUseCase;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import javax.inject.Inject;

public class ComicsPresenter implements ComicsContract.Presenter {

    private GetLatestComicUseCase useCase;
    private Subscription subscription;
    private ComicsContract.View view;

    @Inject
    public ComicsPresenter(ComicsContract.View view, GetLatestComicUseCase useCase) {
        this.useCase = useCase;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {

        stop();
        view.showLoading(true);
        useCase.run(null)
                .subscribe(new GetLatestComicSubscriber());
    }

    @Override
    public void stop() {
        if (subscription != null) {
            subscription.cancel();
        }
    }

    private class GetLatestComicSubscriber implements Subscriber<GetLatestComicUseCase.ResponseValue> {

        @Override
        public void onSubscribe(Subscription s) {
            subscription = s;
        }

        @Override
        public void onNext(GetLatestComicUseCase.ResponseValue responseValue) {
            view.showLoading(false);
            view.update(responseValue.getComic().getId());
        }

        @Override
        public void onError(Throwable t) {
            view.showLoading(false);
            view.showError();
        }

        @Override
        public void onComplete() {
            view.showLoading(false);
        }
    }
}
