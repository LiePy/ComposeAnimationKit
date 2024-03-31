///*
// * Copyright (c) 2024.
// * @username: LiePy
// * @file: LoadingButtonKtTest.kt
// * @project: ComposeAnimationKit
// * @model: ComposeAnimationKit.ComposeAnimationKit.androidTest
// * @date: 2024/1/5 下午3:01
// */
//
//package com.lie.composeanimationkit.animation
//
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.test.assert
//import androidx.compose.ui.test.hasClickAction
//import androidx.compose.ui.test.hasContentDescription
//import androidx.compose.ui.test.hasProgressBarRangeInfo
//import androidx.compose.ui.test.hasSetTextAction
//import androidx.compose.ui.test.hasText
//import androidx.compose.ui.test.junit4.createComposeRule
//import androidx.compose.ui.test.performTouchInput
//import com.lie.composeanimationkit.AnimationKit
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.runBlocking
//import org.junit.Assert.*
//import org.junit.Rule
//
//import org.junit.Test
//
///**
// * @desc:
// * @author: lei
// * @createDate: 2024/1/5
// */
//class LoadingButtonKtTest {
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    // use createAndroidComposeRule<YourActivity>() if you need access to an activity
//    @Test
//    fun testLoadingButton() {
//        var state: LoadingButtonState
//        composeTestRule.setContent {
//            state = rememberLoadingButtonState()
//
//            AnimationKit.LoadingAnimation.LoadingButton(state = state) {
//                //点击切换到下一状态
////                state.nextState(true)
//
//                //一般使用以下用法，根据具体当前状态来分发点击事件
//                when (it) {
//                    is LoadingState.Ready ->
//                        state.changeToState(LoadingState.Loading)
//
//                    is LoadingState.Loading ->
//                        state.changeToState(LoadingState.Success)
//
//                    else -> state.changeToState(LoadingState.Ready)
//                }
//            }
//
//        }
//
//
//        composeTestRule
//            .onNode(hasClickAction())
//            .assert(hasText("Upload")) // hasText is a SemanticsMatcher
//
//        composeTestRule.onNode(hasClickAction() or hasSetTextAction()).performTouchInput {
//
////            runBlocking {
//                down(0, Offset.Zero)
//                up(1)
//
////                delay(200)
//
//                down(2, Offset.Zero)
//                up(3)
////            }
//
//        }
//
////        runBlocking {
////            delay(500)
//
//            composeTestRule
//                .onNode(hasClickAction())
//                .assert(hasContentDescription("success")) // hasText is a SemanticsMatcher
////        }
//
//    }
//}