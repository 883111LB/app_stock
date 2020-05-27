package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.data.TickItemBo;
import com.cvicse.stock.markets.presenter.presenter_new.StockTickPresenter;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

/**
 * 逐笔数据
 * Created by liu_zlu on 2017/3/7 13:40
 */
public class StockTickContract {
    public interface View extends IView {
        //返回股票逐笔数据成功
        void onTickItemsSuccess(ArrayList<TickItemBo> tickItemBos);
        //返回股票逐笔数据失败
        void onTickItemsFail();
    }
    @MVProvides(StockTickPresenter.class)
    public interface Presenter extends IPresenter<View> {
        // 初始化stockId
        void init(QuoteItem quoteItem);

        //刷新逐笔数据
        void queryTickItems();

        //加载更多逐笔数据
        void loadTickItems();
    }
}
