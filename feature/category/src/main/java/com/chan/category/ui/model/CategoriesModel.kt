package com.chan.category.ui.model

data class CategoriesModel(
    val id: String,
    val name: String,
    val imageUrl: String = "https://play-lh.googleusercontent.com/0wpYfhH5VQprQGLqC5wT6ieowMfW9n8Oae3goG0uKozq3FZYyzl4CJL5sgznUBGHErhe",
    val subCategories: List<CategoriesModel> = emptyList()
)