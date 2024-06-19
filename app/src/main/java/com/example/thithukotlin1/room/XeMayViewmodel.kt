package com.example.thithukotlin1.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MayTinhViewmodel(application: Application) : AndroidViewModel(application) {
    private val responsitory: MayTinhResponsitory

    val getAll: LiveData<List<MayTinh>>

    init {
        val mayTinhDao = MayTinhDb.getDatabase(application).mayTinhDao()
        responsitory = MayTinhResponsitory(mayTinhDao)
        getAll = responsitory.getAll.asLiveData()
    }

    fun insert(mayTinh: MayTinh) = viewModelScope.launch {
        responsitory.insert(mayTinh)
    }

    fun update(mayTinh: MayTinh) = viewModelScope.launch {
        responsitory.update(mayTinh)
    }

    fun delete(mayTinh: MayTinh) = viewModelScope.launch {
        responsitory.delete(mayTinh)
    }

}

class MayTinhViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MayTinhViewmodel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MayTinhViewmodel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}