package com.abdelrhman.xkcd.comic.domain;

import android.support.annotation.NonNull;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
/*
https://github.com/ReactiveX/RxAndroid/issues/238
https://github.com/ribot/android-boilerplate/blob/master/app/src/test/java/uk/co/ribot/androidboilerplate/util/RxSchedulersOverrideRule.java
  */
public class RxSchedulersOverrideRule implements TestRule {

    private final Function<Callable<Scheduler>, Scheduler> mRxAndroidSchedulersHook =
            new Function<Callable<Scheduler>, Scheduler>() {
                @Override
                public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable)
                        throws Exception {
                    return getScheduler();
                }
            };

    private final Function<Scheduler, Scheduler> mRxJavaImmediateScheduler =
            new Function<Scheduler, Scheduler>() {
                @Override
                public Scheduler apply(@NonNull Scheduler scheduler) throws Exception {
                    return getScheduler();
                }
            };

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxAndroidPlugins.reset();
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(mRxAndroidSchedulersHook);

                RxJavaPlugins.reset();
                RxJavaPlugins.setIoSchedulerHandler(mRxJavaImmediateScheduler);
                RxJavaPlugins.setNewThreadSchedulerHandler(mRxJavaImmediateScheduler);

                base.evaluate();

                RxAndroidPlugins.reset();
                RxJavaPlugins.reset();
            }
        };
    }

    public Scheduler getScheduler() {
        return Schedulers.trampoline();
    }

}

