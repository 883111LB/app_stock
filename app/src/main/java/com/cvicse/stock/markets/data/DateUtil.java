package com.cvicse.stock.markets.data;

import com.cvicse.stock.common.FillConfig;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by ruan_ytai on 17-2-23.
 */

public class DateUtil {

    public static final String MONTH_TRADEDATE = "traddate";
    public static final String MONTH_HOLIDAYDATE = "holidaydate";
    public static final String MONTH_OUTSIDEDATE = "outsidedate";

    /**
     * 获取当天日期,转换当天日期为字符串 2017-02-23的 形式
     */
    public static String CurrentDateConvert(){
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(new Date().getTime());
        String year = calendar.get(Calendar.YEAR) + "-";
        String month = "";
        String day = "";
        if(calendar.get(Calendar.MONTH) < 10){
            month = "0" + (calendar.get(Calendar.MONTH) + 1)+ "-";
        }else{
            month = (calendar.get(Calendar.MONTH) + 1)+ "-";
        }
        if(calendar.get(Calendar.DAY_OF_MONTH)< 10){
            day = "0"+ calendar.get(Calendar.DAY_OF_MONTH) + "";
        }else{
            day = calendar.get(Calendar.DAY_OF_MONTH) + "";
        }

        String date = year + month + day;
        return date;
    }

    /**
     * 日期转换,2017-2-9转换为2017-02-09
     */
    public static String DateConvert(String date){
       String[] dateArr = date.split("-");
        String year = "";
        String month = "";
        String day = "";
        if(dateArr.length == 3){
            year = dateArr[0] + "-";
            month = dateArr[1];
            day = dateArr[2];
            if(Integer.parseInt(month)< 10){
                month = "0" + month + "-";
            } else if (Integer.parseInt(month)>= 10) {
                month = month + "-";
            }
            if(Integer.parseInt(day) < 10){
                day = "0"+ day;
            }
        }
        return year + month + day;
    }

    /**
     * 画日历需要的年和月,返回(0,2017-01)或者（0,2017-1）
     */
    public static HashMap<Integer,String > calculateDate(String startDay, String endDay){

        HashMap<Integer,String> returnMap= new HashMap<>();
        int size = DateUtil.calculateSpan(startDay,endDay);
        int year1 = Integer.parseInt(startDay.substring(0,4));
        int year2 = Integer.parseInt(endDay.substring(0,4));
        int month1=Integer.parseInt(startDay.substring(5,7));
        if(year1 == year2){
            //同一年
          if(size == 2){
              returnMap.put(0,startDay.substring(0,7));
              returnMap.put(1,endDay.substring(0,7));
          }else if(size == 3){
              returnMap.put(0,startDay.substring(0,7));
              returnMap.put(1,year1 +"-" +(Integer.parseInt(startDay.substring(5,7))+1) +"");
              returnMap.put(2,endDay.substring(0,7));
          }
            return returnMap;
        }else{
            //跨年
            if(size == 2){
                returnMap.put(0,startDay.substring(0,7));
                returnMap.put(1,endDay.substring(0,7));
            }else if(size == 3){
                returnMap.put(0,startDay.substring(0,7));
                if(12 - month1 >=1){
                    returnMap.put(1,year1 +"-" +(Integer.parseInt(startDay.substring(5,7))+1) +"");
                }else{
                    returnMap.put(1,year2 +"-" +(Integer.parseInt(endDay.substring(5,7))-1) +"");
                }
                returnMap.put(2,endDay.substring(0,7));
            }
            return returnMap;
        }
    }

    /**
     * 计算横跨的月份 日期形式为2017-02-22
     */
    public static int calculateSpan(String startDay, String endDay){
        String[] month1 = startDay.split("-");
        String[] month2 = endDay.split("-");
        if(Integer.parseInt(month1[1]) <= Integer.parseInt(month2[1])){
            return Integer.parseInt(month2[1]) - Integer.parseInt(month1[1]) + 1;
        }else{
            return 12 - Integer.parseInt(month1[1]) + Integer.parseInt(month2[1]) +1;
        }
    }

    /**
     * 交割月 时间转换
     * @param date list里的数据是"1702"格式的，将它转化为2017年02月
     */
    public static String OptionExpireDateConvert(String date){
        //获取前2位
        String year = date.substring(0, 2);
        //获取后2位
        String month = date.substring(2,4);
        if (date.length() == 5) {
            return "20" + year + "年" + month + "月" + date.substring(4);
        }
        return "20" + year + "年" + month + "月";
    }


    /**
     * 期权计算 当前时间距离期权到期日的剩余的天数
     *
     * @param targetDay 当前选择月的期权到期日
     * expDate字段，形如"20170322"
     */
    public static String  getRemainDaysForOption(String targetDay){
        int year = Integer.parseInt(targetDay.substring(0,4));//取出年份
        int month = Integer.parseInt(targetDay.substring(4,6));//取出月份
        int day = Integer.parseInt(targetDay.substring(6,8));//取出到期日

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());//用当前时间初始化日历时间
        long currentTime = calendar.getTimeInMillis();
        calendar.set(year,month-1,day); // 月份要 -1，即 7 表示八月
        long targetTime = calendar.getTimeInMillis();
        if(currentTime >= targetTime){
            return "0";
        }
        String str = (targetTime - currentTime)/(1000*60*60*24) +"";
        if(str==""){
            return FillConfig.SIGNLE_LINE;
        }
        return str;
    }

    /**
     *  返回两个月之内的节假日和周末
     *  @param totalDate  两个月之内的时间数据
     *  @param holidayList  全部的节假日数据
     */
    public static ArrayList<String> queryValidHoliady(ArrayList<String> totalDate, ArrayList<String> holidayList){
        ArrayList<String> needHolidayList = new ArrayList<>();
        ArrayList<String> resultDay = new ArrayList<>();
        String year = "";
        String month = "";
        String day = "";
        for(String date:holidayList){
            year = date.substring(0,4);
            month = date.substring(4,6);
            day = date.substring(6,8);

            needHolidayList.add(year + "-" + month +"-"+day);
        }

        for(String  calenday:totalDate){
            for(String holiday : needHolidayList){
                if(calenday.equals(holiday)){
                    resultDay.add(holiday);
                }
            }
        }
        return resultDay;
    }

    /**
     * 计算某个月份中的交易日、节假日(包括周末和法定节假日)、两个月外的日期
     * 具体的某个月份
     *
     * @param currentYear 当前的年份
     * @param currentMonth  当前的月份
     * @param totalDate      服务器返回的所有新股日期(一般为当前的前30天，之后的后30天)
     * @param holidayList   所有假期
     */
    public static HashMap<String,ArrayList<String>> queryMonthData(String currentYear,String currentMonth,
                     ArrayList<String> totalDate,ArrayList<String> holidayList){

        HashMap<String,ArrayList<String>> dataMap = new HashMap<>();

        ArrayList<String> currentMonthList = getMonthDays(Integer.parseInt(currentYear),
                Integer.parseInt(currentMonth));//获取当前月份的所有日期

        ArrayList<String> tradlist = new ArrayList<>();        // 交易日日期
        ArrayList<String> holidaylist = new ArrayList<>();     // 节假日
        ArrayList<String> outsideList = new ArrayList<>();     // 两个月外的日期

        String startDay = totalDate.get(0);
        String endDay = totalDate.get(totalDate.size() - 1);

        //HashMap<Integer,String> needDateMap = calculateDate(startDay,endDay);//需要的年月
       /* String[] dateArr = null;
        for(int i=0;i<needDateMap.size();i++){
            dateArr= needDateMap.get(i).split("-");
        }*/

        int startDayYear = Integer.parseInt(startDay.substring(0,4));
        int startDayMonth = Integer.parseInt(startDay.substring(5,7));
        int startDayDay = Integer.parseInt(startDay.substring(8,10));

        int endDayYear = Integer.parseInt(endDay.substring(0,4));
        int endDayMonth = Integer.parseInt(endDay.substring(5,7));
        int endDayDay = Integer.parseInt(endDay.substring(8,10));


        int year = 0;
        int month = 0;
        int day = 0;

        for(String monthDay : currentMonthList){
            int cYear = Integer.parseInt(monthDay.substring(0,4));
            int cMonth = Integer.parseInt(monthDay.substring(4,6));
            int cDay = Integer.parseInt(monthDay.substring(6,8));

            /**
             * 开始的月份与当前要设置的而月份相同时
             */
            if(startDayMonth == Integer.parseInt(currentMonth)){
                if(cYear == Integer.parseInt(currentYear) && cMonth == Integer.parseInt(currentMonth)
                        && cDay<startDayDay){
                    outsideList.add(monthDay);
                }

                if(cYear ==Integer.parseInt(currentYear) && cMonth == Integer.parseInt(currentMonth)
                        && cDay>=startDayDay ){
                    tradlist.add(monthDay);
                }

                if(holidayList.contains(monthDay)){
                    holidaylist.add(monthDay);//节假日
                }
            }else if(endDayMonth == Integer.parseInt(currentMonth) ){
                /**
                 * 结束的月份与当前要设置的而月份相同时
                 */
                if(cYear == Integer.parseInt(currentYear) && cMonth == Integer.parseInt(currentMonth)
                        && cDay>endDayDay){
                    outsideList.add(monthDay);
                }

                if(cYear ==Integer.parseInt(currentYear) && cMonth == Integer.parseInt(currentMonth)
                        && cDay<=endDayDay){
                    tradlist.add(monthDay);
                }

                if(holidayList.contains(monthDay)){
                    holidaylist.add(monthDay);//节假日
                }
            }else{
                tradlist.add(monthDay);
                if(holidayList.contains(monthDay)){
                    holidaylist.add(monthDay);//节假日
                }
            }

        }
        dataMap.put(MONTH_TRADEDATE,tradlist);
        dataMap.put(MONTH_HOLIDAYDATE,holidaylist);
        dataMap.put(MONTH_OUTSIDEDATE,outsideList);

        return  dataMap;
    }

    /**
     * 获取指定月份的总天数，并拼接成需要的String
     */
    private static ArrayList<String> getMonthDays(int year,int month){

        ArrayList<String> resultStr = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        String monthStr = month<10 ? "0"+month: month+"";

        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for(int i=1;i<=maxDay;i++){
           String dayStr = i<10 ? "0"+i : i+"";
           String result = year + monthStr +dayStr;
            resultStr.add(result);
        }
        return resultStr;
    }

}
