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

public class GetLatestComicUseCaseTest {
    @Mock
    private ComicRepository repository;
    private GetLatestComicUseCase useCase;

    @Rule
    public RxSchedulersOverrideRule rule = new RxSchedulersOverrideRule();
    private Comic comic;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        comic = new Comic(1000, null, null, null, null, null);
        useCase = new GetLatestComicUseCase(repository);
    }

    @Test
    public void testUseCase(){
        Mockito.when(repository.getLatest()).thenReturn(Flowable.just(comic));
        GetLatestComicUseCase.RequestValue requestValue = new GetLatestComicUseCase.RequestValue();
        Comic comic1 = useCase.run(requestValue).blockingFirst().getComic();
        Mockito.verify(repository).getLatest();
        assertNotNull(comic1);
        assertEquals(comic1, comic);
    }

    @Test
    public void testSuccessSubscription(){
        Mockito.when(repository.getLatest()).thenReturn(
                new Flowable<Comic>() {
                    @Override
                    protected void subscribeActual(Subscriber<? super Comic> s) {
                        s.onNext(comic);
                    }
                }
        );
        GetLatestComicUseCase.ResponseValue responseValue = useCase.run(null).blockingFirst();
        assertNotNull(responseValue);
        assertNotNull(responseValue.getComic());
        assertEquals(comic, responseValue.getComic());
    }

    @Test(expected = RuntimeException.class)
    public void testFail(){
        Mockito.when(repository.getLatest()).thenReturn(
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