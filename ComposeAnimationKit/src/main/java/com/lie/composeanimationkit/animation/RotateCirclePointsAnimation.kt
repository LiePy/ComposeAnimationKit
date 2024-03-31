/*
 * Copyright (c) 2024.
 * @username: LiePy
 * @file: RotateCirclePointsAnimation.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.ComposeAnimationKit.main
 * @date: 2024/1/5 下午5:55
 */

package com.lie.composeanimationkit.animation

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.lie.composeanimationkit.view.calculateXY

/**
 * @desc: 经典多点转圈加载动画
 * @author: lei
 * @createDate: 2024/1/5
 */
@Composable
fun LA.RotateCirclePointsAnimation(
    modifier: Modifier = Modifier.fillMaxSize(),
    durationMillis: Int = 2000,
    delayTime: Int = 0,
    easing: Easing = FastOutSlowInEasing,
    radiusMax: Float = 20f,
    radiusMin: Float = 3f,
    pointsColor: Color = Color.White,
    pointsNum: Int = 10,
    overRadius: Float = 100f
) {
    RotationAnimation(
        modifier,
        durationMillis,
        delayTime,
        easing,
    ) {
        Canvas(modifier = modifier) {
            (0 until pointsNum).forEach {
                val pointRadius = radiusMin + (radiusMax - radiusMin) * it / (pointsNum - 1)
                //计算各个顶点坐标
                val (x, y) = calculateXY(360f / pointsNum * it, overRadius)
                drawCircle(pointsColor, pointRadius, center = Offset(x, y))
            }
        }
    }
}

@Preview
@Composable
fun RotateCirclePointsAnimationPreview() {
    LA.RotateCirclePointsAnimation()
}