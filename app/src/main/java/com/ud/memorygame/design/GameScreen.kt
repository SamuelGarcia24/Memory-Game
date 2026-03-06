package com.ud.memorygame.design

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ud.memorygame.viewModel.GameViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    cardCount: Int,  // receive card count from navigation
    viewModel: GameViewModel = viewModel()
) {
    // initialize game when card count changes
    LaunchedEffect(cardCount) {
        viewModel.initializeGame(cardCount)
    }

    // calculate columns based on card count
    val columns = when (cardCount) {
        12 -> 3  // 4x3 grid
        16 -> 4  // 4x4 grid
        20 -> 4  // 4x5 grid (still 4 columns)
        else -> 3
    }

    // grid layout for the memory cards
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // create cards based on viewModel.cards size
        items(viewModel.cards.size) { index ->
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth()
            ) {
                MemoryCard(
                    imageRes = viewModel.cards[index],
                    isFlipped = viewModel.flippedCards[index],
                    onClick = {
                        viewModel.onCardClicked(index)
                    }
                )
            }
        }
    }
}