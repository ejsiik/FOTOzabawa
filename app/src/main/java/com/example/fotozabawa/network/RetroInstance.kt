package com.example.fotozabawa.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {

    companion object{
        val baseURL = "http://192.168.0.192:8080"

        fun getRetroInstance(): Retrofit{
            return Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                ))
                .build()
        }
    }
}