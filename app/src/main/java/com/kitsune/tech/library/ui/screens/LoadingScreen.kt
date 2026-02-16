package com.kitsune.tech.library.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kitsune.tech.library.R
import com.kitsune.tech.library.ui.theme.GoldLibrary
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(
    onLoadingComplete: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2500)
        onLoadingComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(180.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "WELCOME TO THE LIBRARY",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                letterSpacing = 2.sp
            )
        }
    }
}
