package com.abdelrhman.xkcd.data;

import com.abdelrhman.xkcd.data.local.LocalComic;
import com.abdelrhman.xkcd.data.remote.dto.ComicDto;

import java.util.Date;
import java.util.GregorianCalendar;

public class Converters {
    public static LocalComic comicDtoToLocalComic(ComicDto dto) {
        int year = Integer.parseInt(dto.getYear());
        int month = Integer.parseInt(dto.getMonth());
        int day = Integer.parseInt(dto.getDay());
        return new LocalComic(
                dto.getNum(), dto.getImg(), dto.getLink(),
                dto.getTitle(), dto.getAlt(), getDate(year, month, day)
        );
    }

    private static Date getDate(int year, int month, int day) {
        return new GregorianCalendar(year,
                month - 1,
                day).getTime();
    }

    public static Comic comicDtoToComic(ComicDto dto){
        int year = Integer.parseInt(dto.getYear());
        int month = Integer.parseInt(dto.getMonth());
        int day = Integer.parseInt(dto.getDay());
        return new Comic(
                dto.getNum(), dto.getImg(), dto.getLink(),
                dto.getTitle(), dto.getAlt(), getDate(year, month, day)
        );
    }

    public static Comic LocalComicToComic(LocalComic localComic) {
        return new Comic(
                localComic.getId(), localComic.getImageUrl(), localComic.getUrl(),
                localComic.getTitle(), localComic.getText(), localComic.getDate()
        );
    }

    public static LocalComic comicToLocalComic(Comic comic) {
        return new LocalComic(
                comic.getId(), comic.getImageUrl(), comic.getUrl(),
                comic.getTitle(), comic.getText(), comic.getDate()
        );
    }

}
