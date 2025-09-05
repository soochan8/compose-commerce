package com.chan.navigation

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onNavigate: (BottomNavDestination) -> Unit,
    navDestinations: List<BottomNavDestination>,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier.wrapContentHeight(),
        containerColor = Color.White,
    ) {
        navDestinations.forEach { screen ->
            NavigationBarItem(
                selected = screen.route.substringBefore("?") == currentRoute?.substringBefore("?"),
                onClick = { onNavigate(screen) },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = stringResource(id = screen.title)
                    )
                },
                label = { Text(stringResource(id = screen.title)) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}