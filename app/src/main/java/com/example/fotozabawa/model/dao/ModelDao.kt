package com.example.fotozabawa.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fotozabawa.model.entity.Model

@Dao
interface ModelDao {

    @Insert
    fun addSettings(model: Model)

    @Query("SELECT * FROM model")
    fun getAllSettings() : LiveData<Model>
}