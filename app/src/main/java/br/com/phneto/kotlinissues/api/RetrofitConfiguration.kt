package br.com.phneto.kotlinissues.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfiguration private constructor(){

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/repos/JetBrains/kotlin/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    companion object {
        private var INSTANCE : RetrofitConfiguration? = null

        fun getInstance() : RetrofitConfiguration {
            if (INSTANCE == null) {
                INSTANCE = RetrofitConfiguration()
            }
            return INSTANCE as RetrofitConfiguration
        }
    }

    fun getGitHubApi(): IGitHubAPI {
        return retrofit.create(IGitHubAPI::class.java)
    }

}