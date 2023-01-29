package com.example.fotozabawa.model.repository

import com.example.fotozabawa.model.dao.ModelDao
import com.example.fotozabawa.model.entity.Model

class ModelRepository(private val modelDao: ModelDao) {
    fun addModel(model : Model){
        modelDao.addSettings(model)
    }

    fun getAllSettings(): Model{
        return modelDao.getAllSettings()
    }
}