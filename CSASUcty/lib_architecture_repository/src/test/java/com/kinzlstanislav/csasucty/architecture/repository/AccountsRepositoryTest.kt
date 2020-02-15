package com.kinzlstanislav.csasucty.architecture.repository

import com.kinzlstanislav.csasucty.architecture.network.api.CSASApiService
import com.kinzlstanislav.csasucty.architecture.network.response.AccountsResponse
import com.kinzlstanislav.csasucty.architecture.repository.mapper.AccountsMapper
import com.kinzlstanislav.csasucty.architecture.repository.model.Account
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class AccountsRepositoryTest {

    private val mockAccountsMapper = mockk<AccountsMapper>()
    private val mockApi = mockk<CSASApiService>()
    private val mockResponse = mockk<Deferred<AccountsResponse>>()
    private val mockAwaitedResponse = mockk<AccountsResponse>()
    private val mockAccount = mockk<Account>()

    private val subject = AccountsRepository(mockAccountsMapper, mockApi)

    @Test
    fun `fetchAccounts()`() {
        every { mockApi.getAccountsAsync() } returns mockResponse
        coEvery { mockResponse.await() } returns mockAwaitedResponse
        every { mockAccountsMapper.mapAccounts(mockAwaitedResponse) } returns listOf(mockAccount, mockAccount)

        runBlocking {
            assertThat(subject.fetchAccounts()).isEqualTo(listOf(mockAccount, mockAccount))
        }
    }
}