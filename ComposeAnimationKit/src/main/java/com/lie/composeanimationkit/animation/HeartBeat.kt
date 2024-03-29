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
import com.lie.composeanimationkit.AnimationKit
import kotlin.math.min

/**
 * @desc 爱心跳动
 * @param modifier 修饰符，需要在此指定大小size
 * @param color 爱心的颜色
 * @param duration 跳动一次的周期时长
 * @author LiePy
 * @date 2023/2/13
 */
@Composable
fun LA.HeartBeat(
    modifier: Modifier = Modifier,
    color: Color = Color.Cyan,
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
        path.moveTo(center.x, center.y - minSize * 0.4f)
        path.relativeCubicTo(
            minSize / 4, -minSize / 4,
            minSize / 2, 0f,
            minSize / 2, minSize / 5
        )
        path.relativeCubicTo(
            0f, minSize / 3,
            -minSize * 0.375f, minSize * 0.375f,
            -minSize / 2, minSize * 3 / 4
        )

        //左半边爱心
        path.moveTo(center.x, center.y - minSize * 0.4f)
        path.relativeCubicTo(
            -minSize / 4, -minSize / 4,
            -minSize / 2, 0f,
            -minSize / 2, minSize / 5
        )
        path.relativeCubicTo(
            0f, minSize / 3,
            minSize * 0.375f, minSize * 0.375f,
            minSize / 2, minSize * 3 / 4
        )
        drawPath(path, color, alpha)
    }
}

@Preview
@Composable
fun HeartBeatPreview() {
    AnimationKit.LoadingAnimation.HeartBeat(modifier = Modifier.fillMaxSize())
}