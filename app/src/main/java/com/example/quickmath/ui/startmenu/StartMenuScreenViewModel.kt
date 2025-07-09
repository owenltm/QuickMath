package com.example.quickmath.ui.startmenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StartMenuScreenViewModel : ViewModel() {
}

class StartMenuScreenViewModelFactory() : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StartMenuScreenViewModel::class.java)) {
            return StartMenuScreenViewModel() as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}