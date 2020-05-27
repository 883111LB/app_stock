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


import android.util.AttributeSet;
import android.widget.SeekBar;

import org.xmlpull.v1.XmlPullParser;

class TintSeekBarHelper extends TintProgressBarHelper implements Tintable {
    private final SeekBar mView;
    private int mThumbAttr;
    private int mTickMarkAttr;
    private int mTickMarkTintModeAttr;
    private int mTickMarkTintAttr;
    TintSeekBarHelper(SeekBar view) {
        super(view);
        mView = view;
    }

    @Override
    void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        super.loadFromAttributes(attrs, defStyleAttr);
        XmlPullParser xmlPullParser = (XmlPullParser)attrs;
        int count = xmlPullParser.getAttributeCount();
        String bgId = null;
        String attributeName = null;
        for(int i = 0;i< count;i++){
            bgId = xmlPullParser.getAttributeValue(i);
            attributeName = xmlPullParser.getAttributeName(i);
            if(attributeName.equals("thumb")){
                if(bgId.startsWith("?")){
                    mThumbAttr = Integer.parseInt(bgId.substring(1));
                }
                continue;
            }
            if(attributeName.equals("tickMark")){
                if(bgId.startsWith("?")){
                    mTickMarkAttr = Integer.parseInt(bgId.substring(1));
                }
                continue;
            }
            if(attributeName.equals("tickMarkTintMode")){
                if(bgId.startsWith("?")){
                    mTickMarkTintModeAttr = Integer.parseInt(bgId.substring(1));
                }
                continue;
            }
            if(attributeName.equals("tickMarkTint")){
                if(bgId.startsWith("?")){
                    mTickMarkTintAttr = Integer.parseInt(bgId.substring(1));
                }
                continue;
            }
        }
    }
    @Override
    public void tint() {
      /*  if(mThumbAttr != 0)
            mView.setThumb(ThemeUtils.getThemeAttrDrawable(mView.getContext(),mThumbAttr));
        if(mTickMarkAttr != 0)
            TintCompat.setTickMark(mView,ThemeUtils.getThemeAttrDrawable(mView.getContext(),mTickMarkAttr));
        if(mTickMarkTintModeAttr != 0)
            TintCompat.setTickMarkTintMode(mView, DrawableUtils.parseTintMode(ThemeUtils.getThemeAttrInt(mView.getContext(),mTickMarkTintModeAttr),null));
        if(mTickMarkTintAttr != 0)
            TintCompat.setTickMarkTintList(mView,ThemeUtils.getThemeAttrColorStateList(mView.getContext(),mTickMarkTintAttr));*/
    }
}
