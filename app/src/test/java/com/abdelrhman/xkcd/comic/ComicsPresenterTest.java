package com.abdelrhman.xkcd.comic;

import com.abdelrhman.xkcd.comic.domain.GetLatestComicUseCase;
import com.abdelrhman.xkcd.data.Comic;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Subscriber;

import io.reactivex.Flowable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ComicsPresenterTest {

    @Mock
    private GetLatestComicUseCase useCase;
    @Mock
    private ComicsContract.View view;
    private ComicsPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new ComicsPresenter(view, useCase);
    }

    @Test
    public void testSuccess() {
        long comicId = 1001;
        Comic comic = new Comic(comicId, null, null, null, null, null);
        GetLatestComicUseCase.ResponseValue responseValue = new GetLatestComicUseCase.ResponseValue(comic);
        Mockito.when(useCase.run(null)).thenReturn(
                new Flowable<GetLatestComicUseCase.ResponseValue>() {
                    @Override
                    protected void subscribeActual(Subscriber<? super GetLatestComicUseCase.ResponseValue> s) {
                        s.onNext(responseValue);
                    }
                }
        );
        presenter.start();
        Mockito.verify(useCase).run(null);
        Mockito.verify(view).update(1001);
        Mockito.verify(view).setPresenter(presenter);
        ArgumentCaptor<Boolean> captor = ArgumentCaptor.forClass(Boolean.class);
        Mockito.verify(view, Mockito.times(2)).showLoading(captor.capture());
        assertTrue(captor.getAllValues().get(0));
        assertFalse(captor.getAllValues().get(1));
    }


    @Test
    public void testFail() {
        Mockito.when(useCase.run(null)).thenReturn(
                new Flowable<GetLatestComicUseCase.ResponseValue>() {
                    @Override
                    protected void subscribeActual(Subscriber<? super GetLatestComicUseCase.ResponseValue> s) {
                        s.onError(new RuntimeException());
                    }
                }
        );
        presenter.start();
        Mockito.verify(useCase).run(null);
        Mockito.verify(view).showError();
        Mockito.verify(view).setPresenter(presenter);
        ArgumentCaptor<Boolean> captor = ArgumentCaptor.forClass(Boolean.class);
        Mockito.verify(view, Mockito.times(2)).showLoading(captor.capture());
        assertTrue(captor.getAllValues().get(0));
        assertFalse(captor.getAllValues().get(1));
    }

    @Test
    public void testComplete() {
        Mockito.when(useCase.run(null)).thenReturn(
                new Flowable<GetLatestComicUseCase.ResponseValue>() {
                    @Override
                    protected void subscribeActual(Subscriber<? super GetLatestComicUseCase.ResponseValue> s) {
                        s.onComplete();
                    }
                }
        );
        presenter.start();
        Mockito.verify(useCase).run(null);
        Mockito.verify(view).setPresenter(presenter);
        ArgumentCaptor<Boolean> captor = ArgumentCaptor.forClass(Boolean.class);
        Mockito.verify(view, Mockito.times(2)).showLoading(captor.capture());
        assertTrue(captor.getAllValues().get(0));
        assertFalse(captor.getAllValues().get(1));
    }
}