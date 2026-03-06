package com.ud.memorygame.design

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.ud.memorygame.R

@Composable
fun MemoryCard(

    // image that will be shown when the card is flipped
    imageRes: Int,

    // indicates if the card is currently flipped
    isFlipped: Boolean,

    // function executed when the user taps the card
    onClick: () -> Unit
) {

    Card(

        modifier = Modifier
            .fillMaxSize()

            // make the card clickable
            .clickable {
                onClick()
            }

    ) {

        Box(

            modifier = Modifier.fillMaxSize(),

            // center the image inside the card
            contentAlignment = Alignment.Center

        ) {

            // decide which image to show
            // if the card is flipped show the card image
            // otherwise show the back image
            val imageToShow =
                if (isFlipped) imageRes
                else R.drawable.hide

            Image(

                painter = painterResource(id = imageToShow),

                contentDescription = "card",

                modifier = Modifier.fillMaxSize()

            )
        }
    }
}