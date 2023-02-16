package com.example.fotozabawa.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetroService {

    @POST("savePhoto")
    fun postSavePhotoOnServer(@Body savePhotoReq: SavePhotoReq): Call<Unit>

    @POST("photos")
    fun postCombinePhotos(@Body combinePhotoReq: CombinePhotoReq): Call<CombinePhotoRsp>
}