package com.dokar.ktspans

interface Root {

    val defaultStyle: StyleSheet

    var style: StyleSheet?

    fun attrs(tagName: String): Attributes?

}