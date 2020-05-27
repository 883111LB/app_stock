package com.cvicse.stock.portfolio.data;

import com.cvicse.stock.util.FormatUtils;
import com.stock.config.FillConfig;

/**
 * 指数轮转数据
 * Created by ding_syi on 17-1-5.
 */
public class StockBannerBean{
    private String name;
    private String lastPrice = FillConfig.DEFALUT;
    private String change = FillConfig.DEFALUT;
    private String changeRate = FillConfig.DEFALUT;
    private String id;

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    private boolean changed = false;
    private long dateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    private boolean isRise = false;

    public StockBannerBean(String name, String lastPrice, String change, String changeRate,String id,long dateTime) {
        this.name= name;
        if(FormatUtils.isStandard(lastPrice)){
            this.lastPrice = lastPrice;
        }
        if(FormatUtils.isStandard(change)){
            this.change = change;
        }
        this.id = id;
        this.dateTime = dateTime;
        if(FormatUtils.isStandard(change) && FormatUtils.isStandard(changeRate)){
            if(change.startsWith("+")){
                if(changeRate.startsWith("+")){
                    this.changeRate = changeRate+"%";
                } else {
                    this.changeRate = "+"+changeRate+"%";
                }
                isRise = true;
            } else {
                if(changeRate.startsWith("-")){
                    this.changeRate = changeRate+"%";
                } else {
                    this.changeRate = "-"+changeRate+"%";
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(String lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        if(FormatUtils.isStandard(change) && FormatUtils.isStandard(changeRate)){
            this.change = change;
            if(!changeRate.contains("-") && !changeRate.contains("+")){
                if(change.startsWith("+")){
                    this.changeRate = "+"+changeRate+"%";
                    isRise = true;
                } else {
                    this.changeRate = "-"+changeRate+"%";
                }
            }else{
                this.changeRate = changeRate+"%";
            }
        }
    }

    public String getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(String changeRate) {
        this.changeRate = changeRate;
    }

    public boolean isRise() {
        return isRise;
    }

    public void setRise(boolean rise) {
        isRise = rise;
    }
}
