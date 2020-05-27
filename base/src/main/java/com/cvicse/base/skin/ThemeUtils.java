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

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.TintTypedArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;

import com.cvicse.base.R;
import com.cvicse.base.ui.BaseFragment;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ThemeUtils {

    private static final ThreadLocal<TypedValue> TL_TYPED_VALUE = new ThreadLocal<>();

    public static final int[] DISABLED_STATE_SET = new int[]{-android.R.attr.state_enabled};
    public static final int[] FOCUSED_STATE_SET = new int[]{android.R.attr.state_focused};
    public static final int[] ACTIVATED_STATE_SET = new int[]{android.R.attr.state_activated};
    public static final int[] PRESSED_STATE_SET = new int[]{android.R.attr.state_pressed};
    public static final int[] CHECKED_STATE_SET = new int[]{android.R.attr.state_checked};
    public static final int[] SELECTED_STATE_SET = new int[]{android.R.attr.state_selected};
    public static final int[] NOT_PRESSED_OR_FOCUSED_STATE_SET = new int[]{
            -android.R.attr.state_pressed, -android.R.attr.state_focused};
    public static final int[] EMPTY_STATE_SET = new int[0];

    private static final int[] TEMP_ARRAY = new int[1];

    public static ColorStateList createDisabledStateList(int textColor, int disabledTextColor) {
        // Now create a new ColorStateList with the default color, and the new disabled
        // color
        final int[][] states = new int[2][];
        final int[] colors = new int[2];
        int i = 0;

        // Disabled state
        states[i] = DISABLED_STATE_SET;
        colors[i] = disabledTextColor;
        i++;

        // Default state
        states[i] = EMPTY_STATE_SET;
        colors[i] = textColor;
        return new ColorStateList(states, colors);
    }

    public static int getThemeAttrColor(Context context, int attr) {
        TEMP_ARRAY[0] = attr;
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, null, TEMP_ARRAY);
        try {
            return a.getColor(0, 0);
        } finally {
            a.recycle();
        }
    }

    public static Drawable getThemeAttrDrawable(Context context, int attr) {
        TEMP_ARRAY[0] = attr;
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, null, TEMP_ARRAY);
        try {
            return a.getDrawable(0);
        } finally {
            a.recycle();
        }
    }

    public static ColorStateList getThemeAttrColorStateList(Context context, int attr) {
        TEMP_ARRAY[0] = attr;
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, null, TEMP_ARRAY);
        try {
            return a.getColorStateList(0);
        } finally {
            a.recycle();
        }
    }
    public static int getThemeAttrResId(Context context, int attr) {
        TEMP_ARRAY[0] = attr;
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, null, TEMP_ARRAY);
        try {
            return a.getResourceId(0,0);
        } finally {
            a.recycle();
        }
    }
    public static int getDisabledThemeAttrColor(Context context, int attr) {
        final ColorStateList csl = getThemeAttrColorStateList(context, attr);
        if (csl != null && csl.isStateful()) {
            // If the CSL is stateful, we'll assume it has a disabled state and use it
            return csl.getColorForState(DISABLED_STATE_SET, csl.getDefaultColor());
        } else {
            // Else, we'll generate the color using disabledAlpha from the theme

            final TypedValue tv = getTypedValue();
            // Now retrieve the disabledAlpha value from the theme
            context.getTheme().resolveAttribute(android.R.attr.disabledAlpha, tv, true);
            final float disabledAlpha = tv.getFloat();

            return getThemeAttrColor(context, attr, disabledAlpha);
        }
    }

    private static TypedValue getTypedValue() {
        TypedValue typedValue = TL_TYPED_VALUE.get();
        if (typedValue == null) {
            typedValue = new TypedValue();
            TL_TYPED_VALUE.set(typedValue);
        }
        return typedValue;
    }

    static int getThemeAttrColor(Context context, int attr, float alpha) {
        final int color = getThemeAttrColor(context, attr);
        final int originalAlpha = Color.alpha(color);
        return ColorUtils.setAlphaComponent(color, Math.round(originalAlpha * alpha));
    }

    public static int getThemeAttrInt(Context context, int attr) {
        TEMP_ARRAY[0] = attr;
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, null, TEMP_ARRAY);
        try {
            return a.getInt(0,-1);
        } finally {
            a.recycle();
        }
    }


    public static void refreshUI(Context context) {
        Activity activity = getWrapperActivity(context);
        if (activity != null) {
            View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
            refreshView(rootView);

            if(activity instanceof FragmentActivity){
                FragmentManager fragmentManager = ((FragmentActivity)activity).getSupportFragmentManager();
                if (fragmentManager != null){
                    List<Fragment> fragments = fragmentManager.getFragments();
                    if(fragments == null){
                        return;
                    }
                    for(Fragment fragment1:fragments){
                        if(fragment1 instanceof Tintable){
                            ((Tintable) fragment1).tint();
                        }
                    }
                }
            }
        }
    }
    public static void refreshUI(Fragment fragment) {
        if(!fragment.isAdded()){
            refreshView(((BaseFragment)fragment).view);
        }
    }
    private static Field sRecyclerBin;
    private static Method sListViewClearMethod;

    private static void refreshView(View view) {
        if (view == null) return;

        view.destroyDrawingCache();
        SkinTag skinTag = (SkinTag)view.getTag(R.id.skin_tag);
        if(skinTag != null){
            skinTag.tint();
        }
        if (view instanceof AbsListView) {
            try {
                if (sRecyclerBin == null) {
                    sRecyclerBin = AbsListView.class.getDeclaredField("mRecycler");
                    sRecyclerBin.setAccessible(true);
                }
                if (sListViewClearMethod == null) {
                    sListViewClearMethod = Class.forName("android.widget.AbsListView$RecycleBin")
                            .getDeclaredMethod("clear");
                    sListViewClearMethod.setAccessible(true);
                }
                sListViewClearMethod.invoke(sRecyclerBin.get(view));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            ListAdapter adapter = ((AbsListView) view).getAdapter();
            while (adapter instanceof WrapperListAdapter) {
                adapter = ((WrapperListAdapter) adapter).getWrappedAdapter();
            }
            if (adapter instanceof BaseAdapter) {
                ((BaseAdapter) adapter).notifyDataSetChanged();
            }
        }
       /* if (view instanceof RecyclerView) {
            try {
                if (sRecycler == null) {
                    sRecycler = RecyclerView.class.getDeclaredField("mRecycler");
                    sRecycler.setAccessible(true);
                }
                if (sRecycleViewClearMethod == null) {
                    sRecycleViewClearMethod = Class.forName("android.support.v7.widget.RecyclerView$Recycler")
                            .getDeclaredMethod("clear");
                    sRecycleViewClearMethod.setAccessible(true);
                }
                sRecycleViewClearMethod.invoke(sRecycler.get(view));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            ((RecyclerView) view).getRecycledViewPool().clear();
            ((RecyclerView) view).invalidateItemDecorations();
        }*/
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                refreshView(((ViewGroup) view).getChildAt(i));
            }
        }
    }

    public static Activity getWrapperActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return getWrapperActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    public static ContextWrapper getWrapperContext(Context context, @StyleRes int themeId) {
        if (context == null) return null;

        return new ContextThemeWrapper(context, themeId);
    }
}
