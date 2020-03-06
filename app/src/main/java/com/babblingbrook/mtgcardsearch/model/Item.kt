package com.babblingbrook.mtgcardsearch.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class Item @JvmOverloads constructor(
    @field:Element(name = "title")
    var title: String = "",
    @field:Element(name = "description")
    var description: String = "",
    @field:Element(name = "pubDate")
    var pubDate: String = "",
    @field:Element(name = "link")
    var link: String = ""
)