package com.example.boardgames

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun BoardSquare(modifier: Modifier, squareId: Int, gameState: GameState, boardSquareToBoardSquares: (gameState: GameState) -> Unit) {

    val currentGameState = remember { gameState }
    val theSymbol = remember { mutableStateOf<Int?>(null) }
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                if (theSymbol.value == null) {
                    if (currentGameState.currentPlayer == 0) {
                        theSymbol.value = R.drawable.x
                    } else {
                        theSymbol.value = R.drawable.o
                    }
                    currentGameState.ownership[squareId] = currentGameState.currentPlayer
                    currentGameState.determineVictory(squareId)
                    if (currentGameState.victoriousPlayer < 0) {
                        currentGameState.nextPlayer()
                    } else {
                        currentGameState.scores[currentGameState.currentPlayer] += 1
                    }
                    val newGameState = GameState(
                        gameName = currentGameState.gameName,
                        numPlayers = currentGameState.numPlayers,
                        playerNames = currentGameState.playerNames,
                        scores = currentGameState.scores,
                        currentPlayer = currentGameState.currentPlayer,
                        ownership = currentGameState.ownership,
                        victoriousPlayer = currentGameState.victoriousPlayer
                    )
                    boardSquareToBoardSquares(newGameState)
                }
            },
    )
    {
        if (theSymbol.value != null) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                painter = painterResource(id = theSymbol.value!!),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Composable
fun BoardSquares(gameState: GameState, boardSquaresToBoard: (gameState: GameState) -> Unit) {

    var currentGameState by remember { mutableStateOf(gameState) }

    Column(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(35.dp),
    ) {
        for (i in 0..2) {
            Row {
                for (j in 0..2) {
                    BoardSquare(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        squareId = i + (3 * j),
                        gameState = gameState,
                        boardSquareToBoardSquares = {updatedGameState -> currentGameState = updatedGameState }
                    )
                }
            }
        }
        boardSquaresToBoard(currentGameState)
    }
}

@Composable
fun BoardBackground(modifier: Modifier) {
    Image(
        modifier = modifier
            .aspectRatio(1f)
            .padding(35.dp),
        painter = painterResource(id = R.drawable.board),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}

@Composable
fun Board(modifier: Modifier, gameState: GameState, boardToOrientation: (newGameState: GameState) -> Unit) {

    var currentGameState by remember { mutableStateOf(gameState) }

    Box(
        modifier = modifier,
    ) {
        BoardBackground(modifier)
        BoardSquares(gameState = gameState) { updatedGameState -> currentGameState = updatedGameState }
        boardToOrientation(currentGameState)
    }
}