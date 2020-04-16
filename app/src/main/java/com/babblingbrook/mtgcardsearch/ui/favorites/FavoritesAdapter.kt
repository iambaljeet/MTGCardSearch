package com.babblingbrook.mtgcardsearch.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.babblingbrook.mtgcardsearch.R
import com.babblingbrook.mtgcardsearch.model.Card
import kotlinx.android.synthetic.main.rv_card_item.view.*

class FavoritesAdapter(private var items: List<Card>, private val listener: OnClickListener) :
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
                if (it?.card_faces != null) {
                    itemView.card_image2.visibility = View.VISIBLE
                    itemView.card_two_block.visibility = View.VISIBLE

                    itemView.card_name.text = it.card_faces[0].name
                    itemView.card_name2.text = it.card_faces[1].name

                    itemView.card_type.text = it.card_faces[0].type_line
                    itemView.card_type2.text = it.card_faces[1].type_line

                    itemView.card_image.load(it.card_faces[0].image_uris?.normal) {
                        placeholder(R.drawable.ic_card_placeholder)
                        crossfade(true)
                    }
                    itemView.card_image2.load(it.card_faces[1].image_uris?.normal) {
                        placeholder(R.drawable.ic_card_placeholder)
                        crossfade(true)
                    }
                } else {
                    itemView.card_name.text = it?.name
                    itemView.card_type.text = it?.type_line
                    itemView.card_image.load(it?.image_uris?.normal) {
                        placeholder(R.drawable.ic_card_placeholder)
                        crossfade(true)
                    }
                }

                setListeners(listener, card)
            }
        }

        private fun setListeners(listener: OnClickListener, card: Card?) {
            itemView.setOnClickListener {
                listener.onCardRowClicked(itemView.card_image, card)
            }
        }
    }
}