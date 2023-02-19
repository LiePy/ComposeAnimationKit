/*
 * Copyright (c) 2023.
 * @username: LiePy
 * @file: EatBeanAnimation.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.ComposeAnimationKit.main
 * @date: 2023/2/18 上午12:53
 */

package com.lie.composeanimationkit.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.lie.composeanimationkit.AnimationKit

/**
 * @desc 吃豆豆动画
 * @param modifier 修饰符
 * @param headColor 脑袋的颜色
 * @param beanColor 豆子的颜色
 * @param headRadius 脑袋的半径
 * @param beanRadius 豆子的半径
 * @author LiePy
 * @date 2023/2/18
 */
@Composable
fun LA.EatBeanAnimation(
    modifier: Modifier,
    headColor: Color = Color.Cyan,
    beanColor: Color = Color.Cyan,
    headRadius: Float = 100f,
    beanRadius: Float = 10f,
) {

    val infiniteTransition = rememberInfiniteTransition()
    val roteDr by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(
            tween(
                500,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    val dx by infiniteTransition.animateFloat(
        initialValue = headRadius / 2 + beanRadius,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            tween(
                1000,
                easing = LinearEasing
            ),
            initialStartOffset = StartOffset(500)
        )
    )

    Canvas(modifier = modifier) {
        drawArc(
            headColor,
            40f - roteDr,
            280f + roteDr * 2,
            true,
            center - Offset(headRadius / 2, headRadius / 2),
            size = Size(headRadius, headRadius)
        )

        drawCircle(beanColor, beanRadius, center + Offset(dx, 0f))
    }
}

@Preview
@Composable
fun EatBeanPreview() {
    AnimationKit.LoadingAnimation.EatBeanAnimation(modifier = Modifier.fillMaxSize())
}