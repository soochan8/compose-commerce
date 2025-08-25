package com.chan.navigation

enum class Routes(val route: String) {
    HOME("home"),
    CATEGORY("category"),
    CATEGORY_DETAIL("categoryDetail/{categoryId}"),
    PRODUCT_DETAIL("productDetail/{productId}"),
    HISTORY("history"),
    MYPAGE("mypage"),
    LOGIN("login"),
    SEARCH("search");

    fun categoryDetailRoute(categoryId: String) =
        "categoryDetail/$categoryId"

    fun productDetailRoute(productId: String) =
        "productDetail/$productId"
}