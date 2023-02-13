/*
 * Copyright (c) 2023.
 * @username: LiePy
 * @file: HeartBeat.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.ComposeAnimationKit.main
 * @date: 2023/2/13 下午9:44
 */

package com.lie.composeanimationkit.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.min

/**
 * @desc 爱心跳动
 * @author LiePy
 * @date 2023/2/13
 */

@Composable
fun HeartBeat(
    modifier: Modifier = Modifier.fillMaxSize(),
    color: Color = Color.Red,
    duration: Int = 600
) {
    val transition = rememberInfiniteTransition()
    val alpha by transition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(duration),
            repeatMode = RepeatMode.Reverse
        )
    )

    val beatSize by transition.animateFloat(
        initialValue = 150f,
        targetValue = 50f,
        animationSpec = infiniteRepeatable(
            tween(duration),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(modifier = modifier) {
        //最小边作为正方形
        val minSize = min(size.height, size.width) - beatSize

        val path = Path()
        //右半边爱心
        path.moveTo(center.x, center.y - minSize / 3)
        path.relativeCubicTo(
            minSize / 4, -minSize / 4,
            minSize / 2, 0f,
            minSize / 2, minSize / 5
        )
        path.relativeCubicTo(
            0f, minSize / 3,
            -minSize * 3 / 8, minSize * 3 / 8,
            -minSize / 2, minSize * 3 / 4
        )

        //左半边爱心
        path.moveTo(center.x, center.y - minSize / 3)
        path.relativeCubicTo(
            -minSize / 4, -minSize / 4,
            -minSize / 2, 0f,
            -minSize / 2, minSize / 5
        )
        path.relativeCubicTo(
            0f, minSize / 3,
            minSize * 3 / 8, minSize * 3 / 8,
            minSize / 2, minSize * 3 / 4
        )
        drawPath(path, color, alpha = alpha)
    }
}

@Preview
@Composable
fun HeartBeatPre() {
    HeartBeat()
}