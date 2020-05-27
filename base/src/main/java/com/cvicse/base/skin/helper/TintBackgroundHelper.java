/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cvicse.base.skin.helper;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.cvicse.base.R;
import com.cvicse.base.skin.LoadTinable;
import com.cvicse.base.skin.ThemeUtils;
import com.cvicse.base.skin.utils.DrawableUtils;

public class TintBackgroundHelper implements LoadTinable {

    private final View mView;

    private int mBackgroundAttr;
    private int mbackgroundTintAttr;
    private int mbackgroundTintModeAttr;
    public TintBackgroundHelper(View view) {
        mView = view;
    }

    public boolean loadFromAttributes(String attributeName ,String bgId) {
        if(attributeName.equals("background")){
            mBackgroundAttr = Integer.parseInt(bgId);
            return true;
        }
        if(attributeName.equals("backgroundTint")){
            mbackgroundTintAttr = Integer.parseInt(bgId);
            return true;
        }
        if(attributeName.equals("backgroundTintMode")){
            mbackgroundTintModeAttr = Integer.parseInt(bgId);
            return true;
        }
        return false;
    }

    @Override
    public boolean loadFromStyle(int index, TypedArray typedArray) {
        if(index == R.styleable.Skin_change_android_background){
            Drawable drawable = typedArray.getDrawable(R.styleable.Skin_change_android_background);
            if(drawable == null)return true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mView.setBackground(drawable);
            } else {
                mView.setBackgroundDrawable(drawable);
            }
            return true;
        }
        return false;
    }

    @Override
    public void tint() {
        if(mBackgroundAttr != 0){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                mView.setBackground( ThemeUtils.getThemeAttrDrawable(mView.getContext(),mBackgroundAttr));
            } else {
                mView.setBackgroundDrawable( ThemeUtils.getThemeAttrDrawable(mView.getContext(),mBackgroundAttr));
            }
        }

        if(mbackgroundTintAttr != 0)
            ViewCompat.setBackgroundTintList(mView,ThemeUtils.getThemeAttrColorStateList(mView.getContext(),mbackgroundTintAttr));
        if(mbackgroundTintModeAttr != 0)
            ViewCompat.setBackgroundTintMode(mView, DrawableUtils.parseTintMode(ThemeUtils.getThemeAttrInt(mView.getContext(),mbackgroundTintModeAttr),null));
    }
}
