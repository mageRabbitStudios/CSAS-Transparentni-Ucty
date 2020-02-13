package com.kinzlstanislav.csasucty.list.view

import androidx.lifecycle.Observer
import com.kinzlstanislav.csasucty.base.extensions.observe
import com.kinzlstanislav.csasucty.base.view.BaseFragment
import com.kinzlstanislav.csasucty.list.viewmodel.ListViewModel
import com.kinzlstanislav.csasucty.list.viewmodel.ListViewModel.ListState.*
import com.kinzlstanislav.hoslaviceo2.list.R
import org.koin.android.viewmodel.ext.android.sharedViewModel

class FragmentList : BaseFragment() {

    override val layoutResourceId = R.layout.list_fragment

    private val listViewModel: ListViewModel by sharedViewModel()

    override fun onFragmentCreated() {
        observe(listViewModel.state, Observer { state ->
            when(state) {
                is AccountsLoaded -> state.accounts.forEach { println(it.name) }
                LoadingAccounts -> {}
                NetworkError -> {}
                GenericError -> {}
            }
        })
    }
}