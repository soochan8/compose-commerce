package com.chan.login.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.navigation.NamedNavArgument
import com.chan.login.R
import com.chan.navigation.BottomNavDestination
import com.chan.navigation.Routes

object LoginDestination : BottomNavDestination {
    override val title = R.string.my_page
    override val icon = Icons.Default.Person
    override val route = Routes.MYPAGE.route
    override val arguments: List<NamedNavArgument> = emptyList()

}