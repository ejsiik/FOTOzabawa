package com.example.fotozabawa.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fotozabawa.model.entity.Model

@Dao
interface ModelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSettings(model: Model)

    @Query("SELECT * FROM model LIMIT 1")
    fun getSettings() : Model
}