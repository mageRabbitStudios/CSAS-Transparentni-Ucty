package com.kinzlstanislav.csasucty.list.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kinzlstanislav.csasucty.architecture.repository.AccountsRepository
import com.kinzlstanislav.csasucty.architecture.repository.model.Account
import com.kinzlstanislav.csasucty.base.extensions.set
import com.kinzlstanislav.csasucty.list.viewmodel.ListViewModel.ListState
import com.kinzlstanislav.csasucty.list.viewmodel.ListViewModel.ListState.*
import com.kinzlstanislav.csasucty.unittesting.BaseViewModelTest
import io.mockk.*
import org.junit.Test
import java.net.ConnectException

class ListViewModelTest : BaseViewModelTest() {


    private companion object {
        val SOME_ACCOUNTS = listOf(
            Account("123", "321", "", "", "", "", 0.0f, "", "Tomas", ""),
            Account("123", "321", "", "", "", "", 0.0f, "", "Franta", "")
        )
    }
    private val mockAccountsRepository = mockk<AccountsRepository>()
    private val mockState = mockk<MutableLiveData<ListState>>(relaxed = true)

    private val subject = ListViewModel(mockAccountsRepository, mockState)

    @Test
    fun `getAccounts() - AccountsLoaded`() = getAccountsTest(
        repositoryAnswer = { SOME_ACCOUNTS },
        expectedState = AccountsLoaded(SOME_ACCOUNTS)
    )

    @Test
    fun `getAccounts() - NetworkError`() = getAccountsTest(
        repositoryAnswer = { throw ConnectException() },
        expectedState = NetworkError
    )

    @Test
    fun `getAccounts() - GenericError`() = getAccountsTest(
        repositoryAnswer = { throw NullPointerException() },
        expectedState = GenericError
    )

    private fun getAccountsTest(
        repositoryAnswer: MockKAnswerScope<List<Account>, List<Account>>.(Call) -> List<Account>,
        expectedState: ListState
    ) {
        mockkStatic("com.kinzlstanislav.csasucty.base.extensions.ExceptionKtxKt")

        coEvery { mockAccountsRepository.fetchAccounts() } answers repositoryAnswer

        subject.getAccounts()

        verifyOrder {
            mockState.set(LoadingAccounts)
            mockState.set(expectedState)
        }
    }
}