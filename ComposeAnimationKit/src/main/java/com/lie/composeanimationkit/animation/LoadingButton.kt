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
import com.lie.composeanimationkit.AnimationKit
import com.lie.composeanimationkit.utils.view.CircularProgressIndicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.min

/**
 * @desc 带加载状态的按钮
 * @param modifier 修饰符
 * @param state 定义按钮状态，一般使用[rememberLoadingButtonState]创建
 * @param colors 定义按钮颜色，@see [defaultLoadingButtonColors]
 * @param size 定义按钮大小
 * @param durationMillis 动画时长，该值越小动画越快
 * @param readyContent 初始显示的内容
 * @param successContent 成功时显示的内容
 * @param errorContent 失败时显示的内容
 * @param onClicked 点击事件处理
 * @author LiePy
 * @date 2022/12/28
 */
@Composable
fun LA.LoadingButton(
    modifier: Modifier = Modifier,
    state: LoadingButtonState = rememberLoadingButtonState(),
    colors: LoadingButtonColors = defaultLoadingButtonColors,
    size: Size = Size(120, 58),
    durationMillis: Int = 300,
    readyContent: @Composable () -> Unit = { defaultReadyContent() },
    successContent: @Composable () -> Unit = { defaultSuccessContent() },
    errorContent: @Composable () -> Unit = { defaultErrorContent() },
    onClicked: (LoadingState) -> Unit = {}
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
        animationSpec = tween(durationMillis, 0, LinearEasing), label = "buttonSize"
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
        animationSpec = tween(durationMillis, 0, LinearEasing), label = "contentAlpha"
    )

    //背景色动画
    val bgColor by animateColorAsState(
        targetValue = when (state.currentState) {
            LoadingState.Ready -> colors.onReadyBackgroundColor
            LoadingState.Loading -> colors.onLoadingBackgroundColor
            LoadingState.Success -> colors.onSuccessBackGroundColor
            LoadingState.Error -> colors.onErrorBackgroundColor
        },
        animationSpec = tween(durationMillis, 0, LinearEasing), label = "backgroundColor"
    )

    //边框动画
    val borderWidth by animateDpAsState(
        targetValue = when (state.currentState) {
            LoadingState.Loading -> 4.dp
            else -> 0.dp
        },
        animationSpec = tween(durationMillis, 0, LinearEasing), label = "borderWidth"
    )

    Box(
        modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = modifier
                .size(buttonState.width.dp, buttonState.height.dp)
                .clip(RoundedCornerShape(100.dp))
                .clickable { onClicked(state.currentState) }
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

@Composable
fun defaultReadyContent() {
    Text(text = "Upload", color = defaultLoadingButtonColors.onReadyContentColor)
}

@Composable
fun defaultSuccessContent() {
    Icon(
        imageVector = Icons.Default.Check, contentDescription = "success",
        tint = defaultLoadingButtonColors.onSuccessContentColor
    )
}

@Composable
fun defaultErrorContent() {
    Icon(
        imageVector = Icons.Default.Delete, contentDescription = "error",
        tint = defaultLoadingButtonColors.onErrorContentColor
    )
}

/**
 * 主要内容的透明度
 */
data class ContentAlpha(
    val content1: Float,
    val content2: Float,
    val content3: Float,
    val content4: Float
)

/**
 * 加载按钮的颜色
 */
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

/**
 * 默认颜色
 */
val defaultLoadingButtonColors = LoadingButtonColors()

/**
 * 一般使用该方法初始化创建加载按钮状态
 * @see LoadingButtonState
 */
@Composable
fun rememberLoadingButtonState(
    initialState: LoadingState = LoadingState.Ready,
): LoadingButtonState = rememberSaveable(saver = LoadingButtonState.Saver) {
    LoadingButtonState(initialState)
}

/**
 * 加载按钮状态容器
 * @see rememberLoadingButtonState
 */
class LoadingButtonState(currentState: LoadingState = LoadingState.Ready) {

    private var _currentState by mutableStateOf(currentState)

    companion object {
        /**
         * The default [Saver] implementation for [currentState].
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

    var currentState: LoadingState
        get() = _currentState
        internal set(value) {
            if (value != _currentState) {
                _currentState = value
            }
        }

    fun changeToState(newState: LoadingState) {
        _currentState = newState
    }

    fun nextState(isSuccess: Boolean) {
        _currentState = if (isSuccess) {
            when (_currentState) {
                is LoadingState.Ready -> LoadingState.Loading
                is LoadingState.Loading -> LoadingState.Success
                else -> LoadingState.Ready
            }
        } else {
            when (_currentState) {
                is LoadingState.Ready -> LoadingState.Loading
                is LoadingState.Loading -> LoadingState.Error
                else -> LoadingState.Ready
            }
        }
    }
}

/**
 * 加载状态密封类，定义具体的加载状态
 */
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

@Preview
@Composable
fun LoadingButtonPreview() {
    val state = rememberLoadingButtonState()

    AnimationKit.LoadingAnimation.LoadingButton(state = state) {
        //点击切换到下一状态
//                    state.nextState(true)

        //一般使用以下用法，根据具体当前状态来分发点击事件
        when (it) {
            is LoadingState.Ready ->
                state.changeToState(LoadingState.Loading)

            is LoadingState.Loading ->
                state.changeToState(LoadingState.Success)

            else -> state.changeToState(LoadingState.Ready)
        }
    }

    //这里是为了展示切换效果，使其自动切换
    LaunchedEffect(key1 = null, block = {
        launch {
            var success = true
            while (true) {
                delay(3000)
                state.nextState(success)
                success = !success
            }
        }
    })
}
