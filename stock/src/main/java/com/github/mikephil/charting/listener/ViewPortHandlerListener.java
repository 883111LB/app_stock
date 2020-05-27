package com.github.mikephil.charting.listener;

import android.graphics.Matrix;
import android.view.View;

/**
 * Created by liu_zlu on 2017/2/20 11:11
 */
public interface ViewPortHandlerListener {

    void refresh(Matrix newMatrix, View chart, boolean invalidate);
}
