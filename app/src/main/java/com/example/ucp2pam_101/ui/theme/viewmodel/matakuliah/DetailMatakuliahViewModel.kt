package com.example.ucp2pam_101.ui.theme.viewmodel.matakuliah

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam_101.data.entity.Matakuliah
import com.example.ucp2pam_101.repository.RepositoryMatakuliah
import com.example.ucp2pam_101.ui.theme.navigation.DestinasiMatakuliahDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailMatakuliahViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMatakuliah: RepositoryMatakuliah,
) : ViewModel() {

    // Mendapatkan KODE dari SavedStateHandle
    private val _kode: String = checkNotNull(savedStateHandle[DestinasiMatakuliahDetail.KODE])

    // Mendapatkan detail mahasiswa dan mengelola UI state
    val detailMatakuliahUiState: StateFlow<DetailMatakuliahUiState> = repositoryMatakuliah.getMatakuliah(_kode)
        .filterNotNull() // Pastikan data tidak null
        .map {
            DetailMatakuliahUiState(
                detailMatakuliahUiEvent = it.toDetailMatakuliahUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailMatakuliahUiState(isLoading = true)) // Set status loading saat mulai
            delay(600) // Simulasi delay jika diperlukan
        }
        .catch {
            emit(
                DetailMatakuliahUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailMatakuliahUiState(
                isLoading = true,
            ),
        )

    // Fungsi untuk menghapus mahasiswa
    fun deleteMatakuliah() {
        detailMatakuliahUiState.value.detailMatakuliahUiEvent.toMatakuliahEntity().let {
            viewModelScope.launch {
                repositoryMatakuliah.deleteMtk(it)
            }
        }
    }
}






data class DetailMatakuliahUiState(
    val detailMatakuliahUiEvent: MatakuliahEvent = MatakuliahEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailMatakuliahUiEvent == MatakuliahEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailMatakuliahUiEvent != MatakuliahEvent()
}

// Fungsi ekstensi untuk mengubah Mahasiswa menjadi MahasiswaEvent
fun Matakuliah.toDetailMatakuliahUiEvent(): MatakuliahEvent {
    return MatakuliahEvent(
        kode = kode,
        nama = nama,
        sks = sks,
        semester = semester,
        jenis = jenis,
        dosenpengampu = dosenpengampu
    )
}