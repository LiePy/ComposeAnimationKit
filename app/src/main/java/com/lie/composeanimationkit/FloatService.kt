/*
 * Copyright (c) 2023.
 * @username: LiePy
 * @file: FloatService.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.app.main
 * @date: 2023/6/27 下午3:20
 */

package com.lie.composeanimationkit

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import kotlinx.coroutines.delay

class FloatService : Service(), LifecycleOwner, SavedStateRegistryOwner {
    private lateinit var windowManager: WindowManager
    private lateinit var lifecycleRegistry: LifecycleRegistry
    private lateinit var savedStateRegistryController: SavedStateRegistryController
    private val layoutParams: WindowManager.LayoutParams by lazy {
        buildLayoutParams()
    }

    private val view by lazy {
        buildComposeView()
    }

    override fun onCreate() {
        super.onCreate()
        // 以下顺序很重要，控制自身Service生命周期
        lifecycleRegistry = LifecycleRegistry(this)
        savedStateRegistryController = SavedStateRegistryController.create(this)
        savedStateRegistryController.performAttach()    // 此行可有可无，我们不调用performRestore内会帮我们调用
        savedStateRegistryController.performRestore(null)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        windowManager = this.getSystemService(WINDOW_SERVICE) as WindowManager

        openWindow()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

        super.onDestroy()
        closeWindow()
    }

    private fun buildLayoutParams(): WindowManager.LayoutParams {
        return WindowManager.LayoutParams().apply {
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            width = ViewGroup.LayoutParams.WRAP_CONTENT
            type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            flags =
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            format = PixelFormat.TRANSLUCENT  //透明
            gravity = Gravity.TOP or Gravity.END  //右上角显示
        }
    }

    private fun buildComposeView(): View {
        val view = LayoutInflater.from(this).inflate(R.layout.view_win, null).apply {
            // set ViewTreeLifecycleOwner
            ViewTreeLifecycleOwner.set(this@apply, this@FloatService)
        }
        view.findViewById<ComposeView>(R.id.tv_compose).apply {
            // set ViewTreeSavedStateRegistryOwner
            setViewTreeSavedStateRegistryOwner(this@FloatService)
            // set ViewTreeLifecycleOwner
            ViewTreeLifecycleOwner.set(this@apply, this@FloatService)

            setContent {
                FloatView()
            }
        }
        return view
    }

    private fun openWindow() {
        windowManager.addView(view, layoutParams)
    }

    private fun closeWindow() {
        windowManager.removeView(view)
    }

    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}

@Composable
fun FloatView() {
    var countDown by remember { mutableStateOf(0) }

    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    LaunchedEffect(key1 = Unit, block = {
        lifecycleScope.launchWhenResumed {
            while (true) {
                delay(1000)
                countDown++
            }
        }
    })

    Text(text = "Count= $countDown")
}
