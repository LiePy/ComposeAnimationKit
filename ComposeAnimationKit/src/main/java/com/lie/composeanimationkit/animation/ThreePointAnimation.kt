/*
 * Copyright (c) 2023. 
 * @username: LiePy
 * @file: ThreePointAnimation.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.ComposeAnimationKit.main
 * @date: 2023/2/19 下午10:01
 */

package com.lie.composeanimationkit.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


/**
 * @desc: 加载动画：三个点
 * @param modifier 修饰符
 * @param color 点的颜色
 * @param duration 动画周期时长，单位/millis
 * @param radiusMin 点最小时半径，单位/px
 * @param radiusMax 点最大时半径，单位/px
 * @author: LiePy
 * @date: 2023/2/13 16:55
 */

@Composable
fun ThreePointAnimation(
    modifier: Modifier = Modifier,
    color: Color = Color.Cyan,
    duration: Int = 500,
    radiusMin: Float = 5f,
    radiusMax: Float = 20f
) {
    val infiniteTransition = rememberInfiniteTransition()
    val radius0 by getRadius(infiniteTransition, duration, 0, radiusMin, radiusMax)
    val radius1 by getRadius(infiniteTransition, duration, duration / 3, radiusMin, radiusMax)
    val radius2 by getRadius(infiniteTransition, duration, duration * 2 / 3, radiusMin, radiusMax)
    val distance by remember{ mutableStateOf(radiusMax * 3) }

    Canvas(modifier = modifier) {
        drawCircle(color, radius0, center = center.minus(Offset(distance, 0f)))
        drawCircle(color, radius1, center = center)
        drawCircle(color, radius2, center = center.plus(Offset(distance, 0f)))
    }
}


@Composable
fun getRadius(
    transition: InfiniteTransition,
    durationMillis: Int,
    delay: Int,
    radiusMin: Float,
    radiusMax: Float
): State<Float> =
    transition.animateFloat(
        initialValue = radiusMin,
        targetValue = radiusMax,
        animationSpec = infiniteRepeatable(
            tween(durationMillis, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(delay)
        )
    )


@Preview
@Composable
fun ThreePointAnimationPreview() {
    ThreePointAnimation(modifier = Modifier.fillMaxSize())
}

