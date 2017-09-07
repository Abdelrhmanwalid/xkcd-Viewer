package com.abdelrhman.xkcd.data.remote;

import com.abdelrhman.xkcd.BuildConfig;
import com.abdelrhman.xkcd.data.remote.dto.ComicDto;
import com.abdelrhman.xkcd.di.ServiceConfig;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

@Singleton
public class XKCDService {

    private XKCDServiceInterface service;

    @Inject
    public XKCDService(@ServiceConfig String baseURl, @ServiceConfig boolean enableLogging) {
        service = buildService(baseURl, enableLogging);
    }

    public Flowable<ComicDto> getComic(long id) {
        return service.getSpecific(id);
    }

    public Flowable<ComicDto> getLatest() {
        return service.getLatest();
    }

    private XKCDServiceInterface buildService(String baseURl, boolean enableLogging) {
        return getDefaultRetrofit(baseURl, enableLogging).create(XKCDServiceInterface.class);
    }

    private Retrofit getDefaultRetrofit(String baseURl, boolean enableLogging) {
        return new Retrofit.Builder()
                .baseUrl(baseURl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClientBuilder(enableLogging).build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient.Builder getOkHttpClientBuilder(boolean enableLogging) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (enableLogging)
            builder.addInterceptor(new LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.BASIC)
                    .log(Platform.INFO)
                    .request("Request")
                    .response("Response")
                    .build());
        builder.readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS);
        return builder;
    }

    interface XKCDServiceInterface {
        @GET("info.0.json")
        Flowable<ComicDto> getLatest();

        @GET("/{id}/info.0.json")
        Flowable<ComicDto> getSpecific(@Path("id") long id);
    }

}
