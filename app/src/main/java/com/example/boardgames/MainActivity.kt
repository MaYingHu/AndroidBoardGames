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
fun Portrait(sessionState: SessionState, gameState: GameState, portraitToApp: (updatedGameState: GameState) -> Unit, resetGame: () -> Unit) {

    var currentGameState by remember { mutableStateOf(gameState) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Title(gameState = currentGameState)
        GameStatus(currentSessionState = sessionState, currentGameState = currentGameState) { resetGame() }
        Board(modifier = Modifier.fillMaxWidth(), sessionState = sessionState, gameState = currentGameState) { updatedGameState -> currentGameState = updatedGameState}
        ScorePortrait(currentSessionState = sessionState)
    }

    portraitToApp( currentGameState )
}

@Composable
fun Landscape(currentSessionState: SessionState, currentGameState: GameState, updateGameState: (newGameState: GameState) -> Unit) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Board(modifier = Modifier.fillMaxHeight(), sessionState = currentSessionState, gameState = currentGameState) { newGameState -> updateGameState(newGameState) }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Title(currentGameState)
            ScoreLandscape(currentSessionState = currentSessionState)
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun App() {

    var currentSessionState by remember { mutableStateOf( SessionState(playerNames = arrayOf("Zinaïda", "Zephyrus")) )}
    var currentGameState by remember { mutableStateOf( GameState() ) }
    var currentScreen by remember { mutableStateOf("Players") }
    var startNewGame by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            NavigationPortrait(currentScreen) { newScreen ->
                currentScreen = newScreen
            }
        },
        content = { padding ->
            if (currentScreen == "Players") {
                Box(Modifier.padding(padding)) {
                    SelectPlayers(currentSessionState.playerNames) { newPlayers ->
                        if (!newPlayers.contentEquals(currentSessionState.playerNames)) {
                            currentSessionState = SessionState(
                                playerNames = newPlayers,
                            )
                            currentGameState = GameState()
                        }
                        currentScreen = "Current"
                    }
                }
            } else {
                Box(Modifier.padding(padding)) {
                    if (startNewGame) {
                        startNewGame = false
                        currentGameState = GameState()
                        Portrait(
                            sessionState = currentSessionState,
                            gameState = currentGameState, //GameState(),
                            portraitToApp = { updatedGameState -> currentGameState = updatedGameState },
                            resetGame = { startNewGame = true }
                        )
                    } else {
                        Portrait(
                            sessionState = currentSessionState,
                            gameState = currentGameState,
                            portraitToApp = { updatedGameState -> currentGameState = updatedGameState },
                            resetGame = { startNewGame = true }
                            )
                    }
                }
            }
        }
    )
}
