package com.abdelrhman.xkcd;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

public abstract class UseCase<Q extends UseCase.RequestValue, R extends UseCase.ResponseValue> {
    private Scheduler subscribeScheduler;
    private Scheduler observeScheduler;

    public UseCase(Scheduler subscribeScheduler, Scheduler observeScheduler) {
        this.subscribeScheduler = subscribeScheduler;
        this.observeScheduler = observeScheduler;
    }

    public Flowable<R> run(Q requestValue) {
        return execute(requestValue)
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler);
    }

    protected abstract Flowable<R> execute(Q requestValue);

    public interface RequestValue {
    }

    public interface ResponseValue {
    }
}
