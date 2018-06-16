package com.hariofspades.dagger2advanced.di;

import dagger.Module;
import dagger.Provides;
import presenter.MainPresenter;

@Module
public abstract class MainActivityModule {

    @Provides
    public static MainPresenter getMainPresenter() {
        return new MainPresenter();
    }
}
