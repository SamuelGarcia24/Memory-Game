package com.ud.memorygame.design

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ud.memorygame.viewModel.GameViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ud.memorygame.R

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,

    // get the viewmodel instance that controls the game logic
    viewModel: GameViewModel = viewModel()
) {

    // grid layout for the memory cards
    LazyVerticalGrid(

        // 4 columns grid
        columns = GridCells.Fixed(3),

        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        // spacing between rows
        verticalArrangement = Arrangement.spacedBy(8.dp),

        // spacing between columns
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        // create 16 cards
        items(12) { index ->

            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth()
            ) {

                // memory card composable
                MemoryCard(

                    // cards
                    imageRes = viewModel.cards[index],

                    // card is flipped if its index matches the flipped index
                    isFlipped = viewModel.flippedCards[index],

                    // notify the viewmodel when the card is clicked
                    onClick = {
                        viewModel.onCardClicked(index)
                    }
                )

            }
        }
    }
}