package com.example.boardgames

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Title(gameState: GameState) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 25.dp),
        text = gameState.gameName,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.displayLarge
    )
}

@Composable
fun GameStatus(gameState: GameState) {

    var currentPlayerName by remember{ mutableStateOf(gameState.playerNames[gameState.currentPlayer]) }
    currentPlayerName = gameState.playerNames[gameState.currentPlayer]

    if (gameState.victoriousPlayer < 0) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            textAlign = TextAlign.Center,
            text = "$currentPlayerName to play…",
            style = MaterialTheme.typography.headlineLarge,
        )
    } else {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            textAlign = TextAlign.Center,
            text = "$currentPlayerName wins!",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
fun ScorePortrait(gameState: GameState) {

    val numPlayers = gameState.numPlayers
    val players = gameState.playerNames
    val scores = gameState.scores

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 0 until numPlayers) {
            Column(
                modifier = Modifier.padding(horizontal = 35.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = players[i],
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = scores[i].toString(),
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    }
}

@Composable
fun ScoreLandscape(gameState: GameState) {

    val numPlayers = gameState.numPlayers
    val playerNames = gameState.playerNames
    val scores = gameState.scores

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        for (i in 0 until numPlayers) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = playerNames[i],
                    style = MaterialTheme.typography.headlineSmall,
                )
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = scores[i].toString(),
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    }
}