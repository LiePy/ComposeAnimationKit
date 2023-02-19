/*
 * Copyright (c) 2023.
 * @username: LiePy
 * @file: WaterWaveAnimation.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.ComposeAnimationKit.main
 * @date: 2023/2/19 下午9:30
 */

package com.lie.composeanimationkit.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview
import com.lie.composeanimationkit.AnimationKit


/**
 * @desc 水位升降动画
 * @param modifier 修饰器
 * @param animColor 水波颜色
 * @param waveWidth 水波波长
 * @param waveHeight 水波波幅
 * @param durationMillis_Y 排水时间
 * @param durationMillis_X 水波周期时长
 * @author LiePy
 * @date 2023/2/19
 */
@Composable
fun LA.WaterWaveAnimation(
    modifier: Modifier = Modifier,
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
        if (canvasSize.equals(Size.Unspecified)) {
            return@LaunchedEffect
        }
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

                //画二阶贝塞尔曲线
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
    AnimationKit.LoadingAnimation.WaterWaveAnimation(Modifier.fillMaxSize())
}