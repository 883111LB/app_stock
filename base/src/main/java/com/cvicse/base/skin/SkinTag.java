package com.cvicse.base.skin;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cvicse.base.R;
import com.cvicse.base.skin.helper.TintBackgroundHelper;
import com.cvicse.base.skin.helper.TintCompoundButtonHelper;
import com.cvicse.base.skin.helper.TintImageHelper;
import com.cvicse.base.skin.helper.TintListViewHelper;
import com.cvicse.base.skin.helper.TintTextHelper;

import java.util.ArrayList;

/**
 * Created by liu_zlu on 2017/2/6 09:21
 */
 class SkinTag implements Tintable {
    private ArrayList<LoadTinable> tintables = new ArrayList<>();
    private View view;
    private int styleId = 0;
    @Override
    public void tint() {
        TypedArray typedArray =  view.getContext().getTheme().obtainStyledAttributes(styleId,R.styleable.Skin_change);
       // TypedValue typedValue = new TypedValue();
        //TintTypedArray typedArray = TintTypedArray.obtainStyledAttributes(view.getContext(), null, R.styleable.Skin_change);
        for(int i =0 ;i <typedArray.length();i++){
            //typedArray.getValue(0,typedValue);
           // if(typedValue.resourceId > 0){
                for(LoadTinable tintable:tintables){
                    if(tintable.loadFromStyle(i,typedArray)){
                        break;
                    }
                }
           // }
        }
        typedArray.recycle();
        for(Tintable tintable:tintables){
            tintable.tint();
        }
    }

    static SkinTag create(View view, AttributeSet attributeSet){
        return new SkinTag(view,attributeSet);
    }
    SkinTag(View view, AttributeSet attributeSet){
        this.view = view;
        view.setTag(R.id.skin_tag,this);
        tintables.add(new TintBackgroundHelper(view));
        if(view instanceof CompoundButton)
            tintables.add(new TintCompoundButtonHelper((CompoundButton) view));
        if(view instanceof TextView)
            tintables.add(new TintTextHelper((TextView) view));
        if(view instanceof ImageView)
            tintables.add(new TintImageHelper((ImageView) view));
        if(view instanceof ListView){
            tintables.add(new TintListViewHelper((ListView) view));
        }
        if(SkinConfig.skinTintFactory != null){
            SkinConfig.skinTintFactory.create(tintables,view);
        }

        loadAttributes(attributeSet);
    }

    private void loadAttributes(AttributeSet attributeSet){
        for (int i = 0; i < attributeSet.getAttributeCount(); i++) {//遍历当前View的属性
            String attrName = attributeSet.getAttributeName(i);//属性名
            String attrValue = attributeSet.getAttributeValue(i);//属性值
            if(!attrValue.startsWith("?") && !"style".equals(attrName)){
                continue;
            }
            if("style".equals(attrName)){
                dealStyle(attrValue);
                continue;
            }
            attrValue = attrValue.substring(1);
            for(LoadAttributes loadAttributes:tintables){
                if(loadAttributes.loadFromAttributes(attrName,attrValue)){
                    break;
                }
            }
        }
    }

    private void dealStyle(String attrValue){
        String styleName = attrValue.substring(attrValue.indexOf("/") + 1);
        int styleID = view.getResources().getIdentifier(styleName, "style", view.getContext().getPackageName());
        styleId = styleID;
    }

}
