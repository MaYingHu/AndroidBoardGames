package com.example.boardgames

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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

@ExperimentalMaterial3Api
@Composable
fun SelectPlayers(currentPlayers: Array<String>, selectPlayersToApp: (newPlayers: Array<String>) -> Unit) {

    var player1 by remember{ mutableStateOf(currentPlayers[0]) }
    var player2 by remember{ mutableStateOf(currentPlayers[1]) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp),
            text = "Enter player names:",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayLarge
        )
        OutlinedTextField(
            modifier = Modifier.padding(top = 20.dp),
            //placeholder = { Text(text = player1) },
            label = { Text(text = "Player 1") },
            value = player1,
            onValueChange = { newText -> player1 = newText },
        )
        OutlinedTextField(
            modifier = Modifier.padding(top = 40.dp),
            //placeholder = { Text(text = player2) },
            label = { Text(text = "Player 2") },
            value = player2,
            onValueChange = { newText -> player2 = newText },
        )
        ElevatedButton(
            modifier = Modifier.padding(top = 40.dp),
            onClick = {
                selectPlayersToApp(arrayOf(player1, player2))
            }
        ) {
            Text(
                text = "DONE"
            )
        }
    }
}
