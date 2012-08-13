package com.siu.android.athismons.location;

import android.content.Context;
import com.siu.android.athismons.AndroidConstants;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class GetLastLocationFactory {

    public static GetLastLocation getLastLocationFinder(Context context) {
        return AndroidConstants.SUPPORTS_GINGERBREAD ? new GingerbreadGetLastLocation(context) : new LegacyGetLastLocation(context);
    }
}
