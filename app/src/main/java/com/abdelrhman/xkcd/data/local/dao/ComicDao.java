package com.abdelrhman.xkcd.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.abdelrhman.xkcd.data.local.LocalComic;

import io.reactivex.Flowable;

@Dao
public interface ComicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(LocalComic... comic);

    @Query("SELECT * FROM comics WHERE id = :id")
    Flowable<LocalComic> getComic(long id);

    @Query("SELECT * FROM comics ORDER BY id DESC LIMIT 1")
    Flowable<LocalComic> getLatest();
}
