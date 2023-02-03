package com.example.fotozabawa.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "model")
data class Model (
    @PrimaryKey val id : Int,
    val time : String,
    val count : String,
    val soundBefore : Int,
    val soundAfter : Int,
    val soundFinish : Int,
    val timeBeforePhotoSound: String
    )