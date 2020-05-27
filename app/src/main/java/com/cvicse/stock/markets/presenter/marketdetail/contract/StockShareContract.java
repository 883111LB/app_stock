package com.cvicse.stock.markets.presenter.marketdetail.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.marketdetail.StockSharePresenter;
import com.mitake.core.FundShareHolderInfo;
import com.mitake.core.ShareHolderHistoryInfo;
import com.mitake.core.StockShareChangeInfo;
import com.mitake.core.StockShareInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 股东页面
 * Created by liu_zlu on 2017/2/10 14:24
 */
public class StockShareContract {
    public interface View extends IView {
        /**
         * 查询股本结构成功
         */
        void onStockShareSuccess(StockShareInfo stockShareInfo);

        /**
         * 查询股本变动成功
         */
        void  onStockShareChangeSuccess(ArrayList<StockShareChangeInfo> stockShareChangeInfos);

        /**
         * 查询股东变动成功
         */
        void onShareHolderHistorySuccess(ArrayList<ShareHolderHistoryInfo> shareHolderHistoryInfos);

        /**
         * 查询最新十大流通股股东成功
         */
        void onTopLiquidShareHolderSuccess(List<HashMap<String, Object>> topLiquidShareHolders);

        /**
         * 查询最新十大股东成功
         */
        void onTopShareHolderSuccess(List<HashMap<String, Object>> topShareHolders);

        /**
         * 查询最新基金持股
         */
        void onFundShareHolderSuccess(FundShareHolderInfo fundShareHolderInfo);
    }

    @MVProvides(StockSharePresenter.class)
    public interface Presenter extends IPresenter<View> {
        /**
         * 初始化stockId
         * @param stockId
         */
        void init(String stockId,String type);
        /**
         * 查询股东相关信息
         */
        void queryStockShare();

    }
}
