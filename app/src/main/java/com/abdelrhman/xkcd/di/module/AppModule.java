package com.abdelrhman.xkcd.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.abdelrhman.xkcd.data.local.ComicDataBase;
import com.abdelrhman.xkcd.data.local.dao.ComicDao;
import com.abdelrhman.xkcd.di.ServiceConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context providesApplication(Application application) {
        return application;
    }

    @Provides
    @ServiceConfig
    static String baseUrl() {
        return "https://xkcd.com/";
    }

    @Provides
    ComicDao comicDao(ComicDataBase dataBase) {
        return dataBase.comicDao();
    }

    @Provides
    @Singleton
    ComicDataBase comicDataBase(Application application) {
        return Room.databaseBuilder(application.getApplicationContext(),
                ComicDataBase.class, "comic_db").build();
    }
}
