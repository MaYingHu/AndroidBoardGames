package com.example.boardgames

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
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
fun GameStatus(currentSessionState: SessionState, currentGameState: GameState, newGame: () -> Unit) {

    val currentPlayerName = currentSessionState.playerNames[currentGameState.currentPlayer]
    val currentTurn = currentGameState.turn

    if (currentGameState.stalemate) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            textAlign = TextAlign.Center,
            text = "It's a draw!",
            style = MaterialTheme.typography.headlineLarge
        )
        ClickableText(
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge + TextStyle(
                textAlign = TextAlign.Center
            ),
            text = AnnotatedString("(Click to play again)"),
            onClick = { newGame() }
        )
    }
    else if (currentGameState.victoriousPlayer < 0) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            textAlign = TextAlign.Center,
            text = "Turn $currentTurn:",
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "$currentPlayerName to play…",
            style = MaterialTheme.typography.headlineLarge,
        )
    } else {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            textAlign = TextAlign.Center,
            text = "$currentPlayerName wins!",
            style = MaterialTheme.typography.headlineLarge
        )
        ClickableText(
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge + TextStyle(
                textAlign = TextAlign.Center
            ),
            text = AnnotatedString("(Click to play again)"),
            onClick = { newGame() }
        )
    }
}

@Composable
fun ScorePortrait(currentSessionState: SessionState) {

    val numPlayers = currentSessionState.numPlayers
    val players = currentSessionState.playerNames
    val scores = currentSessionState.scores

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
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
fun ScoreLandscape(currentSessionState: SessionState) {

    val numPlayers = currentSessionState.numPlayers
    val playerNames = currentSessionState.playerNames
    val scores = currentSessionState.scores

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