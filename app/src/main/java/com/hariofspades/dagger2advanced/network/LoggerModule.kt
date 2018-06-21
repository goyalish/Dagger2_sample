package com.hariofspades.dagger2advanced.network

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

/**
 * Created by sandeepsingh on 6/19/18.
 */
class LoggerModule @Inject constructor() {

    fun provideLogger(): HttpLoggingInterceptor {
        Log.i("Logger Module ", "Is being called")
        val logging = HttpLoggingInterceptor {
            Log.i("Dagger2Advanced", it)
        }
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging;
    }
}