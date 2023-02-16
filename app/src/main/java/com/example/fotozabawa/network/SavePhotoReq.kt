package com.example.fotozabawa.network

import com.example.fotozabawa.model.entity.PhotoEntity
import com.google.gson.annotations.SerializedName

data class SavePhotoReq(@SerializedName("photo")val photo: PhotoEntity )
