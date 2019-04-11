package com.mytaxi.android_demo.idlingResource;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class TestIdlingResource implements IdlingResource {

    @Nullable
    private static TestIdlingResource mIdlingResource;

    @Nullable
    private volatile IdlingResource.ResourceCallback mCallback;

    private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback = callback;
    }

    public void changeIdleState(boolean isIdleNow) {
        mIsIdleNow.set(isIdleNow);
        if (isIdleNow && mCallback != null) {
            mCallback.onTransitionToIdle();
        }
    }

    public static TestIdlingResource getInstance() {
        if (mIdlingResource == null) {
            mIdlingResource = new TestIdlingResource();
        }
        return mIdlingResource;
    }
}
