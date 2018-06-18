package com.hariofspades.dagger2advanced.di;

import com.hariofspades.dagger2advanced.interfaces.RandomUsersApi;
import com.hariofspades.dagger2advanced.network.NetworkManager;
import com.hariofspades.dagger2advanced.presenter.MainPresenter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public abstract class MainActivityModule {


    @Provides
    public static MainPresenter getMainPresenter(NetworkManager networkManager,
                                                 Retrofit retrofit, RandomUsersApi randomUsersApi) {
        return new MainPresenter(networkManager, retrofit, randomUsersApi);
    }

}
