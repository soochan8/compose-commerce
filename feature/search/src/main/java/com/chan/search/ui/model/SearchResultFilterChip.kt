package com.chan.search.ui.model

import androidx.compose.runtime.Immutable

@Immutable
data class SearchResultFilterChip(
    val image: String = "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTA4MzFfMjk0%2FMDAxNjMwMzY0NDkyODA3.Nk03J6zqVlXO3WZ1d5VtVikVbhdWkZwFu4nXHILBmj0g.aNfbPHZFvpSeaDNdrN__Lw2E29VR6IxhbhVgoT0caqkg.PNG.kma450815%2F21101_24218_4218.png&type=sc960_832",
    val text: String,
    val chipType: FilterChipType,
    val isSelected: Boolean = false,
)

enum class FilterChipType {
    TOGGLE,
    DROP_DOWN
}