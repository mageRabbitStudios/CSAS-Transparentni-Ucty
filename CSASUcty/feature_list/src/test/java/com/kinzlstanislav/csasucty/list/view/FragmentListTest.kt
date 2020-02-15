package com.kinzlstanislav.csasucty.list.view

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.kinzlstanislav.csasucty.architecture.repository.model.Account
import com.kinzlstanislav.csasucty.base.extensions.set
import com.kinzlstanislav.csasucty.list.view.adapter.AccountViewHolder
import com.kinzlstanislav.csasucty.list.viewmodel.ListViewModel
import com.kinzlstanislav.csasucty.list.viewmodel.ListViewModel.ListState
import com.kinzlstanislav.csasucty.list.viewmodel.ListViewModel.ListState.*
import com.kinzlstanislav.csasucty.viewtesting.*
import com.kinzlstanislav.csasucty.viewtesting.matchers.assertViewHolderOfItemAtPosition
import com.kinzlstanislav.hoslaviceo2.list.R
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class FragmentListTest : FragmentKoinTest<FragmentList>() {

    private companion object {
        val SOME_ACCOUNTS = listOf(
            Account(
                accountNumber = "12345",
                bankCode = "",
                transparencyFrom = "12 12 2020",
                transparencyTo = "",
                publicationTo = "",
                actualizationDate = "",
                balance = 300.0f,
                currency = "CZK",
                name = "Ucet Narodni Banky",
                iban = ""
            ),
            Account(
                accountNumber = "890-342",
                bankCode = "",
                transparencyFrom = "31 04 1999",
                transparencyTo = "",
                publicationTo = "",
                actualizationDate = "",
                balance = 100000.0f,
                currency = "GBP",
                name = "Ucet Elizabeth II.",
                iban = ""
            )
        )
    }
    override val fragmentInstance = FragmentList()

    private val mockViewModel = mockk<ListViewModel>(relaxed = true)
    private val subjectVMState = MutableLiveData<ListState>()

    override fun setup() {
        super.setup()
        mockKoinForFragment {
            single { mockViewModel }
        }
        every { mockViewModel.state } returns subjectVMState
        launchFragment()
    }

    @Test
    fun fragmentFlow() {

        // states
        subjectVMState.set(LoadingAccounts)
        assertIsViewVisible(R.id.accounts_progress_bar)

        subjectVMState.set(NetworkError)
        assertIsViewGone(R.id.accounts_progress_bar)
        assertIsViewVisible(R.id.accounts_network_error)
        assertHasText(R.id.accounts_network_error, "Network Error")

        subjectVMState.set(GenericError)
        assertIsViewGone(R.id.accounts_network_error)
        assertIsViewVisible(R.id.accounts_generic_error)
        assertHasText(R.id.accounts_generic_error, "Generic Error")

        subjectVMState.set(AccountsLoaded(SOME_ACCOUNTS))
        assertIsViewGone(R.id.accounts_generic_error)
        assertIsViewVisible(R.id.carousel_container)
        assertIsViewVisible(R.id.carousel_page_indicator)
        assertHasText(R.id.carousel_page_indicator, "1/2")

        assertAccountItem(
            position = 0,
            name = "Ucet Narodni Banky",
            accountNumber = "12345",
            transparencyFrom = "12 12 2020",
            currency = "CZK",
            balance = "300.0"
        )
        assertAccountItem(
            position = 1,
            name = "Ucet Elizabeth II.",
            accountNumber = "890-342",
            transparencyFrom = "31 04 1999",
            currency = "GBP",
            balance = "100000.0"
        )
    }

    private fun assertAccountItem(
        position: Int,
        name: String,
        accountNumber: String,
        transparencyFrom: String,
        currency: String,
        balance: String
    ) {
        R.id.carousel.let {
            assertDisplayedAtPosition(it, position, R.id.account_name, name)
            assertDisplayedAtPosition(it, position, R.id.account_number, accountNumber)
            assertDisplayedAtPosition(it, position, R.id.transparency_from, transparencyFrom)
            assertDisplayedAtPosition(it, position, R.id.account_balance_currency, currency)
            assertDisplayedAtPosition(it, position, R.id.account_balance, balance)
        }
        assertViewHolderOfItemAtPosition(
            getViewFromActivityById(R.id.carousel) as RecyclerView,
            position,
            AccountViewHolder::class.java
        )
    }

}