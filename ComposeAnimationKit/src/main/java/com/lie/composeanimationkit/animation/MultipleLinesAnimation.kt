/*
 * Copyright (c) 2023.
 * @username: LiePy
 * @file: MultipleLinesAnimation.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.ComposeAnimationKit.main
 * @date: 2023/2/18 上午1:48
 */

package com.lie.composeanimationkit.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

/**
 * @desc 多竖线规律伸缩动画
 * @param modifier 修饰符
 * @param color 颜色
 * @param strokeWidth 线条粗细
 * @param length 线条长度
 * @param duration 动画时长
 * @author LiePy
 * @date 2023/2/18
 */
@Composable
fun MultipleLinesAnimation(
    modifier: Modifier,
    color: Color = Color.Cyan,
    strokeWidth: Float = 5f,
    length: Float = 40f,
    duration: Int = 500,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val dd = duration / 5
    val targetDy = length * 3 / 8
    val dy0 = infiniteTransition.getDy(delayMillis = 0, targetValue = targetDy, duration = 500)
    val dy1 = infiniteTransition.getDy(delayMillis = dd, targetValue = targetDy, duration = 500)
    val dy2 = infiniteTransition.getDy(delayMillis = dd * 2, targetValue = targetDy, duration = 500)
    val dy3 = infiniteTransition.getDy(delayMillis = dd * 3, targetValue = targetDy, duration = 500)
    val dy4 = infiniteTransition.getDy(delayMillis = dd * 4, targetValue = targetDy, duration = 500)

    Canvas(modifier = modifier) {
        drawLine(
            color,
            center - Offset(strokeWidth * 4, length / 2 - dy0.value),
            center - Offset(strokeWidth * 4, -length / 2 + dy0.value),
            strokeWidth
        )
        drawLine(
            color,
            center - Offset(strokeWidth * 2, length / 2 - dy1.value),
            center - Offset(strokeWidth * 2, -length / 2 + dy1.value),
            strokeWidth
        )
        drawLine(
            color,
            center - Offset(0f, length / 2 - dy2.value),
            center - Offset(0f, -length / 2 + dy2.value),
            strokeWidth
        )
        drawLine(
            color,
            center - Offset(-strokeWidth * 2, length / 2 - dy3.value),
            center - Offset(-strokeWidth * 2, -length / 2 + dy3.value),
            strokeWidth
        )
        drawLine(
            color,
            center - Offset(-strokeWidth * 4, length / 2 - dy4.value),
            center - Offset(-strokeWidth * 4, -length / 2 + dy4.value),
            strokeWidth
        )
    }
}

/**
 *
 */
@Composable
private fun InfiniteTransition.getDy(
    delayMillis: Int,
    targetValue: Float,
    duration: Int
): State<Float> =
    this.animateFloat(
        initialValue = 0f,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            tween(
                duration,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(delayMillis)
        )
    )

@Preview
@Composable
fun MultipleLinesAnimationPre() {
    MultipleLinesAnimation(modifier = Modifier.fillMaxSize())
}