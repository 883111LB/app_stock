package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.StockVolumePresenter;

/**
 * 分量
 * Created by tang_hua on 2020/2/28.
 */

public class StockVolumeContract {
    public interface View extends IView {

        void moreVolumeSuccess(String[] volumes, String[] buyVolumes, String[] sellVolumes);
    }

    @MVProvides(StockVolumePresenter.class)
    public interface Presenter extends IPresenter<View> {

        /**
         * 分量
         */
        void moreVolumeRequest(String code, String subtype);
    }
}
