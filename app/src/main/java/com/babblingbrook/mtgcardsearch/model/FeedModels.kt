package com.babblingbrook.mtgcardsearch.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel", strict = false)
data class Channel @JvmOverloads constructor(
    @field:ElementList(inline = true, name = "item")
    var item: MutableList<Item> = mutableListOf()
)

@Root(name = "rss", strict = false)
data class Feed @JvmOverloads constructor(
    @field:Element var channel: Channel = Channel()
)

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