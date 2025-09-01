package com.chan.navigation

enum class Routes(val route: String) {
    HOME("home"),
    CATEGORY("category"),
    CATEGORY_DETAIL("categoryDetail/{categoryId}"),
    PRODUCT_DETAIL("productDetail/{productId}"),
    HISTORY("history"),
    MYPAGE("mypage"),
    LOGIN("login"),
    SEARCH("search"),
    CART("cart"),
    CART_POPUP("cart/{productId}");

    fun categoryDetailRoute(categoryId: String) =
        "categoryDetail/$categoryId"

    fun productDetailRoute(productId: String) =
        "productDetail/$productId"

    fun cartPopUpRoute(productId: String) =
        "cart/$productId"
}
fun createLoginRoute(redirect: String = ""): String {
    return "${Routes.LOGIN.route}?redirect=$redirect"
}
