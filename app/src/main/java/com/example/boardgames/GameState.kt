package com.example.boardgames

class GameState(
    var gameName: String = "Noughts and Crosses",
    var numPlayers: Int = 2,
    var playerNames: Array<String> = arrayOf("Player 1", "Player 2"),
    var scores: Array<Int> = arrayOf(0, 0),
    var currentPlayer: Int = 0,
    var ownership: Array<Int> = arrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
    var victoriousPlayer: Int = -1
    ) {

    fun nextPlayer() {
        currentPlayer = if (currentPlayer == 0) {
            1
        } else {
            0
        }
    }

    fun determineVictory(latest: Int) {
        var count = 0

        for (arr in arrayOf(
            arrayOf(0, 1, 2),
            arrayOf(0, 4, 8),
            arrayOf(0, 3, 6),
            arrayOf(1, 4, 7),
            arrayOf(2, 5, 8),
            arrayOf(2, 4, 6),
            arrayOf(3, 4, 5),
            arrayOf(6, 7, 8))
        ) {
            for (i in arr) {
                if (ownership[i] == currentPlayer) {
                    count += 1
                }
            }
            if (count == 3) {
                victoriousPlayer = currentPlayer
            } else {
                count = 0
            }
        }
    }
}

class SessionState(
    var numPlayers: Int = 2,
    var playerNames: Array<String> = arrayOf("Player 1", "Player 2"),
    var scores: Array<Int> = arrayOf(0, 0)
)