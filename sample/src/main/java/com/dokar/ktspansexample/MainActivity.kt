package com.dokar.ktspansexample

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.dokar.ktspans.*

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.text)
        textView.movementMethod = LinkMovementMethod.getInstance()

        showHome()
    }

    private val globalStyle: StyleSheet by lazy {
        val textColor = ContextCompat.getColor(this, R.color.text_color)
        val accent = ContextCompat.getColor(this, R.color.teal_200)
        createStyle {
            h1 {
                color = textColor
            }

            h3 {
                color = textColor
            }

            h5 {
                color = textColor
            }

            a {
                color = accent
            }

            bullet {
                lineHeight = 1.2.em
                bulletColor = accent
                gapWidth = 8.dp
                bulletRadius = 3.dp
            }

            quote {
                stripeColor = accent
                stripeWidth = 4.dp
                gapWidth = 8.dp
            }

            hr {
                height = 1.dp
                color = Color.LTGRAY
            }

            "serif" {
                fontFamily = Typeface.SERIF
            }
        }
    }

    private fun showHome() {
        textView.text = createSpanned {
            style(globalStyle)

            +header()

            h3 { +"Summary" }
            p {
                attrs { fontStyle = Typeface.BOLD }
                +"Inspired by "
                a("https://github.com/Kotlin/kotlinx.html") {
                    +"kotlinx.html"
                }
                +", KtSpans is a library provides "
                +"html & css like dsl to write spans on Android."
            }
            br {}

            h3 { +"Built-in tags:" }
            p { +"h1 - h6, p, b, i, u, s, hr, sup, sub, img, clickable" }
            br {}
            p {
                attrs { fontStyle = Typeface.BOLD }
                clickable({ showBuiltinTags() }) {
                    +"View built-in tags"
                }
            }
            br {}

            h3 { +"Common attributes:" }
            p {
                +"fontSize, fontStyle, fontFamily, color, "
                +"backgroundColor, align, lineHeight, decoration"
            }
            br {}
            p {
                attrs { fontStyle = Typeface.BOLD }
                clickable({ showAttrs() }) {
                    +"View attributes"
                }
            }
            br {}

            h3 { +"Custom tags" }
            p { +"Create custom tags for any spans" }
            p {
                attrs { fontStyle = Typeface.BOLD }
                clickable({ showCustomTags() }) {
                    +"View custom tags"
                }
            }
        }
    }

    private fun showBuiltinTags() {
        val icons = arrayOf(
            ContextCompat.getDrawable(this, R.mipmap.ic_logo_android)!!,
            ContextCompat.getDrawable(this, R.mipmap.ic_logo_kotlin)!!,
            ContextCompat.getDrawable(this, R.mipmap.ic_logo_java)!!
        )
        textView.text = createSpanned {
            style(globalStyle)

            +header()

            h3 { +"Basic" }
            p { +"A simple paragraph" }
            p {
                b { +"Bold(b), " }
                i { +"Italic(i), " }
                b { i { +"and Bold with Italic(b & i)" } }
                br {}
                u { +"Underline(u)" }
                +" and "
                s { +"Strike through(s)" }
            }
            br {}

            h3 { +"Superscript & subscript" }
            p {
                +"2"
                sup { +"2" }
                +" = 4"
            }
            p {
                +"A"
                sub { +"1" }
                +" = A"
                sub { +"2" }
                +" = 0"
            }
            br {}

            h3 { +"Quote" }
            quote {
                className = "serif"
                +"Stay hungry,"
                br {}
                +"stay foolish."
            }
            br {}

            h3 { +"Items (bullet)" }
            for (i in 1..5) {
                bullet {
                    +"Item $i"
                }
            }
            br {}

            h3 { +"Url" }
            p {
                a("https://www.github.com") { +"Github.com" }
            }
            br {}

            h3 { +"Clickable" }
            p {
                clickable({
                    Toast.makeText(it.context, "Clicked!", Toast.LENGTH_SHORT).show()
                }) {
                    +"Clickable text"
                }
            }
            br {}

            h3 { +"Image" }
            p {
                val iconHeight = 60.dp.value.toInt()
                for (icon in icons) {
                    img(icon) {
                        fullAttrs { height = iconHeight }
                    }
                    +" "
                }
            }
            br {}

            h3 { +"Horizontal rule" }

            +footer()
        }
    }

    private fun showCustomTags() {
        textView.text = createSpanned {
            style(globalStyle)

            +header()

            h3 { +"dashedHr" }
            dashedHr {}
            dashedHr {
                fullAttrs {
                    height = 2.dp
                }
            }

            +footer()
        }
    }

    private fun showAttrs() {
        val accent = ContextCompat.getColor(this, R.color.teal_200)
        textView.text = createSpanned {
            style(globalStyle)

            +header()

            h3 { +"Font size" }
            p {
                span {
                    attrs { fontSize = 16.sp }
                    +"16s sp, "
                }
                span {
                    attrs { fontSize = 22.sp }
                    +"22 sp, "
                }
                span {
                    attrs { fontSize = 2.em }
                    +"2 em"
                }
            }
            br {}

            h3 { +"Font Style" }
            p {
                span {
                    attrs { fontStyle = Typeface.NORMAL }
                    +"Normal, "
                }
                span {
                    attrs { fontStyle = Typeface.BOLD }
                    +"Bold, "
                }
                span {
                    attrs { fontStyle = Typeface.ITALIC }
                    +"Italic, "
                }
                span {
                    attrs { fontStyle = Typeface.BOLD_ITALIC }
                    +"Bold & italic"
                }
            }
            br {}

            h3 { +"Font family" }
            p {
                span {
                    attrs { fontFamily = Typeface.DEFAULT }
                    +"Default, "
                }
                span {
                    attrs { fontFamily = Typeface.SANS_SERIF }
                    +"Sans, "
                }
                span {
                    attrs { fontFamily = Typeface.SERIF }
                    +"Serif, "
                }
                span {
                    attrs { fontFamily = Typeface.MONOSPACE }
                    +"Mono"
                }
            }
            br {}

            h3 { +"Alignment" }
            p {
                attrs { align = Layout.Alignment.ALIGN_NORMAL }
                +"Normal"
            }
            p {
                attrs { align = Layout.Alignment.ALIGN_CENTER }
                +"Center"
            }
            p {
                attrs { align = Layout.Alignment.ALIGN_OPPOSITE }
                +"Opposite"
            }
            br {}

            h3 { +"Line height" }
            p {
                attrs { lineHeight = 30.sp }
                +"30 sp"
            }
            p {
                attrs { lineHeight = 3.em }
                +"2 em"
            }
            br {}

            h3 { +"Color" }
            p {
                span {
                    attrs { color = accent }
                    +"Kt"
                }
                +"Span"
                span {
                    attrs { color = accent }
                    +"s"
                }
            }
            br {}

            h3 { +"Background color" }
            p {
                attrs {
                    color = Color.WHITE
                    backgroundColor = Color.DKGRAY
                }
                +"Light and dark."
            }
            br {}

            h3 { +"Decoration" }
            p {
                span {
                    attrs { decoration = Decoration.Underline }
                    +"Underline"
                }
                +", "
                span {
                    attrs { decoration = Decoration.Strikethrough }
                    +"Strike through"
                }
            }

            +footer()
        }
    }

    private fun RootTag.header(): Spanned {
        val banner = ContextCompat.getDrawable(this@MainActivity, R.mipmap.ic_banner)!!
        val rootStyle = this.style
        return createSpanned {
            style(rootStyle)

            img(banner) {
                attrs {
                    align = Layout.Alignment.ALIGN_CENTER
                }
            }
            br {}

            h1 {
                attrs { align = Layout.Alignment.ALIGN_CENTER }
                +"html & css like spans"
            }

            hr {}
        }
    }

    private fun RootTag.footer(): Spanned {
        val rootStyle = this.style
        return createSpanned {
            style(rootStyle)
            hr {}
            p {
                attrs { align = Layout.Alignment.ALIGN_CENTER }
                clickable({
                    showHome()
                }) {
                    +"Home"
                }
            }
        }
    }
}