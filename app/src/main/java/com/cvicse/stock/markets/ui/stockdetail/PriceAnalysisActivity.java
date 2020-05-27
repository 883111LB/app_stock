package com.cvicse.stock.markets.ui.stockdetail;

        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.view.Gravity;
        import android.widget.FrameLayout;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import com.cvicse.annotations.MVPInject;
        import com.cvicse.stock.R;
        import com.cvicse.stock.base.PBaseActivity;
        import com.cvicse.stock.base.ToolBar;
        import com.cvicse.stock.chart.view.HSBarChartView;
        import com.cvicse.stock.markets.data.HSBarChartData;
        import com.cvicse.stock.markets.presenter.marketdetail.contract.PriceAnalysisContract;
        import com.cvicse.stock.utils.TextUtils;
        import com.cvicse.stock.view.HSChartView;
        import com.mitake.core.bean.quote.MarketUpDownItem;

        import java.util.ArrayList;

        import butterknife.BindView;

        import static com.mitake.core.network.Network.context;


/**
 * 数据分析
 * Created by shi_yhui on 2018/11/16.
 */

public class PriceAnalysisActivity extends PBaseActivity implements PriceAnalysisContract.View{

        @MVPInject
        PriceAnalysisContract.Presenter paPresenter;

        @BindView(R.id.frl_hs_flow)
        FrameLayout frlCashFlow;
        @BindView(R.id.toolbar)
        ToolBar toolbar;
        @BindView(R.id.tev_datetime)
        TextView tevdatatime;
        @BindView(R.id.tev_datetime1)
        TextView tevdatatime1;
        @BindView(R.id.tev_todayupnum)
        TextView tevtodayupnum;
        @BindView(R.id.tev_todaydownnum)
        TextView tevtodaydownnum;
        @BindView(R.id.tev_todayupstop)
        TextView tevtodayupstop;
        @BindView(R.id.tev_todaydownstop)
        TextView tevtodaydownstop;
        @BindView(R.id.tev_changeupnum)
        TextView tevchangeupnum;
        @BindView(R.id.tev_changedownnum)
        TextView tevchangedownnum;
        @BindView(R.id.tev_changeupstop)
        TextView tevchangeupstop;
        @BindView(R.id.tev_changedownstop)
        TextView tevchangedownstop;


        @Override
        protected int getLayoutId() {
                return R.layout.activity_price_analysis;
        }

        @Override
        protected void initViews(Bundle savedInstanceState) {

        }

        @Override
        protected void initData() {
                paPresenter.requestMarketUpDown();
//                paPresenter.requestMarketUpDownTime();
        }

        public static void actionStart(Context context) {
                Intent intent = new Intent(context, PriceAnalysisActivity.class);
                context.startActivity(intent);
        }

        @Override
        public void onMarketUpDownSuccesss(MarketUpDownItem upDownItem) {
                ArrayList<HSBarChartData> hsbarChartViewArrayList = new ArrayList<>();
                if (upDownItem==null){
                        return;
                }
                TextUtils.setText(tevdatatime,upDownItem.tTime.substring(8,10)+":"+upDownItem.tTime.substring(10,12));
                TextUtils.setText(tevdatatime1,upDownItem.tTime.substring(8,10)+":"+upDownItem.tTime.substring(10,12));
                TextUtils.setText(tevtodayupnum,upDownItem.tUp);
                TextUtils.setText(tevtodaydownnum,upDownItem.tDown);
                TextUtils.setText(tevtodayupstop,upDownItem.tLimitUp);
                TextUtils.setText(tevtodaydownstop,upDownItem.tLimitDown);


                int todaydate1=Integer.parseInt(upDownItem.tUp);
                int todaydate2=Integer.parseInt(upDownItem.tDown);
                int todaydate3=Integer.parseInt(upDownItem.tLimitUp);
                int todaydate4=Integer.parseInt(upDownItem.tLimitDown);
                int ydate1=Integer.parseInt(upDownItem.yUp);
                int ydate2=Integer.parseInt(upDownItem.yDown);
                int ydate3=Integer.parseInt(upDownItem.yLimitUp);
                int ydate4=Integer.parseInt(upDownItem.yLimitDown);
//                System.out.println(upDownItem.list+"1111111");
                if (todaydate1>=ydate1){
                        TextUtils.setText(tevchangeupnum,Integer.toString(todaydate1-ydate1));
                        TextView textView = (TextView) findViewById(R.id.tev_changeuplessmore);
                        textView.setText("今日新增");
                }else {
                        TextUtils.setText(tevchangeupnum,Integer.toString(ydate1-todaydate1));
                        TextView textView = (TextView) findViewById(R.id.tev_changeuplessmore);
                        textView.setText("今日减少");
                }
                if (todaydate2>=ydate2){
                        TextUtils.setText(tevchangedownnum,Integer.toString(todaydate2-ydate2));
                        TextView textView = (TextView) findViewById(R.id.tev_changedownlessmore);
                        textView.setText("今日新增");
                }else {
                        TextUtils.setText(tevchangedownnum,Integer.toString(ydate2-todaydate2));
                        TextView textView = (TextView) findViewById(R.id.tev_changedownlessmore);
                        textView.setText("今日减少");
                }
                if (todaydate3>=ydate3){
                        TextUtils.setText(tevchangeupstop,Integer.toString(todaydate3-ydate3));
                        TextView textView = (TextView) findViewById(R.id.tev_stopuplessmore);
                        textView.setText("，较上一交易日增加");
                }else {
                        TextUtils.setText(tevchangeupstop,Integer.toString(ydate3-todaydate3));
                        TextView textView = (TextView) findViewById(R.id.tev_stopuplessmore);
                        textView.setText("，较上一交易日减少");
                }
                if (todaydate4>=ydate4){
                        TextUtils.setText(tevchangedownstop,Integer.toString(todaydate4-ydate4));
                        TextView textView = (TextView) findViewById(R.id.tev_stopdownlessmore);
                        textView.setText("，较上一交易日增加");
                }else {
                        TextUtils.setText(tevchangedownstop,Integer.toString(ydate4-todaydate4));
                        TextView textView = (TextView) findViewById(R.id.tev_stopdownlessmore);
                        textView.setText("，较上一交易日减少");
                }
                for (int i=0;i<upDownItem.list.size();i++){
                        int hsChartColor=0;
                        if (i>=0&&i<10){
                                hsChartColor=Color.GREEN;
                        }else if (i==10){
                                hsChartColor=Color.WHITE;
                        }else {
                                hsChartColor=Color.RED;
                        }
                        String number=String.valueOf(upDownItem.list.get(i));
                        if (i==0){
                                hsbarChartViewArrayList.add(new HSBarChartData("-10",number,hsChartColor));
                        }else if (i==1){
                                hsbarChartViewArrayList.add(new HSBarChartData("-9",number,hsChartColor));
                        }else if (i==2){
                                hsbarChartViewArrayList.add(new HSBarChartData("-8",number,hsChartColor));
                        }else if (i==3){
                                hsbarChartViewArrayList.add(new HSBarChartData("-7",number,hsChartColor));
                        }else if (i==4){
                                hsbarChartViewArrayList.add(new HSBarChartData("-6",number,hsChartColor));
                        }else if (i==5){
                                hsbarChartViewArrayList.add(new HSBarChartData("-5",number,hsChartColor));
                        }else if (i==6){
                                hsbarChartViewArrayList.add(new HSBarChartData("-4",number,hsChartColor));
                        }else if (i==7){
                                hsbarChartViewArrayList.add(new HSBarChartData("-3",number,hsChartColor));
                        }else if (i==8){
                                hsbarChartViewArrayList.add(new HSBarChartData("-2",number,hsChartColor));
                        }else if (i==9){
                                hsbarChartViewArrayList.add(new HSBarChartData("-1",number,hsChartColor));
                        }else if (i==10){
                                hsbarChartViewArrayList.add(new HSBarChartData("0",number,hsChartColor));
                        }else if (i==11){
                                hsbarChartViewArrayList.add(new HSBarChartData("1",number,hsChartColor));
                        }else if (i==12){
                                hsbarChartViewArrayList.add(new HSBarChartData("2",number,hsChartColor));
                        }else if (i==13){
                                hsbarChartViewArrayList.add(new HSBarChartData("3",number,hsChartColor));
                        }else if (i==14){
                                hsbarChartViewArrayList.add(new HSBarChartData("4",number,hsChartColor));
                        }else if (i==15){
                                hsbarChartViewArrayList.add(new HSBarChartData("5",number,hsChartColor));
                        }else if (i==16){
                                hsbarChartViewArrayList.add(new HSBarChartData("6",number,hsChartColor));
                        }else if (i==17){
                                hsbarChartViewArrayList.add(new HSBarChartData("7",number,hsChartColor));
                        }else if (i==18){
                                hsbarChartViewArrayList.add(new HSBarChartData("8",number,hsChartColor));
                        }else if (i==19){
                                hsbarChartViewArrayList.add(new HSBarChartData("9",number,hsChartColor));
                        }else if (i==20){
                                hsbarChartViewArrayList.add(new HSBarChartData("10",number,hsChartColor));
                        }
                        // 添加柱状图
                        HSBarChartView hsbarChartView = new HSBarChartView(context);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        layoutParams.gravity = Gravity.CENTER;
                        hsbarChartView.setData( hsbarChartViewArrayList );
                        hsbarChartView.setLayoutParams(layoutParams);
                        frlCashFlow.addView( hsbarChartView );
                }
        }

//        @Override
//        public void onMaeketUpDownTimeSuccess(ArrayList<QuoteItem> list) {
//                if (list==null){
//                        return;
//                }
//                for (int i=0;i<list.size();i++){
//                        TextUtils.setText(tevdatatime,list.get(i).datetime);
//                        TextUtils.setText(tevdatatime1,list.get(i).datetime);
//                }
//        }
}
