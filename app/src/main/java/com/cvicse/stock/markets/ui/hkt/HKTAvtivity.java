package com.cvicse.stock.markets.ui.hkt;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.utils.ToastUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.markets.ui.hkt.presenter.contract.HKTContract;
import com.cvicse.stock.markets.ui.hkt.utils.HKTLineChartMarkerUtils;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshScrollView;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.TextUtils;
import com.cvicse.stock.view.fupan.DayAxisValueFormatter;
import com.cvicse.stock.view.fupan.ValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.mitake.core.bean.HKTItem;
import com.mitake.core.request.chart.TongLineRequest;
import com.stock.config.FillConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 沪深港通页面
 * Created by tang_hua on 2020/5/13.
 */

public class HKTAvtivity extends PBaseActivity implements HKTContract.View, View.OnClickListener {

    @MVPInject
    HKTContract.Presenter presenter;

    @BindView(R.id.refresh)
    PullToRefreshScrollView refreshScrollView;
    @BindView(R.id.tev_ggt_h)
    TextView tev_ggt_h;// 港股通（沪）
    @BindView(R.id.tev_ggt_s)
    TextView tev_ggt_s;// 港股通（深）
    @BindView(R.id.tev_label_h3)
    TextView tev_label_h3;// 港股通（深）标签
    @BindView(R.id.tev_label_s3)
    TextView tev_label_s3;// 港股通（深）标签
    @BindView(R.id.lineChart)
    LineChart lineChart;// 折线图
    @BindView(R.id.tev_type_hkt)
    TextView tev_type_hkt;// 港股通额度
    @BindView(R.id.tev_type_hst)
    TextView tev_type_hst;// 沪深股通额度
    @BindView(R.id.tev_type_hkt_red)
    TextView tev_type_hkt_red;// 港股通额度(红色字体）
    @BindView(R.id.tev_type_hst_red)
    TextView tev_type_hst_red;// 沪深股通额度(红色字体）
    @BindView(R.id.tev_label)
    TextView tev_label;// 图表标题
    boolean typeFlag = true;// true:港股通额度; false:沪深股通额度

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hkt;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tev_type_hkt.setOnClickListener(this);
        tev_type_hst.setOnClickListener(this);
        initHKTLineChart();
        refreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (typeFlag) {
                    presenter.tongLineRequest(TongLineRequest.Type.TYPE_HKT);
                } else {
                    presenter.tongLineRequest(TongLineRequest.Type.TYPE_HST);
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

            }
        });
    }

    @Override
    protected void initData() {
        presenter.tongLineRequest(TongLineRequest.Type.TYPE_HKT);
    }

    /**
     * 沪深港通走势 成功
     */
    @Override
    public void tongLineRequestSuccess(final List<HKTItem> hktItems) {
        refreshScrollView.onRefreshComplete();
        if (null == hktItems || hktItems.size() == 0) {
            return;
        }
        // 上方展示的数据
        HKTItem item = hktItems.get(hktItems.size() - 1);
        TextUtils.setText(tev_ggt_h, getNumFlag(item.shInflowAmount), "--");
        tev_ggt_h.setTextColor(getAmountColor(item.shInflowAmount));
        tev_label_h3.setTextColor(getAmountColor(item.shInflowAmount));
        TextUtils.setText(tev_ggt_s, getNumFlag(item.szInflowAmount), "--");
        tev_ggt_s.setTextColor(getAmountColor(item.szInflowAmount));
        tev_label_s3.setTextColor(getAmountColor(item.szInflowAmount));

        if (hktItems.size() > 241) {
            lineChart.getXAxis().setLabelCount(331, true);
            lineChart.getXAxis().setAxisMaximum(331);
        }

        /**
         * 折线图数据
         */
        List<Entry> lineEntries1 = new ArrayList<>();
        List<Entry> lineEntries2 = new ArrayList<>();
        List<Entry> lineEntries3 = new ArrayList<>();
        for (int i = 0; i < hktItems.size(); i++) {
            HKTItem hktItem = hktItems.get(i);
            lineEntries1.add(new Entry(i, getNum(FormatUtils.formatValue(hktItem.shInflowAmount))));// 经沪市流向港股
            lineEntries2.add(new Entry(i, getNum(FormatUtils.formatValue(hktItem.szInflowAmount))));// 经深市流向港股
            lineEntries3.add(new Entry(i, getNum(FormatUtils.formatValue(hktItem.shSzInflowAmount))));// 合计流向
        }
        // 经沪市流向港股
        LineDataSet dataSet1 = new LineDataSet(lineEntries1, "经沪市流向港股");
        dataSet1.setColor(getResources().getColor(R.color.red));//线条颜色
        dataSet1.setDrawCircles(false);//不显示圆点
        dataSet1.setHighlightEnabled(true); // 是否允许高亮
        dataSet1.setDrawHighlightIndicators(true); // 设置是否有拖拽高亮指示器

        // 经深市流向港股
        LineDataSet dataSet2 = new LineDataSet(lineEntries2, "经深市流向港股");
        dataSet2.setColor(getResources().getColor(R.color.edit_blue_actionbar));//线条颜色
        dataSet2.setDrawCircles(false);//不显示圆点
        dataSet2.setHighlightEnabled(true); // 是否允许高亮
        dataSet2.setDrawHighlightIndicators(true); // 设置是否有拖拽高亮指示器

        // 合计流向
        LineDataSet dataSet3 = new LineDataSet(lineEntries3, "合计流向");
        dataSet3.setColor(getResources().getColor(R.color.listview_highlight));//线条颜色
        dataSet3.setDrawCircles(false);//不显示圆点
        dataSet3.setHighlightEnabled(true); // 是否允许高亮
        dataSet3.setDrawHighlightIndicators(true); // 设置是否有拖拽高亮指示器

        // 折线们
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet1);
        dataSets.add(dataSet2);
        dataSets.add(dataSet3);

        // 放入数据
        LineData lineData = new LineData(dataSets);
        lineData.setDrawValues(false);//是否绘制线条上的文字
        lineChart.setDrawMarkers(true);
        //设置点击chart图对应的数据弹出标注
        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(lineChart);
        HKTLineChartMarkerUtils mv = new HKTLineChartMarkerUtils(this, xAxisFormatter, hktItems);
        mv.setChartView(lineChart); // For bounds control
        lineChart.setMarker(mv);

        lineChart.setData(lineData);
        lineChart.invalidate();
        lineChart.getAxisLeft().setAxisMinimum(lineChart.getYMin() > 0 ? 0f : lineChart.getYMin());
    }

    /**
     * 沪深港通走势 失败
     */
    @Override
    public void tongLineRequestFailure(String message) {
        ToastUtils.showLongToast(message);
        refreshScrollView.onRefreshComplete();
    }

    /**
     * 图表初始化设置
     */
    void initHKTLineChart() {
        lineChart.setEnabled(true);
        //不显示图表网格;
        lineChart.setDrawGridBackground(false);
        //显示边框
        lineChart.setDrawBorders(false);
        // 允许触摸事件
        lineChart.setTouchEnabled(true);
        lineChart.setSelected(true);
        // 不允许拖动
        lineChart.setDragEnabled(true);
        // 不允许缩放
        lineChart.setPinchZoom(false);// 挤压缩放
        lineChart.setScaleXEnabled(false);// x轴
        lineChart.setScaleYEnabled(false);// y轴
        lineChart.setDoubleTapToZoomEnabled(false);// 双击缩放
        // 默认不展示描述
        Description description = new Description();
        description.setEnabled(false);
        lineChart.setDescription(description);
        // 设置外边距（边框到父控件的距离）
        lineChart.setmMinLeftOffset(10f);
        lineChart.setmMinRightOffset(20f);
        lineChart.setmMinBottomOffset(20f);
        lineChart.setmMinTopOffset(45f);
        //X轴设置显示位置在底部
        lineChart.getXAxis().setEnabled(true);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(-0.9f); // 最小值-0.5f，为了使左侧留出点空间
        xAxis.setGranularity(1f);// 间隔为1
//                xAxis.setLabelCount(241);
        xAxis.setLabelCount(241, true);
        xAxis.setAxisMaximum(241);
        xAxis.setAxisMinimum(0);
        xAxis.setDrawLabels(true);
        xAxis.setTextColor(getResources().getColor(R.color.gray));

        xAxis.setDrawGridLines(false); // 不绘制X轴网格线
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            // 自定义值格式
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                switch ((int)value) {
                    case 0:
                        return "9:30";
                    case 240:
                    case 241:
                    case 248:
                        return "15:00";
                    default:
                        return "";
                }
            }
        });

        // 设置右侧y轴不显示
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getAxisLeft().setEnabled(true);
        YAxis leftAxis = lineChart.getAxisLeft();
        //保证Y轴从0开始，不然会上移一点
//        leftAxis.setAxisMinimum(0f);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setTextColor(getResources().getColor(R.color.gray));
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            // 自定义值格式
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int)value + "";
            }
        });

        // 不绘制图例
        Legend legend = lineChart.getLegend();
        legend.setForm(Legend.LegendForm.DEFAULT);
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        legend.setTextColor(ColorUtils.DEFALUT());
    }

    @Override
    public void onClick(View v) {
        lineChart.getXAxis().setLabelCount(241, true);
        lineChart.getXAxis().setAxisMaximum(241);
        switch (v.getId()) {
            case R.id.tev_type_hkt:// 港股通额度
                if (!typeFlag) {
                    typeFlag = true;
                    // 修改类型颜色
                    tev_type_hkt_red.setVisibility(View.VISIBLE);
                    tev_type_hkt.setVisibility(View.GONE);
                    tev_type_hst_red.setVisibility(View.GONE);
                    tev_type_hst.setVisibility(View.VISIBLE);
                    // 修改图表x轴最大值
                    tev_label.setText("陆资流向港股趋势");
                    presenter.tongLineRequest(TongLineRequest.Type.TYPE_HKT);
                }
                break;
            case R.id.tev_type_hst:// 沪深股通额度
                if (typeFlag) {
                    typeFlag = false;
                    // 修改类型颜色
                    tev_type_hkt_red.setVisibility(View.GONE);
                    tev_type_hkt.setVisibility(View.VISIBLE);
                    tev_type_hst_red.setVisibility(View.VISIBLE);
                    tev_type_hst.setVisibility(View.GONE);

                    // 修改图表x轴最大值
                    tev_label.setText("港资流向趋势");
                    presenter.tongLineRequest(TongLineRequest.Type.TYPE_HST);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 数字格式转换
     * @return 浮点数
     */
    private float getNum(String value) {
        if (null == value || "".equals(value)) {
            return 0;
        }
        if (value.startsWith("+")) {
            return Float.valueOf(value.substring(1));
        }
        return Float.valueOf(value);
    }
    /**
     * 数字格式转换
     * @return 浮点数(带正号）
     */
    private String getNumFlag(String value) {
        value = FormatUtils.format(value);
        if (null == value || "".equals(value) || FillConfig.DEFALUT.equals(value)) {
            return FillConfig.DEFALUT;
        }
        if (value.startsWith("-") || "0".equals(value)) {
            return value;
        }
        return "+" + value;
    }

    /**
     * 根据内容返回颜色（+：红，-：绿）
     */
    private int getAmountColor(String amount) {
        if (null == amount || "".equals(amount)) {
            return ColorUtils.DEFALUT();
        }
        if (amount.startsWith("-")) {
            return ColorUtils.DOWN;
        } else if (amount.startsWith("+") || !"0".equals(amount)) {
            return ColorUtils.UP;
        }
        return ColorUtils.DEFALUT();
    }
}
