package com.example.ucp2pam_101.ui.theme.viewmodel.dosen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam_101.data.entity.Dosen
import com.example.ucp2pam_101.repository.RepositoryDosen
import com.example.ucp2pam_101.ui.theme.navigation.DestinasiDosenDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class DetailDosenViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDosen: RepositoryDosen,
): ViewModel(){
    // Mendapatkan NIDN dari SavedStateHandle
    private val _nidn: String = checkNotNull(savedStateHandle[DestinasiDosenDetail.NIDN])

    // Mendapatkan detail mahasiswa dan mengelola UI state
    val detailUiState: StateFlow<DetailUiState> = repositoryDosen.getDosen(_nidn)
        .filterNotNull() // Pastikan data tidak null
        .map {
            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true)) // Set status loading saat mulai
            delay(600) // Simulasi delay jika diperlukan
        }
        .catch {
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiState(
                isLoading = true,
            ),
        )
}

// Data class untuk UI State
data class DetailUiState(
    val detailUiEvent: DosenEvent = DosenEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == DosenEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != DosenEvent()
}

// Fungsi ekstensi untuk mengubah Mahasiswa menjadi MahasiswaEvent
fun Dosen.toDetailUiEvent(): DosenEvent {
    return DosenEvent(
        nidn = nidn,
        nama = nama,
        jenisKelamin = jeniskelamin
    )
}