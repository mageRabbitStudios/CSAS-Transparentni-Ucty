package com.kinzlstanislav.csasucty.list.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kinzlstanislav.csasucty.architecture.repository.AccountsRepository
import com.kinzlstanislav.csasucty.architecture.repository.model.Account
import com.kinzlstanislav.csasucty.base.extensions.coroutine
import com.kinzlstanislav.csasucty.base.extensions.isConnectionError
import com.kinzlstanislav.csasucty.base.extensions.set
import com.kinzlstanislav.csasucty.list.viewmodel.ListViewModel.ListState.*
import java.lang.Exception

class ListViewModel(
    private val accountsRepository: AccountsRepository,
    val state: MutableLiveData<ListState> = MutableLiveData()
) : ViewModel() {

    sealed class ListState {
        data class AccountsLoaded(val accounts: List<Account>) : ListState()
        object LoadingAccounts : ListState()
        object NetworkError : ListState()
        object GenericError : ListState()
    }

    init {
        getAccounts()
    }

    fun getAccounts() = with(state) {
        set(LoadingAccounts)
        coroutine {
            try {
                set(AccountsLoaded(accountsRepository.fetchAccounts()))
            } catch (e: Exception) {
                set(if (e.isConnectionError()) NetworkError else GenericError)
            }
        }
    }
}