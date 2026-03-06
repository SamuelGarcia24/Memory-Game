package com.ud.memorygame.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ud.memorygame.R
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    // base images used to create the pairs
    private val baseImages = listOf(
        R.drawable.card_1,
        R.drawable.card_2,
        R.drawable.card_3,
        R.drawable.card_4,
        R.drawable.card_5,
        R.drawable.card_6
    )

    // list that represents the board cards (12 cards shuffled)
    var cards by mutableStateOf(
        (baseImages + baseImages).shuffled()
    )
        private set

    // stores the index of the first selected card
    var firstSelectedCardIndex by mutableStateOf<Int?>(null)

    // stores the index of the second selected card
    var secondSelectedCardIndex by mutableStateOf<Int?>(null)

    // list that stores the flipped state of the 12 cards
    // false = card hidden
    // true = card visible
    var flippedCards by mutableStateOf(List(12) { false })
        private set

    // called when a card is clicked
    fun onCardClicked(index: Int) {

        // ignore click if the card is already flipped
        if (flippedCards[index]) return

        // ignore click if two cards are already selected
        if (firstSelectedCardIndex != null && secondSelectedCardIndex != null) {
            return
        }

        // flip the selected card
        flippedCards = flippedCards.toMutableList().also {
            it[index] = true
        }

        // store first selected card
        if (firstSelectedCardIndex == null) {

            firstSelectedCardIndex = index

        }
        // store second selected card and check match
        else if (secondSelectedCardIndex == null) {

            secondSelectedCardIndex = index

            checkMatch()

        }
    }

    // checks if the two selected cards are a match
    // checks if the two selected cards are a match
    private fun checkMatch() {

        val firstIndex = firstSelectedCardIndex
        val secondIndex = secondSelectedCardIndex

        if (firstIndex == null || secondIndex == null) return

        // launch coroutine to wait before flipping back
        viewModelScope.launch {

            // wait 1 second so player can see the cards
            delay(1000)

            // if cards are different flip them back
            if (cards[firstIndex] != cards[secondIndex]) {

                flippedCards = flippedCards.toMutableList().also {
                    it[firstIndex] = false
                    it[secondIndex] = false
                }
            }

            // reset selected cards
            firstSelectedCardIndex = null
            secondSelectedCardIndex = null
        }
    }
}