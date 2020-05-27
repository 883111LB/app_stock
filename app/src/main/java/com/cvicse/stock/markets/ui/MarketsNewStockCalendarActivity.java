package com.cvicse.stock.markets.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.utils.StringUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.markets.data.DateUtil;
import com.cvicse.stock.markets.presenter.contract.NewStockCalendarContract;
import com.mitake.core.NewShareDates;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import cn.aigestudio.datepicker.bizs.decors.DPDecor;
import cn.aigestudio.datepicker.bizs.themes.DPCNTheme;
import cn.aigestudio.datepicker.bizs.themes.DPTManager;
import cn.aigestudio.datepicker.bizs.themes.WhiteDPTheme;
import cn.aigestudio.datepicker.views.DatePicker;

import static java.lang.Integer.parseInt;


/** 新股日历
 * Created by ruan_ytai on 17-2-22.
 */

public class MarketsNewStockCalendarActivity extends PBaseActivity implements NewStockCalendarContract.View{

    @MVPInject
    NewStockCalendarContract.Presenter presenter;

    @BindView(R.id.lel_main) LinearLayout lelMain;
    @BindView(R.id.pull_scl_calendar) ScrollView pullSclCalendar;

    @Override
    protected int getLayoutId() {
        return R.layout.calendar_selector;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        if(Setting.isNight()){
            DPTManager.getInstance().initCalendar(new DPCNTheme());
        } else {
            DPTManager.getInstance().initCalendar(new WhiteDPTheme());
        }
//        DaggerMarketsComponent.builder().marketsModule(new MarketsModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        presenter.requestNewCalendar();
       //presenter.requestHoliday();
    }

    /**
     * 请求新股日历列表成功
     * @param infos
     * @param holidayList 节假日列表
     */
    @Override
    public void onRequestNewCalendarSuccess(final ArrayList<NewShareDates> infos, ArrayList<String> holidayList) {

        /**
         * 取第一条和最后一条数据比
         * normalday的格式"2017-04-02"
         * holiday的格式"20161213"
         */
        if(infos != null && infos.size()>0 && holidayList!= null&& holidayList.size()>0){
            String startDay = infos.get(0).getNormalDay();
            String endDay = infos.get(infos.size() - 1).getNormalDay();



            //日历的个数
            int size = DateUtil.calculateSpan(startDay,endDay);
            HashMap<Integer,String> calculateDate = DateUtil.calculateDate(startDay,endDay);

            /**
             * 作为可否点击事件判断的依据
             */
            final HashMap<String,String> dates = new HashMap<>();
            int year = 0;
            int month = 0;
            int day = 0;
            String date = "";
            int sg = 0;
            int ss = 0;
            int zq = 0;

            //所有可点击的日期
            final ArrayList<String>  totalDate = new ArrayList<>();
            final ArrayList<String>  holidayListDate = new ArrayList<>();
            for(String dateStr:holidayList){
                holidayListDate.add(dateStr.substring(0,4)+"-"+Integer.parseInt(dateStr.substring(4,6))
                        +"-"+Integer.parseInt(dateStr.substring(6,8)));
            }
            /**
             * 获取日期底部的数据
             */
            for(NewShareDates newshareDate:infos){

                String sgStr = "";
                String ssStr = "";
                String zqStr = "";
                String bottomText = "";

                date = newshareDate.getNormalDay();
                year = parseInt(date.substring(0,4));
                month = parseInt(date.substring(5,7));
                day = parseInt(date.substring(8,10));

                totalDate.add(year+"-"+month+"-"+day);

                sg = parseInt(newshareDate.getSg());
                ss = parseInt(newshareDate.getSs());
                zq = parseInt(newshareDate.getZq());
                if(sg > 0){
                    sgStr = sg + "申购&";
                }else{
                    if(ss > 0){
                        ssStr = ss + "上市&";
                    }
                }
                if(ss > 0){
                    if(ssStr.equals("")) {
                        ssStr = ss + "上市";
                    }
                }

                if(zq > 0){
                    zqStr = zq + "中签";
                }

                if(!StringUtils.isEmpty(sgStr)){
                    bottomText = sgStr;
                }

                if(!StringUtils.isEmpty(ssStr)){
                    bottomText = bottomText + ssStr;
                }

                if(!StringUtils.isEmpty(zqStr)){
                    bottomText = bottomText + zqStr;
                }

                if(!StringUtils.isEmpty(bottomText)){
                    //字符串以 & 结尾时，绘制不出来文字
                    if(bottomText.endsWith("&")){
                        String str = bottomText.replace("&","");
                        dates.put(year+"-"+month+"-"+day,str);
                    }else{
                        dates.put(year+"-"+month+"-"+day,bottomText);
                    }
                }
            }

            /**
             * 创建datePicker
             */
            for(int i=0;i<size;i++){
                DatePicker datePicker = new DatePicker(this);

                String[] dateArr = calculateDate.get(i).split("-");

                //三种分离出的的数据(每个月节假日、交易日、交易日外的)
                ///HashMap<String,ArrayList<String>> allDate = DateUtil.queryMonthData(dateArr[0],dateArr[1],totalDateSource,holidayList);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                if(i == 1){
                    layoutParams.setMargins(0,dip2px(this,10),0,dip2px(this,10));
                }
                datePicker.setLayoutParams(layoutParams);
                datePicker.setBackgroundColor(Color.WHITE);
                lelMain.addView(datePicker);


                /**
                 * 必须调用，为DatePicter指定一个确切年月，
                 * 设置当前月历显示的年月
                 */
                datePicker.setDate(Integer.parseInt(dateArr[0]),Integer.parseInt(dateArr[1]));

                datePicker.setDPDecor(new DPDecor(){
                    @Override
                    public String getBottomText(String data) {
                        return dates.get(data);
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
                            if(totalDate.contains(date)){
                                CalendarDayActivity.startAction(MarketsNewStockCalendarActivity.this, date);
                            }
                        }
                });
            }
        }
    }


    /**
     * 请求新股日历失败
     */
    @Override
    public void onRequestNewCalendarFail() {

    }

   /* *//**
     * 请求节假日成功
     *//*
    @Override
    public void onRequestHolidaySuccess(ArrayList<String> info) {

    }

    *//**
     * 请求节假日失败
     *//*
    @Override
    public void onRequestHolidayFail() {

    }*/

    /**
     * 根据手机的分辨率从 dp 的单位转换为 px(像素)
     */
   private int dip2px(Context context,float dpValue){
       final float scale = context.getResources().getDisplayMetrics().density;
       return (int)(dpValue * scale + 0.5f);
   }
}
