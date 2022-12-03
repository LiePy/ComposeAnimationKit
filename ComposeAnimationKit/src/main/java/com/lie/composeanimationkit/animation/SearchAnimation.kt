/*
 * Copyright (c) 2022.
 * @username: LiePy
 * @file: SearchAnimation.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.ComposeAnimationKit.main
 * @date: 2022/11/29 下午8:33
 */

package com.lie.composeanimationkit.animation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lie.composeanimationkit.AnimationKit
import kotlin.math.min

typealias SA = AnimationKit.SearchAnimation

/**
 * 雷达扫描动画
 * @param modifier 修饰符
 * @param color 主题色
 * @param strokeWidth 线条粗细
 */
@Composable
fun SA.RadarSearchAnimation(
    modifier: Modifier = Modifier.size(150.dp),
    color: Color = Color.Green,
    strokeWidth: Float = 10f,
) {
    //循环动画
    val trans = rememberInfiniteTransition()
    val rote by trans.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(5000, 0, LinearEasing),
            repeatMode = RepeatMode.Restart,
        )
    )

    Box {
        Canvas(modifier = modifier) {
            val realSize = min(size.width, size.height)

            val realR = (realSize - strokeWidth) / 2

            val r1 = realR / 3
            val r2 = realR * 2 / 3

            //三个圆圈
            drawCircle(color, r1, style = Stroke(strokeWidth))
            drawCircle(color, r2, style = Stroke(strokeWidth))
            drawCircle(color, realR, style = Stroke(strokeWidth))

            //十字坐标系
            drawLine(
                color,
                Offset(size.width / 2, (size.height - realSize) / 2),
                Offset(size.width / 2, (size.height + realSize) / 2),
                strokeWidth = strokeWidth
            )
            drawLine(
                color,
                Offset((size.width - realSize) / 2, size.height / 2),
                Offset((size.width + realSize) / 2, size.height / 2),
                strokeWidth = strokeWidth
            )
        }

        Canvas(modifier = modifier.rotate(rote)) {
            val realSize = min(size.width, size.height)
            //扫描视图
            drawCircle(
                Brush.sweepGradient(
                    0f to Color.Transparent,
                    0.05f to color,
                    0.051f to Color.Transparent

                ),
                realSize / 2,
            )
        }
    }
}

@Preview
@Composable
fun RadarSearchAnimationPreview() {
    SA.RadarSearchAnimation()
}

/**
 * 搜索动画，可内嵌子组件，但是注意是在BoxScope中，且默认的Alignment是居中
 * @param modifier 修饰符，最好在这里定义你要的size
 * @param color 初始中心的颜色
 * @param targetColor 散开时的颜色
 * @param size 涟漪最大的时候的size，单位是px，可能需要与本组件的大小不同
 * @param duration 从中间到最开的时间
 * @param content 子内容
 */
@Composable
fun SA.RippleSearchingAnimation(
    modifier: Modifier = Modifier.size(150.dp),
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
    SA.RippleSearchingAnimation()
}