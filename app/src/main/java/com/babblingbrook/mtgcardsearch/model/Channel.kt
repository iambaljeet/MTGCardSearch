package com.babblingbrook.mtgcardsearch.model

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel", strict = false)
data class Channel @JvmOverloads constructor(
    @field:ElementList(inline = true, name = "item")
    var item: MutableList<Item> = mutableListOf()
)