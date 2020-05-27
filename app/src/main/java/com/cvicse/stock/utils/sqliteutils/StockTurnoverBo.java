package com.cvicse.stock.utils.sqliteutils;

/**
 * 所属板块 保存本地数据实体类
 * Created by tang_hua on 2020/3/4.
 */

public class StockTurnoverBo {

    String codeFlag;// 股票代码
    String code;// 股票所属板块代码

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeFlag() {
        return codeFlag;
    }

    public void setCodeFlag(String codeFlag) {
        this.codeFlag = codeFlag;
    }

    public StockTurnoverBo() {
    }

    public StockTurnoverBo(String codeFlag, String code) {
        this.codeFlag = codeFlag;
        this.code = code;
    }
}
