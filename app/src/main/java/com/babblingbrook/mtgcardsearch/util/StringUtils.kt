package com.babblingbrook.mtgcardsearch.util

import java.util.regex.Pattern

/**
 * Should probably use an Html parser for anything more complicated than what we are getting
 * from our RSS feed. This is all one source, simple, and consistent, so we use it here.
 */
fun getImageLink(string: String): String {
    val pattern = Pattern.compile("src\\s*=\\s*([\"'])?([^\"']*)")
    val matcher = pattern.matcher(string)
    var match: String? = null
    if (matcher.find()) {
        match = matcher.group(2)
    }
    return match ?: ""
}

fun getDescription(string: String): String {
    val pattern = Pattern.compile("(?<=<p>)(.*?)(?=</p>)")
    val matcher = pattern.matcher(string)
    var match: String? = null
    if (matcher.find()) {
        match = matcher.group(0)
    }
    return match ?: ""
}

fun getLink(string: String): String {
    val pattern = Pattern.compile("<a\\s+(?:[^>]*?\\s+)?href=\"([^\"]*)\"")
    val matcher = pattern.matcher(string)
    var match: String? = null
    if (matcher.find()) {
        match = matcher.group(0)
    }
    return match ?: ""
}