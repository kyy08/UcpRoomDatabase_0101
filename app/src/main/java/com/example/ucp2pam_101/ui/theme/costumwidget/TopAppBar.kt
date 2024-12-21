package com.example.ucp2pam_101.ui.theme.costumwidget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@Composable
fun TopAppBar(
    onBack: () -> Unit,
    showBackButton: Boolean = true,
    judul: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF006400), // Warna hijau tua sebagai latar belakang
    contentColor: Color = Color.Black // Warna teks dan ikon
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        if (showBackButton) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = contentColor // Set icon color to match text
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        // Centered title text
        Text(
            text = judul,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = contentColor,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
