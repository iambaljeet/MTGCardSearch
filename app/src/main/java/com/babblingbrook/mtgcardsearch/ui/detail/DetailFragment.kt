package com.babblingbrook.mtgcardsearch.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.babblingbrook.mtgcardsearch.R
import com.babblingbrook.mtgcardsearch.model.Card
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject

class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()

    @Inject
    lateinit var viewModel: DetailViewModel

    private lateinit var card: Card

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.setCardData(args.cardData)

        viewModel.card.observe(viewLifecycleOwner, Observer {
            card = it
            toolbar.title = it.name
            card_image.load(it.imageUris?.artCrop)
            card_type.text = it.type_line
            oracle_text.text = it.oracleText
        })

        viewModel.isFavorite.observe(viewLifecycleOwner, Observer {
            updateFavoritesIcon(it)
        })

        var isToolbarShown = false

        card_detail_scrollview.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
                val shouldShowToolbar = scrollY > toolbar.height

                if (isToolbarShown != shouldShowToolbar) {
                    isToolbarShown = shouldShowToolbar

                    appbar.isActivated = shouldShowToolbar

                    toolbar_layout.isTitleEnabled = shouldShowToolbar
                }
            }
        )

        favorite.setOnClickListener {
            if (card.isFavorite) {
                viewModel.removeFavorite(card)
            } else {
                viewModel.addFavorite(card)
            }
        }

        toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
    }

    private fun updateFavoritesIcon(isFavorite: Boolean) {
        if (isFavorite) {
            favorite.setImageDrawable(
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_favorite
                )
            )
        } else {
            favorite.setImageDrawable(
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_favorite_border
                )
            )
        }
    }
}
