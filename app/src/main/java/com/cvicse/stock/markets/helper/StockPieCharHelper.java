package com.cvicse.stock.markets.helper;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cvicse.stock.R;
import com.cvicse.stock.chart.view.PieView;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.markets.adapter.TurnoverLsvAdapter;
import com.cvicse.stock.markets.data.PieData;
import com.cvicse.stock.util.FormatUtils;
import com.mitake.core.AddValueModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/** 成交模块图表帮助类 成交统计--饼图
 * Created by tang_xqing on 2017/8/17.
 */

public class StockPieCharHelper {

    // 成交
    @BindView(R.id.frl_turnover)
    FrameLayout frlTurnover;
    @BindView(R.id.lst_turnover)
    ListView lstTurnover;

    private TurnoverLsvAdapter turnoverLsvAdapter;
    // 饼图数据
    private  ArrayList<PieData> pieDateList;
    private  ArrayList<AddValueModel> addValueModels;
    private AddValueModel addValueModel;

    // 颜色表 (注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {
            ColorUtils.STOCK_TOTAL_BUY_U, ColorUtils.STOCK_TOTAL_BUY_L,
            ColorUtils.STOCK_TOTAL_BUY_M, ColorUtils.STOCK_TOTAL_BUY_S,
            ColorUtils.STOCK_TOTAL_SELL_U, ColorUtils.STOCK_TOTAL_SELL_L,
            ColorUtils.STOCK_TOTAL_SELL_M,ColorUtils.STOCK_TOTAL_SELL_S };


    private Context mContext;

    public StockPieCharHelper( Context context,View view){
        ButterKnife.bind(this,view);
        this.mContext = context;

        turnoverLsvAdapter  = new TurnoverLsvAdapter(mContext);
        lstTurnover.setAdapter( turnoverLsvAdapter );
    }

    /**
     * 饼图数据
     * @return
     */
    public void setData(ArrayList<AddValueModel> addValueModels ){
        pieDateList = new ArrayList<>();

        addValueModel = addValueModels.get(0);
        String ultraLBuyV = addValueModel.getUltraLargeBuyVolume();  // 超大买入成交量
        String largeBuyV = addValueModel.getLargeBuyVolume();  // 大单买入成交量
        String mediumBuyV =  addValueModel.getMediumBuyVolume();//中单买入成交量
        String smallBuyV = addValueModel.getSmallBuyVolume();//小单买入成交量

        String ultraLSellV =  addValueModel.getUltraLargeSellVolume(); // 超大单卖出成交量
        String largeSellV = addValueModel.getLargeSellVolume();// 大单卖出成交量
        String mediumSellV = addValueModel.getMediumSellVolume();// 中单卖出成交量
        String smallSellV = addValueModel.getSmallSellVolume();// 小单卖出成交量

        // 判断超大买入成交量
        PieData pieDate = new PieData();
        pieDate.setValue( parseDouble(ultraLBuyV) );  // 设置数值
        pieDateList.add(pieDate);

        // 判断大单买入成交量
        pieDate = new PieData();
        pieDate.setValue( parseDouble(largeBuyV) );
        pieDateList.add(pieDate);

        // 判断中单买入成交量
        pieDate = new PieData();
        pieDate.setValue( parseDouble(mediumBuyV) );  // 设置数值
        pieDateList.add(pieDate);

        // 判断小单买入成交量
        pieDate = new PieData();
        pieDate.setValue( parseDouble(smallBuyV) );  // 设置数值
        pieDateList.add(pieDate);

        // 判断超大卖出成交量
        pieDate = new PieData();
        pieDate.setValue( parseDouble(ultraLSellV) );  // 设置数值
        pieDateList.add(pieDate);

        // 设置大单卖出成交量
        pieDate = new PieData();
        pieDate.setValue( parseDouble(largeSellV) );  // 设置数值
        pieDateList.add(pieDate);

        // 判断中单卖出成交量
        pieDate = new PieData();
        pieDate.setValue( parseDouble(mediumSellV) );  // 设置数值
        pieDateList.add(pieDate);

        // 设置小单卖出成交量
        pieDate = new PieData();
        pieDate.setValue( parseDouble(smallSellV) );
        pieDateList.add(pieDate);

        // 计算百分比，角度
        setAnglePercent();

        initView();
    }

    /**
     * 饼图
     */
    private void initView(){

        turnoverLsvAdapter.setData( pieDateList );

        // 添加饼形图
        PieView pieView = new PieView(mContext);
        pieView.setData(pieDateList);
        pieView.setStartAngle(270.0f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        pieView.setLayoutParams( layoutParams );
        frlTurnover.addView( pieView );
    }

    /**
     * 计算饼图百分比，角度
     */
    private  void setAnglePercent(){
        double sumValue = 0.0;
        // 计算总和
        for(int i=0;i<pieDateList.size();i++){
            sumValue = sumValue + pieDateList.get(i).getValue();
        }
        for(int i=0;i<pieDateList.size();i++){
            PieData pieData = pieDateList.get(i);
            if(sumValue >0){
                pieData.setPercent(formatDecimal(pieData.getValue()/sumValue));
            }

            pieData.setAngle((float) (pieData.getValue()/sumValue *360));
            pieData.setColor(mColors[i % mColors.length]);
        }
    }

    /**
     * 字符转换为数值
     * @param data
     * @return  double型数值
     */
    private  double parseDouble( String data ){
        if( !FormatUtils.isStandard(data) ){
            return 0;
        }
        return Double.parseDouble(data);
    }

    /**
     * 四舍五入保留两位
     * @param data
     * @return
     */
    private  double formatDecimal(double data) {
        java.math.BigDecimal bigDec = new java.math.BigDecimal(data);
        double total = bigDec.setScale(2, java.math.BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        return total;
    }

}
