package com.exercise.coding.ebay.earthquakeanalytics.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.exercise.coding.ebay.earthquakeanalytics.home.earthquake.ColorCode;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by kushahuja on 7/14/17.
 */


public class ActivityUtil {
    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static ColorCode getColorCode(double magnitude) {

        if (magnitude > 0 && magnitude < 8) {
            return ColorCode.YELLOW;
        } else if (magnitude == 8) {
            return ColorCode.ORANGE;

        } else {
            return ColorCode.RED;
        }
    }
}
