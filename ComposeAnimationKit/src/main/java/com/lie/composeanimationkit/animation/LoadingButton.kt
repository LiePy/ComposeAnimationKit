/*
 * Copyright (c) 2022.
 * @username: LiePy
 * @file: LoadingButton.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.ComposeAnimationKit.main
 * @date: 2022/12/28 下午1:36
 */

package com.lie.composeanimationkit.animation

import android.util.Size
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lie.composeanimationkit.utils.view.CircularProgressIndicator
import kotlin.math.min

/**
 * @desc 带加载状态的按钮
 * @author LiePy
 * @date 2023/1/14
 */
@Composable
fun LA.LoadingButton(
    modifier: Modifier = Modifier,
    state: LoadingButtonState = rememberLoadingButtonState(),
    colors: LoadingButtonColors = defaultLoadingButtonColors,
    size: Size = Size(120, 58),
    durationMillis: Int = 300,
    readyContent: @Composable () -> Unit = {
        Text(text = "Upload", color = colors.onReadyContentColor)
    },
    successContent: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Default.Check, contentDescription = null,
            tint = colors.onSuccessContentColor
        )
    },
    errorContent: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Default.Delete, contentDescription = null,
            tint = colors.onErrorContentColor
        )
    },
    onClicked: () -> Unit = {}
) {

    val sizeMin = min(size.height, size.width)
    val buttonSize2 = Size(sizeMin, sizeMin)

    val contentAlpha1 = ContentAlpha(1f, 0f, 0f, 0f)
    val contentAlpha2 = ContentAlpha(0f, 1f, 0f, 0f)
    val contentAlpha3 = ContentAlpha(0f, 0f, 1f, 0f)
    val contentAlpha4 = ContentAlpha(0f, 0f, 0f, 1f)

    //按钮尺寸动画，自定义animate Value
    val buttonState by animateValueAsState(
        targetValue = when (state.currentState) {
            LoadingState.Loading -> buttonSize2
            else -> size
        }, typeConverter = TwoWayConverter(convertToVector = { bs: Size ->
            AnimationVector2D(
                bs.width.toFloat(),
                bs.height.toFloat()
            )
        }, convertFromVector = { vector: AnimationVector2D ->
            Size(vector.v1.toInt(), vector.v2.toInt())
        }),
        animationSpec = tween(durationMillis, 0, LinearEasing)
    )

    //内容透明度动画，自定义animate Value
    val contentAlpha by animateValueAsState(
        targetValue = when (state.currentState) {
            LoadingState.Ready -> contentAlpha1
            LoadingState.Loading -> contentAlpha2
            LoadingState.Success -> contentAlpha3
            LoadingState.Error -> contentAlpha4
        }, typeConverter = TwoWayConverter(convertToVector = { ca: ContentAlpha ->
            AnimationVector4D(
                ca.content1,
                ca.content2,
                ca.content3,
                ca.content4
            )

        }, convertFromVector = { vector: AnimationVector4D ->
            ContentAlpha(vector.v1, vector.v2, vector.v3, vector.v4)
        }),
        animationSpec = tween(durationMillis, 0, LinearEasing)
    )

    //背景色动画
    val bgColor by animateColorAsState(
        targetValue = when (state.currentState) {
            LoadingState.Ready -> colors.onReadyBackgroundColor
            LoadingState.Loading -> colors.onLoadingBackgroundColor
            LoadingState.Success -> colors.onSuccessBackGroundColor
            LoadingState.Error -> colors.onErrorBackgroundColor
        },
        animationSpec = tween(durationMillis, 0, LinearEasing)
    )

    //边框动画
    val borderWidth by animateDpAsState(
        targetValue = when (state.currentState) {
            LoadingState.Loading -> 4.dp
            else -> 0.dp
        },
        animationSpec = tween(durationMillis, 0, LinearEasing)
    )

    Box(
        modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = modifier
                .size(buttonState.width.dp, buttonState.height.dp)
                .clip(RoundedCornerShape(100.dp))
                .clickable { onClicked() }
                .background(bgColor)
                .border(borderWidth, Color(0xBBFFFFFF), RoundedCornerShape(100.dp)),

            )

        //初始时显示
        Box(
            modifier = Modifier.alpha(contentAlpha.content1),
            contentAlignment = Alignment.Center
        ) {
            readyContent()
        }

        //加载时显示
        CircularProgressIndicator(
            modifier = Modifier
                .alpha(contentAlpha.content2)
                .size(sizeMin.dp)
        )

        //成功时显示
        Box(modifier = Modifier.alpha(contentAlpha.content3)) {
            successContent()
        }

        //错误或失败时显示
        Box(modifier = Modifier.alpha(contentAlpha.content4)) {
            errorContent()
        }

    }


}

@Preview
@Composable
fun LoadingButtonPreview() {
    LA.LoadingButton()
}

//主要内容的透明度
data class ContentAlpha(
    val content1: Float,
    val content2: Float,
    val content3: Float,
    val content4: Float
)

data class LoadingButtonColors(
    val onReadyBackgroundColor: Color = Color.Cyan,
    val onLoadingBackgroundColor: Color = Color.White,
    val onSuccessBackGroundColor: Color = Color.Green,
    val onErrorBackgroundColor: Color = Color.Red,
    val onReadyContentColor: Color = Color.White,
    val onLoadingContentColor: Color = Color.Blue,
    val onSuccessContentColor: Color = Color.White,
    val onErrorContentColor: Color = Color.White
)

val defaultLoadingButtonColors = LoadingButtonColors()

@Composable
fun rememberLoadingButtonState(
    initialState: LoadingState = LoadingState.Ready,
): LoadingButtonState = rememberSaveable(saver = LoadingButtonState.Saver) {
    LoadingButtonState(initialState)
}


class LoadingButtonState(val currentState: LoadingState = LoadingState.Ready) {

    companion object {
        /**
         * The default [Saver] implementation for [PagerState].
         */
        val Saver: Saver<LoadingButtonState, *> = listSaver(
            save = {
                listOf<Any>(
                    it.currentState.code,
                )
            },
            restore = {
                LoadingButtonState(
                    currentState = LoadingState.getState(it[0] as Int),
                )
            }
        )
    }
}


sealed class LoadingState(val code: Int) {
    object Ready : LoadingState(0)
    object Loading : LoadingState(1)
    object Success : LoadingState(2)
    object Error : LoadingState(3)

    companion object {
        fun getState(code: Int): LoadingState {
            return when (code) {
                0 -> Ready
                1 -> Loading
                2 -> Success
                else -> Error
            }
        }
    }
}
