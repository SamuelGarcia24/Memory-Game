package com.ud.memorygame.design

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.ud.memorygame.R
import androidx.compose.animation.core.*
import androidx.compose.ui.graphics.*

@Composable
fun MemoryCard(
    imageRes: Int,
    isFlipped: Boolean,
    onClick: () -> Unit
) {
    // Animación de la rotación (0 a 180 grados)
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "cardFlip"
    )

    Card(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                // Aplicamos la rotación en el eje Y
                rotationY = rotation
                cameraDistance = 12f * density // Agrega profundidad 3D
            }
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (rotation <= 90f) {
                // Lado de atrás (oculto)
                Image(
                    painter = painterResource(id = R.drawable.hide),
                    contentDescription = "back",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                // Lado de adelante (imagen real)
                // Invertimos la imagen 180 grados para que no se vea "espejada"
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "front",
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { rotationY = 180f }
                )
            }
        }
    }
}