/*
 * Copyright (c) 2022.
 * @username: LiePy
 * @file: RadarSearchAnimation.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.ComposeAnimationKit.main
 * @date: 2022/11/29 下午8:33
 */

package com.lie.composeanimationkit.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import com.lie.composeanimationkit.AnimationKit
import kotlin.math.min

/**
 * @desc 雷达扫描动画
 * @param modifier 修饰符
 * @param color 主题色
 * @param strokeWidth 线条粗细
 * @author LiePy
 * @date 2022/11/29
 */
@Composable
fun LA.RadarSearchAnimation(
    modifier: Modifier = Modifier,
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
    AnimationKit.LoadingAnimation.RadarSearchAnimation(Modifier.fillMaxSize())
}