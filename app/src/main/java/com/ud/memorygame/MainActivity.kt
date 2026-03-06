package com.ud.memorygame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ud.memorygame.design.GameScreen
import com.ud.memorygame.design.LevelsScreen
import com.ud.memorygame.design.MenuScreen
import com.ud.memorygame.ui.theme.MemoryGameTheme
import com.ud.memorygame.utils.MusicManager

class MainActivity : ComponentActivity() {

    private lateinit var musicManager: MusicManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        musicManager = MusicManager(this)

        setContent {
            MemoryGameTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MemoryGameApp(
                        musicManager = musicManager
                    )
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        musicManager.pauseMusic()
    }

    override fun onResume() {
        super.onResume()
        musicManager.resumeMusic()
    }

    override fun onDestroy() {
        super.onDestroy()
        musicManager.stopMusic()
    }
}

@Composable
fun MemoryGameApp(
    musicManager: MusicManager
) {
    val navController = rememberNavController()

    // start music when app launches
    LaunchedEffect(Unit) {
        musicManager.startMusic()
    }

    NavHost(
        navController = navController,
        startDestination = "menu"
    ) {
        composable("menu") {
            MenuScreen(navController = navController)
        }

        composable("levels") {
            LevelsScreen(navController = navController)
        }

        composable(
            "game/{cardCount}",
            arguments = listOf(navArgument("cardCount") { type = NavType.IntType })
        ) { backStackEntry ->
            val cardCount = backStackEntry.arguments?.getInt("cardCount") ?: 12

            // pause music when game starts
            LaunchedEffect(Unit) {
                musicManager.pauseMusic()
            }

            GameScreen(
                modifier = Modifier,
                cardCount = cardCount
            )

            // resume music when leaving game
            DisposableEffect(Unit) {
                onDispose {
                    musicManager.resumeMusic()
                }
            }
        }
    }
}