package com.cvicse.stock.markets.ui.stockdetail;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.utils.ToastUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.markets.adapter.FpzdTopAdapter;
import com.cvicse.stock.markets.presenter.fp.contract.FPContract;
import com.cvicse.stock.markets.ui.StockDetailActivity;
import com.cvicse.stock.markets.ui.stockdetail.zdfb.utils.FpBarChart;
import com.cvicse.stock.view.DivideViewPager;
import com.cvicse.stock.view.fupan.DayAxisValueFormatter;
import com.cvicse.stock.view.fupan.FpBarChartMarkerUtils;
import com.cvicse.stock.view.fupan.ValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.compound.CompoundUpDownBean;
import com.mitake.core.request.compound.CompoundUpDownRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;


/**
 * 数据分析-复盘涨跌
 * Created by shi_yhui on 2018/11/16.
 */

public class ReplayActivity extends PBaseActivity  implements View.OnClickListener, FPContract.View {

        @MVPInject
        FPContract.Presenter presenter1;

        @BindView(R.id.toolbar)
        ToolBar toolbar;
        @BindView(R.id.replaydate)
        TextView replaydate;// 日期

        @BindView(R.id.vip_sh) DivideViewPager mVipSh;// 指数数据
        private FpzdTopAdapter fpzdTopAdapter;
        // 涨跌分布上面的选择市场按钮
        @BindView(R.id.tev_marker1)
        TextView tev_marker1;// 全部
        @BindView(R.id.tev_marker2)
        TextView tev_marker2;// 沪市
        @BindView(R.id.tev_marker3)
        TextView tev_marker3;// 深市
        @BindView(R.id.tev_marker4)
        TextView tev_marker4;// 创业板
        @BindView(R.id.bar_zdfb)
        FpBarChart bar_zdfb;// 涨跌分布柱状图
        // 涨跌分布的图例
        @BindView(R.id.tev_legend_up)
        TextView tev_legend_up;// 上涨
        @BindView(R.id.tev_legend_flat)
        TextView tev_legend_flat;// 平盘
        @BindView(R.id.tev_legend_down)
        TextView tev_legend_down;// 下跌
        @BindView(R.id.tev_legend_rise)
        TextView tev_legend_rise;// 涨停
        @BindView(R.id.tev_legend_stop)
        TextView tev_legend_stop;// 停牌
        @BindView(R.id.tev_legend_fallstop)
        TextView tev_legend_fallstop;// 跌停
        // 涨跌分析 折线图
        @BindView(R.id.line_zdfb)
        LineChart line_zdfb;
        @BindView(R.id.tev_zdfx_time)
        TextView tev_zdfx_time;// 涨跌分析图例 左侧时间
        @BindView(R.id.tev_zdfx_down)
        TextView tev_zdfx_down;// 涨跌分析 跌家数
        @BindView(R.id.tev_zdfx_up)
        TextView tev_zdfx_up;// 涨跌分析 涨家数

        // 年月日
        int replayYear;
        int replayMonth;
        int replayDay;
        // 涨跌分布请求参数：市场类型
        String markerType = CompoundUpDownRequest.MarketType.ALL;
        // 涨跌分布请求参数：日期类型
        int dateType = CompoundUpDownRequest.DateType.TODAY;
        String dateTime;// 涨跌分布请求参数：请求日期
        Highlight highlight;// 上一次点击的折线图

        int times = 0;// 计数，行情请求20次后，调用一次涨跌分布接口

        @Override
        protected int getLayoutId() {
                return R.layout.activity_replay;
        }

        @Override
        protected void initViews(Bundle savedInstanceState) {
                replaydate.setOnClickListener(this);
                tev_marker1.setOnClickListener(this);
                tev_marker2.setOnClickListener(this);
                tev_marker3.setOnClickListener(this);
                tev_marker4.setOnClickListener(this);
        }

        @Override
        protected void initData() {
                Calendar calendar = Calendar.getInstance();//获取系统的日期
                // 年
                replayYear = calendar.get(Calendar.YEAR);
                //月
                replayMonth = calendar.get(Calendar.MONTH) + 1;
                //日
                replayDay = calendar.get(Calendar.DAY_OF_MONTH);
                String dateStr = addDataFormat(replayMonth, replayDay) + getWeek(replayYear, replayMonth, replayDay);
                replaydate.setText(dateStr);

                initZs();// 初始化指数
                initZdfb();// 涨跌分布
        }

        /**
         * 初始化指数数据
         */
        private void initZs() {
                presenter1.quoteRequest();
                fpzdTopAdapter = new FpzdTopAdapter(this);
                fpzdTopAdapter.setCount(6);
                mVipSh.setAdapter(fpzdTopAdapter);
                mVipSh.setOnItemClickListener(new DivideViewPager.OnItemClickListener() {
                        @Override
                        public void onItemClick(DivideViewPager parent, View view, int position) {
                                StockDetailActivity.startActivity(ReplayActivity.this, fpzdTopAdapter.getData(), position);
                        }
                });
        }

        /**
         * 涨跌分布
         */
        private void initZdfb() {
                initZdfbBarChart();// 初始化柱状图设置
                initZdfxLineChart();// 涨跌分析折线图设置
                // 默认进入页面请求全部
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                dateTime = sdf.format(new Date());
                presenter1.compoundUpDownRequest(markerType, dateTime, dateType);
        }

        public static void actionStart(Context context) {
                Intent intent = new Intent(context, ReplayActivity.class);
                context.startActivity(intent);
        }

        @Override
        public void onClick(View v) {
                switch (v.getId()) {
                        case R.id.replaydate:
                                /*日历控件*/
                                DatePickerDialog datedp = new DatePickerDialog(this, DatePickerDialog.THEME_DEVICE_DEFAULT_DARK, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker datePicker, int iyear, int monthOfYear, int dayOfMonth) {
                                                replayYear = iyear;/*年*/
                                                replayMonth = monthOfYear + 1;/*月-1*/
                                                replayDay = dayOfMonth;/*日**//*iyear:年，monthOfYear:月-1，dayOfMonth:日*/
                                                String startdateStr = addDataFormat(replayMonth, replayDay) + getWeek(replayYear, replayMonth, replayDay);
                                                replaydate.setText(startdateStr);
                                                Date date = new Date();
                                                if (date.getDate() == dayOfMonth && date.getMonth() == monthOfYear) {
                                                        // 如果选择的是今天，datetype日期类型改为当天
                                                        dateType = CompoundUpDownRequest.DateType.TODAY;
                                                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                                                        dateTime = sdf.format(new Date());
                                                        markerType = CompoundUpDownRequest.MarketType.ALL;
                                                        presenter1.compoundUpDownRequest(markerType, dateTime, dateType);
                                                } else {
                                                        markerType = CompoundUpDownRequest.MarketType.ALL;
                                                        dateType = CompoundUpDownRequest.DateType.LATEST_THIRTY_DAY;
                                                        dateTime = datatimeFormat(replayYear, replayMonth, replayDay);
                                                        presenter1.compoundUpDownRequest(markerType, dateTime, dateType);
                                                }
                                        }
                                }, replayYear, replayMonth, replayDay);
                                DatePicker picker = datedp.getDatePicker();
                                Date date = new Date();//当前时间
                                // 计算30天前的日期
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);
                                calendar.add(Calendar.DATE, -30);
                                Date minDate = calendar.getTime();

                                picker.setMaxDate(date.getTime());//设置最大日期
                                picker.setMinDate(minDate.getTime());//设置最小日期
                                datedp.show();
                                break;
                        case R.id.tev_marker1:
                                markerType = CompoundUpDownRequest.MarketType.ALL;
                                presenter1.compoundUpDownRequest(markerType, dateTime, dateType);
                                break;
                        case R.id.tev_marker2:
                                markerType = CompoundUpDownRequest.MarketType.SH;
                                presenter1.compoundUpDownRequest(markerType, dateTime, dateType);
                                break;
                        case R.id.tev_marker3:
                                markerType = CompoundUpDownRequest.MarketType.SZ;
                                presenter1.compoundUpDownRequest(markerType, dateTime, dateType);
                                break;
                        case R.id.tev_marker4:
                                markerType = CompoundUpDownRequest.MarketType.CY;
                                presenter1.compoundUpDownRequest(markerType, dateTime, dateType);
                                break;
                        default:
                                break;
                }
        }
        /**
         * 拼接日期
         * @param startMonth 月
         * @param dayOfMonth 日
         */
        private String addDataFormat(int startMonth, int dayOfMonth) {
                return startMonth + "月" + dayOfMonth + "日";
        }

        /**
         * 获取星期几
         */
        public static String getWeek(int iyear, int startMonth, int dayOfMonth) {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date;
                if (startMonth < 10) {
                        date = iyear + "-0" + startMonth;
                } else {
                        date = iyear + "-" + startMonth;
                }
                if (dayOfMonth < 10) {
                        date = date + "-0" + dayOfMonth;
                } else {
                        date = date + "-" + dayOfMonth;
                }
                Calendar cal = Calendar.getInstance();
                try {
                        cal.setTime(sdf.parse(date));
                        int i = cal.get(Calendar.DAY_OF_WEEK);
                        switch (i) {
                                case 1:
                                        return "星期日";
                                case 2:
                                        return "星期一";
                                case 3:
                                        return "星期二";
                                case 4:
                                        return "星期三";
                                case 5:
                                        return "星期四";
                                case 6:
                                        return "星期五";
                                case 7:
                                        return "星期六";
                                default:
                                        return "";
                        }
                } catch (ParseException e) {
                        e.printStackTrace();
                        return "";
                }

        }

        /**
         * 把日期变成yy-MM-dd的格式
         */
        private String datatimeFormat(int year, int month, int day) {
                String date;
                if (month < 10) {
                        date = year + "0" + month;
                } else {
                        date = year + "" + month;
                }
                if (day < 10) {
                        date = date + "0" + day;
                } else {
                        date = date + day;
                }
                return date;
        }
        /**
         * 把日期变成MM-dd的格式
         */
        private String datatimeFormat(String dateTime) {
                String date;
                date = dateTime.substring(4,6) + "-" + dateTime.substring(6,8) + " " + dateTime.substring(8,10) + ":" + dateTime.substring(10);
                return date;
        }

        /**
         * 涨跌分布 成功
         */
        @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
        @Override
        public void compoundUpDownRequestSuccess(final List<CompoundUpDownBean> list) {
                if (null == list) {
                        ToastUtils.showLongToast("暂无数据");
                        return;
                }
                // 取到最后一条的数据
                CompoundUpDownBean compoundUpDownBean = list.get(list.size() - 1);

                /**
                 * 柱状图 图例添加数据
                 */
                tev_legend_up.setText(Html.fromHtml("上涨<font color='#ff0000'>" + compoundUpDownBean.riseCount + "</font>家"));
                tev_legend_flat.setText(Html.fromHtml("平盘<font color='#888888'>" + compoundUpDownBean.flatCount + "</font>家"));
                tev_legend_down.setText(Html.fromHtml("下跌<font color='#008400'>" + compoundUpDownBean.fallCount + "</font>家"));
                tev_legend_rise.setText(Html.fromHtml("涨停<font color='#ff0000'>" + compoundUpDownBean.riseLimitCount + "</font>家"));
                tev_legend_stop.setText(Html.fromHtml("停牌<font color='#888888'>" + compoundUpDownBean.stopCount + "</font>家"));
                tev_legend_fallstop.setText(Html.fromHtml("跌停<font color='#008400'>" + compoundUpDownBean.fallLimitCount + "</font>家"));

                /**
                 * 柱状图数据
                 */
                ArrayList<BarEntry> entries = new ArrayList<>();
                entries.add(new BarEntry(0, Float.parseFloat(compoundUpDownBean.riseLimitCount)));// 涨停
                entries.add(new BarEntry(1, Float.parseFloat(compoundUpDownBean.riseFallRange[20])));
                entries.add(new BarEntry(2, Float.parseFloat(compoundUpDownBean.riseFallRange[19])));
                entries.add(new BarEntry(3, Float.parseFloat(compoundUpDownBean.riseFallRange[18])));
                entries.add(new BarEntry(4, Float.parseFloat(compoundUpDownBean.riseFallRange[17])));
                entries.add(new BarEntry(5, Float.parseFloat(compoundUpDownBean.riseFallRange[16])));
                entries.add(new BarEntry(6, Float.parseFloat(compoundUpDownBean.riseFallRange[15])));
                entries.add(new BarEntry(7, Float.parseFloat(compoundUpDownBean.riseFallRange[14])));
                entries.add(new BarEntry(8, Float.parseFloat(compoundUpDownBean.riseFallRange[13])));
                entries.add(new BarEntry(9, Float.parseFloat(compoundUpDownBean.riseFallRange[12])));
                entries.add(new BarEntry(10, Float.parseFloat(compoundUpDownBean.riseFallRange[11])));
                entries.add(new BarEntry(11, Float.parseFloat(compoundUpDownBean.riseFallRange[10])));
                entries.add(new BarEntry(12, Float.parseFloat(compoundUpDownBean.riseFallRange[9])));
                entries.add(new BarEntry(13, Float.parseFloat(compoundUpDownBean.riseFallRange[8])));
                entries.add(new BarEntry(14, Float.parseFloat(compoundUpDownBean.riseFallRange[7])));
                entries.add(new BarEntry(15, Float.parseFloat(compoundUpDownBean.riseFallRange[6])));
                entries.add(new BarEntry(16, Float.parseFloat(compoundUpDownBean.riseFallRange[5])));
                entries.add(new BarEntry(17, Float.parseFloat(compoundUpDownBean.riseFallRange[4])));
                entries.add(new BarEntry(18, Float.parseFloat(compoundUpDownBean.riseFallRange[3])));
                entries.add(new BarEntry(19, Float.parseFloat(compoundUpDownBean.riseFallRange[2])));
                entries.add(new BarEntry(20, Float.parseFloat(compoundUpDownBean.riseFallRange[1])));
                entries.add(new BarEntry(21, Float.parseFloat(compoundUpDownBean.riseFallRange[0])));
                entries.add(new BarEntry(22, Float.parseFloat(compoundUpDownBean.fallLimitCount)));// 跌停
                // 每一个BarDataSet代表一类柱状图
                BarDataSet barDataSet = new BarDataSet(entries, "");
                BarData data = new BarData(barDataSet);
                bar_zdfb.setData(data);
                barDataSet.setColors(getColors());// 柱子颜色
                barDataSet.setFormLineWidth(1f);
                barDataSet.setFormSize(10.f);
                //不显示柱状图顶部值
                barDataSet.setDrawValues(true);
                barDataSet.setValueTextColor(getResources().getColor(R.color.gray));
                bar_zdfb.invalidate();

                /**
                 * 折线图 图例
                 */
                tev_zdfx_time.setText(datatimeFormat(compoundUpDownBean.dateTime));
                tev_zdfx_down.setText("涨家数 " + compoundUpDownBean.fallCount + "家");
                tev_zdfx_up.setText("跌家数 " + compoundUpDownBean.riseCount + "家");

                /**
                 * 折线图数据
                 */
                List<Entry> lineEntries1 = new ArrayList<>();
                List<Entry> lineEntries2 = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                        CompoundUpDownBean upDownBean = list.get(i);
                        lineEntries1.add(new Entry(i, Integer.valueOf(upDownBean.riseCount)));// 上涨家数
                        lineEntries2.add(new Entry(i, Integer.valueOf(upDownBean.fallCount)));// 下跌家数
                }
                // 涨家数
                LineDataSet dataSet1 = new LineDataSet(lineEntries1, "涨家数");
                dataSet1.setColor(getResources().getColor(R.color.red));//线条颜色
                dataSet1.setDrawCircles(false);//不显示圆点
                dataSet1.setHighlightEnabled(true); // 是否允许高亮
                dataSet1.setDrawHighlightIndicators(true); // 设置是否有拖拽高亮指示器

                // 跌家数
                LineDataSet dataSet2 = new LineDataSet(lineEntries2, "跌家数");
                dataSet2.setColor(getResources().getColor(R.color.green_limit));//线条颜色
                dataSet2.setDrawCircles(false);//不显示圆点
                dataSet2.setDrawCircles(false);//不显示圆点
                dataSet2.setHighlightEnabled(true); // 是否允许高亮
                dataSet2.setDrawHighlightIndicators(true); // 设置是否有拖拽高亮指示器

                // 折线们
                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(dataSet1);
                dataSets.add(dataSet2);

                // 放入数据
                LineData lineData = new LineData(dataSets);
                lineData.setDrawValues(false);//是否绘制线条上的文字
                line_zdfb.setData(lineData);
                line_zdfb.invalidate();
                line_zdfb.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                                Highlight h = line_zdfb.getHighlightByTouchPoint(event.getX(), event.getY());
                                if (null != highlight && highlight.equalTo(h)) {
                                        return false;
                                }
                                highlight = h;
                                CompoundUpDownBean touchBean = list.get((int)h.getX());
                                tev_zdfx_time.setText(datatimeFormat(touchBean.dateTime));
                                tev_zdfx_down.setText("涨家数 " + touchBean.fallCount + "家");
                                tev_zdfx_up.setText("跌家数 " + touchBean.riseCount + "家");
                                return false;
                        }
                });
//                line_zdfb.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//                        @Override
//                        public void onValueSelected(Entry e, Highlight h) {
//                                if (null != h) {
//                                        CompoundUpDownBean touchBean = list.get((int)h.getX());
//                                        tev_zdfx_time.setText(datatimeFormat(touchBean.dateTime));
//                                        tev_zdfx_down.setText("涨家数 " + touchBean.fallCount + "家");
//                                        tev_zdfx_up.setText("跌家数 " + touchBean.riseCount + "家");
//                                }
//                        }
//
//                        @Override
//                        public void onNothingSelected() {
//
//                        }
//                });
        }

        /**
         * 涨跌分布请求失败
         */
        @Override
        public void compoundUpDownRequestFailure(String message) {
                ToastUtils.showLongToast(message);
        }

        /**
         * 指数行情请求 成功
         */
        @Override
        public void quoteRequestSuccess(ArrayList<QuoteItem> list) {
                fpzdTopAdapter.setData(list);
                times++;
                if (times > 60) {
                        times = 0;
                        presenter1.compoundUpDownRequest(markerType, dateTime, dateType);
                }
        }

        /**
         * 指数行情请求 失败
         */
        @Override
        public void quoteRequestFailire(String message) {

        }


        /**
         * 涨跌分布图表初始化设置
         */
        void initZdfbBarChart() {
//                final String[] labelY = {"涨停", "5-10", "2-5", "0-2", "0", "0-2", "2-5", "5-10", "跌停"};
                final String[] labelY = {"涨停", "9,+∞", "8,9", "7,8", "6,7", "5,6",
                        "4,5", "3,4", "2,3", "1,2", "0,1", "0", "-1,0", "-2,-1",
                        "-3,-2", "-4,-3", "-5,-4", "-6,-5", "-7,-6", "-8,-7", "-9,-8", "-∞,-9", "跌停"};
                //不显示图表网格;
                bar_zdfb.setDrawGridBackground(false);
                //背景阴影
                bar_zdfb.setDrawBarShadow(false);
                //显示边框
                bar_zdfb.setDrawBorders(false);
                // 允许触摸事件
                bar_zdfb.setTouchEnabled(true);
                // 不允许拖动
                bar_zdfb.setDragEnabled(false);
                // 不允许缩放
                bar_zdfb.setPinchZoom(false);// 挤压缩放
                bar_zdfb.setScaleXEnabled(false);// x轴
                bar_zdfb.setScaleYEnabled(false);// y轴
                bar_zdfb.setDoubleTapToZoomEnabled(false);// 双击缩放
                // 将Y数据显示在柱子的上方
                bar_zdfb.setDrawValueAboveBar(true);
                bar_zdfb.setmMinBottomOffset(30f);
                // 默认不展示描述
                Description description = new Description();
                description.setEnabled(false);
                bar_zdfb.setDescription(description);
                bar_zdfb.offsetTopAndBottom(20);

                //X轴设置显示位置在底部
                XAxis xAxis = bar_zdfb.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setAxisMinimum(-0.9f); // 最小值-0.5f，为了使左侧留出点空间
                xAxis.setAxisMaximum(23f);
                xAxis.setGranularity(1f);
                xAxis.setDrawLabels(true);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setLabelCount(23);
                xAxis.setLabelRotationAngle(-60);
                xAxis.setTextColor(getResources().getColor(R.color.gray));

                xAxis.setDrawGridLines(false); // 不绘制X轴网格线
                xAxis.setValueFormatter(new IAxisValueFormatter() {
                        // 自定义值格式
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                                if ((int)value == 23) {
                                        return "";
                                }
                                return labelY[(int) value] + "";
                        }
                });

                // 设置右侧y轴不显示
                bar_zdfb.getAxisRight().setEnabled(false);
                YAxis leftAxis = bar_zdfb.getAxisLeft();
                //保证Y轴从0开始，不然会上移一点
                leftAxis.setAxisMinimum(0);
                leftAxis.setLabelCount(5, true);
                leftAxis.setTextColor(getResources().getColor(R.color.gray));
                leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
                leftAxis.setDrawLabels(true);

                // 不绘制图例
                bar_zdfb.getLegend().setForm(Legend.LegendForm.NONE);

                // 显示marker
                bar_zdfb.setDrawMarkers(true);
                //设置点击chart图对应的数据弹出标注
                ValueFormatter xAxisFormatter = new DayAxisValueFormatter(bar_zdfb);
                FpBarChartMarkerUtils mv = new FpBarChartMarkerUtils(this, xAxisFormatter);
                mv.setChartView(bar_zdfb); // For bounds control
                bar_zdfb.setMarker(mv);
        }
        /**
         * 涨跌分析图表初始化设置
         */
        void initZdfxLineChart() {
                //不显示图表网格;
                line_zdfb.setDrawGridBackground(false);
                //显示边框
                line_zdfb.setDrawBorders(false);
                // 允许触摸事件
                line_zdfb.setTouchEnabled(true);
                line_zdfb.setSelected(true);
                line_zdfb.setEnabled(true);
                // 不允许拖动
                line_zdfb.setDragEnabled(false);
                // 不允许缩放
                line_zdfb.setPinchZoom(false);// 挤压缩放
                line_zdfb.setScaleXEnabled(false);// x轴
                line_zdfb.setScaleYEnabled(false);// y轴
                line_zdfb.setDoubleTapToZoomEnabled(false);// 双击缩放
                // 默认不展示描述
                Description description = new Description();
                description.setEnabled(false);
                line_zdfb.setDescription(description);
                // 设置外边距（边框到父控件的距离）
                line_zdfb.setmMinLeftOffset(10f);
                line_zdfb.setmMinRightOffset(20f);
                //X轴设置显示位置在底部
                line_zdfb.getXAxis().setEnabled(true);
                XAxis xAxis = line_zdfb.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
                xAxis.setAxisMinimum(-0.9f); // 最小值-0.5f，为了使左侧留出点空间
                xAxis.setGranularity(1f);// 间隔为1
//                xAxis.setLabelCount(241);
                xAxis.setLabelCount(241, true);
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
                                        case 60:
                                                return "10:30";
                                        case 150:
                                                return "11:30/13:00";
                                        case 180:
                                                return "14:00";
                                        case 240:
                                                return "15:00";
                                        default:
                                                return "";
                                }
                        }
                });

                // 设置右侧y轴不显示
                line_zdfb.getAxisRight().setEnabled(false);
                line_zdfb.getAxisLeft().setEnabled(true);
                YAxis leftAxis = line_zdfb.getAxisLeft();
                //保证Y轴从0开始，不然会上移一点
                leftAxis.setAxisMinimum(0f);
                leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
                leftAxis.setTextColor(getResources().getColor(R.color.gray));
                leftAxis.setValueFormatter(new IAxisValueFormatter() {
                        // 自定义值格式
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                                if ((int)value == 0) {
                                        return "";
                                }
                                return (int)value + "家";

                        }
                });

                // 不绘制图例
                line_zdfb.getLegend().setForm(Legend.LegendForm.NONE);
        }

        /**
         * 涨跌分布的颜色
         */
        private int[] getColors() {
                int[] colors = {
                        getResources().getColor(R.color.red),
                        getResources().getColor(R.color.red),
                        getResources().getColor(R.color.red),
                        getResources().getColor(R.color.red),
                        getResources().getColor(R.color.red),
                        getResources().getColor(R.color.red),
                        getResources().getColor(R.color.red),
                        getResources().getColor(R.color.red),
                        getResources().getColor(R.color.red),
                        getResources().getColor(R.color.red),
                        getResources().getColor(R.color.red),
                        getResources().getColor(R.color.gray),
                        getResources().getColor(R.color.green_limit),
                        getResources().getColor(R.color.green_limit),
                        getResources().getColor(R.color.green_limit),
                        getResources().getColor(R.color.green_limit),
                        getResources().getColor(R.color.green_limit),
                        getResources().getColor(R.color.green_limit),
                        getResources().getColor(R.color.green_limit),
                        getResources().getColor(R.color.green_limit),
                        getResources().getColor(R.color.green_limit),
                        getResources().getColor(R.color.green_limit),
                        getResources().getColor(R.color.green_limit),
                };
                return colors;
        }
}
