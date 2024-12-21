package com.example.ucp2pam_101.ui.theme.view.dosen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam_101.data.entity.Dosen
import com.example.ucp2pam_101.ui.theme.costumwidget.TopAppBar
import com.example.ucp2pam_101.ui.theme.viewmodel.dosen.HomeDosenViewModel
import com.example.ucp2pam_101.ui.theme.viewmodel.dosen.HomeUiState
import com.example.ucp2pam_101.ui.theme.viewmodel.dosen.PenyediaDosenViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeDosenView(
    viewModel: HomeDosenViewModel = viewModel(factory = PenyediaDosenViewModel.Factory),
    onAddDosen: () -> Unit = {},
    onBack: () -> Unit,
    onDetailClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                judul = "List Dosen",
                showBackButton = true,
                onBack = onBack,
                modifier = modifier
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddDosen,
                shape = MaterialTheme.shapes.medium,
                containerColor = Color.Yellow, // Warna kuning
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Dosen",
                    tint = Color.Black
                )
            }
        }
    ) { innerPadding ->
        val homeUiState by viewModel.homeUiState.collectAsState()

        BodyHomeDosenView(
            homeUiState = homeUiState,
            onClick = {
                onDetailClick(it)
                println(it)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyHomeDosenView(
    homeUiState: HomeUiState,
    onClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    when {
        homeUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Yellow) // Warna kuning
            }
        }

        homeUiState.isError -> {
            LaunchedEffect(homeUiState.errorMessage) {
                homeUiState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            }
        }

        homeUiState.listDsn.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada data dosen.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Yellow, // Warna kuning
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            ListDosen(
                listDsn = homeUiState.listDsn,
                onClick = {
                    onClick(it)
                    println(it)
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun ListDosen(
    listDsn: List<Dosen>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        items(
            items = listDsn,
            itemContent = { dsn ->
                CardDosen(
                    dsn = dsn,
                    onClick = { onClick(dsn.nidn) }
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDosen(
    dsn: Dosen,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = Color.Yellow // Setting background color to Yellow
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Person, contentDescription = "", tint = Color.White)
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = dsn.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "", tint = Color.White)
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = dsn.nidn,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Face, contentDescription = "", tint = Color.White)
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = dsn.jeniskelamin,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}

