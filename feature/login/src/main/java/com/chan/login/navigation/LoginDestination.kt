package com.chan.login.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument
import com.chan.login.R
import com.chan.navigation.BottomNavDestination
import com.chan.navigation.Routes

object LoginDestination : BottomNavDestination {
    private const val REDIRECT_KEY = "redirect"

    override val title = R.string.my_page
    override val icon = Icons.Default.Person
    override val route = "${Routes.LOGIN.route}?$REDIRECT_KEY={$REDIRECT_KEY}"
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(REDIRECT_KEY) {
            defaultValue = ""
        }
    )
}