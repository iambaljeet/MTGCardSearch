package com.babblingbrook.mtgcardsearch.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class Feed @JvmOverloads constructor(
    @field:Element var channel: Channel = Channel()
)