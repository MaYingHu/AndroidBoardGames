package com.example.boardgames

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun NavigationPortrait(currentScreen: String, newScreen: (screen: String) -> Unit) {

    val navigationIcons = mapOf(
        "Players" to Icons.Filled.AccountCircle,
        "Games" to Icons.Filled.List,
        "Settings" to Icons.Filled.Settings,
        "Current" to Icons.Filled.PlayArrow
    )

    NavigationBar {
        navigationIcons.forEach { screen ->
            if (currentScreen != screen.key)
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = screen.value,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = screen.key
                    )
                },
                selected = true,
                onClick = {
                    newScreen(screen.key)
                }
            )
        }
    }
}

@Composable
fun NavigationLandscape() {

    val navigationIcons = mapOf(
        Icons.Filled.AccountCircle to "Players",
        Icons.Filled.List to "Games",
        Icons.Filled.Settings to "Settings"
    )

    NavigationRail {
        navigationIcons.forEach {
            NavigationRailItem(
                icon = {
                    Icon(
                        imageVector = it.key,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = it.value
                    )
                },
                selected = true,
                onClick = {}
            )
        }
    }
}