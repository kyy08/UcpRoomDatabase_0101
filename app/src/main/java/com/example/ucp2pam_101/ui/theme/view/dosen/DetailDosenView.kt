package com.example.ucp2pam_101.ui.theme.view.dosen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam_101.data.entity.Dosen
import com.example.ucp2pam_101.ui.theme.costumwidget.TopAppBar
import com.example.ucp2pam_101.ui.theme.viewmodel.dosen.DetailDosenViewModel
import com.example.ucp2pam_101.ui.theme.viewmodel.dosen.DetailUiState
import com.example.ucp2pam_101.ui.theme.viewmodel.dosen.PenyediaDosenViewModel
import com.example.ucp2pam_101.ui.theme.viewmodel.dosen.toDosenEntity
import com.example.ucp2pam_101.ui.theme.viewmodel.matakuliah.PenyediaMatakuliahViewModel

@Composable
fun DetailDosenView(
    modifier: Modifier = Modifier,
    viewModel: DetailDosenViewModel = viewModel(factory = PenyediaDosenViewModel.Factory),
    onBack: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Detail Dosen",
                showBackButton = true,
                onBack = onBack,
                modifier = modifier
            )
        },
    ) { innerPadding ->
        val detailDosenUiState by viewModel.detailUiState.collectAsState()

        BodyDetailDosen(
            modifier = Modifier.padding(innerPadding),
            detailDosenUiState = detailDosenUiState,
        )
    }
}

@Composable
fun BodyDetailDosen(
    modifier: Modifier = Modifier,
    detailDosenUiState: DetailUiState = DetailUiState(),
) {
    when {
        detailDosenUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        detailDosenUiState.isUiEventNotEmpty -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailDosen(
                    dosen = detailDosenUiState.detailUiEvent.toDosenEntity(),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }

        detailDosenUiState.isUiEventEmpty -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data tidak ditemukan",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ItemDetailDosen(
    modifier: Modifier = Modifier,
    dosen: Dosen
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Yellow, // Set the card background color to Yellow
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailDosen(judul = "NIDN", isinya = dosen.nidn)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailDosen(judul = "Nama", isinya = dosen.nama)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailDosen(judul = "Jenis Kelamin", isinya = dosen.jeniskelamin)
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun ComponentDetailDosen(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        Text(
            text = "$judul :",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 2.dp)
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
        )
    }
}


