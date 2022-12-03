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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lie.composeanimationkit.animation.RadarSearchAnimation
import com.lie.composeanimationkit.animation.RippleSearchAnimation
import com.lie.composeanimationkit.animation.RotationAnimation
import com.lie.composeanimationkit.ui.theme.ComposeAnimationKitTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAnimationKitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyVerticalGrid(
                        cells = GridCells.Adaptive(150.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            BorderBox {
                                AnimationKit.SearchAnimation.RippleSearchAnimation()
                            }
                        }
                        item {
                            BorderBox {
                                AnimationKit.SearchAnimation.RadarSearchAnimation()
                            }
                        }
                        item {
                            BorderBox {
                                AnimationKit.LoadingAnimation.RotationAnimation(
                                    modifier = Modifier.size(150.dp)
                                ) {
                                    Text(text = "I'm Roting~", modifier = Modifier.align(Alignment.Center))
                                }
                            }
                        }


                    }
                }
            }
        }
    }
}

@Composable
fun BorderBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier.border(0.5.dp, Color.Gray),
        contentAlignment = Alignment.Center
    )
    {
        content()
    }
}