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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lie.composeanimationkit.animation.*
import com.lie.composeanimationkit.ui.theme.ComposeAnimationKitTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                   MainView()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainView() {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(150.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        //涟漪扩散动画
        item {
            MyContainer {
                AnimationKit.SearchAnimation.RippleSearchAnimation(modifier = Modifier.fillMaxSize())
            }
        }

        //雷达动画
        item {
            MyContainer {
                AnimationKit.SearchAnimation.RadarSearchAnimation(modifier = Modifier.fillMaxSize())
            }
        }

        //旋转动画
        item {
            MyContainer {
                AnimationKit.LoadingAnimation.RotationAnimation(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "I'm Roting~",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        //排水动画
        item {
            MyContainer {
                AnimationKit.LoadingAnimation.WaterWaveAnimation(modifier = Modifier.fillMaxSize())
            }
        }

        //加载按钮
        item {
            MyContainer {
                val state = rememberLoadingButtonState()

                AnimationKit.LoadingAnimation.LoadingButton(state = state) {
                    state.nextState(true)
                }

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
        }

        //心跳动画
        item {
            MyContainer {
                HeartBeat(modifier = Modifier.fillMaxSize())
            }
        }


    }
}

@Composable
fun MyContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier.size(150.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    )
    {
        Box(
            modifier = modifier.size(150.dp),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Preview
@Composable
fun MainViewPreview() {
    MainView()
}