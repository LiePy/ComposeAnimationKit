/*
 * Copyright (c) 2023.
 * @username: LiePy
 * @file: RippleAnimation.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.ComposeAnimationKit.main
 * @date: 2023/2/19 下午9:36
 */

package com.lie.composeanimationkit.animation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.lie.composeanimationkit.AnimationKit

/**
 * @desc 涟漪动画，可内嵌子组件，但是注意是在BoxScope中，且默认的Alignment是居中
 * @param modifier 修饰符，最好在这里定义你要的size
 * @param color 初始中心的颜色
 * @param targetColor 散开时的颜色
 * @param size 涟漪最大的时候的size，单位是px，可能需要与本组件的大小不同
 * @param duration 从中间到最开的时间
 * @param content 子内容
 * @author LiePy
 * @date 2023/2/19
 */
@Composable
fun LA.RippleAnimation(
    modifier: Modifier = Modifier,
    color: Color = Color.Cyan,
    targetColor: Color = Color.Transparent,
    size: Float = 250f,
    duration: Int = 3000,
    content: @Composable BoxScope.() -> Unit = {},
) {

    val infiniteTransition = rememberInfiniteTransition()
    val color1 by infiniteTransition.animateColor(
        initialValue = color,
        targetValue = targetColor,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
            initialStartOffset = StartOffset(0)
        )
    )
    val size1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = size,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
            initialStartOffset = StartOffset(0)
        )
    )

    val color2 by infiniteTransition.animateColor(
        initialValue = color,
        targetValue = targetColor,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
            initialStartOffset = StartOffset(1000)
        )
    )
    val size2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = size,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
            initialStartOffset = StartOffset(1000)
        )
    )

    val color3 by infiniteTransition.animateColor(
        initialValue = color,
        targetValue = targetColor,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
            initialStartOffset = StartOffset(2000)
        )
    )
    val size3 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = size,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
            initialStartOffset = StartOffset(2000)
        )
    )

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = modifier) {
            drawCircle(color1, radius = size1)
        }

        Canvas(modifier = modifier) {
            drawCircle(color2, radius = size2)
        }

        Canvas(modifier = modifier) {
            drawCircle(color3, radius = size3)
        }

        content()
    }
}

@Preview
@Composable
fun RippleSearchingAnimationPreview() {
    AnimationKit.LoadingAnimation.RippleAnimation(Modifier.fillMaxSize())
}