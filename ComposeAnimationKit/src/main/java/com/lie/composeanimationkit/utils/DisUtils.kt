/*
 * Copyright (c) 2023.
 * @username: LiePy
 * @file: DisUtils.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.ComposeAnimationKit.main
 * @date: 2023/7/22 上午12:59
 */

package com.lie.composeanimationkit.utils

import android.content.res.Resources
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt


/**
 * @desc: 屏幕单位转换
 * @author: lei
 * @createDate: 2023/7/22
 */


fun Int.toDp() : Dp = (this / Resources.getSystem().displayMetrics.density + 0.5f).dp

fun Int.toDpInt() : Int = (this / Resources.getSystem().displayMetrics.density + 0.5f).roundToInt()

fun Int.toSp() : Int = (this * Resources.getSystem().displayMetrics.density + 0.5f).roundToInt()