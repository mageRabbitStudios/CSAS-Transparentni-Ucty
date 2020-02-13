package com.kinzlstanislav.csasucty.list.view

import androidx.lifecycle.Observer
import com.gtomato.android.ui.transformer.FlatMerryGoRoundTransformer
import com.kinzlstanislav.csasucty.base.extensions.observe
import com.kinzlstanislav.csasucty.base.view.BaseFragment
import com.kinzlstanislav.csasucty.list.view.adapter.AccountListAdapter
import com.kinzlstanislav.csasucty.list.viewmodel.ListViewModel
import com.kinzlstanislav.csasucty.list.viewmodel.ListViewModel.ListState.*
import com.kinzlstanislav.hoslaviceo2.list.R
import kotlinx.android.synthetic.main.list_fragment.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class FragmentList : BaseFragment() {

    override val layoutResourceId = R.layout.list_fragment

    private val listViewModel: ListViewModel by sharedViewModel()

    private val accountsAdapter = AccountListAdapter()

    override fun onFragmentCreated() {
        observe(listViewModel.state, Observer { state ->
            flipper.showView(when(state) {
                is AccountsLoaded -> carousel.also {
                    accountsAdapter.accounts = state.accounts.toMutableList()
                }
                is LoadingAccounts -> loader
                is NetworkError -> network_error
                is GenericError -> generic_error
            })
        })
        carousel.transformer = FlatMerryGoRoundTransformer()
        carousel.adapter = accountsAdapter
    }
}