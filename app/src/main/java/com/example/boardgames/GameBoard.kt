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
fun BoardSquare(modifier: Modifier, squareId: Int, sessionState: SessionState, gameState: GameState, boardSquareToBoardSquares: (gameState: GameState) -> Unit) {

    val currentSessionState = remember { sessionState }
    val currentGameState = remember { gameState }
    val theSymbol = remember { mutableStateOf<Int?>(R.drawable.ablanksquare) }
    val interactionSource = remember { MutableInteractionSource() }

    if (currentGameState.ownership[squareId] == -1) {
        theSymbol.value = R.drawable.ablanksquare
    }

    Box(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                if (currentGameState.ownership[squareId] == -1) {
                    if (currentGameState.currentPlayer == 0) {
                        theSymbol.value = R.drawable.x
                    } else {
                        theSymbol.value = R.drawable.o
                    }
                    currentGameState.ownership[squareId] = currentGameState.currentPlayer
                    currentGameState.determineVictory()
                    currentGameState.determineStalemate()
                    if (currentGameState.victoriousPlayer < 0) {
                        currentGameState.nextPlayer()
                    } else {
                        currentSessionState.scores[currentGameState.currentPlayer] += 1
                    }
                    val newGameState = GameState(
                        gameName = currentGameState.gameName,
                        numPlayers = currentGameState.numPlayers,
                        currentPlayer = currentGameState.currentPlayer,
                        ownership = currentGameState.ownership,
                        victoriousPlayer = currentGameState.victoriousPlayer,
                        stalemate = currentGameState.stalemate,
                        turn = if (currentGameState.currentPlayer == 0) { currentGameState.turn + 1 } else { currentGameState.turn }
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
fun BoardSquares(sessionState: SessionState, gameState: GameState, boardSquaresToBoard: (gameState: GameState) -> Unit) {

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
                        sessionState = sessionState,
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
fun Board(modifier: Modifier, sessionState: SessionState, gameState: GameState, boardToOrientation: (newGameState: GameState) -> Unit) {

    var currentGameState by remember { mutableStateOf(gameState) }

    Box(
        modifier = modifier,
    ) {
        BoardBackground(modifier)
        BoardSquares(sessionState = sessionState, gameState = gameState) { updatedGameState -> currentGameState = updatedGameState }
        boardToOrientation(currentGameState)
    }
}