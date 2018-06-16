package com.hariofspades.dagger2advanced.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hariofspades.dagger2advanced.R;
import com.hariofspades.dagger2advanced.interfaces.RandomUsersApi;
import com.hariofspades.dagger2advanced.security.AESAlgorithm;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import presenter.DummyDependency;
import presenter.MainPresenter;
import presenter.SecondActivityPresenter;
import retrofit2.Retrofit;

public class SecondActivity extends DaggerAppCompatActivity {
    @Inject
    Retrofit retrofit;

    @Inject
    SecondActivityPresenter mSecondActivityPresenter;

    @Inject
    DummyDependency mDummyDependency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        retrofit.create(RandomUsersApi.class);
        //Toast.makeText(this,mSecondActivityPresenter.getName(),Toast.LENGTH_LONG).show();
        //Toast.makeText(this,mDummyDependency.getName(),Toast.LENGTH_LONG).show();
        Toast.makeText(this,mDummyDependency.getName(),Toast.LENGTH_LONG).show();
        try {
            String enc = AESAlgorithm.encrypt("Hello");
            Log.i(SecondActivity.class.getSimpleName(), enc);
        }catch (Exception e) {
            Log.e(SecondActivity.class.getSimpleName(), e.getMessage());
        }
    }
}
