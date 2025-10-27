package com.chan.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.White

@Composable
fun MyPageScreen(
    state: MyPageContract.State,
    onEvent: (MyPageContract.Event) -> Unit
) {
    Column( modifier = Modifier
        .fillMaxSize()
        .background(White)
        .padding(Spacing.spacing4),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "마이 페이지 입니다.")

        Button(
            modifier = Modifier.fillMaxWidth().padding(Spacing.spacing4),
            onClick = { onEvent(MyPageContract.Event.OnLogoutClicked) }) {
            Text("로그아웃")
        }
    }
}