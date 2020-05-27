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

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.TextViewCompat;
import android.widget.TextView;

import com.cvicse.base.R;
import com.cvicse.base.skin.LoadTinable;
import com.cvicse.base.skin.ThemeUtils;

public class TintTextHelper implements LoadTinable {

    final TextView mView;

    private int mTextColorHintAttr;
    private int mDrawableLeftAttr;
    private int mDrawableTopAttr;
    private int mDrawableRightAttr;
    private int mDrawableBottomAttr;

    private int mTextColorAttr;
    public TintTextHelper(TextView view) {
        mView = view;
    }

    public boolean loadFromAttributes(String attributeName, String bgId) {
        if(attributeName.equals("drawableLeft")){
            mDrawableLeftAttr = Integer.parseInt(bgId);
            return true;
        }
        if(attributeName.equals("drawableTop")){
            mDrawableTopAttr = Integer.parseInt(bgId);
            return true;
        }
        if(attributeName.equals("drawableRight")){
            mDrawableRightAttr = Integer.parseInt(bgId);
            return true;
        }
        if(attributeName.equals("drawableRight")){
            mDrawableBottomAttr = Integer.parseInt(bgId);
            return true;
        }
        if(attributeName.equals("textColor")){
            mTextColorAttr = Integer.parseInt(bgId);
            return true;
        }
        if(attributeName.equals("textColorHint")){
            mTextColorHintAttr = Integer.parseInt(bgId);
            return true;
        }
        return false;
    }

    @Override
    public boolean loadFromStyle(int index, TypedArray typedArray) {
       if(R.styleable.Skin_change_android_textColor == index){
           ColorStateList colorStateList = typedArray.getColorStateList(index);
           if(colorStateList != null){
               mView.setTextColor(colorStateList);
           }
           return true;
       }
        return false;
    }

    @Override
    public void tint() {
        if(mTextColorAttr != 0)
            mView.setTextColor(ThemeUtils.getThemeAttrColorStateList(mView.getContext(),mTextColorAttr));
        if(mTextColorHintAttr != 0)
            mView.setHintTextColor(ThemeUtils.getThemeAttrColorStateList(mView.getContext(),mTextColorHintAttr));
        Drawable[] drawables = mView.getCompoundDrawables();
        if(mDrawableLeftAttr != 0)
            drawables[0] = ThemeUtils.getThemeAttrDrawable(mView.getContext(),mDrawableLeftAttr);
        if(mDrawableTopAttr != 0)
            drawables[1] = ThemeUtils.getThemeAttrDrawable(mView.getContext(),mDrawableTopAttr);
        if(mDrawableRightAttr != 0)
            drawables[2] = ThemeUtils.getThemeAttrDrawable(mView.getContext(),mDrawableRightAttr);
        if(mDrawableBottomAttr != 0)
            drawables[3] = ThemeUtils.getThemeAttrDrawable(mView.getContext(),mDrawableBottomAttr);
        TextViewCompat.setCompoundDrawablesRelative(mView,drawables[0],drawables[1],drawables[2],drawables[3]);
    }
}
