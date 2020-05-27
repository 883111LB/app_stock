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
import android.support.v4.widget.CompoundButtonCompat;
import android.widget.CompoundButton;

import com.cvicse.base.R;
import com.cvicse.base.skin.LoadTinable;
import com.cvicse.base.skin.ThemeUtils;
import com.cvicse.base.skin.utils.DrawableUtils;

public class TintCompoundButtonHelper implements LoadTinable {

    private final CompoundButton mView;

    private int mButtonAttr;
    private int mButtonTintAttr;
    private int mButtonTintModeModeAttr;

    public TintCompoundButtonHelper(CompoundButton view) {
        mView = view;
    }

    public boolean loadFromAttributes(String attributeName, String bgId) {
        if(attributeName.equals("button")){
            mButtonAttr = Integer.parseInt(bgId);
            return true;
        }
        if(attributeName.equals("buttonTint")){
            mButtonTintAttr = Integer.parseInt(bgId);
            return true;
        }
        if(attributeName.equals("buttonTintMode")){
            mButtonTintModeModeAttr = Integer.parseInt(bgId);
            return true;
        }
        return false;
    }

    @Override
    public boolean loadFromStyle(int index, TypedArray typedArray) {
        if(index == R.styleable.Skin_change_android_button){
            Drawable drawable = typedArray.getDrawable(index);
            if(drawable != null){
                mView.setButtonDrawable(drawable);
            }
            return true;
        }
        return false;
    }

    @Override
    public void tint() {
        if(mButtonAttr != 0)
            mView.setButtonDrawable(ThemeUtils.getThemeAttrDrawable(mView.getContext(),mButtonAttr));
        if(mButtonTintAttr != 0)
            CompoundButtonCompat.setButtonTintList(mView,ThemeUtils.getThemeAttrColorStateList(mView.getContext(),mButtonTintAttr));
        if(mButtonTintModeModeAttr != 0)
            CompoundButtonCompat.setButtonTintMode(mView, DrawableUtils.parseTintMode(ThemeUtils.getThemeAttrInt(mView.getContext(),mButtonTintModeModeAttr),null));
    }
}
