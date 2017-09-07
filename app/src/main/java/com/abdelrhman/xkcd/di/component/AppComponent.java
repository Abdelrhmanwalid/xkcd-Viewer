package com.abdelrhman.xkcd.di.component;

import android.app.Application;

import com.abdelrhman.xkcd.data.ComicRepository;
import com.abdelrhman.xkcd.data.local.LocalDataManager;
import com.abdelrhman.xkcd.data.remote.RemoteDataManager;
import com.abdelrhman.xkcd.data.remote.XKCDService;
import com.abdelrhman.xkcd.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @Singleton
    XKCDService XKCDService();

    @Singleton
    LocalDataManager localDataManager();

    @Singleton
    RemoteDataManager remoteDataManager();

    @Singleton
    ComicRepository comicRepository();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
