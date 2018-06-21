package com.hariofspades.dagger2advanced.di;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hariofspades.dagger2advanced.interfaces.RandomUsersApi;
import com.hariofspades.dagger2advanced.network.LoggerModule;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class AppModule {
    @Binds
    public abstract Context bindContext(Application application);

    @Provides
    public static Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    public static LoggerModule provideLogger() {
        return new LoggerModule();
    }

    @Provides
    public static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient()
                .newBuilder()
                .addInterceptor(new LoggerModule().provideLogger())
                .build();
    }

    @Provides
    public static Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://test.mcm.simplicitycrm.com/services/mobileapi/201605/")
                //.baseUrl("https://randomuser.me/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    public static RandomUsersApi getRandomUserService(Retrofit retrofit) {
        return retrofit.create(RandomUsersApi.class);
    }
}
