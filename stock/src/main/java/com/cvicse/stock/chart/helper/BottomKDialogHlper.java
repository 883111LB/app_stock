package com.cvicse.stock.chart.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.haitong.R;
import com.cvicse.base.exception.BaseException;
import com.cvicse.base.widget.ExtendedRadioGroup;
import com.cvicse.stock.chart.listener.OnChartListener;
import com.stock.config.KSetting;

/**
 * Created by liu_zlu on 2017/3/21 20:32
 * 指标设置帮助类
 */
public class BottomKDialogHlper {
    private final Context context;
    private boolean isLand;
    private Dialog dialog;

    private ImageView imgClose;
    private ExtendedRadioGroup erdgRightType;
    private TextView tevSure;
    OnChartListener onChartListener;

    public void setOnChartListener(OnChartListener onChartListener) {
        this.onChartListener = onChartListener;
    }

    public BottomKDialogHlper(Context context) {
        this.context = context;
    }

    public void show() {
        if (dialog == null) {
            createDialog();
        }
        refreshView();
        dialog.show();
    }

    private void refreshView() {
        erdgRightType.setSelectPosition(KSetting.getKType());
    }

    private void createDialog() {
        dialog = new Dialog(getActivity(context), R.style.Dialog);
        dialog.setContentView(R.layout.dialog_chart_ktype_setting);
        imgClose = (ImageView) dialog.findViewById(R.id.img_close);
        erdgRightType = (ExtendedRadioGroup) dialog.findViewById(R.id.erdg_right_type);
        tevSure = (TextView) dialog.findViewById(R.id.tev_sure);
        initViews();
    }

    private void initViews() {
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tevSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!saveSetting()) return;
                dialog.dismiss();
                if(onChartListener != null){
                    onChartListener.onKTypeClick();
                }
            }
        });
    }

    private boolean saveSetting() {
        KSetting.setKType(erdgRightType.getSelectPosition());
        return true;
    }

    public void setLand(boolean isLand){
       this.isLand = isLand;
    }

    private Activity getActivity( Context context) {
        if( null==context ){
            throw new BaseException("");
        }
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }

        if (context instanceof Activity) {
            return (Activity) context;
        }
       return null;
    }
}
