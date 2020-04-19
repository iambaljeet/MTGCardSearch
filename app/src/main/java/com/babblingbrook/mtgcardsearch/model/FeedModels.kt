package com.babblingbrook.mtgcardsearch.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class Feed @JvmOverloads constructor(
    @field:Element var channel: Channel = Channel()
)

@Root(name = "channel", strict = false)
data class Channel @JvmOverloads constructor(
    @field:ElementList(inline = true, name = "item")
    var item: MutableList<FeedItem> = mutableListOf()
)

@Entity
@Root(name = "item", strict = false)
data class FeedItem @JvmOverloads constructor(
    @PrimaryKey
    @field:Element(name = "title")
    var title: String = "",
    @field:Element(name = "description")
    var description: String = "",
    @field:Element(name = "pubDate")
    var pubDate: String = "",
    @field:Element(name = "link")
    var link: String = ""
)