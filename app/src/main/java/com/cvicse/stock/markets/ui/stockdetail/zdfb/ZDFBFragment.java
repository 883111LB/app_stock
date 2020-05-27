package com.cvicse.stock.markets.ui.stockdetail.zdfb;

import android.os.Bundle;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.github.mikephil.charting.charts.BarChart;

import butterknife.BindView;

/**
 * 涨跌分布图表
 * Created by tang_hua on 2020/2/20.
 */

public class ZDFBFragment extends PBaseFragment {

    @BindView(R.id.bar_increment)
    BarChart bar_increment;// 柱状图 贷款增量

    public static ZDFBFragment newInstance() {
        return new ZDFBFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zdfb;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        bar_increment.setSelected(true);
    }

    @Override
    protected void initData() {
//        // y 轴数据
//        ArrayList<BarEntry> yValues = new ArrayList<>();
//        // 2.0 ----x 轴数据
//        // ArrayList<String> xValues = new ArrayList<>();
//
//        for (int x = 0; x < 30; x++) {
//            // 2.0 ----xValues.add(String.valueOf(i));
//            float y = (float) (Math.random() * 30);
//            yValues.add(new BarEntry(x, y));
//        }
//
//        // y 轴数据集
//        BarDataSet barDataSet = new BarDataSet(yValues, "条形图");
//
//        // 2.0 ---- mBarData = new BarData(xValues, barDataSet);
//        BarData mBarData = new BarData(barDataSet);
//        bar_increment.setData(mBarData);


//        Set<String> balTypes = new HashSet<>();
//        List<String> balNames = new ArrayList<>();
//        for (int i = 0; i < 2; i++) {
//            String balType = i + "";
//            balTypes.add(balType);
//            String balName;
//            if ("1".equals(balType)) {
//                balName = "当前";
//            } else if("2".equals(balType)) {
//                balName = "年初";
//            }else{
//                balName = "去年同期";
//            }
//            balNames.add(balName);
//        }
//
//        // 以余额类型为键，将客户类型、余额总和存到increment中
//        Map<String, List<Map<String, String>>> increment = new HashMap<>();
//        for (String balType : balTypes) {
//            List<Map<String, String>> list = new ArrayList<>();
//            for (int i = 0; i < 2; i++) {
//                Map<String, String> map = new HashMap<>();
//                map.put("custType", i + "");
//                map.put("balSumStr", i + "");
//                list.add(map);
//            }
//            increment.put(balType, list);
//        }
//
//        // 将increment中的数据解析，作为柱状图bar_increment的数据来源
//        List<IBarDataSet> dataSets = new ArrayList<>();
//        List<Integer> colors = new ArrayList<>();
//        colors.add(getResources().getColor(R.color.green_dark));
//        colors.add(getResources().getColor(R.color.red));
//        int i = 0;
////        List<Set<String>> xSetList = new ArrayList<>();
//        Set<String> xSet = new HashSet<>();
//        for (Iterator it = balTypes.iterator(); it.hasNext(); i++) {// 获取每个余额类型
//            String barType = it.next().toString();
//            String barName = balNames.get(i);
//            List<BarEntry> d = new ArrayList<>();
//            DecimalFormat df = new DecimalFormat("###,###,##0.00");
//            BarDataSet data ;
//            for (int j = 0; j < increment.get(barType).size(); j++) {// 获取increment中对应余额类型的数据
//                BarEntry barEntry = new BarEntry(j, 100f);
//                d.add(barEntry);
//                xSet.add(increment.get(barType).get(j).get("custType"));
//                data = new BarDataSet(d, barName+" "+df.format(Float.valueOf(increment.get(barType).get(j).get("balSumStr")))+"万元");
//                data.setColor(colors.get(i));
//                data.setDrawValues(false);
//                dataSets.add(data);
//            }
////            xSetList.add(xSet);
//        }
//        BarData data1 = new BarData(dataSets);
//
//        //调整横纵坐标轴
//        bar_increment.getAxisRight().setEnabled(false);
//        XAxis xAxis = bar_increment.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴的位置
//        xAxis.setDrawGridLines(false);
//        xAxis.setGranularity(1f);//设置最小间隔，防止当放大时，出现重复标签。
//
//        // 横轴的类别
//        List<String> xValues = new ArrayList<>();
//        for (Iterator it = xSet.iterator(); it.hasNext(); i++) {
//            String xValue = it.next().toString();
//            xValues.add(xValue);
//        }
//
//        MyXFormatter myXFormatter = new MyXFormatter(xValues);
//        xAxis.setValueFormatter(myXFormatter);
//
//        YAxis yAxis = bar_increment.getAxisLeft();
//        yAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                DecimalFormat df = new DecimalFormat("###,###,##0.00");
//                return "" + df.format(value) + "万元";
//            }
//        });
//
//        // 柱状图由叠放改为并列
//        int barAmount = xValues.size();
//        float barWidth = 0.2f;//设置柱状图宽度
//        float groupSpace;//柱状图组之间的间距
//        if (barAmount > 1) {
//            groupSpace = 1 - barWidth * (barAmount - 1);
//        }else {
//            groupSpace = 0.5f;
//        }
//        float barSpace = 0f;
//        data1.setBarWidth(barWidth);
////        if (result.size() != 1) {
//            data1.groupBars(groupSpace - 1, groupSpace, barSpace);
////        }
//
//        bar_increment.setData(data1);
//        bar_increment.setPinchZoom(true);
//
//        // 默认不展示描述
//        Description description = new Description();
//        description.setEnabled(false);
//        bar_increment.setDescription(description);
//
        bar_increment.invalidate();
    }
}
