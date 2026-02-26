package com.ud.memorygame.design
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.ud.memorygame.R

@Composable
fun MemoryCard(imageRes : Int,
               isFlipped: Boolean,
               onClick: () -> Unit
    ) {

    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onClick()
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
                contentAlignment = Alignment.Center
        ) {
            val imageToShow =
                if (isFlipped) imageRes
                else R.drawable.hide
            Image(
                painter = painterResource(id = imageToShow),
                contentDescription = "Card",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}