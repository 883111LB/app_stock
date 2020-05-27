package com.cvicse.stock.markets.dagger;

import com.cvicse.stock.markets.ui.CalendarDayActivity;
import com.cvicse.stock.markets.ui.HKFragment;
import com.cvicse.stock.markets.ui.HKTFragment;
import com.cvicse.stock.markets.ui.HKTMoreActivity;
import com.cvicse.stock.markets.ui.MarketsNewStockCalendarActivity;
import com.cvicse.stock.markets.ui.NewStockDetailActivity;
import com.cvicse.stock.markets.ui.OptionListActivity;
import com.cvicse.stock.markets.ui.RankingListActivity;
import com.cvicse.stock.markets.ui.SHQQActivity;
import com.cvicse.stock.markets.ui.SHQQAllFragment;
import com.cvicse.stock.markets.ui.SHQQCallFragment;
import com.cvicse.stock.markets.ui.SHQQPutFragment;
import com.cvicse.stock.markets.ui.SHQQTFragment;
import com.cvicse.stock.markets.ui.SHSZFragment;
import com.cvicse.stock.markets.ui.SHSZTopFragment;
import com.cvicse.stock.markets.ui.SectionDetailActivity;
import com.cvicse.stock.markets.ui.SectionFragment;
import com.cvicse.stock.markets.ui.SectionMoreActivity;
import com.cvicse.stock.markets.ui.StockDetailFragment;
import com.cvicse.stock.markets.ui.new_ui.MarketsNewStockCalendarActivityNew;
import com.cvicse.stock.markets.ui.new_ui.NewStockDetailActivityNew;
import com.cvicse.stock.markets.ui.stockdetail.FundSummaryFragment;

import dagger.Component;

/**
 * Created by ruan_ytai on 17-1-16.
 */
@Component(modules = MarketsModule.class)
public interface MarketsComponent{

    /**
     * 沪深首页
     */
    SHSZFragment inject(SHSZFragment SHSZFragment);

    /**
     * 沪深上方滑动页
     */
    SHSZTopFragment inject(SHSZTopFragment SHSZTopFragment);

    /**
     * 新股日历
     */
    MarketsNewStockCalendarActivity inject(MarketsNewStockCalendarActivity calendarActivity);
    /**
     * 新股日历
     */
    MarketsNewStockCalendarActivityNew inject(MarketsNewStockCalendarActivityNew calendarActivity);

    /**
     * 每日新股日历
    */
    CalendarDayActivity inject(CalendarDayActivity calendarActivity);

    /**
     * 新股详情
     */
    NewStockDetailActivity inject(NewStockDetailActivity newStockDetailActivity);
    /**
     * 新股详情
     */
    NewStockDetailActivityNew inject(NewStockDetailActivityNew newStockDetailActivity);

    /**
     * 排行榜页面
     */
    RankingListActivity inject(RankingListActivity rankingListActivity);

    /**
     * 港股页面
     */
    HKFragment inject(HKFragment ggFragment);

    /**
     * 板块页面
     */
    SectionFragment inject(SectionFragment sectionFragment);

    /**
     * 更多板块页面
     */
    SectionMoreActivity inject(SectionMoreActivity sectionMoreActivity);

    /**
     * 板块详情页面
     */
    SectionDetailActivity inject(SectionDetailActivity sectionDetailActivity);

    /**
     * 港股通
     */
    HKTFragment inject(HKTFragment hktFragment);

    /**
     * 港股通
     */
    HKTMoreActivity inject(HKTMoreActivity hktFragment);

    /**
     * 期权列表
     */
    OptionListActivity inject(OptionListActivity optionListActivity);

    /**
     * 上证期权
     */
    SHQQActivity inject(SHQQActivity shqqActivity);

    /**
     * 上证期权T型fragment
     */
    SHQQTFragment inject(SHQQTFragment shqqActivity);

    /**
     * 上证期权全部Fragment
     */
    SHQQAllFragment inject(SHQQAllFragment shqqAllFragment);

    /**
     * 上证期权认购Fragment
     */
    SHQQCallFragment inject(SHQQCallFragment shqqCalllFragment);

    /**
     * 上证期权认沽Fragment
     */
    SHQQPutFragment inject(SHQQPutFragment shqqAllFragment);

    /**
     * 港股通
     */
    StockDetailFragment inject(StockDetailFragment stockDetailFragment);

    /**
     * 基金摘要页面
     */
    FundSummaryFragment inject(FundSummaryFragment fundSummaryFragment);


}
