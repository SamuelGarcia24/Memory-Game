package com.ud.memorygame.design
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ud.memorygame.R

@Composable
fun GameScreen(
    modifier: Modifier = Modifier
) {

    var flippedIndex by remember { mutableStateOf<Int?>(null) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(16) { index ->

            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth()
            ) {

                MemoryCard(
                    imageRes = R.drawable.card_1,
                    isFlipped = flippedIndex == index,
                    onClick = {
                        flippedIndex = index
                    }
                )

            }
        }
    }
}