package com.chan.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    navDestinations: List<BottomNavDestination>
) {
    NavigationBar {
        navDestinations.forEach { screen ->
            NavigationBarItem(
                selected = screen.route == currentRoute,
                onClick = { onNavigate(screen.route) },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = stringResource(id = screen.title)
                    )
                },
                label = { Text(stringResource(id = screen.title)) }
            )
        }
    }
}