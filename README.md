![](./arts/ktspans.png)

[README - 中文](./README_zh.md)

# KtSpans

Inspired by [kotlinx.html](https://github.com/Kotlin/kotlinx.html), KtSpans is a library provides **html** & **css** like DSL to write spans on Android.

# How?

Add dependency to project:

```groovy
implementation 'io.github.dokar3:ktspans:1.0.3'
```

Create our span like this:

```kotlin
textView.text = createSpanned {
    style {
        h3 { 
            color = 0xFF8D07F6.toInt() 
        }
        "serif" { 
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
        +"Make spans on Android easy to use"
    }
    br {}
    h3 { +"::after" }
    p {
        className = "serif"
        +"More possibilities is comming"
    }
}
```

And we got this:

![](./arts/screenshot_01_clipped.png)

# More usages

### Attributes inside tags:

```kotlin
p {
    attrs {
        fontStyle = Typeface.BOLD 
    }
    +"Some text"
}
```

### Full attributes for some special tags:

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

### Use style from outside:

```kotlin
val appTextStyle = createStyle {
    h1 { ... }
    p { ... }
    quote { ... }
    bullet { ... }
    hr { ... }
}
```

Inside `createSpanned`:

```kotlin
createSpanned {
    style(appTextStyle)
    ...
}
```

### Include/reuse spans:

```kotlin
fun header(): Spanned {
    val banner = ContextCompat.getDrawable(this, R.mipmap.ic_banner)!!
    return createSpanned{
        img(banner) {}
        h1 { +"Title" }
        p { ... }
    }
}
```

Inside  `createSpanned`:

```kotlin
createSpanned{
    +header()
    
    p { ... }
    ...
}
```

# Tags and attributes

### Built-in tags:

**`h1` - `h6`,  `p`, `b`, `i`, `u`, `s`, `hr`, `sup`, `sub`, `img`, `clickable`**

### Common attributes:

**fontSize: Size? = null**

Create some sizes like this: `16.sp`, `12.px`, `10.dp`, `2.em`, `Px(18)`, `Em(2.0f)`

**fontFamily: Typeface? = null**

Use system typefaces or load from assets/file: `Typeface.DEFAULT`, `Typeface.DEFAULT_BOLD`, `Typeface.SANS_SERIF`, `Typeface.SERIF`, `Typeface.MONOSPACE`

**fontStyle: Int = Typeface.NORMAL**

Values: `Typeface.NORMAL`, `Typeface.BOLD`, `Typeface.ITALIC`, `Typeface.BOLD_ITALIC`

**lineHeight: Size? = null**

Same as `fontSize`

**align: Layout.Alignment? = null**

Values: `Layout.Alignment.ALIGN_NORMAL`, `Layout.Alignment.ALIGN_CENTER`, `Layout.Alignment.ALIGN_OPPOSITE`

**color: Int = 0**

Same as Android's color ints

**backgroundColor: Int = 0**

Same as Android's color ints

**decoration: Decoration? = null**

Values: `Decoration.Underline`, `Decoration.Strikethrough`

# Custom tags

### Simple dashedHr example:

1. Write an extension function:

```kotlin
@SpanTagMarker
inline fun Tag.dashedHr(crossinline body: SelfClosingTag.() -> Unit) {
    val tag = SelfClosingTag("dashed-hr", root)
    // This line is redundant in this case, a self closing tag should not
    // contain any content.
    // tag.body()
    val spans = spans = arrayOf(DashedHrSpan())
    insertTag(tag, spans, isBlockElement = true)
}
```

2. Use it:

```kotlin
createSpanned{
    ...
    dashedHr {}
    ...
}
```

Result:

![](./arts/screenshot_02_clipped.png)

### With custom attributes:

1. Custom attributes class:

```kotlin
class DashedHrAttributes(var height: Size = 1.dp) : Attributes()
```

2. Custom tag class:

```kotlin
class DashedHr(root: IRoot?) : SelfClosingTag(NAME, root) {
    companion object {
        const val NAME = "dashed-hr"
    }
}
```

3. Extension function for `fullAttrs` dsl:

```kotlin
@StyleTagMarker
inline fun DashedHr.fullAttrs(body: DashedHrAttributes.() -> Unit) {
    val attrs = DashedHrAttributes()
    attrs.body()
    this.attrs = attrs
}
```

4. Extension function for span dsl:

```kotlin
@SpanTagMarker
inline fun Tag.dashedHr(crossinline body: DashedHr.() -> Unit) {
    val tag = DashedHr(root)
    tag.body()
    root!!.defaultStyle.dashedHr {}
    val attrs = (tag.attrs ?: root!!.attrs(tag.name)) as DashedHrAttributes
    val height = attrs.height.value
    val color = attrs.color
    insertTag(tag, arrayOf(DashedHrSpan(height, color)), isBlockElement = true)
}
```

5. Extension function for style dsl:

```kotlin
inline fun StyleSheet.dashedHr(body: DashedHrAttributes.() -> Unit) {
    val attrs = tagAttrs[DashedHr.NAME] as? DashedHrAttributes
        ?: DashedHrAttributes().also {
            tagAttrs[DashedHr.NAME] = it
        }
    attrs.body()
}
```

6. Use it:

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

Result:

![](./arts/screenshot_03_clipped.png)

# License

[Apache License 2.0](./LICENSE)

