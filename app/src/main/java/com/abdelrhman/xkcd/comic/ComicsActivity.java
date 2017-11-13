package com.abdelrhman.xkcd.comic;

import android.support.v7.app.AppCompatActivity;


public class ComicsActivity extends AppCompatActivity implements ComicsContract.View {

    private ComicsContract.Presenter presenter;


    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    protected void onStop() {
        presenter.stop();
        super.onStop();
    }

    @Override
    public void setPresenter(ComicsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading(boolean isActive) {

    }

    @Override
    public void update(long latestComicId) {

    }
}
