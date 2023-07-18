package co.touchlab.kampkit.ui.theme

import androidx.compose.runtime.Composable

@Composable
expect fun KaMPKitTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
)
