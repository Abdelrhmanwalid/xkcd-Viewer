package com.abdelrhman.xkcd.comic;

import com.abdelrhman.xkcd.BasePresenter;
import com.abdelrhman.xkcd.BaseView;

public interface ComicsContract {

    interface View extends BaseView<Presenter> {
        void update(long latestComicId);
    }

    interface Presenter extends BasePresenter {

    }

}
