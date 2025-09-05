package com.chan.mypage.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.chan.mypage.MyPageScreen
import com.chan.navigation.NavGraphProvider
import javax.inject.Inject

class MyPageNavGraph @Inject constructor() : NavGraphProvider {
    override fun addGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable(MyPageDestination.route) {
            MyPageScreen()
        }
    }
}