/*
 * Copyright (c) 2023.
 * @username: LiePy
 * @file: NinePointAnimation.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.ComposeAnimationKit.main
 * @date: 2023/2/19 下午10:04
 */

package com.lie.composeanimationkit.animation

import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


/**
 * @desc: 经典三点加载的扩展版本，九点加载
 * @param modifier 修饰符
 * @param color 点的颜色
 * @param duration 动画周期时长，单位/millis
 * @param radiusMin 点最小时半径，单位/px
 * @param radiusMax 点最大时半径，单位/px
 * @author: LiePy
 * @date: 2023/2/13 17:57
 */
@Composable
fun NinePointAnimation(
    modifier: Modifier = Modifier,
    color: Color = Color.Cyan,
    duration: Int = 500,
    radiusMin: Float = 5f,
    radiusMax: Float = 20f,
    ) {
    val infiniteTransition = rememberInfiniteTransition()
    val radius0 by getRadius(infiniteTransition, duration, 0, radiusMin, radiusMax)
    val radius1 by getRadius(infiniteTransition, duration, duration / 3, radiusMin, radiusMax)
    val radius2 by getRadius(infiniteTransition, duration, duration * 2 / 3, radiusMin, radiusMax)
    val radius3 by getRadius(infiniteTransition, duration, duration / 3, radiusMin, radiusMax)
    val radius4 by getRadius(infiniteTransition, duration, duration * 2 / 3, radiusMin, radiusMax)
    val radius5 by getRadius(infiniteTransition, duration, 0, radiusMin, radiusMax)
    val radius6 by getRadius(infiniteTransition, duration, duration * 2 / 3, radiusMin, radiusMax)
    val radius7 by getRadius(infiniteTransition, duration, 0, radiusMin, radiusMax)
    val radius8 by getRadius(infiniteTransition, duration, duration * 1 / 3, radiusMin, radiusMax)
    val distance by remember{ mutableStateOf(radiusMax * 3) }

    Canvas(modifier = modifier) {
        drawCircle(color, radius0, center = center.plus(Offset(-distance, distance)))
        drawCircle(color, radius1, center = center.plus(Offset(0f, distance)))
        drawCircle(color, radius2, center = center.plus(Offset(distance, distance)))
        drawCircle(color, radius3, center = center.plus(Offset(-distance, 0f)))
        drawCircle(color, radius4, center = center)
        drawCircle(color, radius5, center = center.plus(Offset(distance, 0f)))
        drawCircle(color, radius6, center = center.plus(Offset(-distance, -distance)))
        drawCircle(color, radius7, center = center.plus(Offset(0f, -distance)))
        drawCircle(color, radius8, center = center.plus(Offset(distance, -distance)))
    }
}


@Preview
@Composable
fun NinePointAnimationPreview() {
    NinePointAnimation(modifier = Modifier.fillMaxSize())
}