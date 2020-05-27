package com.cvicse.stock.markets.presenter.marketdetail.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.data.MarginTradingBo;
import com.cvicse.stock.markets.presenter.marketdetail.StockSummaryPresenter;
import com.mitake.core.BonusFinance;
import com.mitake.core.ForecastRating;
import com.mitake.core.Forecastyear;
import com.mitake.core.NewIndex;
import com.mitake.core.TradeDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 概要页面
 * Created by liu_zlu on 2017/2/9 16:38
 */
public class StockSummaryContract {
    public interface View extends IView {
        /**
         * 获取最新指标成功
         */
        void onNewIndexSuccess(NewIndex newIndex);

        /**
         * 获取大事提醒成功
         */
        void onImportantNoticeSuccess(List<HashMap<String,Object>> infoList);

        /**
         * 获取分红配送成功
         */
        void onBonusFinanceSuccess(ArrayList<BonusFinance> bonusFinances);
        /**
         * 获取融资证券成功
         */
        void onTradeDetailSuccess(TradeDetail tradeDetail);
        /**
         * 获取机构预测成功
         */
        void onForecastyearSuccess(Forecastyear forecastyear);
        /**
         * 获取机构评等成功
         */
        void onForecastRatingSuccess(ForecastRating forecastRating);

        // 获取大宗交易成功
        void onBlockTradeSuccess(List<HashMap<String ,Object>> infoList);
        //获取董秘问答成功
        void onIntearActiveSuccess(List<HashMap<String ,Object>> infoList);
        // 龙虎榜-买入前5营业部
        void onCharts5BuysSuccess(List<HashMap<String,Object>> buyList);

        // 龙虎榜-卖出前5营业部
        void onCharts5SellsSuccess(List<HashMap<String,Object>> sellsList);

        // 个股融资融券数据
        void onStockMarginTrade(List<MarginTradingBo> marginTradingBoList);
    }

    @MVProvides(StockSummaryPresenter.class)
    public interface Presenter extends IPresenter<View> {
        /**
         * 初始化stockId
         * @param stockId
         */
        void init(String stockId);
        /**
         * 查询概要信息
         */
        void queryMarketSummary();

        // 请求大事提醒
        void queryImportantNotice(String apiType);

        void loadImportantNotice(String apiType);

        // 请求大宗交易
        void requestBlockTrade();
        //请求董秘问答
        void requestIntearActive();
        // 龙虎榜
        void requestCharts5BuysSells();

        // 个股融资融券
        void requestStockMarginTrade();
    }
}
