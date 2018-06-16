package com.hariofspades.dagger2advanced.di;

import dagger.Module;
import dagger.Provides;
import presenter.SecondActivityPresenter;

@Module
public abstract class SecondActivityModule {
    @Provides
    public static SecondActivityPresenter getSecondActivityPresenter() {
        return new SecondActivityPresenter();
    }
}
