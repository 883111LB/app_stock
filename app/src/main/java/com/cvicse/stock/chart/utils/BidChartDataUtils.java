package com.cvicse.stock.chart.utils;

import android.util.Log;

import com.github.mikephil.charting.data.Entry;
import com.mitake.core.response.chart.BidItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 集合竞价线图数据处理 工具类
 * Created by tang_hua on 2020/4/9.
 */

public class BidChartDataUtils {

    /**
     * 集合竞价时间-数据(副图用）
     */
    public static Map<String, float[]> getMap() {
        Map<String, float[]> map = new HashMap();
        // key：时间（9:15开始，每3秒1根）；value：{买1，买2, 颜色(int)}（默认0）
        String[] s = {"00", "03", "06", "09", "12", "15", "18", "21", "27", "30",
                "33", "36", "39", "42", "45", "48", "51", "54", "57"};
        for (int i = 15; i < 25; i ++) {
            for (int j = 0; j < s.length; j++) {
                map.put(i + s[j], new float[]{0, 0, 0});
            }
        }
        map.put("2500", new float[]{0, 0, 0});
        return map;
    }
    /**
     * 集合竞价时间-数据(折线图用）
     */
    public static Map<String, Integer> getMapNum() {
        Map<String, Integer> map = new HashMap();
        // key：时间（9:15开始，每3秒1根）；value：第几个数据
        String[] s = {"00", "03", "06", "09", "12", "15", "18", "21", "24", "27",
                "30", "33", "36", "39", "42", "45", "48", "51", "54", "57"};
        int num = 0;
        for (int i = 15; i < 25; i ++) {
            for (int j = 0; j < s.length; j++) {
                map.put(i + s[j], num);
                num++;
            }
        }
        map.put("2500", num);
        return map;
    }

    /**
     *
     */
    public static String[] getKeyStrings() {
        String[] keys = new String[202];
        // key：时间（9:15开始，每3秒1根）；value：{买1，买2, 颜色(int)}（默认0）
        String[] s = {"00", "03", "06", "09", "12", "15", "18", "21", "24", "27",
                "30", "33", "36", "39", "42", "45", "48", "51", "54", "57"};
        int num = 0;
        for (int i = 15; i < 25; i ++) {
            for (int j = 0; j < s.length; j++) {
                keys[num] = i + s[j];
                num++;
            }
        }
        keys[200] = "2500";
        return keys;
    }

    /**
     * 集合竞价时间-线(折线图用）
     */
    public static List<Entry> getLineList(float startValue, CopyOnWriteArrayList<BidItem> list) {
        List<Entry> lineEntries = new ArrayList<>();
        Map<String, Integer> map = BidChartDataUtils.getMapNum();
        // 取到最后一根线的下标数值
        String time1 = list.get(list.size() - 1).time;
        time1 = time1.substring(time1.length() - 4);
        int listMaxSize = map.get(time1) + 1;// 最后一根线是第几根
        // 默认所有数据为startValue
        for (int i = 0; i < listMaxSize; i++) {
            lineEntries.add(new Entry(i, startValue));
        }
        // 放入数据
        for (int i = 0; i < list.size(); i++) {
            BidItem bidItem = list.get(i);
            // 取时间后四位作为取map的可以
            String time = bidItem.time;
            time = time.substring(time.length() - 4);
            lineEntries.get(map.get(time)).setY(Float.valueOf(bidItem.closePrice));// 最新价
        }
        // 调整（如果为空，取前一根线的值）
        for (int i = 1; i < lineEntries.size(); i++) {
            if (lineEntries.get(i).getY() == startValue) {
                lineEntries.get(i).setY(lineEntries.get(i-1).getY());// 最新价
            }
        }
        return lineEntries;
    }
}
