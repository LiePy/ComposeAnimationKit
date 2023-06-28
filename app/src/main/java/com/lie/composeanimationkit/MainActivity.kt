/*
 * Copyright (c) 2022.
 * @username: LiePy
 * @file: MainActivity.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.app.main
 * @date: 2022/11/29 下午8:40
 */

package com.lie.composeanimationkit

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lie.composeanimationkit.animation.EatBeanPreview
import com.lie.composeanimationkit.animation.ExpandShrinkAnimationPreview
import com.lie.composeanimationkit.animation.HeartBeatPreview
import com.lie.composeanimationkit.animation.LoadingButtonPreview
import com.lie.composeanimationkit.animation.MultipleLinesAnimationPreview
import com.lie.composeanimationkit.animation.NinePointAnimationPreview
import com.lie.composeanimationkit.animation.PositronAnimationPreview
import com.lie.composeanimationkit.animation.RadarSearchAnimationPreview
import com.lie.composeanimationkit.animation.RippleSearchingAnimationPreview
import com.lie.composeanimationkit.animation.RotateExpansionArcAnimationPreview
import com.lie.composeanimationkit.animation.RotationAnimationPreview
import com.lie.composeanimationkit.animation.ThreePointAnimationPreview
import com.lie.composeanimationkit.animation.WaterWavePreview
import com.lie.composeanimationkit.ui.theme.ComposeAnimationKitTheme
import com.lie.composeanimationkit.view.SpiderWebRadarLineDiagramPreview

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var instance: ComponentActivity
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this@MainActivity

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

        // 启动悬浮Service
        startService(Intent(this, FloatService::class.java))
    }
}

@Composable
fun MainView() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(SINGLE_BOX_SIZE.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        //涟漪扩散动画
        item { MyContainer { RippleSearchingAnimationPreview() } }

        //雷达动画
        item { MyContainer { RadarSearchAnimationPreview() } }

        //旋转动画
        item { MyContainer { RotationAnimationPreview() } }

        //排水动画
        item { MyContainer { WaterWavePreview() } }

        //加载按钮
        item { MyContainer { LoadingButtonPreview() } }

        //心跳动画
        item { MyContainer { HeartBeatPreview() } }

        //吃豆豆动画
        item { MyContainer { EatBeanPreview() } }

        //多竖线规律伸缩动画
        item { MyContainer { MultipleLinesAnimationPreview() } }

        //经典三点加载动画
        item { MyContainer { ThreePointAnimationPreview() } }

        //九点加载动画
        item { MyContainer { NinePointAnimationPreview() } }

        //旋转伸缩圆弧加载动画
        item { MyContainer { RotateExpansionArcAnimationPreview() } }

        //电子加载动画
        item { MyContainer { PositronAnimationPreview() } }

        //折叠和展开方块加载动画
        item { MyContainer { ExpandShrinkAnimationPreview() } }

        //蛛网图
        item { MyContainer { SpiderWebRadarLineDiagramPreview() } }

    }
}

@Composable
fun MyContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier.size(SINGLE_BOX_SIZE.dp),
        border = BorderStroke(0.3.dp, Color.LightGray)
    )
    {
        Box(
            modifier = modifier.size(SINGLE_BOX_SIZE.dp),
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

/**
 * 在此修改页面展示的每个动画的大小尺寸
 */
const val SINGLE_BOX_SIZE = 120