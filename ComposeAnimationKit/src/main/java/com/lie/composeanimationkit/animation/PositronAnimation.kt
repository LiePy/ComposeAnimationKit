/*
 * Copyright (c) 2023.
 * @username: LiePy
 * @file: PositronAnimation.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.ComposeAnimationKit.main
 * @date: 2023/2/19 下午10:38
 */

package com.lie.composeanimationkit.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview


/**
 * @desc: 像电子运动的动画
 * @param modifier 修饰符
 * @param color 圆弧线的颜色
 * @param radiusMin 点最小时半径，单位/px
 * @param radiusMax 点最大时半径，单位/px
 * @param width 圆弧线段的宽度，单位/px
 * @param duration 动画周期时长，单位/millis
 * @author: LiePy
 * @date: 2023/2/16 17:38
 */
@Composable
fun PositronAnimation(
    modifier: Modifier = Modifier,
    color: Color = Color.Cyan,
    radiusMin: Float = 50f,
    radiusMax: Float = 80f,
    width: Float = 8f,
    duration: Int = 800
) {
    val infiniteTransition = rememberInfiniteTransition()

    //旋转角度
    val rote by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(duration, easing = LinearOutSlowInEasing),
            RepeatMode.Restart
        )
    )

    //变化大小
    val desSize by infiniteTransition.animateFloat(
        initialValue = radiusMin,
        targetValue = radiusMax,
        animationSpec = infiniteRepeatable(
            tween(duration, easing = LinearOutSlowInEasing),
            RepeatMode.Reverse
        )
    )

    Canvas(modifier = modifier.rotate(rote)) {
        val path = Path()
        path.addArc(
            Rect(
                center.x - desSize,
                center.y - desSize,
                center.x + desSize,
                center.y + desSize
            ), 0f, 60f
        )
        path.addArc(
            Rect(
                center.x - desSize,
                center.y - desSize,
                center.x + desSize,
                center.y + desSize
            ), 180f, 60f
        )
        drawPath(path, color, style = Stroke(width = width))
        drawCircle(color, desSize - 30f)
    }
}

@Preview
@Composable
fun PositronAnimationPreview() {
    PositronAnimation(Modifier.fillMaxSize())
}