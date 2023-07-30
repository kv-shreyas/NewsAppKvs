package com.shreyasDev.newsapp.api

import com.shreyasDev.newsapp.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    //static
    companion object {
        //lazy means, we only initialize this once (singleton)
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        //lateinit can only be used with a var property whereas lazy will always be used with val property
        val api by lazy {
            retrofit.create(NewsAPI::class.java)
        }
    }
}