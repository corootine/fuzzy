package com.corootine.fuzzy.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.google.accompanist.insets.ProvideWindowInsets

@Composable
fun FuzokuTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = FuzokuColors,
    ) {
        ProvideWindowInsets {
            content()
        }
    }
}