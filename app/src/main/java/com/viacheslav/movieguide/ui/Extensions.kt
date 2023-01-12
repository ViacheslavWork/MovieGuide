package com.viacheslav.movieguide

/**
 * Created by Viacheslav Avd on 12.01.2023
 */

fun List<String>.toLine(): String {
    val strBuilder = StringBuilder()
    this.forEachIndexed { index, string ->
        strBuilder.append(string)
        if (index != this.size - 1) strBuilder.append(", ")
    }
    return strBuilder.toString()
}