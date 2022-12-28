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
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lie.composeanimationkit.utils.view.CircularProgressIndicator


@Composable
fun LA.LoadingButton() {
    var stateIndex by remember { mutableStateOf(0) }

    val buttonState1 = ButtonState(Size(120, 48))
    val buttonState2 = ButtonState(Size(48, 48))
    val buttonState3 = ButtonState(Size(120, 48))

    val contentAlpha1 = ContentAlpha(1f, 0f, 0f)
    val contentAlpha2 = ContentAlpha(0f, 1f, 0f)
    val contentAlpha3 = ContentAlpha(0f, 0f, 1f)

    val buttonState by animateValueAsState(
        targetValue = when (stateIndex % 3) {
            0 -> buttonState1
            1 -> buttonState2
            else -> buttonState3
        }, typeConverter = TwoWayConverter(convertToVector = { bs ->
            AnimationVector2D(
                bs.size.width.toFloat(),
                bs.size.height.toFloat()
            )

        }, convertFromVector = { vector: AnimationVector2D ->
            ButtonState(
                Size(vector.v1.toInt(), vector.v2.toInt()),
            )
        }),
        animationSpec = tween(3000, 0, LinearEasing)
    )


    val contentAlpha by animateValueAsState(
        targetValue = when (stateIndex % 3) {
            0 -> contentAlpha1
            1 -> contentAlpha2
            else -> contentAlpha3
        }, typeConverter = TwoWayConverter(convertToVector = { ca: ContentAlpha ->
            AnimationVector3D(
                ca.content1,
                ca.content2,
                ca.content3
            )

        }, convertFromVector = { vector: AnimationVector3D ->
            ContentAlpha(vector.v1, vector.v2, vector.v3)
        }),
        animationSpec = tween(3000, 0, LinearEasing)
    )

    val bgColor by animateColorAsState(
        targetValue = when (stateIndex % 3) {
            0 -> Color.Cyan
            1 -> Color.Yellow
            else -> Color.Green
        },
        animationSpec = tween(3000, 0, LinearEasing)
    )


    Box(
        modifier = Modifier
            .size(buttonState.size.width.dp, buttonState.size.height.dp)
            .clip(RoundedCornerShape(100.dp))
            .clickable { stateIndex++ }
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Upload", modifier = Modifier.alpha(contentAlpha.content1))

        CircularProgressIndicator(modifier = Modifier.alpha(contentAlpha.content2))

        Icon(
            imageVector = Icons.Default.Check, contentDescription = null,
            modifier = Modifier.alpha(contentAlpha.content3)
        )

    }

}

@Preview
@Composable
fun LoadingButtonPreview() {
    LA.LoadingButton()
}

//按钮状态
data class ButtonState(
    val size: Size,
)

//主要内容的透明度
data class ContentAlpha(
    val content1: Float,
    val content2: Float,
    val content3: Float
)