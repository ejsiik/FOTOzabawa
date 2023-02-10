package com.example.fotozabawa.network

import androidx.recyclerview.widget.RecyclerView
import com.example.fotozabawa.model.entity.JustString
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface RetroService {

    @GET("hello")
    suspend fun getHelloFromApi(): Response<JustString>

    @POST("savePhoto")
    suspend fun postSavePhotoOnServer(savePhotoReq: SavePhotoReq): Call<Void>

    @POST("photos")
    suspend fun postCombinePhotos(combinePhotoReq: CombinePhotoReq): Call<CombinePhotoRsp>
}