package com.kinzlstanislav.csasucty.viewtesting

import android.os.Looper
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.kinzlstanislav.csasucty.viewtesting.helpers.InstrumentationTestsHelper
import org.assertj.core.api.Assertions
import org.junit.Assert
import org.robolectric.Shadows
import org.robolectric.annotation.LooperMode
import org.robolectric.config.ConfigurationRegistry

fun FragmentKoinTest<*>.assertIsViewGone(resourceId: Int) {
    getViewFromActivityById(resourceId)?.let {
        Assertions.assertThat(it.visibility).isEqualTo(View.GONE)
    }
}

fun FragmentKoinTest<*>.assertIsViewVisible(resourceId: Int) {
    getViewFromActivityById(resourceId)?.let {
        Assertions.assertThat(it.visibility).isEqualTo(View.VISIBLE)
    }}

fun FragmentKoinTest<*>.assertIsViewInvisible(resourceId: Int) {
    getViewFromActivityById(resourceId)?.let {
        Assertions.assertThat(it.visibility).isEqualTo(View.INVISIBLE)
    }}

/**
 * Great for asserting whenever your view is in the hierarchy or not
 * */
fun FragmentKoinTest<*>.printViewHierarchy() {
    InstrumentationTestsHelper.printViewHierarchy(subject.requireActivity())
}

fun FragmentKoinTest<*>.getResString(@StringRes id: Int, args: Any? = null): String
        = targetContext.resources.getString(id, args)

fun FragmentKoinTest<*>.getResInteger(@IntegerRes id: Int) = targetContext.resources.getInteger(id)

fun FragmentKoinTest<*>.getResColor(@ColorRes id: Int) = ContextCompat.getColor(targetContext, id)

fun FragmentKoinTest<*>.getResDrawable(@DrawableRes id: Int) = ContextCompat.getDrawable(targetContext, id)

fun FragmentKoinTest<*>.getViewFromActivityById(id: Int): View? {
    val activity = subject.requireActivity()
    activity.let {
        it.findViewById<View>(id)?.let { view ->
            return view
        } ?: run {
            Assert.fail("View is null and so no visibility check can take place on it")
        }
    }
    return null
}

fun FragmentKoinTest<*>.assertHasText(id: Int, text: String) {
    onView(withId(id)).check(matches(withText(text)))
}

/**
 * Executes all posted tasks on the main looper, such as animations.
 * */
fun FragmentKoinTest<*>.executeAllPostedTasksOnMainLooper() {
    if (isLooperModePaused()) {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
    } else throw UninitializedPropertyAccessException("Idling on looper won't work if the mode in't " +
            "PAUSED (Realistic). Add @LooperMode(LooperMode.Mode.PAUSED) as an annotation to the test function.")
}

private fun isLooperModePaused() = ConfigurationRegistry.get(LooperMode.Mode::class.java) == LooperMode.Mode.PAUSED
