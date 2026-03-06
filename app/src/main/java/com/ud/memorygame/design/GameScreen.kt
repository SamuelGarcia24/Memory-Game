package com.ud.memorygame.design

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ud.memorygame.viewModel.GameViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    cardCount: Int,
    onNavigateBack: () -> Unit,
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
        20 -> 4  // 4x5 grid
        else -> 3
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header with Moves counter
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Moves: ${viewModel.moves}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // grid layout for the memory cards
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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

            // Game Controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp, start = 16.dp, end = 16.dp, top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { viewModel.resetGame() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Reset")
                }
                Button(
                    onClick = onNavigateBack,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Exit")
                }
            }
        }

        // Victory Dialog
        if (viewModel.isGameOver) {
            VictoryDialog(
                moves = viewModel.moves,
                onReset = { viewModel.resetGame() },
                onExit = onNavigateBack
            )
        }
    }
}

@Composable
fun VictoryDialog(moves: Int, onReset: () -> Unit, onExit: () -> Unit) {
    Dialog(onDismissRequest = {}) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Congratulations!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "You've matched all pairs in $moves moves!",
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onReset,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Play Again")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onExit,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Main Menu")
                }
            }
        }
    }
}
