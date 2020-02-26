package com.babblingbrook.mtgcardsearch.util

import androidx.paging.PagedList

fun pagedListConfig() = PagedList.Config.Builder()
    .setInitialLoadSizeHint(175)
    .setEnablePlaceholders(false)
    .setPageSize(175)
    .build()