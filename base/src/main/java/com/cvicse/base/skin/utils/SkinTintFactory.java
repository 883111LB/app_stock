package com.cvicse.base.skin.utils;

import android.view.View;

import com.cvicse.base.skin.LoadTinable;

import java.util.ArrayList;

/**
 * Created by liuzilu on 2017/3/5.
 */

public interface SkinTintFactory {

    void create(ArrayList<LoadTinable> tintables,View view);
}
