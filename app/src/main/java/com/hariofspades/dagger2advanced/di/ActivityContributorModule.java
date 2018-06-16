package com.hariofspades.dagger2advanced.di;

import com.hariofspades.dagger2advanced.MainActivity;
import com.hariofspades.dagger2advanced.view.SecondActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityContributorModule {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = SecondActivityModule.class)
    abstract SecondActivity contributeSecondActivity();

}
