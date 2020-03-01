package com.babblingbrook.mtgcardsearch.ui.search

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var viewModel: SearchViewModel

    private val searchResultAdapter = SearchAdapter(listOf(), this)

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
        rv_cards.addItemDecoration(DividerItemDecoration(requireContext(),
            LinearLayoutManager.VERTICAL))
        rv_cards.adapter = searchResultAdapter

        viewModel.status.observe(viewLifecycleOwner, Observer {
            updateStatus(it)
        })
        viewModel.cards.observe(viewLifecycleOwner, Observer {
            searchResultAdapter.replaceData(it)
        })

        search_field.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.length > 1) {
                    viewModel.getCards(text.toString())
                } else {
                    searchResultAdapter.clearData()
                }
            }
        }
    }

    override fun onCardRowClicked(view: View, card: Card?) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(card)
        this.findNavController().navigate(action)
    }

    private fun updateStatus(status: Status) {
        when (status) {
            Status.LOADING -> {
                loading.visibility = View.VISIBLE
                error.visibility = View.GONE
            }
            Status.SUCCESS -> {
                loading.visibility = View.GONE
                error.visibility = View.GONE
            }
            Status.FAILED -> {
                loading.visibility = View.GONE
                error.visibility = View.VISIBLE
            }
        }
    }
}
