package com.babblingbrook.mtgcardsearch.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.babblingbrook.mtgcardsearch.R
import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.ui.Status
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : Fragment(), SearchAdapter.OnClickListener {

    @Inject
    lateinit var viewModel: SearchViewModel

    private val searchResultAdapter = SearchAdapter(listOf(), this)
    private val feedAdapter = FeedAdapter(mutableListOf())

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_cards.layoutManager = LinearLayoutManager(requireContext())
        rv_cards.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
        rv_cards.adapter = searchResultAdapter

        rv_feeds.layoutManager = LinearLayoutManager(requireContext())
        rv_feeds.adapter = feedAdapter
        viewModel.cards.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Status.Success -> {
                    hideStatusViews()
                    searchResultAdapter.replaceData(it.data)
                }
                is Status.Loading -> showLoading()
                is Status.Error -> showError()
            }
        })

        viewModel.channel.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Status.Success -> {
                    hideStatusViews()
                    it.data?.let { feed ->
                        feedAdapter.replaceData(feed.channel.item)
                    }
                }
                is Status.Loading -> showLoading()
                is Status.Error -> showError()
            }
        })

        showFeeds()
    }

    override fun onResume() {
        super.onResume()
        search_field.doOnTextChanged { text, _, _, _ ->
            text?.let {
                if (text.length > 1) {
                    hideFeeds()
                    viewModel.search(text.toString())
                } else {
                    showFeeds()
                    searchResultAdapter.clearData()
                }
            }
        }
    }

    private fun showFeeds() {
        rv_cards.visibility = View.GONE
        rv_feeds.visibility = View.VISIBLE
    }

    private fun hideFeeds() {
        rv_cards.visibility = View.VISIBLE
        rv_feeds.visibility = View.GONE
    }

    override fun onCardRowClicked(view: View, card: Card?) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(card)
        this.findNavController().navigate(action)
    }

    private fun showLoading() {
        loading.visibility = View.VISIBLE
        error.visibility = View.GONE
    }

    private fun showError() {
        error.visibility = View.VISIBLE
        loading.visibility = View.GONE
    }

    private fun hideStatusViews() {
        loading.visibility = View.GONE
        error.visibility = View.GONE
    }
}
