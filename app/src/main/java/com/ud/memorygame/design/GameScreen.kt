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
import com.ud.memorygame.utils.MusicManager
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    cardCount: Int,
    onNavigateBack: () -> Unit,
    musicManager: MusicManager,
    viewModel: GameViewModel = viewModel()
) {
    // start gameplay music when screen appears
    LaunchedEffect(Unit) {
        musicManager.startGameplayMusic()
    }

    // stop music when screen disappears (menu/levels will start their own)
    DisposableEffect(Unit) {
        onDispose {
            musicManager.stopMusic()
        }
    }

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
            // Header with Moves, Timer and Pairs counter
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Time: ${formatTime(viewModel.secondsElapsed)}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Moves: ${viewModel.moves}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Pairs: ${viewModel.pairsFound} / ${cardCount / 2}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
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
                    onClick = {
                        viewModel.resetGame()
                        // music continues playing (same screen)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Reset")
                }
                Button(
                    onClick = {
                        // JUST NAVIGATE BACK - music handled by menu/levels screens
                        onNavigateBack()
                    },
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
                time = viewModel.secondsElapsed,
                onReset = {
                    viewModel.resetGame()
                    // music continues playing (same screen)
                },
                onExit = {
                    // JUST NAVIGATE BACK - music handled by menu/levels screens
                    onNavigateBack()
                }
            )
        }
    }
}

@Composable
fun VictoryDialog(moves: Int, time: Int, onReset: () -> Unit, onExit: () -> Unit) {
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
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Total Time: ${formatTime(time)}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Total Moves: $moves",
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

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
}