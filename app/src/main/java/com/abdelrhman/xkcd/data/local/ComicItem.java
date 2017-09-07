package com.abdelrhman.xkcd.data.local;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;

import com.abdelrhman.xkcd.data.Comic;
import com.abdelrhman.xkcd.data.remote.dto.ComicDto;

import java.util.Date;
import java.util.GregorianCalendar;

@Entity(tableName = "comics", indices = {@Index("id")}, primaryKeys = "id")
public class ComicItem extends Comic {

    public ComicItem(long id, String imageUrl, String url, String title, String text, Date date) {
        super(id, imageUrl, url, title, text, date);
    }

    public ComicItem(Comic comic){
        super(comic.getId(), comic.getImageUrl(), comic.getUrl(), comic.getTitle(), comic.getText(), comic.getDate());
    }

    public ComicItem(ComicDto dto) {
        super(dto.getNum(), dto.getImg(), dto.getLink(), dto.getTitle(), dto.getAlt(),
                new GregorianCalendar(Integer.parseInt(dto.getYear()),
                        Integer.parseInt(dto.getMonth()) - 1,
                        Integer.parseInt(dto.getDay())).getTime());
    }
}
