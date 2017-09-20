package com.abdelrhman.xkcd.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.abdelrhman.xkcd.data.local.converters.Converters;
import com.abdelrhman.xkcd.data.local.dao.ComicDao;

@Database(entities = LocalComic.class, version = 1)
@TypeConverters(Converters.class)
public abstract class ComicDataBase extends RoomDatabase {
    public abstract ComicDao comicDao();
}
