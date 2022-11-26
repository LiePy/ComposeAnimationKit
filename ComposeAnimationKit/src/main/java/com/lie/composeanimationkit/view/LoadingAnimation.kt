package com.lie.composeanimationkit.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate

/**
 * 简单好用的旋转动画，传入需要旋转的子Composable作为内容
 *
 * @param durationMillis 动画旋转一周所用的时间，时间越长，旋转速度越慢
 * @param delayTime 动画旋转一周后停留的时间
 * @param easing 动画效果，默认线性：LinearEasing
 * @param content 旋转的内容
 */
@Composable
fun RotationAnimation(
    durationMillis: Int = 3000,
    delayTime: Int = 0,
    easing: Easing = LinearEasing,
    content: @Composable () -> Unit
) {
    val tran = rememberInfiniteTransition()
    val rote by tran.animateFloat(
        initialValue = 0f, targetValue = 360f, animationSpec = infiniteRepeatable(
            tween(durationMillis, delayTime, easing)
        )
    )
    Box(
        Modifier.rotate(rote)
    ) {
        content()
    }
}