package com.corootine.fuzzy.ui.widgets

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale

@Composable
fun ScalingCircularProgressIndicator(modifier: Modifier, color: Color = MaterialTheme.colors.primary) {
    val infiniteTransition = rememberInfiniteTransition()

    val smallCircleScale by infiniteTransition.animateFloat(
        initialValue = 0.05f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val bigCircleScale by infiniteTransition.animateFloat(
        initialValue = 0.05f,
        targetValue = 0.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, delayMillis = 100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(modifier = modifier) {
        scale(scale = smallCircleScale) {
            drawCircle(color = color, alpha = 0.5f)
        }
        scale(scale = bigCircleScale) {
            drawCircle(color = color, alpha = 0.5f)
        }
    }
}