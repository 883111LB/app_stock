package com.cvicse.base.skin;

import android.content.res.TypedArray;

/**
 * Created by liu_zlu on 2017/2/28 17:06
 */
public interface LoadAttributes {
    boolean loadFromAttributes(String attributeName ,String bgId);

    boolean loadFromStyle(int index ,TypedArray typedArray);
}
