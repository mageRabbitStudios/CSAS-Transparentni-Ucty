package com.kinzlstanislav.csasucty.viewtesting

import android.content.Context
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
abstract class FragmentKoinTest<FRAGMENT : Fragment> {

    private companion object {
        val TEST_ENVIRONMENT_THEME = R.style.Theme_AppCompat
    }

    private lateinit var fragmentScenario: FragmentScenario<Fragment>

    val targetContext: Context get() = InstrumentationRegistry.getInstrumentation().targetContext

    abstract val fragmentInstance: FRAGMENT

    @Suppress("UNCHECKED_CAST")
    val subject: FRAGMENT
        get() {
            var fragment: Fragment? = null
            fragmentScenario.onFragment {
                fragment = it
            }
            return fragment as FRAGMENT
        }

    @Mock
    protected lateinit var mockNavController: NavController

    @CallSuper
    @Before
    open fun setup() {
        Intents.init()
        InstrumentationRegistry.getInstrumentation().targetContext
            .applicationContext.setTheme(TEST_ENVIRONMENT_THEME)
        MockitoAnnotations.initMocks(this)
    }

    @CallSuper
    @After
    open fun tearDown() {
        Intents.release()
    }

    protected fun launchFragment() {
        fragmentScenario = launchFragmentInContainer {
            fragmentInstance as Fragment
        }

        subject.view?.let { view ->
            Navigation.setViewNavController(view, mockNavController)
        }
    }

    protected fun mockKoinForFragment(mocks: Module.() -> Unit) {
        startKoin {
            modules(
                module {
                    mocks()
                }
            )
        }
    }
}