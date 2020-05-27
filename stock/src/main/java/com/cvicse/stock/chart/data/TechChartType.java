package com.cvicse.stock.chart.data;

/** K线图线图类型、指标类型
 * Created by tang_xqing on 2018/8/31.
 */

public class TechChartType {
    public enum  Type {
        DAY,
        WEEK,
        MONTH,
        YEAR,
        ONE,
        FIVE,
        FIFTH,
        THRITY,
        SIXTY,
        ONEHUNDREDTWENTY;
        public boolean isMinute(){
            return this == FIVE || this == FIFTH || this == THRITY || this == SIXTY || this == ONE || this == ONEHUNDREDTWENTY;
        }

        public boolean isDay(){
            return this == DAY;
        }
    }

    public enum KType {
        VOLUME,
        MACD,
        DMA,
        WR,
        BOLL,
        KDJ,
        RSI,
        SAR,
        VR,
        CR,
        DMI,
        BIAS,
        OBV,
        BBI,
        AMO,
        CCI,
        MTM,
        ROC,
        BRAR,
        NVIPVI,
        PSY;
        public String toString(){
            if(this == VOLUME){
                return "VOL";
            }
            if(this == MACD){
                return "MACD";
            }
            if(this == DMA){
                return "DMA";
            }
            if(this == WR){
                return "WR";
            }
            if(this == BOLL){
                return "BOLL";
            }
            if(this == KDJ){
                return "KDJ";
            }
            if(this == RSI){
                return "RSI";
            }
            if(this == VR){
                return "VR";
            }
            if(this == CR){
                return "CR";
            }
            if( this == DMI ){
                return "DMI";
            }
            if( this == BIAS ){
                return "BIAS";
            }
            if( this == OBV ){
                return "OBV";
            }
            if( this == BBI ){
                return "BBI";
            }
            if( this == AMO ){
                return "AMO";
            }
            if( this == CCI ){
                return "CCI";
            }
            if( this == MTM ){
                return "MTM";
            }
            if( this == ROC ){
                return "ROC";
            }
            if( this == BRAR ){
                return "BRAR";
            }
            if( this == NVIPVI ){
                return "NVI/PVI";
            }
            if( this == PSY ){
                return "PSY";
            }
            return "";
        }
        public int intValue(){
            if(this == VOLUME){
                return 0;
            }
            if(this == MACD){
                return 1;
            }
            if(this == KDJ){
                return 2;
            }
            if(this == DMA){
                return 3;
            }
            if(this == BOLL){
                return 4;
            }
            if(this == RSI){
                return 5;
            }
            if(this == WR){
                return 6;
            }
            if(this == VR){
                return 7;
            }
            if(this == CR){
                return 8;
            }
            if(this == DMI){
                return 9;
            }
            if(this == BIAS){
                return 10;
            }
            if(this == OBV){
                return 11;
            }
            if(this == BBI){
                return 12;
            }
            if(this == AMO){
                return 13;
            }
            if(this == CCI){
                return 14;
            }
            if(this == MTM){
                return 15;
            }
            if(this == ROC){
                return 16;
            }
            if(this == BRAR){
                return 17;
            }
            if(this == NVIPVI ){
                return 18;
            }
            if( this == PSY ){
                return 19;
            }
            return 0;
        }
    }
}
