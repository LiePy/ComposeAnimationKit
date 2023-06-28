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
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner

class FloatService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        openWindow()

        return super.onStartCommand(intent, flags, startId)
    }

    private fun openWindow() {
        val windowManager = MainActivity.instance.getSystemService(WINDOW_SERVICE) as WindowManager
        val layoutParams = WindowManager.LayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
//        layoutParams.token = this.getWindow().getDecorView().getWindowToken();  //这样设置，在activity中打开悬浮框可绕过权限；
        layoutParams.flags =
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        layoutParams.format = PixelFormat.TRANSLUCENT;  //透明
        layoutParams.gravity = Gravity.TOP or Gravity.RIGHT;  //右上角显示
        val view = LayoutInflater.from(this).inflate(R.layout.view_win, null).apply {
            // set ViewTreeLifecycleOwner
            ViewTreeLifecycleOwner.set(this@apply, MainActivity.instance)
        }
        view.findViewById<ComposeView>(R.id.tv_compose).apply {
            // set ViewTreeSavedStateRegistryOwner
            setViewTreeSavedStateRegistryOwner(MainActivity.instance)
            // set ViewTreeLifecycleOwner
            ViewTreeLifecycleOwner.set(this@apply, MainActivity.instance)

            setContent {
                FloatView()
            }
        }
        windowManager.addView(view, layoutParams);
    }
}

@Composable
fun FloatView() {
    Text(text = "Hello Compose Float!")
}