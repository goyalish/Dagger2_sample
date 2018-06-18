package com.hariofspades.dagger2advanced;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.hariofspades.dagger2advanced.adapter.RandomUserAdapter;
import com.hariofspades.dagger2advanced.presenter.MainPresenter;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

public class MainActivity extends DaggerAppCompatActivity {

    @Inject
    MainPresenter mMainPresenter;

    RecyclerView recyclerView;
    RandomUserAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        Timber.plant(new Timber.DebugTree());

        HttpLoggingInterceptor httpLoggingInterceptor = new
                HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                Timber.i(message);
            }
        });

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        populateUsers();
        Toast.makeText(this, mMainPresenter.sayHello(), Toast.LENGTH_LONG).show();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void populateUsers() {
        mMainPresenter.callJoinApi("test.user2@simplicitycrm.com", "P@ssW0rd");

        /*Call<RandomUsers> randomUsersCall = getRandomUserService().getRandomUsers(20);
        randomUsersCall.enqueue(new Callback<RandomUsers>() {
            @Override
            public void onResponse(Call<RandomUsers> call, @NonNull Response<RandomUsers> response) {
                if(response.isSuccessful()) {
                    mAdapter = new RandomUserAdapter();
                    mAdapter.setItems(response.body().getResults());
                    recyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<RandomUsers> call, Throwable t) {
                Timber.i(t.getMessage());
            }
        });*/
    }


}
