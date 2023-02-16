package com.example.fotozabawa.network

import com.google.gson.annotations.SerializedName

data class CombinePhotoReq(@SerializedName("photoNames") val photoNames: List<String>,
                           @SerializedName("baner") val baner: Int,
                           @SerializedName("filter") val filter: Int)