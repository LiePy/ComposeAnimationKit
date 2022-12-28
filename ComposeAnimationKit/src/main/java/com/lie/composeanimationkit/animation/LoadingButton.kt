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
import kotlin.math.min


@Composable
fun LA.LoadingButton(
    modifier: Modifier = Modifier,
    colors: LoadingButtonColors = defaultLoadingButtonColors,
    size: Size = Size(120, 58),
    durationMillis: Int = 300
) {
    var stateIndex by remember { mutableStateOf(0) }

    val sizeMin = min(size.height, size.width)
    val buttonSize2 = Size(sizeMin, sizeMin)

    val contentAlpha1 = ContentAlpha(1f, 0f, 0f)
    val contentAlpha2 = ContentAlpha(0f, 1f, 0f)
    val contentAlpha3 = ContentAlpha(0f, 0f, 1f)

    val buttonState by animateValueAsState(
        targetValue = when (stateIndex % 3) {
            1 -> buttonSize2
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
        animationSpec = tween(durationMillis, 0, LinearEasing)
    )

    val bgColor by animateColorAsState(
        targetValue = when (stateIndex % 3) {
            0 -> Color.Cyan
            1 -> Color.Yellow
            else -> Color.Green
        },
        animationSpec = tween(durationMillis, 0, LinearEasing)
    )

    Box(modifier,
        contentAlignment = Alignment.Center) {
        Box(
            modifier = modifier
                .size(buttonState.width.dp, buttonState.height.dp)
                .clip(RoundedCornerShape(100.dp))
                .clickable { stateIndex++ }
                .background(bgColor)
                .border(4.dp, Color(0xBBE4E4E4), RoundedCornerShape(100.dp)),

        )

        Text(text = "Upload", modifier = Modifier.alpha(contentAlpha.content1))

        CircularProgressIndicator(modifier = Modifier
            .alpha(contentAlpha.content2)
            .size(sizeMin.dp))

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

//主要内容的透明度
data class ContentAlpha(
    val content1: Float,
    val content2: Float,
    val content3: Float
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

