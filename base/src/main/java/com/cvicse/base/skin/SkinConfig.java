package com.cvicse.base.skin;

import com.cvicse.base.skin.utils.SkinTintFactory;

/**
 * Created by liuzilu on 2017/3/6.
 */

public class SkinConfig {
    static SkinTintFactory skinTintFactory;
    public static void setSkinTintFactory(SkinTintFactory skinTintFactory){
        SkinConfig.skinTintFactory = skinTintFactory;
    }
}
