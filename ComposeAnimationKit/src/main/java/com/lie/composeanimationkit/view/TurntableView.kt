/*
 * Copyright (c) 2023.
 * @username: LiePy
 * @file: SpiderWebRadarLineDiagram.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.ComposeAnimationKit.main
 * @date: 2023/3/22 下午7:46
 */

package com.lie.composeanimationkit.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.*
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import java.lang.Float.min
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

/**
 * @description: 蜘蛛网雷达折线图
 * @author: 三石
 * @date: 2023/3/13 17:30
 *
 * @param modifier 修饰符
 * @param dataList 需要绘制的数据列表
 * @param labelList 数据列表对应的标签
 * @param layerNum 绘制蛛网的层数
 * @param maxData_ 最外层蛛网代表的最大值，为空则取 dataList 中的最大值
 */
@OptIn(ExperimentalTextApi::class)
@Composable
fun TurntableView(
    modifier: Modifier,
    labelList: List<String>,
    count_: Int? = null
) {
    //数据长度和标签长度判断处理，若不相等或为空抛出异常
//    if (dataList.size != labelList.size || dataList.isEmpty()) {
//        throw IllegalArgumentException("dataList.size can not be empty,and it must equals to paramList.size!")
//    }
    //计算数据长度，用于确定绘制几边形
    val count = count_ ?: labelList.size
    //确定最外层代表的数值上限
//    val maxData = maxData_ ?: dataList.maxOf { it }

    //drawText()绘制文本要用到
    val textMeasurer = rememberTextMeasurer()
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
    //定义衰减动画的衰减属性，指数衰减、摩擦力和临界值
    val exponentDecay = exponentialDecay<Float>(0.1f, 1f)
    //记录上一次onDrag的时间，用于计算两次onDrag的间隔时间
    var lastOnDragTime by remember { mutableStateOf(0L) }

    Canvas(modifier = modifier
        .onSizeChanged {
            //记录Canvas中心点坐标
            centerPoint = Offset(it.width / 2f, it.height / 2f)
        }
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
        val edge = min(size.height, size.width)
        //计算多边形相接圆的半径
        val radius = edge / 2
        val myCenter = center
        //计算多边形相邻顶点的圆心角
        val roteStep = 360f / count
        //画中心点
        drawCircle(Color.Black, 7.5f, myCenter)
        rotate(rotation + flingRotation.value) {
            var startAngle = 0f
            for (i in 0 until count) {
                drawArc(
                    Color.hsv(i * roteStep, 1f, 1f),
                    startAngle, roteStep, true,
                    Offset(0f, (size.height - size.width) / 2),
                    Size(size.width, size.width)
                )
                startAngle += roteStep
            }
        }
        drawCircle(Color(0xffeeeeee), size.width/6, myCenter)
        drawCircle(Color(0xffdddddd), size.width/6, myCenter, 1f, Stroke(9f))

        val path1 = Path()
        path1.moveTo(size.width * 5 / 12, myCenter.y)
        path1.lineTo(myCenter.x, myCenter.y - size.height / 8)
        path1.lineTo(size.width * 7 / 12, myCenter.y)
        path1.close()
        drawPath(path1, Color(0xffeeeeee))
//        drawPath(path1, Color(0xffdddddd), 1f, Stroke(9f))

//        //画标签文本
//        drawParamLabel(
//            count, roteStep, radius, textMeasurer, labelList, rotation + flingRotation.value
//        )
    }
}

@Preview(showBackground = true)
@Composable
fun TurntablePreview() {
    TurntableView(
        modifier = Modifier.fillMaxSize(),
        labelList = (0 .. 5).toList().map { it.toString() },
//        count_ = 360
    )
}
