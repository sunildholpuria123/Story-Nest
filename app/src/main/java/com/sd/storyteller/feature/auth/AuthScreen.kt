package com.sd.storyteller.feature.auth

/**
 * Created by SDHOLPURIA on 02-11-2026.
 */

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.sd.storyteller.core.designsystem.theme.StoryNestPalette
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AuthScreen(
    onAuthenticated: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    var isLogin by remember { mutableStateOf(true) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(state.isAuthenticated) {
        if (state.isAuthenticated) onAuthenticated()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isLogin) "Welcome to StoryNest" else "Create your account",
            style = MaterialTheme.typography.headlineMedium,
            color = StoryNestPalette.TextPrimary
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = StoryNestPalette.TextPrimary,
                unfocusedTextColor = StoryNestPalette.TextPrimary,
                cursorColor = StoryNestPalette.Primary,
                focusedLabelColor = StoryNestPalette.Primary,
                unfocusedLabelColor = StoryNestPalette.TextSecondary,
                focusedBorderColor = StoryNestPalette.Primary,
                unfocusedBorderColor = StoryNestPalette.TextHint
            )
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = StoryNestPalette.TextPrimary,
                unfocusedTextColor = StoryNestPalette.TextPrimary,
                cursorColor = StoryNestPalette.Primary,
                focusedLabelColor = StoryNestPalette.Primary,
                unfocusedLabelColor = StoryNestPalette.TextSecondary,
                focusedBorderColor = StoryNestPalette.Primary,
                unfocusedBorderColor = StoryNestPalette.TextHint
            )
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                if (isLogin) {
                    viewModel.login(email.trim(), password)
                } else {
                    viewModel.register(email.trim(), password)
                }
            },
            enabled = !state.isLoading &&
                    email.isNotBlank() &&
                    password.length >= 6,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text(if (isLogin) "Login" else "Register")
            }
        }

        TextButton(
            onClick = {
                isLogin = !isLogin
                viewModel.clearError()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (isLogin) "New user? Create account"
                else "Already have an account? Login"
            )
        }

        if (state.isEmailVerificationRequired) {
            Text(
                "Registration successful! Please verify your email, then log in.",
                color = MaterialTheme.colorScheme.primary
            )
        }

        state.error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
