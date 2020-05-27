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

package com.cvicse.base.skin;

import android.support.annotation.CallSuper;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.cvicse.base.utils.StringUtils;

class TintProgressBarHelper implements Tintable{

    private final ProgressBar mView;

    private int mIndeterminateDrawableAttr;
    private int mprogressDrawableAttr;
    TintProgressBarHelper(ProgressBar view) {
        mView = view;
    }
    @CallSuper
    void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        int count = attrs.getAttributeCount();
        String bgId = StringUtils.EMPTY;
        String attributeName = StringUtils.EMPTY;
        for(int i = 0;i< count;i++){
            bgId = attrs.getAttributeValue(i);
            attributeName = attrs.getAttributeName(i);
            if(attributeName.equals("indeterminateDrawable")){
                if(bgId.startsWith("?")){
                    mIndeterminateDrawableAttr = Integer.parseInt(bgId.substring(1));
                }
                continue;
            }
            if(attributeName.equals("progressDrawable")){
                if(bgId.startsWith("?")){
                    mprogressDrawableAttr = Integer.parseInt(bgId.substring(1));
                }
                continue;
            }
        }
    }

    @Override
    public void tint() {
        /*if(mIndeterminateDrawableAttr != 0)
            TintCompat.setIndeterminateDrawable(mView, ThemeUtils.getThemeAttrResId(mView.getContext(),mIndeterminateDrawableAttr));
        if(mprogressDrawableAttr != 0)
            TintCompat.setProgressDrawable(mView,ThemeUtils.getThemeAttrResId(mView.getContext(),mprogressDrawableAttr));*/
    }
}
