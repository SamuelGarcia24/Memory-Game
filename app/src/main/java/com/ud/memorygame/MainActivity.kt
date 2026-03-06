package com.ud.memorygame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.ud.memorygame.design.GameScreen
import com.ud.memorygame.ui.theme.MemoryGameTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // enables edge to edge layout for modern android ui
        enableEdgeToEdge()

        setContent {

            // apply the app theme
            MemoryGameTheme {

                // scaffold is the base layout structure for the screen
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {

                    // main game screen composable
                    GameScreen()

                }
            }
        }
    }
}