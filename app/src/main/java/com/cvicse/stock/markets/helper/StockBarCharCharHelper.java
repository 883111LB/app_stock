package com.cvicse.stock.markets.helper;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.chart.view.BarChartView;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.markets.data.BarChartData;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.AddValueModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/** 成交模块图表帮助类 资金流向--柱状图
 * Created by tang_xqing on 2017/8/17.
 */

public class StockBarCharCharHelper {
    // 资金流向
    @BindView(R.id.frl_cash_flow)
    FrameLayout frlCashFlow;
    // 主力流入
    @BindView(R.id.tev_funds_in)
    TextView tevFundsIn;
    // 散户流入
    @BindView(R.id.tev_other_in)
    TextView tevOtherIn;
    // 主力流出
    @BindView(R.id.tev_funds_out)
    TextView tevFundsOut;
    // 散户流出
    @BindView(R.id.tev_other_out)
    TextView tevOtherOut;
    // 主力净流入
    @BindView(R.id.tev_funds_netinflow)
    TextView tevFundsNetInflow;
    // 散户净流入
    @BindView(R.id.tev_other_netinflow)
    TextView tevOtherNetInflow;

    private AddValueModel addValueModel;

    private Context context;

    public StockBarCharCharHelper(Context context,View view){
        ButterKnife.bind(this,view);
        this.context = context;
    }

    /**
     * 设置柱状图数据
     */
    private void setView(){
        ArrayList<BarChartData> barChartViewArrayList = new ArrayList<>();
        // 主力流入
        if(!isArrayEmpty(addValueModel.getFundsInflows())){
            TextUtils.setText(tevFundsIn, FormatUtils.format(addValueModel.getFundsInflows()[0]),FillConfig.DEFALUT);
        }
        // 散户流入
        if(!isArrayEmpty(addValueModel.getOthersFundsInflows()))
            TextUtils.setText(tevOtherIn, FormatUtils.format(addValueModel.getOthersFundsInflows()[0]),FillConfig.DEFALUT);
        // 主力流出
        if(!isArrayEmpty(addValueModel.getFundsOutflows()))
           TextUtils.setText(tevFundsOut, FormatUtils.format(addValueModel.getFundsOutflows()[0]),FillConfig.DEFALUT);
        // 散户流出
        if(!isArrayEmpty(addValueModel.getOthersFundsOutflows()))
            TextUtils.setText(tevOtherOut, FormatUtils.format(addValueModel.getOthersFundsOutflows()[0]),FillConfig.DEFALUT);

        String ultraLargeNetInflow = addValueModel.getUltraLargeNetInflow();
        String largeNetInflow = addValueModel.getLargeNetInflow();
        int ultraColor = 0;
        if(!android.text.TextUtils.isEmpty(ultraLargeNetInflow) && ultraLargeNetInflow.contains(FillConfig.SIGNLE_LINE)){
            ultraColor = Color.GREEN;
        }else{
            ultraColor = Color.RED;
        }
        int largeColor = 0;
        if(!android.text.TextUtils.isEmpty(largeNetInflow) && largeNetInflow.contains(FillConfig.SIGNLE_LINE)){
            largeColor = Color.GREEN;
        }else{
            largeColor = Color.RED;
        }
        barChartViewArrayList.add( new BarChartData("超大单",checkData(ultraLargeNetInflow),ultraColor));
        barChartViewArrayList.add( new BarChartData("大单",checkData(largeNetInflow), largeColor));
        // 主力净流入=大单净流入+超大单净流入
        String fundsNetInflow = parseDouble( ultraLargeNetInflow ) + parseDouble( largeNetInflow )+"";
        TextUtils.setText(tevFundsNetInflow, FormatUtils.format(fundsNetInflow));
        if(!android.text.TextUtils.isEmpty(fundsNetInflow) && fundsNetInflow.startsWith(FillConfig.SIGNLE_LINE)){
            tevFundsNetInflow.setTextColor(ColorUtils.DOWN);
        }else{
            tevFundsNetInflow.setTextColor(ColorUtils.UP);
        }

        String mediumNetInflow = addValueModel.getMediumNetInflow();
        String smallNetInflow = addValueModel.getSmallNetInflow();
        int mediumColor = 0;
        if( !android.text.TextUtils.isEmpty(mediumNetInflow) && mediumNetInflow.contains(FillConfig.SIGNLE_LINE)){
            mediumColor = Color.GREEN;
        }else{
            mediumColor = Color.RED;
        }
        int smallColor = 0;
        if(  !android.text.TextUtils.isEmpty(smallNetInflow) && smallNetInflow.contains(FillConfig.SIGNLE_LINE)){
            smallColor = Color.GREEN;
        }else{
            smallColor = Color.RED;
        }
        barChartViewArrayList.add( new BarChartData("中单",checkData(mediumNetInflow), mediumColor));
        barChartViewArrayList.add( new BarChartData("小单",checkData(smallNetInflow), smallColor));
        // 散户净流入=中单净流入+小单净流入
        String othersNetInflow = parseDouble( mediumNetInflow ) + parseDouble( smallNetInflow )+"";
        TextUtils.setText(tevOtherNetInflow, FormatUtils.format(othersNetInflow));
        if(  !android.text.TextUtils.isEmpty(othersNetInflow) &&  othersNetInflow.startsWith(FillConfig.SIGNLE_LINE)){
            tevOtherNetInflow.setTextColor(ColorUtils.DOWN);
        }else{
            tevOtherNetInflow.setTextColor(ColorUtils.UP);
        }

        // 添加柱状图
        BarChartView barChartView = new BarChartView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        barChartView.setData( barChartViewArrayList );
        barChartView.setLayoutParams(layoutParams);
        frlCashFlow.addView( barChartView );
    }

    /**
     * 计算资金流向数据 柱状图数据
     * 主力净流入=大单净流入+超大单净流入
     * 散户净流入=中单净流入+小单净流入
     * @return
     */
    public  void setData(ArrayList<AddValueModel> addValueModels){
        this.addValueModel = addValueModels.get(0);

        setView();
    }


    private double parseDouble( String ultraLargeNetInflow ){
        if(FormatUtils.isStandard(ultraLargeNetInflow)){
            return Double.parseDouble( ultraLargeNetInflow );
        }
        return 0;
    }

    private String checkData(String data){
        if(android.text.TextUtils.isEmpty(data)){
            return FillConfig.DEFALUT;
        }

        if (data.startsWith(FillConfig.SIGNLE_LINE)){
            data = data.substring(1);
        }
        return data;
    }

    private boolean isArrayEmpty(Object[] objects){
        if(null == objects || objects.length <= 0){
            return true;
        }
        return false;
    }
}
