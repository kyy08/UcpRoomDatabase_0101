package com.example.ucp2pam_101.ui.theme.view.matakuliah

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam_101.ui.theme.costumwidget.TopAppBar
import com.example.ucp2pam_101.ui.theme.viewmodel.dosen.HomeDosenViewModel
import com.example.ucp2pam_101.ui.theme.viewmodel.dosen.PenyediaDosenViewModel
import com.example.ucp2pam_101.ui.theme.viewmodel.matakuliah.PenyediaMatakuliahViewModel
import com.example.ucp2pam_101.ui.theme.viewmodel.matakuliah.UpdateMatakuliahViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UpdateMatakuliahView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateMatakuliahViewModel = viewModel(factory = PenyediaMatakuliahViewModel.Factory),
    viewModelDsn: HomeDosenViewModel = viewModel(factory = PenyediaDosenViewModel.Factory),

    ) {
    val uiState = viewModel.updateUIState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val DsnList by viewModelDsn.homeUiState.collectAsState()

    // Menangani pesan snackbar ketika ada perubahan pada snackBarMessage
    LaunchedEffect(uiState.snackBarMessage) {
        println("LaunchedEffect triggered")
        uiState.snackBarMessage?.let { message ->
            println("Snackbar message received: $message")
            coroutineScope.launch {
                println("Launching coroutine for snacbar")
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetSnackBarMessage() // Reset setelah snackbar ditampilkan
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            // Menggunakan TopAppBar kustom
            TopAppBar(
                judul = "Edit Matakuliah",
                showBackButton = true,
                onBack = onBack,
                modifier = Modifier
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            InsertBodyMatakuliah(
                uiState = uiState,
                listDosen = DsnList,
                onValueChange = { updatedEvent ->
                    viewModel.updateState(updatedEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFields()) {
                            viewModel.updateData()
                            delay(600)
                            withContext(Dispatchers.Main) {
                                onNavigate() // Navigasi setelah update selesai
                            }
                        }
                    }
                },
            )
        }
    }
}