package com.rushbox.android.rushboxapp.comparators;

import com.rushbox.android.rushboxapp.model.Provider;

import java.util.Comparator;

/**
 * Created by Ronner on 16-12-2015.
 */
public class StoreAvailabilityAndDistanceComparator implements Comparator<Provider> {

    @Override
    public int compare(Provider store1, Provider store2) {
        int result = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            result = Boolean.compare(store2.isBO_Open(),store1.isBO_Open());
        }
        if (result == 0)
            result =Double.compare(store1.getNU_Distance(), store2.getNU_Distance());
        return result;
    }
}
