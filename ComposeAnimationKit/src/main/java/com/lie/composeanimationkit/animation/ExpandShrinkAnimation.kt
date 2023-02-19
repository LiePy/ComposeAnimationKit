/*
 * Copyright (c) 2023.
 * @username: LiePy
 * @file: ExpandShrinkAnimation.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.ComposeAnimationKit.main
 * @date: 2023/2/19 下午10:49
 */

package com.lie.composeanimationkit.animation

import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay

/**
 * @description: 折叠和展开方块加载动画
 * @param modifier 修饰符
 * @param color 方块颜色
 * @param width 方块宽
 * @param height 方块高
 * @param duration 动画周期时长
 * @author: LiePy
 * @date: 2023/2/16 17:38
 */
@Composable
fun ExpandShrinkAnimation(
    modifier: Modifier = Modifier,
    color: Color = Color.Cyan,
    width: Dp = 40.dp,
    height: Dp = 40.dp,
    duration: Int = 500
) {
    var count by remember{ mutableStateOf(0) }
    var count2 by remember{ mutableStateOf(0) }
    val visible by remember { derivedStateOf { count % 2 == 0 } }
    val lifeOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = true) {
        lifeOwner.lifecycleScope.launchWhenResumed {
            while (true) {
                count++
                if (visible) {
                    count2 ++
                }
                delay(duration.toLong())
            }
        }
    }

    Box(modifier = modifier) {
        AnimatedVisibility(
            visible = visible, modifier = Modifier.align(Alignment.Center),
            enter = if (count2 % 2 == 0) expandHorizontally(spring()) else expandVertically(spring()),
            exit = if (count2 % 2 == 0) shrinkVertically(spring()) else shrinkHorizontally(spring())
        ) {
            Box(modifier = Modifier.background(color).size(width, height))
        }
    }
}

@Preview
@Composable
fun ExpandShrinkAnimationPreview() {
    ExpandShrinkAnimation(Modifier.fillMaxSize())
}