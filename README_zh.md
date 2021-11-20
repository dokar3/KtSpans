![](./arts/ktspans.png)

[README - English](./README_en.md)

# KtSpans

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.dokar3/ktspans/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.dokar3/ktspans)

启发自[kotlinx.html](https://github.com/Kotlin/kotlinx.html), KtSpans是一个提供了类似 **html** 和 **css** 的DSL，用于在Android上创建span的开源库。

# 如何使用?

在项目中添加依赖：

```groovy
implementation 'io.github.dokar3:ktspans:latest_version'
```

创建 Spanned：

```kotlin
textView.text = createSpanned {
    style {
        h3 {
            color = 0xFF8D07F6.toInt()
        }
        quote {
            stripeColor = 0x72AAAAAA
            stripeWidth = 2.dp
            gapWidth = 8.dp
        }
        "serif-font" {
            fontFamily = Typeface.SERIF
        }
    }

    h3 { +"::before" }
    p {
        b { +"KtSpans" }
        +" is inspired by "
        a("https://github.com/Kotlin/kotlinx.html") {
            +"kotlinx.html"
        }
    }
    br {}
    h3 { +"#now" }
    p {
        +"Make more fun with Android spans"
    }
    br {}
    h3 { +"::after" }
    quote {
        className = "serif-font"
        +"\"I never think of the future, it comes soon enough.\"—Albert Einstein"
    }
}
```

结果：

![](./arts/screenshot_01_clipped.png)

# 更多使用

### 标签内属性:

```kotlin
p {
    attrs {
        fontStyle = Typeface.BOLD
    }
    +"Some text"
}
```

### 一些特殊标签的完整属性:

```kotlin
quote {
    fullAttrs {
        stripeColor = accent
        stripeWidth = 4.dp
        gapWidth = 8.dp
    }
    +"A quote here"
}
```

### 使用外部的样式:

```kotlin
val appTextStyle = createStyle {
    h1 { ... }
    p { ... }
    quote { ... }
    bullet { ... }
    hr { ... }
}
```

然后在 `createSpanned` 中:

```kotlin
createSpanned {
    style(appTextStyle)
    ...
}
```

### 包含/重用span:

```kotlin
fun header(): Spanned {
    val banner = ContextCompat.getDrawable(this, R.mipmap.ic_banner)!!
    return createSpanned {
        img(banner) {}
        h1 { +"Title" }
        p { ... }
    }
}
```

然后在 `createSpanned` 中:

```kotlin
createSpanned {
    +header()

    p { ... }
    ...
}
```

# 标签和属性

### 内置标签:

**`h1` - `h6`,  `p`, `b`, `i`, `u`, `s`, `hr`, `sup`, `sub`, `img`, `clickable`**

### 共有属性:

**fontSize: Size? = null**

使用如下方式: `16.sp`, `12.px`, `10.dp`, `2.em`, `Px(18)`, `Em(2.0f)`

**fontFamily: Typeface? = null**

使用系统字体或从assets/文件加载: `Typeface.DEFAULT`, `Typeface.DEFAULT_BOLD`, `Typeface.SANS_SERIF`, `Typeface.SERIF`, `Typeface.MONOSPACE`

**fontStyle: Int = Typeface.NORMAL**

值: `Typeface.NORMAL`, `Typeface.BOLD`, `Typeface.ITALIC`, `Typeface.BOLD_ITALIC`

**lineHeight: Size? = null**

与`fontSize`相同

**align: Layout.Alignment? = null**

值: `Layout.Alignment.ALIGN_NORMAL`, `Layout.Alignment.ALIGN_CENTER`, `Layout.Alignment.ALIGN_OPPOSITE`

**color: Int = 0**

和Android的int颜色一样

**backgroundColor: Int = 0**

和Android的int颜色一样

**decoration: Decoration? = null**

Values: `Decoration.Underline`, `Decoration.Strikethrough`

# 自定义属性

### 简单的 dashedHr 例子:

1. 创建一个扩展函数:

```kotlin
@SpanTagMarker
inline fun Tag.dashedHr(crossinline body: SelfClosingTag.() -> Unit) {
    val tag = SelfClosingTag("dashed-hr", root)
    // 这一行是多余的，自闭合标签不该包含内容
    // tag.body()
    val spans = spans = arrayOf(DashedHrSpan())
    insertTag(tag, spans, isBlockElement = true)
}
```

2. 然后使用:

```kotlin
createSpanned {
    ...
    dashedHr {}
    ...
}
```

结果:

![](./arts/screenshot_02_clipped.png)

### 带自定义属性的标签自定义:

1. 自定义属性类:

```kotlin
class DashedHrAttributes(var height: Size = 1.dp) : Attributes()
```

2. 自定义标签类:

```kotlin
class DashedHr(root: IRoot?) : SelfClosingTag(NAME, root) {
    companion object {
        const val NAME = "dashed-hr"
    }
}
```

3. 用于 `fullAttrs` dsl 的扩展函数:

```kotlin
@StyleTagMarker
inline fun DashedHr.fullAttrs(body: DashedHrAttributes.() -> Unit) {
    this.attrs = DashedHrAttributes().also(body)
}
```

4. 用于span dsl 的扩展函数:

```kotlin
@SpanTagMarker
inline fun Tag.dashedHr(crossinline body: DashedHr.() -> Unit) {
    val tag = DashedHr(root).also(body)
    root!!.defaultStyle.dashedHr {}
    val attrs = (tag.attrs ?: root!!.attrs(tag.name)) as DashedHrAttributes
    val height = attrs.height.value
    val color = attrs.color
    insertTag(tag, arrayOf(DashedHrSpan(height, color)), isBlockElement = true)
}
```

5. 用于样式 dsl 的扩展函数:

```kotlin
inline fun StyleSheet.dashedHr(body: DashedHrAttributes.() -> Unit) {
    val attrs = tagAttrs[DashedHr.NAME] as? DashedHrAttributes
        ?: DashedHrAttributes().also {
            tagAttrs[DashedHr.NAME] = it
        }
    attrs.body()
}
```

6. 然后使用:

```kotlin
creatSpan {
    style {
        dashedHr {
            height = 1.5.dp
        }
    }
    ...
    dashedHr {}
    dashedHr {
        fullAttrs {
            height = 2.dp
        }
    }
    ...
}
```

结果将是这样的:

![](./arts/screenshot_03_clipped.png)

# 许可证

[Apache License 2.0](./LICENSE)

