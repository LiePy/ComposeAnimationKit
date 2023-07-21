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
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

/**
 * @desc
 * @author LiePy
 * @date 2023/6/20
 */
@Composable
fun ItemPage() {
    var parentSize by remember { mutableStateOf(IntSize(0, 0)) }

    Box(modifier = Modifier
        .fillMaxSize()
        .onSizeChanged {
            parentSize = it
        }
        .background(Color.Cyan)
    ) {
        var expended by remember { mutableStateOf(false) }
        val transition = updateTransition(targetState = expended, label = "")
        val width by transition.animateInt(label = "") {
            if (it) parentSize.width else 200
        }
        val height by transition.animateInt(label = "") {
            if (it) parentSize.height else 200
        }


        Box(modifier = Modifier
            .background(Color.Green)
            .width(width.dp)
            .height(height.dp)
            .clickable {
                expended = !expended
            }) {

        }
    }
}

@Preview
@Composable
fun ItemPagePreview() {
    ItemPage()
}