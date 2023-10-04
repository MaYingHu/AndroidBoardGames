package com.example.boardgames

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.example.boardgames.ui.theme.BoardGamesTheme

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoardGamesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BoardGamesTheme {
                        App()
                    }
                }
            }
        }
    }
}

@Composable
fun Portrait(gameState: GameState, portraitToApp: (updatedGameState: GameState) -> Unit) {

    var currentGameState by remember { mutableStateOf(gameState) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Title(gameState = currentGameState)
        GameStatus(currentGameState)
        Board(modifier = Modifier.fillMaxWidth(), gameState = currentGameState) { updatedGameState -> currentGameState = updatedGameState}
        portraitToApp(currentGameState)
        ScorePortrait(currentGameState)
    }
}

@Composable
fun Landscape(currentGameState: GameState, updateGameState: (newGameState: GameState) -> Unit) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        NavigationLandscape()
        Board(modifier = Modifier.fillMaxHeight(), gameState = currentGameState) { newGameState -> updateGameState(newGameState) }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Title(currentGameState)
            ScoreLandscape(currentGameState)
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun App() {

    var currentGameState by remember { mutableStateOf(GameState(playerNames = arrayOf("Zinaïda", "Zephyrus"))) }
    var currentScreen by remember { mutableStateOf("Players") }

    Scaffold(
        bottomBar = {
            NavigationPortrait(currentScreen) { newScreen ->
                currentScreen = newScreen
            }
        },
        content = { padding ->
            if (currentScreen == "Players") {
                Box(Modifier.padding(padding)) {
                    SelectPlayers(currentGameState.playerNames) { newPlayers ->
                        currentGameState = GameState(
                            gameName = currentGameState.gameName,
                            numPlayers = currentGameState.numPlayers,
                            playerNames = newPlayers,
                            scores = currentGameState.scores,
                            currentPlayer = currentGameState.currentPlayer,
                            victoriousPlayer = currentGameState.victoriousPlayer
                        )
                        currentScreen = "Current"

                    }
                }
            } else {
                Box(Modifier.padding(padding)) {
                    Portrait(currentGameState) { updatedGameState ->
                        currentGameState = updatedGameState
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PortraitPreview() {
    val gameState = GameState()
    BoardGamesTheme {
        Portrait(gameState) {}
    }
}

@Preview(showBackground = true, device = Devices.AUTOMOTIVE_1024p, widthDp = 720, heightDp = 360)
@Composable
fun LandscapePreview() {
    val gameState = GameState()
    BoardGamesTheme {
        Landscape(gameState) {}
    }
}