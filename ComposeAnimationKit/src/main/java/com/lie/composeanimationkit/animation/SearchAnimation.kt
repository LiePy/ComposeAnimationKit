/*
 * Copyright (c) 2022.
 * @username: LiePy
 * @file: SearchAnimation.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.ComposeAnimationKit.main
 * @date: 2022/11/29 下午8:33
 */

package com.lie.composeanimationkit.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.lie.composeanimationkit.AnimationKit
import kotlin.math.min

typealias SA = AnimationKit.SearchAnimation

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

            //三个圆圈
            drawCircle(color, realSize / 6, style = Stroke(strokeWidth))
            drawCircle(color, realSize * 2 / 6, style = Stroke(strokeWidth))
            drawCircle(color, realSize / 2, style = Stroke(strokeWidth))

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