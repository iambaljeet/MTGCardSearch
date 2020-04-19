package com.babblingbrook.mtgcardsearch.ui.search

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.babblingbrook.mtgcardsearch.R
import com.babblingbrook.mtgcardsearch.model.FeedItem
import com.babblingbrook.mtgcardsearch.util.getDescription
import com.babblingbrook.mtgcardsearch.util.getImageLink
import kotlinx.android.synthetic.main.rv_feed_item.view.*

class FeedAdapter(private var channel: List<FeedItem>, private val listener: OnClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnClickListener {
        fun onFeedItemClicked(item: FeedItem)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_feed_item, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return (holder as CardViewHolder).bind(channel[position], listener)
    }

    fun replaceData(list: List<FeedItem>) {
        channel = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = channel.size

    class CardViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        fun bind(item: FeedItem,  listener: OnClickListener) {
            itemView.article_image.load(item.link)
            val imageLink = getImageLink(item.description)
            itemView.article_image.load(imageLink)
            itemView.article_desc.text = Html.fromHtml(getDescription(item.description))
            itemView.article_title.text = item.title
            itemView.article_date.text = item.pubDate

            itemView.setOnClickListener {
                listener.onFeedItemClicked(item)
            }
        }
    }
}