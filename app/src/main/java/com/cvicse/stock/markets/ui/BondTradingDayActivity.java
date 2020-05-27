package com.cvicse.stock.markets.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.utils.SizeUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.markets.data.DateUtil;
import com.cvicse.stock.markets.presenter.contract.BondTradingCalendarContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import cn.aigestudio.datepicker.bizs.decors.DPDecor;
import cn.aigestudio.datepicker.bizs.themes.DPCNTheme;
import cn.aigestudio.datepicker.bizs.themes.DPTManager;
import cn.aigestudio.datepicker.bizs.themes.WhiteDPTheme;
import cn.aigestudio.datepicker.views.DatePicker;

import static java.lang.Integer.parseInt;

/**
 * 新债日历
 */
public class BondTradingDayActivity extends PBaseActivity implements BondTradingCalendarContract.View {
    @MVPInject
    BondTradingCalendarContract.Presenter presenter;

    @BindView(R.id.topbar)
    ToolBar topbar;
    @BindView(R.id.lel_main)
    LinearLayout lelMain;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bond_trading_day;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        if(Setting.isNight()){
            DPTManager.getInstance().initCalendar(new DPCNTheme());
        }else{
            DPTManager.getInstance().initCalendar(new WhiteDPTheme());
        }
    }

    @Override
    protected void initData() {
        presenter.requestBondTradingDay();
    }

    /**
     * 新债日历
     * @param bndTradList
     */
    @Override
    public void onRequestBondTradingDaySuccess(List<HashMap<String, Object>> bndTradList, ArrayList<String> holidayList) {
        if( null == bndTradList || bndTradList.isEmpty() ){
            return;
        }

        String startDate = (String) bndTradList.get(0).get("NORMALDAY");
        String endDate = (String)bndTradList.get(bndTradList.size()-1).get("NORMALDAY");
        int size = DateUtil.calculateSpan(startDate, endDate);  // 计算共几个月份
        HashMap<Integer, String> dateTotal = DateUtil.calculateDate(startDate, endDate);  // 需要画的哪几个月份

        // 对节假日数据格式转换
        final ArrayList<String> holidayListDate = new ArrayList<>();
        for (int i = 0; i < holidayList.size(); i++) {
            String s = holidayList.get(i);

            holidayListDate.add(s.substring(0,4)+"-"+Integer.parseInt(s.substring(4,6))
                    +"-"+Integer.parseInt(s.substring(6,8)));
        }

        // 对每个日期点的数据进行统计
        final HashMap<String,String> dateText = new HashMap<>();  // 日期 + 文字
        final ArrayList<String>  totalDate = new ArrayList<>();  // 日期
        for (HashMap<String, Object> objectHashMap : bndTradList) {
            String normalday = (String) objectHashMap.get("NORMALDAY");

            int sg = Integer.parseInt((String) objectHashMap.get("sg"));
            int jjsg = Integer.parseInt((String) objectHashMap.get("jjsg"));
            int dss = Integer.parseInt((String) objectHashMap.get("dss"));

            String bottomText = sg > 0 ? (sg+"申购") : "";
            bottomText =  bottomText + ( jjsg > 0 ? (jjsg+"待申购&") : "");
            bottomText = bottomText +(dss > 0 ? (dss+"待上市") : "");

            int year = parseInt(normalday.substring(0,4));
            int month = parseInt(normalday.substring(5,7));
            int day = parseInt(normalday.substring(8,10));

            //字符串以 & 结尾时，绘制不出来文字
            if(bottomText.endsWith("&")){
                bottomText = bottomText.replace("&","");
            }

            totalDate.add( year+"-"+month+"-"+day);
            dateText.put( year+"-"+month+"-"+day,bottomText);
        }

        // 创建DatePicker
        for (int i = 0; i < size; i++) {
            DatePicker datePicker = new DatePicker(this);
            LinearLayout.LayoutParams  layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            if( i == 1 ){
                layoutParams.setMargins(0, SizeUtils.dp2px(this,10),0,SizeUtils.dp2px(this,10));
            }
            datePicker.setLayoutParams(layoutParams);
            datePicker.setBackgroundColor(Color.WHITE);
            lelMain.addView(datePicker);

            // 设置DatePicker属性. 必须为datePicker设置日期setDate
            String[] dateArr = dateTotal.get(i).split("-");
            datePicker.setDate(Integer.parseInt(dateArr[0]),Integer.parseInt(dateArr[1]));

            datePicker.setDPDecor(new DPDecor(){
                @Override
                public String getBottomText(String data) {
                    return dateText.get(data);
                }

                @Override
                public boolean isContains(String data) {
                    return totalDate.contains(data);
                }

                @Override
                public boolean isHoliday(String data) {
                    return holidayListDate.contains(data);
                }
            });

            datePicker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
                @Override
                public void onDatePicked(String date) {
                    if( totalDate.contains(date)){
                        // 跳转到当日新债列表界面
                        BondNewSharesCalActivity.startActivity(BondTradingDayActivity.this,date);
                    }
                }
            });
        }
    }

    @Override
    public void onRequestBondTradingDayFail() {
    }

    @Override
    public void onRequestBndNewSharesCalSuccess(HashMap<String, Object> bndNewSharesCal) {

    }

    @Override
    public void onRequestBndNewSharesCalFail() {

    }

    @Override
    public void onRequestBndShareDetailSuccess(HashMap<String, Object> bndShartDetail) {

    }

    @Override
    public void onRequestBndShareDetailFail() {

    }

}
