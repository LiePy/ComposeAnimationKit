# ComposeAnimationKit

这是一个关于Android，基于Jetpack-Compose框架的动画、view或viewGroup的集合

持续更新中。。。

如果你看到一些比较有趣的动画，如果这里没有，欢迎提交issue

如果你也热爱compose，如果你也追求优雅，欢迎加入~

# 动画示例：

<img src="https://gitee.com/lie_py/compose-animation-kit-preview/raw/main/SamplePreview/SamplePage2023_1_14.gif"/>

| 预览图                                                                                                                                            | 名称                      |
|------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------|
| <img src="https://gitee.com/lie_py/compose-animation-kit-preview/raw/main/SearchAnimation/RadarSearchAnimation.gif" width="48" height="48" />  | [RadarSearchAnimation]  |
| <img src="https://gitee.com/lie_py/compose-animation-kit-preview/raw/main/SearchAnimation/RippleSearchAnimation.gif" width="48" height="48" /> | [RippleSearchAnimation] |

# 接入文档

## 一、在你的Android项目中导入ComposeAnimationKit

```groovy
//历史版本及最新版本请查看：https://s01.oss.sonatype.org/#nexus-search;quick~ComposeAnimationKit
implementation 'io.github.LiePy:ComposeAnimationKit:1.1.2'  //最新版本
```

## 二、使用动画

```kotlin
import com.lie.composeanimationkit.animation.RotationAnimation

AnimationKit.LoadingAnimation.RotationAnimation(
    modifier = Modifier.size(150.dp)
) {
    Text(text = "I'm Roting~", modifier = Modifier.align(Alignment.Center))
}
```

