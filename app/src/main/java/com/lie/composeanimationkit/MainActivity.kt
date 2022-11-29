/*
 * Copyright (c) 2022.
 * @username: LiePy
 * @file: MainActivity.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.app.main
 * @date: 2022/11/29 下午8:40
 */

package com.lie.composeanimationkit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.lie.composeanimationkit.ui.theme.ComposeAnimationKitTheme
import com.lie.composeanimationkit.animation.RotationAnimation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAnimationKitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Test("Android App")
                }
            }
        }
    }
}

@Composable
fun Test(name: String) {

    //AnimationKit库内的动画使用方法示例
    AnimationKit
        .LoadingAnimation
        .RotationAnimation {
            Box(modifier = Modifier.background(Color.Green)) {
                Text(text = "I am rotation")
            }
        }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeAnimationKitTheme {
        Test("Android")
    }
}