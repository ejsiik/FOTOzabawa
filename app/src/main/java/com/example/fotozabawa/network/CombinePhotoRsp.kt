package com.example.fotozabawa.network

import com.google.gson.annotations.SerializedName

data class CombinePhotoRsp(@SerializedName("combinedPhoto") val combinedPhoto: String,
@SerializedName("combinedName") val combinedName: String, @SerializedName("status") val status: String)