package com.babblingbrook.mtgcardsearch.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.babblingbrook.mtgcardsearch.R
import com.babblingbrook.mtgcardsearch.model.Card
import kotlinx.android.synthetic.main.rv_card_item.view.*

class SearchAdapter(private var items: List<Card>, private val listener: OnClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnClickListener {
        fun onCardRowClicked(view: View, card: Card?)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_card_item, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return (holder as CardViewHolder).bind(items[position], listener)
    }

    fun clearData() {
        items = listOf()
        notifyDataSetChanged()
    }

    fun replaceData(list: List<Card>) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    class CardViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        fun bind(card: Card?, listener: OnClickListener) {
            card.let {
                if (it?.cardFaces != null) {
                    itemView.card_image2.visibility = View.VISIBLE
                    itemView.card_two_block.visibility = View.VISIBLE

                    itemView.card_name.text = it.cardFaces[0].name
                    itemView.card_name2.text = it.cardFaces[1].name

                    itemView.card_type.text = it.cardFaces[0].typeLine
                    itemView.card_type2.text = it.cardFaces[1].typeLine

                    itemView.card_image.load(it.cardFaces[0].imageUris?.normal) {
                        placeholder(R.drawable.ic_card_placeholder)
                        crossfade(true)
                    }
                    itemView.card_image2.load(it.cardFaces[1].imageUris?.normal) {
                        placeholder(R.drawable.ic_card_placeholder)
                        crossfade(true)
                    }
                } else {
                    itemView.card_name.text = it?.name
                    itemView.card_type.text = it?.type_line
                    itemView.card_image.load(it?.imageUris?.normal) {
                        placeholder(R.drawable.ic_card_placeholder)
                        crossfade(true)
                    }
                }

                itemView.setOnClickListener {
                    listener.onCardRowClicked(itemView.card_image, card)
                }
            }
        }
    }
}