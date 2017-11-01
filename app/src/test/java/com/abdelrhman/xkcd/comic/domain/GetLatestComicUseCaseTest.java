package com.abdelrhman.xkcd.comic.domain;

import com.abdelrhman.xkcd.data.Comic;
import com.abdelrhman.xkcd.data.ComicRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Flowable;

import static org.junit.Assert.*;

public class GetLatestComicUseCaseTest {
    @Mock
    private ComicRepository repository;
    private GetLatestComicUseCase useCase;

    @Rule
    public RxSchedulersOverrideRule rule = new RxSchedulersOverrideRule();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        useCase = new GetLatestComicUseCase(repository);
    }

    @Test
    public void testUseCase(){
        Comic comic = new Comic(1000, null, null, null, null, null);
        Mockito.when(repository.getLatest()).thenReturn(Flowable.just(comic));
        GetLatestComicUseCase.RequestValue requestValue = new GetLatestComicUseCase.RequestValue();
        Comic comic1 = useCase.run(requestValue).blockingFirst().getComic();
        Mockito.verify(repository).getLatest();
        assertNotNull(comic1);
        assertEquals(comic1.getId(), comic.getId());
    }

}