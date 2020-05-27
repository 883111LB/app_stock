package com.cvicse.stock.markets.data;

/**
 * Created by ding_syi on 17-1-22.
 */
public class IndustrySection {
    /** 行业名车*/
    private String industryName;
    /** 涨跌幅*/
    private String rangeRate;
    /** 行业名车*/
    private String leader;

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getRangeRate() {
        return rangeRate;
    }

    public void setRangeRate(String rangeRate) {
        this.rangeRate = rangeRate;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }
}
