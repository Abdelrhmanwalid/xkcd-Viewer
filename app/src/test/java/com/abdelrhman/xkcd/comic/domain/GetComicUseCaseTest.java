package com.abdelrhman.xkcd.comic.domain;

import com.abdelrhman.xkcd.data.Comic;
import com.abdelrhman.xkcd.data.ComicRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Subscriber;

import io.reactivex.Flowable;

import static org.junit.Assert.*;

public class GetComicUseCaseTest {
    @Mock
    private ComicRepository repository;
    private GetComicUseCase useCase;

    @Rule
    public RxSchedulersOverrideRule rule = new RxSchedulersOverrideRule();
    private Comic comic;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        comic = new Comic(1000, null, null, null, null, null);
        useCase = new GetComicUseCase(repository);
    }

    @Test
    public void testUseCase(){
        Mockito.when(repository.getComic(1000)).thenReturn(Flowable.just(comic));
        GetComicUseCase.RequestValue requestValue = new GetComicUseCase.RequestValue(1000);
        Comic comic1 = useCase.run(requestValue).blockingFirst().getComic();
        Mockito.verify(repository).getComic(1000);
        assertNotNull(comic1);
        assertEquals(comic1.getId(), comic.getId());
    }

    @Test
    public void testSuccessSubscription(){
        Mockito.when(repository.getComic(1000)).thenReturn(
                new Flowable<Comic>() {
                    @Override
                    protected void subscribeActual(Subscriber<? super Comic> s) {
                        s.onNext(comic);
                    }
                }
        );
        GetComicUseCase.RequestValue requestValue = new GetComicUseCase.RequestValue(1000);
        GetComicUseCase.ResponseValue responseValue = useCase.run(requestValue).blockingFirst();
        assertNotNull(responseValue);
        assertNotNull(responseValue.getComic());
        assertEquals(comic, responseValue.getComic());
    }

    @Test(expected = RuntimeException.class)
    public void testFail(){
        Mockito.when(repository.getComic(1000)).thenReturn(
                new Flowable<Comic>() {
                    @Override
                    protected void subscribeActual(Subscriber<? super Comic> s) {
                        s.onError(new RuntimeException());
                    }
                }
        );
        useCase.run(null).blockingFirst();
    }
}