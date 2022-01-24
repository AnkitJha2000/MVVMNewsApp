package com.androiddevs.mvvmnewsapp.api

import com.androiddevs.mvvmnewsapp.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{
        // lazy keyword means that this retrofit will be created only once
        // and will be used by everyone

        private val retrofit by lazy {
            // interceptor is used to intercept the network in between
            // this level will set which part will be intercepted
            // this time body will be shown
            // then we make a client that will take the interceptor

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        // this the api we will use everytime to call api
        //
        val api by lazy {
            retrofit.create(NewsAPI::class.java)
        }

    }

}