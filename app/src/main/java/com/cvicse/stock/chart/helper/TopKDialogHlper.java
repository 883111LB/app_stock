package com.cvicse.stock.chart.helper;

import android.app.Dialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.base.utils.StringUtils;
import com.cvicse.base.utils.ToastUtils;
import com.cvicse.base.widget.ExtendedRadioGroup;
import com.cvicse.stock.R;
import com.cvicse.stock.chart.ui.StockKlineFragment;
import com.cvicse.stock.utils.TextUtils;
import com.cvicse.stock.view.ToggleButton;
import com.stock.config.KSetting;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 设置弹出框
 * 均线设置
 * Created by liu_zlu on 2017/3/21 12:18
 */
public class  TopKDialogHlper {

    @BindView(R.id.img_close) ImageView imgClose;

    @BindView(R.id.edt_day) EditText edtDay;
    @BindView(R.id.tbtn_day) ToggleButton tbtnDay;

    @BindView(R.id.edt_week) EditText edtWeek;
    @BindView(R.id.tbtn_week) ToggleButton tbtnWeek;

    @BindView(R.id.edt_month) EditText edtMonth;
    @BindView(R.id.tbtn_month) ToggleButton tbtnMonth;

    @BindView(R.id.content) LinearLayout content;

    @BindView(R.id.tev_sure) TextView tevSure;

    @BindView(R.id.erdg_right_type)
    ExtendedRadioGroup erdgRightType;

    private Dialog dialog ;
    StockKlineFragment stockFragment;

    private String type;

    public TopKDialogHlper(StockKlineFragment stockFragment,String type){
        this.stockFragment = stockFragment;
        this.type = type;
    }

    /**
     * 显示dialog
     */
    public void show(){
        if(dialog == null){
            createDialog();
        }
        refreshView();
        dialog.show();
    }

    private void createDialog() {
        dialog = new Dialog(stockFragment.getActivity(),R.style.Dialog);
        dialog.setContentView(R.layout.dialog_chart_setting);

        ButterKnife.bind(this,dialog);
        initViews();
    }

    private void refreshView() {
        TextUtils.setText(edtDay, KSetting.getAverage_1()+"");
        TextUtils.setText(edtWeek, KSetting.getAverage_2()+"");
        TextUtils.setText(edtMonth, KSetting.getAverage_3()+"");
        tbtnDay.setToggleOn(KSetting.getAverage_Visiable1());
        tbtnWeek.setToggleOn(KSetting.getAverage_Visiable2());
        tbtnMonth.setToggleOn(KSetting.getAverage_Visiable3());

        // new 都有显示前后复权
        erdgRightType.setSelectPosition(KSetting.getKRightSubType());
    }

    private void initViews() {
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tbtnDay.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                edtDay.setFocusable(on);
                edtDay.setFocusableInTouchMode(on);
            }
        });
        tbtnWeek.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                edtWeek.setFocusable(on);
                edtWeek.setFocusableInTouchMode(on);
            }
        });
        tbtnMonth.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                edtMonth.setFocusable(on);
                edtMonth.setFocusableInTouchMode(on);
            }
        });

        // 确定按钮的点击事件
        tevSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!saveSetting()) return;
                dialog.dismiss();
            }
        });
    }

    /**
     * 存储选择的设置
     * @return
     */
    private boolean saveSetting(){
        int num_1 = 0;
        int num_2 = 0;
        int num_3 = 0;
        String temp = "";

        if(StringUtils.isEmpty(temp = edtDay.getText().toString())){
            ToastUtils.showLongToast("均线值不能为空");
            return false;
        }
        num_1 = Integer.parseInt(temp);
        if(StringUtils.isEmpty(temp = edtWeek.getText().toString())){
            ToastUtils.showLongToast("均线值不能为空");
            return false;
        }
        num_2 = Integer.parseInt(temp);
        if(StringUtils.isEmpty(temp = edtMonth.getText().toString())){
            ToastUtils.showLongToast("均线值不能为空");
            return false;
        }
        num_3 = Integer.parseInt(temp);

        boolean right = false;
        //复权类型改变时,right为true
        if(erdgRightType.getSelectPosition() != KSetting.getKRightSubType()){
            right = true;
        }

        KSetting.setRightType(erdgRightType.getSelectPosition());

        KSetting.setAverage_1(num_1);
        KSetting.setAverage_2(num_2);
        KSetting.setAverage_3(num_3);

        KSetting.setAverageVisiable_1(edtDay.isFocusable());
        KSetting.setAverageVisiable_2(edtWeek.isFocusable());
        KSetting.setAverageVisiable_3(edtMonth.isFocusable());

        //更新操作
        stockFragment.updateSetting(right);
        return true;
    }
}
