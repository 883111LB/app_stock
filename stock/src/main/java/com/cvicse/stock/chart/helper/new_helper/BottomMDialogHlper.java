package com.cvicse.stock.chart.helper.new_helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.haitong.R;
import com.cvicse.base.exception.BaseException;
import com.cvicse.base.widget.ExtendedRadioGroup;
import com.cvicse.stock.chart.listener.OnChartListener;
import com.stock.config.MSetting;

/**
 * 走势副图指标设置帮助类
 * Created by tang_xqing on 2018/3/19
 */
public class BottomMDialogHlper {
    private final Context context;
    private Dialog dialog;
    private ImageView imgClose;

    private ExtendedRadioGroup erdgRightType;
    private TextView tevSure;
    private boolean isLand;
    private OnChartListener onChartListener;

    public void setOnChartListener(OnChartListener onChartListener) {
        this.onChartListener = onChartListener;
    }
    public BottomMDialogHlper(Context context) {
        this.context = context;
    }

    public void show() {
        if ( null == dialog) {
            createDialog();
        }
        refreshView();
        dialog.show();
    }

    private void refreshView() {
        erdgRightType.setSelectPosition(MSetting.getMSubType());
    }

    private void createDialog() {
        dialog = new Dialog(getActivity(context), R.style.Dialog);
        dialog.setContentView(R.layout.dialog_chart_mtype_setting);
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
                if( null != onChartListener){
                    onChartListener.onKTypeClick();
                }
            }
        });
    }

    private boolean saveSetting() {
        MSetting.setMSubType(erdgRightType.getSelectPosition());
        return true;
    }

    public void setLand(boolean isLand){
       this.isLand = isLand;
    }

    private Activity getActivity( Context context) {
        if(null == context){
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
