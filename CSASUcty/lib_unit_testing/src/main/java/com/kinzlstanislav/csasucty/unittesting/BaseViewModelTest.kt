package com.kinzlstanislav.csasucty.unittesting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.rules.TestRule

abstract class BaseViewModelTest : BaseMockitoTest() {

    @get:Rule
    internal var instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    internal var coroutinesTestRule: CoroutinesTestRule = CoroutinesTestRule()
}