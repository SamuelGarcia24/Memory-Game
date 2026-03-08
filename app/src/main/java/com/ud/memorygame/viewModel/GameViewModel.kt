package com.ud.memorygame.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ud.memorygame.R
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
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
        R.drawable.card_6,
        R.drawable.card_7,
        R.drawable.card_8,
        R.drawable.card_9,
        R.drawable.card_10
    )

    // list that represents the board cards
    var cards by mutableStateOf(listOf<Int>())
        private set

    // stores the index of the first selected card
    var firstSelectedCardIndex by mutableStateOf<Int?>(null)

    // stores the index of the second selected card
    var secondSelectedCardIndex by mutableStateOf<Int?>(null)

    // list that stores the flipped state of the cards
    var flippedCards by mutableStateOf(listOf<Boolean>())
        private set

    // the victory state
    var isGameOver by mutableStateOf(false)
        private set

    // moves counter
    var moves by mutableStateOf(0)
        private set

    // pairs found counter
    var pairsFound by mutableStateOf(0)
        private set

    // time in seconds
    var secondsElapsed by mutableStateOf(0)
        private set

    private var timerJob: Job? = null
    private var currentCardCount = 0

    private var onSoundEffect: ((Int) -> Unit)? = null

    fun setSoundListener(listener: (Int) -> Unit) {
        onSoundEffect = listener
    }

    // initialize game with specific number of cards
    fun initializeGame(cardCount: Int) {
        currentCardCount = cardCount
        // calculate how many pairs we need
        val pairsNeeded = cardCount / 2

        // take the first 'pairsNeeded' images and duplicate them
        val selectedImages = baseImages.take(pairsNeeded)

        // create pairs and shuffle
        cards = (selectedImages + selectedImages).shuffled()

        // initialize flipped cards (all false)
        flippedCards = List(cardCount) { false }

        // reset selections
        firstSelectedCardIndex = null
        secondSelectedCardIndex = null
        isGameOver = false
        moves = 0
        pairsFound = 0
        secondsElapsed = 0
        
        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (!isGameOver) {
                delay(1000)
                secondsElapsed++
            }
        }
    }



    // called when a card is clicked
    fun onCardClicked(index: Int) {

        // sound when touching the card
        onSoundEffect?.invoke(R.raw.touch)

        // ignore click if game is over
        if (isGameOver) return

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
            moves++ // increment moves when second card is picked
            checkMatch()
        }
    }

    // checks if the two selected cards are a match
    private fun checkMatch() {
        val firstIndex = firstSelectedCardIndex
        val secondIndex = secondSelectedCardIndex

        if (firstIndex == null || secondIndex == null) return

        if (cards[firstIndex] != cards[secondIndex]) {
            onSoundEffect?.invoke(R.raw.no) // Error sound
        } else {
            onSoundEffect?.invoke(R.raw.success) // Sound of success
            if (flippedCards.all { it }) {
                onSoundEffect?.invoke(R.raw.win) // win
            }
        }

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
            } else {
                // it's a match!
                pairsFound++

                // Check if all cards are flipped
                if (flippedCards.all { it }) {
                    isGameOver = true
                    timerJob?.cancel()
                }
            }

            // reset selected cards
            firstSelectedCardIndex = null
            secondSelectedCardIndex = null
        }
    }

    fun resetGame() {
        initializeGame(currentCardCount)
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
