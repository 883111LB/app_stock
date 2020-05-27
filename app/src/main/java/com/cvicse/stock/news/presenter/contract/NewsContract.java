package com.cvicse.stock.news.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.news.presenter.NewsPresenter;

/**
 * Created by ruan_ytai on 17-1-7.
 */

public class NewsContract {

    public interface View extends IView{

    }

    @MVProvides(NewsPresenter.class)
    public interface Presenter extends IPresenter<View>{

    }
}
