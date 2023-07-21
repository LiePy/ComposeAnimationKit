/*
 * Copyright (c) 2023.
 * @username: LiePy
 * @file: DragRotatableView.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.ComposeAnimationKit.main
 * @date: 2023/7/21 下午10:43
 */

package com.lie.composeanimationkit.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.atan2

/**
 * @description: 可拖动旋转组件
 * @author: 三石
 * @date: 2023/7/21 20:39
 *
 * @param modifier 修饰符
 * @param content 内容部分
 * @param exponentDecay 定义衰减动画的衰减属性，指数衰减、摩擦力和临界值
 */
@Composable
fun DragRotatableView(
    modifier: Modifier,
    content: @Composable () -> Unit,
    exponentDecay:DecayAnimationSpec<Float> = exponentialDecay(0.5f, 1f)
) {
    //获取协程作用域
    val coroutineScope = rememberCoroutineScope()
    //记录计算的旋转角度
    var rotation by remember { mutableStateOf(0f) }
    //记录手指每次移动的起始点
    var startPoint by remember { mutableStateOf(Offset.Zero) }
    //记录手指每次移动的终点
    var endPoint by remember { mutableStateOf(Offset.Zero) }
    //记录Canvas在大小确定时的中心点
    var centerPoint by remember { mutableStateOf(Offset.Zero) }
    //drag最后一次的速度，作为fling开始的速度
    var flingStartSpeed by remember { mutableStateOf(0f) }
    //手指松开后的惯性旋转角度
    val flingRotation = remember { Animatable(0f) }
    //记录上一次onDrag的时间，用于计算两次onDrag的间隔时间
    var lastOnDragTime by remember { mutableStateOf(0L) }

    Box(modifier = modifier
        .onSizeChanged {
            //记录Canvas中心点坐标
            centerPoint = Offset(it.width / 2f, it.height / 2f)
        }
        .rotate(rotation + flingRotation.value)
        //手指拖动转动
        .pointerInput(Unit) {
            detectDragGestures(onDragStart = { point ->
                startPoint = point
                //新的拖动手势触发时，立刻停止上一次的fling
                coroutineScope.launch {
                    flingRotation.animateDecay(0f, exponentDecay)
                }
            }, onDragEnd = {
                //拖动手势结束时，开始fling
                coroutineScope.launch {
                    flingRotation.animateDecay(flingStartSpeed, exponentDecay)
                }
            }) { _, dragAmount ->
                endPoint = startPoint + dragAmount
                //这里Math.atan2函数对正负做了处理，所以不需要分象限处理
                (atan2(endPoint.y - centerPoint.y, endPoint.x - centerPoint.x) - atan2(
                    startPoint.y - centerPoint.y, startPoint.x - centerPoint.x
                )).let { radian ->
                    //弧度制转换成角度单位
                    Math
                        .toDegrees(radian.toDouble())
                        .toFloat()
                        .let { rota ->
                            rotation += rota
                            System
                                .currentTimeMillis()
                                .let { currentTime ->
                                    //计算每秒钟旋转的速度
                                    flingStartSpeed = rota * 1000 / (currentTime - lastOnDragTime)
                                    lastOnDragTime = currentTime
                                }
                        }
                }
                startPoint = endPoint
            }
        }
        //点击停止fling转动
        .pointerInput(Unit) {
            detectTapGestures {
                coroutineScope.launch {
                    flingRotation.animateDecay(0f, exponentDecay)
                }
            }
        }) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun DragRotatablePreview() {
    DragRotatableView(
        modifier = Modifier.fillMaxSize(),
        content = {
            Box(modifier = Modifier.size(200.dp), contentAlignment = Alignment.Center) {
                Text(text = "hello world!")
            }
        }
    )
}
