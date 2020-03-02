package com.babblingbrook.mtgcardsearch.ui.favorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.babblingbrook.mtgcardsearch.R
import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.ui.Status
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_favorites.*
import javax.inject.Inject

class FavoritesFragment : Fragment(), FavoritesAdapter.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var viewModel: FavoritesViewModel

    private val favoritesAdapter = FavoritesAdapter(listOf(), this)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_favorites.layoutManager = LinearLayoutManager(requireContext())
        rv_favorites.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
        rv_favorites.adapter = favoritesAdapter
        viewModel.cards.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Status.Success -> {
                    hideStatusViews()
                    favoritesAdapter.replaceData(it.data)
                }
                is Status.Loading -> {
                    showLoading()
                }
            }
        })

        toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
    }

    override fun onCardRowClicked(view: View, card: Card?) {
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(card)
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
