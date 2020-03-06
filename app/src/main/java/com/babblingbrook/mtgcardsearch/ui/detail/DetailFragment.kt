package com.babblingbrook.mtgcardsearch.ui.detail

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.babblingbrook.mtgcardsearch.R
import com.babblingbrook.mtgcardsearch.model.Card
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_detail.*
import java.util.regex.Pattern
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
            card_type.text = it.type_line
            updateFavoritesIcon(it.isFavorite)

            it.imageUris?.let { imageUris ->
                card_image.load(imageUris.artCrop)
            }

            it.oracleText?.let { oracleText ->
                oracle_text.visibility = View.VISIBLE
                oracle_text.text = parseMana(oracleText)
            }

            it.flavorText?.let { flavorText ->
                flavor_text.visibility = View.VISIBLE
                flavor_text.text = flavorText
            }

            it.manaCost?.let { manaCost ->
                mana_cost.text = parseMana(manaCost)
            }

            it.loyalty?.let { loyalty ->
                power_toughness.text = loyalty
            }

            if (it.power != null && it.toughness != null) {
                power_toughness.text =
                    resources.getString(R.string.power_toughness, it.power, it.toughness)
            }
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

    fun parseMana(string: String): SpannableStringBuilder {
        val patternString = StringBuilder()
        patternString.append('(')
        var first = true
        for (key in replacements.keys) {
            if (first) {
                first = false
            } else {
                patternString.append("|")
            }
            patternString.append(Pattern.quote(key))
        }
        patternString.append(")")

        val spannable = SpannableStringBuilder(string)
        val pattern = Pattern.compile(patternString.toString())
        val matcher = pattern.matcher(string)
        while (matcher.find()) {
            val match = matcher.group(1) ?: ""
            replacements[match]?.let {
                val drawable = ContextCompat.getDrawable(requireContext(), it)
                drawable?.let { d ->
                    d.setBounds(0, 0, 75, 75)
                    spannable.setSpan(
                        ImageSpan(d, ImageSpan.ALIGN_BOTTOM),
                        matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
        }
        return spannable
    }

    private val replacements =
        mapOf(
            "{T}" to R.drawable.ic_t,
            "{Q}" to R.drawable.ic_q,
            "{E}" to R.drawable.ic_e,
            "{PW}" to R.drawable.ic_pw,
            "{CHAOS" to R.drawable.ic_chaos,
            "{X}" to R.drawable.ic_x,
            "{Y}" to R.drawable.ic_y,
            "{Z}" to R.drawable.ic_z,
            "{0}" to R.drawable.ic_0,
            "{½}" to R.drawable.ic_half,
            "{1}" to R.drawable.ic_1,
            "{2}" to R.drawable.ic_2,
            "{3}" to R.drawable.ic_3,
            "{4}" to R.drawable.ic_4,
            "{5}" to R.drawable.ic_5,
            "{6}" to R.drawable.ic_6,
            "{7}" to R.drawable.ic_7,
            "{8}" to R.drawable.ic_8,
            "{9}" to R.drawable.ic_9,
            "{10}" to R.drawable.ic_10,
            "{11}" to R.drawable.ic_11,
            "{12}" to R.drawable.ic_12,
            "{13}" to R.drawable.ic_13,
            "{14}" to R.drawable.ic_14,
            "{15}" to R.drawable.ic_15,
            "{16}" to R.drawable.ic_16,
            "{17}" to R.drawable.ic_17,
            "{18}" to R.drawable.ic_18,
            "{19}" to R.drawable.ic_19,
            "{20}" to R.drawable.ic_20,
            "{100}" to R.drawable.ic_100,
            "{1000000}" to R.drawable.ic_1000000,
            "{∞}" to R.drawable.ic_infinity,
            "{W/U}" to R.drawable.ic_wu,
            "{W/B}" to R.drawable.ic_wb,
            "{B/R}" to R.drawable.ic_br,
            "{B/G}" to R.drawable.ic_bg,
            "{U/B}" to R.drawable.ic_ub,
            "{U/R}" to R.drawable.ic_ur,
            "{R/G}" to R.drawable.ic_rg,
            "{R/W}" to R.drawable.ic_rw,
            "{G/W}" to R.drawable.ic_gw,
            "{G/U}" to R.drawable.ic_gu,
            "{2/W}" to R.drawable.ic_2w,
            "{2/U}" to R.drawable.ic_2u,
            "{2/B}" to R.drawable.ic_2b,
            "{2/R}" to R.drawable.ic_2r,
            "{2/G}" to R.drawable.ic_2g,
            "{P}" to R.drawable.ic_p,
            "{W/P}" to R.drawable.ic_wp,
            "{U/P}" to R.drawable.ic_up,
            "{B/P}" to R.drawable.ic_bp,
            "{R/P}" to R.drawable.ic_rp,
            "{G/P}" to R.drawable.ic_gp,
            "{HW}" to R.drawable.ic_hw,
            "{HR}" to R.drawable.ic_hr,
            "{W}" to R.drawable.ic_w,
            "{U}" to R.drawable.ic_u,
            "{B}" to R.drawable.ic_b,
            "{R}" to R.drawable.ic_r,
            "{G}" to R.drawable.ic_g,
            "{C}" to R.drawable.ic_c,
            "{S}" to R.drawable.ic_s
        )
}
