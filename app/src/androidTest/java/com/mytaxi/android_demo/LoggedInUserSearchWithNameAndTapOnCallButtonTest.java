package com.mytaxi.android_demo;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;
import android.util.Log;

import com.mytaxi.android_demo.activities.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class LoggedInUserSearchWithNameAndTapOnCallButtonTest {

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        activityActivityTestRule.launchActivity(null);
        mIdlingResource = activityActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class,false,false);
    @Rule
    public GrantPermissionRule runtimePermissionRule = GrantPermissionRule.grant(ACCESS_FINE_LOCATION);


    @Test
    public void testLoggedInUserSearchWithNameAndTapOnCallButton() {

        try {
            onView(withContentDescription("Open navigation drawer")).perform(ViewActions.click());
            onView(withText(R.string.text_item_title_logout)).check(matches(isDisplayed())).perform(ViewActions.click());
        } catch (NoMatchingViewException e) {
            Log.i("TEST_LOG", "User has not logged in yet -> Proceed the steps with login flow");
        }

        onView(withId(R.id.edt_username)).perform(ViewActions.typeText("crazydog335"));
        onView(withId(R.id.edt_password)).perform(ViewActions.typeText("venture"));
        onView(withId(R.id.btn_login)).perform(click());
        Log.i("TEST_LOG", "User successfully logged In");

        onView(allOf(withId(R.id.textSearch),isDisplayed())).perform(ViewActions.typeText("sa"));
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.wait(Until.findObject(By.textContains("Sarah Scott")),2).click();
        Log.i("TEST_LOG", "User searched with 'sa' and selected second one with driver name 'Sarah Scott'");

        onView(withText(R.string.title_activity_driver_profile)).check(matches(isDisplayed()));
        onView(withId(R.id.fab)).perform(click());
        Log.i("TEST_LOG", "User navigated to driver profile screen and tapped on call button");
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}
