package com.cvicse.stock.markets.data;

/**  饼图数据
 * Created by tang_xqing on 2017/8/17.
 */

public class PieData {
    /**
     * 用户关心的数据
     */
    private String name;//名字
    private double value;//数值
    private double percent;//百分比

    /**
     * 非用户关心的数据
     */
    private int color = 0;//颜色
    private float angle = 0;//角度

    public PieData(){}

    public PieData(String name,float value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
