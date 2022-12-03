/*
 * Copyright (c) 2022.
 * @username: LiePy
 * @file: MyAnnotation.kt
 * @project: ComposeAnimationKit
 * @model: ComposeAnimationKit.ComposeAnimationKit.main
 * @date: 2022/12/3 下午8:54
 */

package com.lie.composeanimationkit.utils.annotation

/**
 * 处于测试阶段的动画，若有发现异常请提交issue至：https://github.com/LiePy/ComposeAnimationKit
 */
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FUNCTION
)
annotation class TestAnimation(val info: String = "")


/**
 * 正处于开发阶段，可能尚无法正常使用
 */
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FUNCTION
)
annotation class DevelopingAnimation(val info: String = "")

