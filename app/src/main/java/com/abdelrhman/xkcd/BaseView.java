package com.abdelrhman.xkcd;

public interface BaseView<T> {
    void setPresenter(T t);

    void showError();

    void showLoading(boolean isActive);
}
