/*
 * Copyright (c) 2022. 
 * @username: LiePy
 * @file: RotationAnimation.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.ComposeAnimationKit.main
 * @date: 2022/11/29 下午8:31
 */

package com.lie.composeanimationkit.animation

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import com.lie.composeanimationkit.AnimationKit

typealias LA = AnimationKit.LoadingAnimation

/**
 * @desc 旋转动画，传入需要旋转的子Composable作为内容
 * @param modifier 修饰符
 * @param durationMillis 动画旋转一周所用的时间，时间越长，旋转速度越慢
 * @param delayTime 动画旋转一周后停留的时间
 * @param easing 动画效果，默认线性：LinearEasing
 * @param content 旋转的内容
 * @author LiePy
 * @date 2022/11/29
 */
@SuppressLint("NeverUsed")
@Composable
fun LA.RotationAnimation(
    modifier: Modifier = Modifier,
    durationMillis: Int = 3000,
    delayTime: Int = 0,
    easing: Easing = LinearEasing,
    content: @Composable BoxScope.() -> Unit
) {
    val tran = rememberInfiniteTransition()
    val rote by tran.animateFloat(
        initialValue = 0f, targetValue = 360f, animationSpec = infiniteRepeatable(
            tween(durationMillis, delayTime, easing)
        )
    )
    Box(
        modifier.rotate(rote)
    ) {
        content()
    }
}

@Preview
@Composable
fun RotationAnimationPreview() {
    AnimationKit.LoadingAnimation.RotationAnimation(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "I'm Roting~",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}