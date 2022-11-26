package com.lie.composeanimationkit.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate

@Composable
fun RotationAnimation(
    time: Int = 3000,
    delayTime: Int = 0,
    content: @Composable () -> Unit
) {
    val tran = rememberInfiniteTransition()
    val rote by tran.animateFloat(initialValue = 0f, targetValue = 360f, animationSpec = infiniteRepeatable(
        tween(time, delayTime, LinearEasing)
    ) )
    Box(
        Modifier.rotate(rote)
    ) {
        content()
    }
}