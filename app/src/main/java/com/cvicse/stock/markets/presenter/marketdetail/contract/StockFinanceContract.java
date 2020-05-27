package com.cvicse.stock.markets.presenter.marketdetail.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.marketdetail.StockFinancePresenter;
import com.mitake.core.MainFinaDataNas;
import com.mitake.core.MainFinaIndexHas;
import com.mitake.core.QuoteItem;

import java.util.HashMap;
import java.util.List;

/**
 * 财务页面
 * Created by liu_zlu on 2017/2/10 13:02
 */
public class StockFinanceContract {
    public interface View extends IView {
        /**
         * 查询财务指标成功
         */
        void onMainFinaIndexHasSuccess(MainFinaIndexHas mainFinaIndexHas);

        /**
         * 查询财务报表成功
         */
        void  onMainFinaDataNasSuccess(List<MainFinaDataNas> mainFinaDataNasList);

        // 主要指标
        void onProfinmainindexSuccess(HashMap<String,Object> infoList);
        // 利润表
        void onProincstatementnewSuccess(HashMap<String,Object> infoList);
        // 资产负债表
        void onProbalsheetnewSuccess(HashMap<String,Object> infoList);

        // 现金流量表
        void onProcfstatementnewSuccess(HashMap<String,Object> infoList);

    }
    @MVProvides(StockFinancePresenter.class)
    public interface Presenter extends IPresenter<View> {
        /**
         * 初始化stockId
         * @param quoteItem
         */
        void init(QuoteItem quoteItem);
        /**
         * 查询财务相关信息
         */
        void queryFinance();
    }
}
