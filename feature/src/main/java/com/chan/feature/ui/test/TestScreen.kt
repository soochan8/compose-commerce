package com.chan.feature.ui.test

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chan.feature.ui.TestContract
import com.chan.feature.ui.TestViewModel

@Composable
fun TestScreen(viewModel: TestViewModel = hiltViewModel()) {

    val viewState = viewModel.viewState.value
    val effectFlow = viewModel.effect
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        effectFlow.collect { effect ->
            when (effect) {
                is TestContract.Effect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    TestContent(
        state = viewState,
        onClick = {
            viewModel.setEvent(TestContract.Event.OnItemClick)
        }
    )
}


@Composable
fun TestContent(
    state: TestContract.State,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onClick) {
            Text(state.user.userName)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestContentPreview() {
    TestContent(
        state = TestContract.State(UserModel("테스트"), false, false),
        onClick = {}
    )
}