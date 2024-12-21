package com.example.ucp2pam_101.ui.theme.viewmodel.matakuliah

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2pam_101.KrsApp

object PenyediaMatakuliahViewModel {

    val Factory = viewModelFactory {
        initializer {
            MatakuliahViewModel(
                KrsApp().containerApp.repositoryMatakuliah
            )
        }
        initializer {
            HomeMatakuliahViewModel(
                KrsApp().containerApp.repositoryMatakuliah,
            )
        }
        initializer {
            DetailMatakuliahViewModel(
                createSavedStateHandle(),
                KrsApp().containerApp.repositoryMatakuliah,
            )
        }
        initializer {
            UpdateMatakuliahViewModel(
                createSavedStateHandle(),
                KrsApp().containerApp.repositoryMatakuliah,
            )
        }
    }
}

fun CreationExtras.KrsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)