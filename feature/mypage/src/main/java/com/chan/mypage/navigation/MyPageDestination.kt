package com.chan.mypage.navigation

import androidx.navigation.NamedNavArgument
import com.chan.navigation.NavDestination
import com.chan.navigation.Routes

object MyPageDestination : NavDestination {
    override val route = Routes.MYPAGE.route
    override val arguments: List<NamedNavArgument> = emptyList()

}