package com.chan.login.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.chan.android.ui.theme.Black
import com.chan.android.ui.theme.Gray
import com.chan.android.ui.theme.LightGray
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.appTypography
import com.chan.login.R
import com.chan.login.ui.LoginContract


@Composable
fun AppLogin(
    state: LoginContract.State,
    onEvent: (LoginContract.Event.AppLoginEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.spacing5)
    ) {
        LoginInputTextField(
            id = state.id,
            onUserId = { onEvent(LoginContract.Event.AppLoginEvent.UserId(it)) },
            password = state.password,
            onUserPassword = { onEvent(LoginContract.Event.AppLoginEvent.UserPassword(it)) }
        )
        Spacer(modifier = Modifier.height(Spacing.spacing6))
        LoginOptions(
            isAutoLoginChecked = state.isAutoLoginChecked,
            onAutoLoginChanged = { onEvent(LoginContract.Event.AppLoginEvent.AutoLoginChanged(it)) },
            isSaveIdChecked = state.isSaveIdChecked,
            onSaveIdChanged = { onEvent(LoginContract.Event.AppLoginEvent.SaveIdChanged(it)) }
        )
        Spacer(modifier = Modifier.height(Spacing.spacing5))
        LoginButtons(
            onLoginClick = { onEvent(LoginContract.Event.AppLoginEvent.OnAppLoginButtonClicked) },
            onRegisterClick = { onEvent(LoginContract.Event.AppLoginEvent.RegisterUser) }
        )
        FindAccount(
            onFindIdClick = { /* TODO */ },
            onFindPasswordClick = { /* TODO */ }
        )
    }
}

@Composable
fun LoginInputTextField(
    id: String,
    onUserId: (String) -> Unit,
    password: String,
    onUserPassword: (String) -> Unit
) {
    var isIdFocused by remember { mutableStateOf(false) }
    var isPasswordFocused by remember { mutableStateOf(false) }
    val idBorderColor = if (isIdFocused) Black else LightGray
    val passwordBorderColor = if (isPasswordFocused) Black else LightGray

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to idBorderColor,
                        0.5f to idBorderColor,
                        0.5f to passwordBorderColor,
                        1.0f to passwordBorderColor
                    )
                ),
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        TextField(
            value = id,
            onValueChange = onUserId,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isIdFocused = it.isFocused },
            placeholder = {
                Text(
                    text = stringResource(R.string.id_label),
                    color = LightGray,
                    style = MaterialTheme.appTypography.loginTextFieldStyle
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Black
            ),
            singleLine = true
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = if (isIdFocused || isPasswordFocused) Black else LightGray
        )
        TextField(
            value = password,
            onValueChange = onUserPassword,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isPasswordFocused = it.isFocused },
            placeholder = {
                Text(
                    text = stringResource(R.string.password_label),
                    color = LightGray,
                    style = MaterialTheme.appTypography.loginTextFieldStyle
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Black
            ),
            singleLine = true
        )
    }
}

@Composable
fun LoginOptions(
    isAutoLoginChecked: Boolean,
    onAutoLoginChanged: (Boolean) -> Unit,
    isSaveIdChecked: Boolean,
    onSaveIdChanged: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isAutoLoginChecked,
            onCheckedChange = onAutoLoginChanged,
            modifier = Modifier.size(Spacing.spacing4),
            colors = CheckboxDefaults.colors(checkedColor = Black)
        )
        Spacer(modifier = Modifier.width(Spacing.spacing2))
        Text(
            text = stringResource(R.string.auto_login),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.width(Spacing.spacing3))
        Checkbox(
            checked = isSaveIdChecked,
            onCheckedChange = onSaveIdChanged,
            modifier = Modifier.size(Spacing.spacing4),
            colors = CheckboxDefaults.colors(checkedColor = Black)
        )
        Spacer(modifier = Modifier.width(Spacing.spacing2))
        Text(
            text = stringResource(R.string.save_id),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun LoginButtons(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Row {
        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(size = 4.dp),
            colors = ButtonDefaults.buttonColors(Black)
        ) {
            Text(
                text = stringResource(R.string.login_label),
                modifier = Modifier.padding(vertical = Spacing.spacing2)
            )
        }
        Spacer(modifier = Modifier.width(Spacing.spacing3))
        Button(
            onClick = onRegisterClick,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(size = 4.dp),
            colors = ButtonDefaults.buttonColors(Black)
        ) {
            Text(
                text = stringResource(R.string.register_label),
                modifier = Modifier.padding(vertical = Spacing.spacing2)
            )
        }
    }
}

@Composable
fun FindAccount(
    onFindIdClick: () -> Unit,
    onFindPasswordClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.spacing4)
    ) {
        TextButton(onClick = onFindIdClick) {
            Text(
                text = stringResource(R.string.find_id_label),
                color = Gray
            )
        }
        VerticalDivider(modifier = Modifier.height(Spacing.spacing4))
        TextButton(onClick = onFindPasswordClick) {
            Text(
                text = stringResource(R.string.find_password_label),
                color = Gray
            )
        }
    }
}