package com.babblingbrook.mtgcardsearch.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.babblingbrook.mtgcardsearch.R
import com.babblingbrook.mtgcardsearch.data.NetworkState
import com.babblingbrook.mtgcardsearch.model.Card
import kotlinx.android.synthetic.main.rv_card_item.view.*
import kotlinx.android.synthetic.main.rv_state.view.*

class SearchAdapter(private val listener: OnClickListener) :
    PagedListAdapter<Card, RecyclerView.ViewHolder>(diffCallback) {

    private var currentNetworkState: NetworkState? = null

    interface OnClickListener {
        fun whenListIsUpdated(size: Int, networkState: NetworkState?)
        fun onCardRowClicked(view: View, card: Card?)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.rv_card_item -> CardViewHolder(view)
            R.layout.rv_state -> StateViewHolder(view)
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.rv_card_item -> (holder as CardViewHolder).bind(getItem(position), listener)
            R.layout.rv_state -> (holder as StateViewHolder).bind(currentNetworkState)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.rv_state
        } else {
            R.layout.rv_card_item
        }
    }

    override fun getItemCount(): Int {
        this.listener.whenListIsUpdated(super.getItemCount(), this.currentNetworkState)
        return super.getItemCount()
    }

    private fun hasExtraRow() =
        currentNetworkState != null && currentNetworkState != NetworkState.SUCCESS

    fun updateNetworkState(newNetworkState: NetworkState?) {
        val currentNetworkState = this.currentNetworkState
        val hadExtraRow = hasExtraRow()
        this.currentNetworkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        determineItemChange(hadExtraRow, hasExtraRow, currentNetworkState, newNetworkState)
    }

    private fun determineItemChange(
        hadExtraRow: Boolean, hasExtraRow: Boolean,
        currentNetworkState: NetworkState?,
        newNetworkState: NetworkState?
    ) {
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && currentNetworkState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Card>() {
            override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean =
                oldItem == newItem
        }
    }

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

                setListeners(listener, card)
            }
        }

        private fun setListeners(listener: OnClickListener, card: Card?) {
            itemView.setOnClickListener {
                listener.onCardRowClicked(itemView.card_image, card)
            }
        }
    }

    class StateViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        fun bind(networkState: NetworkState?) {
            setViews(networkState)
        }

        private fun setViews(networkState: NetworkState?) {
            when (networkState) {
                NetworkState.FAILED -> {
                    itemView.state_button.visibility = View.VISIBLE
                    itemView.state_error_msg.visibility = View.VISIBLE
                    itemView.state_progress_bar.visibility = View.GONE
                }
                NetworkState.RUNNING -> {
                    itemView.state_button.visibility = View.GONE
                    itemView.state_error_msg.visibility = View.GONE
                    itemView.state_progress_bar.visibility = View.VISIBLE
                }
                NetworkState.SUCCESS -> {
                    itemView.state_button.visibility = View.GONE
                    itemView.state_error_msg.visibility = View.GONE
                    itemView.state_progress_bar.visibility = View.GONE
                }
            }
        }
    }
}