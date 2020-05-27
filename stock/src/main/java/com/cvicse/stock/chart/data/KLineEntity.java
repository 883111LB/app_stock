package com.cvicse.stock.chart.data;

import com.mitake.core.parser.FQItem;

import java.util.List;

/**
 * k线图处理后数据
 * Created by liu_zlu on 2017/1/17 17:32
 */
public class KLineEntity  extends MakerEntity{
    // 是否是上升
    public boolean isUp;
    // 底部展示时间
    public String date;
    // 成交时间
    public String datetime;
    // 开盘价
    public float open;
    public String openS;

    // 最高级
    public float high;
    public String highS;

    // 最低价
    public float low;
    public String lowS;

    // 收盘价
    public float close;
    public String closeS;

    // 参考价
    public float reference_price;
    public String reference_priceS;

    //成交量
    public float volume;
    public String volumeStrK;
    public String volumeStr = "";

    public float tranPrice; // 成交额
    public String tranPriceS;

    public String change;
    public String changeRate;

    public List<FQItem> kSubEntityList;  // 复权信息

    public float MA1; // 用于顶部图均价
    public float MA2;
    public float MA3;

    public float MA10;  // 用于计算DMA指标
    public float MA20;  // 用于计算DMA指标
    public float MA50;  // 用于计算DMA指标

    public float dea;

    public float dif;

    public float macd;

    public float k;

    public float d;

    public float j;

    public float rsi1;

    public float rsi2;

    public float rsi3;

    public float up;

    public float mb;

    public float dn;

    public float obv;

    public float wr5;

    public float wr10;

    public float tr;

    public float plusDm;

    public float plusDm14;

    public float minusDm;

    public float minusDm14;

    public float tr14;

    public float pdi;
    public float mdi;
    public float adx;
    public float adxr;
    public float dx;

    public float sar;

    public float sign;

    public float mid; //最高价、最低价、以及收盘价的平均值

    public float cr = 0;
    public float cr10sum = 0;
    public float crpercent = 0;
    public float crHigh = 0;
    public float crLow = 0;
    public float crMa1 = 0;
    public float crMa2 = 0;
    public float crMa3 = 0;
    public float crMa4 = 0;

    public float vr13;
    public float vrup;
    public float vrdown;
    public float vr13denominator;

    public float vr13numerator = 0;
    public float vr26;
    public float dif10;
    public float difma50;

    public float amoM1;  // AMO 指标平均线
    public float amoM2;
    public float amoM3;

    public float mtm;  // MTM 指标
    public float mtmMA;

    public float bbi;  // BBI指标

    public float bias6;  // BIAS指标
    public float bias12;
    public float bias24;

    public float roc; // ROC指标
    public float rocMA;

    public boolean isUpPSY;  // PSY指标
    public float psy;
    public float psyMA;

    // AR指标
    public float arHO;
    public float arOL;
    public float ar;

    // BR指标
    public float brHPC;
    public float brPCL;
    public float br;

    public float nvi = 100;  //NVI指标
    public float pvi = 100;   // PVI指标

    public float tpyCCI;  // CCI指标
    public float maCCI;
    public float cci;

    public String iopv;// 基金净值

    public String getDatetime() {
        return date;
    }

    public float getOpenPrice() {
        return open;
    }

    public float getHighPrice() {
        return high;
    }

    public float getLowPrice() {
        return low;
    }

    public float getClosePrice() {
        return close;
    }

    public float getMA1() {
        return MA1;
    }

    public float getMA2() {
        return MA2;
    }

    public float getMA3() {
        return MA3;
    }

    public float getDea() {
        return dea;
    }

    public float getDif() {
        return dif;
    }

    public float getMacd() {
        return macd;
    }

    public float getK() {
        return k;
    }

    public float getD() {
        return d;
    }

    public float getJ() {
        return j;
    }

    public float getRsi1() {
        return rsi1;
    }

    public float getRsi2() {
        return rsi2;
    }

    public float getRsi3() {
        return rsi3;
    }

    public float getUp() {
        return up;
    }

    public float getMb() {
        return mb;
    }

    public float getDn() {
        return dn;
    }

    public String getIopv() {
        return iopv;
    }

    public void setIopv(String iopv) {
        this.iopv = iopv;
    }
}
