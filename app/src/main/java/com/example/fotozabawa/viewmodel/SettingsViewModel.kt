package com.example.fotozabawa.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.fotozabawa.model.database.FotoDatabase
import com.example.fotozabawa.model.entity.Model
import com.example.fotozabawa.model.repository.ModelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val modelRepository: ModelRepository

    init {
        val database = FotoDatabase.getDatabase(application)
        modelRepository = ModelRepository(database.modelDao())
    }

    fun addSettings(model: Model) {
        viewModelScope.launch(Dispatchers.IO) {
            modelRepository.addModel(model)
        }
    }

    fun getSettings(): LiveData<Model> {
        return modelRepository.getAllSettings()
    }

}