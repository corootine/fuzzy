package com.corootine.fuzzy.ui.theme

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

fun ComponentActivity.setFuzokuContent(content: @Composable () -> Unit) {
    setContent {
        FuzokuTheme {
            content()
        }
    }
}