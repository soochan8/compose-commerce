package com.chan.navigation

enum class Routes(val route: String) {
    HOME("home"),
    CATEGORY("category"),
    CATEGORY_DETAIL("categoryDetail/{categoryId}"),
    HISTORY("history"),
    MYPAGE("mypage");

    fun categoryDetailRoute(categoryId: String) =
        "categoryDetail/$categoryId"
}