package com.cvicse.stock.markets.dagger;

import com.cvicse.base.dagger.BaseModule;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.CalendarDayPresenter;
import com.cvicse.stock.markets.presenter.HKPresenter;
import com.cvicse.stock.markets.presenter.HKTMorePresenter;
import com.cvicse.stock.markets.presenter.HKTPresenter;
import com.cvicse.stock.markets.presenter.MarketDetailPresenter;
import com.cvicse.stock.markets.presenter.NewStockCalendarPresenter;
import com.cvicse.stock.markets.presenter.NewStockDetailPresenter;
import com.cvicse.stock.markets.presenter.OptionListPresenter;
import com.cvicse.stock.markets.presenter.RankingListPresenter;
import com.cvicse.stock.markets.presenter.SHQQAllPresenter;
import com.cvicse.stock.markets.presenter.SHQQCallPresenter;
import com.cvicse.stock.markets.presenter.SHQQPutPresenter;
import com.cvicse.stock.markets.presenter.SHQQTPresenter;
import com.cvicse.stock.markets.presenter.SHSZPresenter;
import com.cvicse.stock.markets.presenter.SHSZTopPresenter;
import com.cvicse.stock.markets.presenter.SectionDetailPresenter;
import com.cvicse.stock.markets.presenter.SectionMorePresenter;
import com.cvicse.stock.markets.presenter.SectionPresenter;
import com.cvicse.stock.markets.presenter.contract.CalendarDayContract;
import com.cvicse.stock.markets.presenter.contract.HKContract;
import com.cvicse.stock.markets.presenter.contract.HKTContract;
import com.cvicse.stock.markets.presenter.contract.HKTMoreConstract;
import com.cvicse.stock.markets.presenter.contract.MarketDetailContract;
import com.cvicse.stock.markets.presenter.contract.NewStockCalendarContract;
import com.cvicse.stock.markets.presenter.contract.NewStockDetailContract;
import com.cvicse.stock.markets.presenter.contract.OptionListContract;
import com.cvicse.stock.markets.presenter.contract.RankingListContract;
import com.cvicse.stock.markets.presenter.contract.SHQQAllContract;
import com.cvicse.stock.markets.presenter.contract.SHQQCallConstract;
import com.cvicse.stock.markets.presenter.contract.SHQQPutConstract;
import com.cvicse.stock.markets.presenter.contract.SHQQTContract;
import com.cvicse.stock.markets.presenter.contract.SHSZContract;
import com.cvicse.stock.markets.presenter.contract.SHSZTopContract;
import com.cvicse.stock.markets.presenter.contract.SectionContract;
import com.cvicse.stock.markets.presenter.contract.SectionDetailContract;
import com.cvicse.stock.markets.presenter.contract.SectionMoreConstract;
import com.cvicse.stock.markets.presenter.marketdetail.FundSummaryPresenter;
import com.cvicse.stock.markets.presenter.marketdetail.contract.FundSummaryContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ruan_ytai on 17-1-16.
 */
@Module
public class MarketsModule extends BaseModule {

    private IView baseView;

    public MarketsModule(IView view){
        baseView = view;
    }

    /**
     * 沪深Fragment
     */
    @Provides
    public SHSZContract.Presenter getSHSZPresenter(){
        return getPersenter(baseView, SHSZPresenter.class);
    }

    /**
     * 沪深TopFragment
     */
    @Provides
    public SHSZTopContract.Presenter getSHSZToPresenter() {
        return getPersenter(baseView, SHSZTopPresenter.class);
    }

    /**
     * 新股日历activity
     */
    @Provides
    public NewStockCalendarContract.Presenter getCalendarPresenter() {
        return getPersenter(baseView, NewStockCalendarPresenter.class);
    }

    /**
     * 每日新股日历activity
     */
    @Provides
    public CalendarDayContract.Presenter getCalendarDayPresenter() {
        return getPersenter(baseView, CalendarDayPresenter.class);
    }

    /**
     * 每日新股日历activity
     */
    @Provides
    public NewStockDetailContract.Presenter getNewStockDetailPresenter() {
        return getPersenter(baseView, NewStockDetailPresenter.class);
    }

    /**
     * 沪深排行榜activity
     */
    @Provides
    public RankingListContract.Presenter getRankingPresenter(){
        return getPersenter(baseView, RankingListPresenter.class);
    }

    /**
     * 港股页面Fragment
     */
    @Provides
    public HKContract.Presenter getHKPresenter(){
        return getPersenter(baseView, HKPresenter.class);
    }

    /**
     * 板块页面Fragment
     */
    @Provides
    public SectionContract.Presenter getSectionPresenter(){
        return getPersenter(baseView, SectionPresenter.class);
    }


    /**
     * 板块更多页面
     */
    @Provides
    public SectionMoreConstract.Presenter getSectionMorePresenter(){
        return getPersenter(baseView, SectionMorePresenter.class);
    }


    /**
     * 板块详情页面
     */
    @Provides
    public SectionDetailContract.Presenter getSectionDetailPresenter(){
        return getPersenter(baseView, SectionDetailPresenter.class);
    }


    /**
     * 页面
     */
    @Provides
    public HKTContract.Presenter getPresenter(){
        return getPersenter(baseView, HKTPresenter.class);
    }



    /**
     * 港股通更多
     */
    @Provides
    public HKTMoreConstract.Presenter getHKTMorePresenter(){
        return getPersenter(baseView, HKTMorePresenter.class);
    }

    /**
     * 期权列表
     */
    @Provides
    public OptionListContract.Presenter getOptionListPresenter(){
        return getPersenter(baseView, OptionListPresenter.class);
    }


    /**
     * 上证期权T型
     */
    @Provides
    public SHQQTContract.Presenter getSHQQTPresenter(){
        return getPersenter(baseView, SHQQTPresenter.class);
    }

    /**
     * 上证期权全部
     */
    @Provides
    public SHQQAllContract.Presenter getSHQQAllPresenter(){
        return getPersenter(baseView, SHQQAllPresenter.class);
    }

    /**
     * 上证期权认沽
     */
    @Provides
    public SHQQPutConstract.Presenter getSHQQPutPresenter(){
        return getPersenter(baseView, SHQQPutPresenter.class);
    }


    /**
     * 上证期权认购
     */
    @Provides
    public SHQQCallConstract.Presenter getSHQQCallPresenter(){
        return getPersenter(baseView, SHQQCallPresenter.class);
    }


    /**
     * 港股通更多
     */
    @Provides
    public MarketDetailContract.Presenter getMarketDetailPresenter(){
        return getPersenter(baseView, MarketDetailPresenter.class);
    }

    /**
     * 基金摘要页面
     */
    @Provides
    public FundSummaryContract.Presenter getFundSummaryContract(){
        return getPersenter(baseView, FundSummaryPresenter.class);
    }

}
