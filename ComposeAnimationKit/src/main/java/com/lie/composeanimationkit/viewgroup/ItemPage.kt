/*
 * Copyright (c) 2023.
 * @username: LiePy
 * @file: ItemPage.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.ComposeAnimationKit.main
 * @date: 2023/7/21 下午10:43
 */

package com.lie.composeanimationkit.viewgroup

import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import com.lie.composeanimationkit.utils.toDp

/**
 * @desc
 * @author LiePy
 * @date 2023/6/20
 */
@Composable
fun ItemPage() {
    // 记录当前最大尺寸
    var parentSize by remember { mutableStateOf(IntSize(0, 0)) }

    Box(modifier = Modifier
        .fillMaxSize()
        .onSizeChanged {
            parentSize = it
        }
        .background(Color.Cyan)) {

        // 记录当前选中的item index
        val currentItemIndex = remember { mutableStateOf(-1) }

        Column {
            ItemContainer(
                index = 0, current = currentItemIndex, parentSize = parentSize,
                itemSize = IntSize(parentSize.width, parentSize.height/3)
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Red)
                )
            }
            ItemContainer(
                index = 1, current = currentItemIndex, parentSize = parentSize,
                itemSize = IntSize(parentSize.width, parentSize.height/3)
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Green)
                )
            }
            ItemContainer(
                index = 2, current = currentItemIndex, parentSize = parentSize,
                itemSize = IntSize(parentSize.width, parentSize.height/3)
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Blue)
                )
            }
        }
    }
}

@Composable
fun ItemContainer(
    index: Int,
    current: MutableState<Int>,
    parentSize: IntSize,
    itemSize: IntSize,
    content: @Composable BoxScope.() -> Unit
) {

    val transition = updateTransition(targetState = index == current.value, label = "")
    val width by transition.animateInt(transitionSpec = { tween(300) }, label = "") {
        if (it) {
            parentSize.width
        } else if (current.value == -1) {
            itemSize.width
        } else {
            0
        }
    }
    val height by transition.animateInt(transitionSpec = { tween(300) }, label = "") {
        if (it) {
            parentSize.height
        } else if (current.value == -1) {
            itemSize.height
        } else {
            0
        }
    }
    Box(modifier = Modifier
        .width(width.toDp())
        .height(height.toDp())
        .clickable {
            if (current.value == index) {
                current.value = -1
            } else {
                current.value = index
            }
        }) {
        content()
    }
}

@Preview
@Composable
fun ItemPagePreview() {
    ItemPage()
}