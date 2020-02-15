package com.kinzlstanislav.csasucty.list.view

import android.widget.TextView
import androidx.lifecycle.Observer
import com.gtomato.android.ui.transformer.FlatMerryGoRoundTransformer
import com.gtomato.android.ui.widget.CarouselView
import com.kinzlstanislav.csasucty.base.extensions.animatePopIn
import com.kinzlstanislav.csasucty.base.extensions.bindView
import com.kinzlstanislav.csasucty.base.extensions.observe
import com.kinzlstanislav.csasucty.base.extensions.showToast
import com.kinzlstanislav.csasucty.base.view.BaseFragment
import com.kinzlstanislav.csasucty.list.view.adapter.AccountListAdapter
import com.kinzlstanislav.csasucty.list.viewmodel.ListViewModel
import com.kinzlstanislav.csasucty.list.viewmodel.ListViewModel.ListState.*
import com.kinzlstanislav.hoslaviceo2.list.R
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.android.synthetic.main.view_accounts.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class FragmentList : BaseFragment() {

    override val layoutResourceId = R.layout.list_fragment

    private val listViewModel: ListViewModel by sharedViewModel()

    private val accountsAdapter = AccountListAdapter()

    private val carouselPageIndicator by bindView<TextView>(R.id.carousel_page_indicator)

    override fun onFragmentCreated() {
        observe(listViewModel.state, Observer { state ->
            flipper.showView(when (state) {
                is AccountsLoaded -> carousel_container.also {
                    carousel.animatePopIn()
                    accountsAdapter.accounts = state.accounts.toMutableList()
                    carouselPageIndicator.text = "0/${accountsAdapter.itemCount}"
                }
                is LoadingAccounts -> loader
                is NetworkError -> network_error
                is GenericError -> generic_error
            })
        })
        setupCarousel()
    }

    private fun setupCarousel() {
        var lastPosition = 0
        carousel.apply {
            transformer = FlatMerryGoRoundTransformer()
            adapter = accountsAdapter
            setOnScrollListener(object : CarouselView.OnScrollListener() {
                override fun onScrolled(
                    carouselView: CarouselView?,
                    position: Int,
                    adapterPosition: Int,
                    offset: Float
                ) {
                    super.onScrolled(carouselView, position, adapterPosition, offset)
                    if (lastPosition != position) {
                        carouselPageIndicator.let {
                            it.text = "$adapterPosition/${accountsAdapter.itemCount}"
                            // do a small pop
                            it.scaleX = 1f
                            it.scaleY = 1f
                            it.animate()
                                .scaleX(1.2f)
                                .scaleY(1.2f)
                                .duration = 300
                        }
                    }
                    lastPosition = position
                }
            })
        }
    }
}