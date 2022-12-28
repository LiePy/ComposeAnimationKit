/*
 * Copyright (c) 2022. 
 * @username: LiePy
 * @file: LoadingAnimation.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.ComposeAnimationKit.main
 * @date: 2022/11/29 下午8:31
 */

package com.lie.composeanimationkit.animation

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview
import com.lie.composeanimationkit.AnimationKit

typealias LA = AnimationKit.LoadingAnimation

/**
 * 简单好用的旋转动画，传入需要旋转的子Composable作为内容
 *
 * @param modifier 修饰符
 * @param durationMillis 动画旋转一周所用的时间，时间越长，旋转速度越慢
 * @param delayTime 动画旋转一周后停留的时间
 * @param easing 动画效果，默认线性：LinearEasing
 * @param content 旋转的内容
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

/**
 *
 */
@Composable
fun LA.WaterWaveAnimation(
    modifier: Modifier = Modifier.fillMaxSize(),
    animColor: Color = Color.Blue,
    waveWidth: Int = 150,
    waveHeight: Int = 150,
    durationMillis_Y: Int = 10000,
    durationMillis_X: Int = 1000,
) {
    //记录当前布局尺寸State
    var canvasSize by remember { mutableStateOf(Size.Unspecified) }

    val transition = rememberInfiniteTransition()
    //水波的横向初相位
    val waveDxAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis_X, easing = LinearEasing)
        )
    )

    //水波的波幅，随水位下降而变得平缓
    val waveHeightAnim by transition.animateFloat(
        initialValue = waveHeight.toFloat(),
        targetValue = 0f,
        animationSpec = infiniteRepeatable(tween(durationMillis_Y, easing = LinearEasing))
    )

    // 水位高度，使用低级动画API 和 LaunchedEffect 搭配 key 来实现使用当前实际height
    val waterHeightAnim = remember { Animatable(0f) }
    LaunchedEffect(key1 = canvasSize, block = {
        waterHeightAnim.animateTo(
            canvasSize.height,
            initialVelocity = 0f,
            animationSpec = infiniteRepeatable(
                tween(durationMillis_Y, 0, LinearEasing),
            )
        )
    })

    Canvas(
        modifier = modifier,
        onDraw = {
            canvasSize = size
            translate(top = waterHeightAnim.value) {
                val path = Path()
                path.reset()
                val halfWaveWidth = waveWidth / 2
                path.moveTo(-waveWidth + (waveWidth * waveDxAnim), 0f)

                for (i in -waveWidth..(size.width.toInt() + waveWidth) step waveWidth) {
                    path.relativeQuadraticBezierTo(
                        halfWaveWidth.toFloat() / 2,
                        -waveHeightAnim,
                        halfWaveWidth.toFloat(),
                        0f
                    )
                    path.relativeQuadraticBezierTo(
                        halfWaveWidth.toFloat() / 2,
                        waveHeightAnim,
                        halfWaveWidth.toFloat(),
                        0f
                    )
                }
                path.lineTo(size.width, size.height)
                path.lineTo(0f, size.height)
                path.close()
                drawPath(path = path, color = animColor)
            }
        }
    )
}

@Preview
@Composable
fun WaterWavePreview() {
    LA.WaterWaveAnimation()
}