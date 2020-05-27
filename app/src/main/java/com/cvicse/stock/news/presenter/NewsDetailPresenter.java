package com.cvicse.stock.news.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.news.presenter.contract.NewsDetailContract;
import com.mitake.core.NewsDetailItem;
import com.mitake.core.request.NewsRequest;
import com.mitake.core.response.NewsResponse;
import com.mitake.core.response.Response;

/**
 * Created by ruan_ytai on 17-1-9.
 */

public class NewsDetailPresenter extends BasePresenter implements NewsDetailContract.Presenter {

    private NewsDetailContract.View newsDetailView;
    private String content;


    @Override
    public void requestNewsDetail(String newsId) {
        NewsRequest newsRequest = new NewsRequest();
        newsRequest.send(newsId,new ResponseCallback(newsRequest) {
            @Override
            public void onBack(Response response) {

                NewsResponse newsResponse= (NewsResponse) response;
                final NewsDetailItem info = newsResponse.info;
                /**
                 * 同一个News，再加上content字段
                 */
                content = info.ABSTRACT_;
                newsDetailView.onRequestSuccess(content);
            }

            @Override
            public void onError(int i, String s) {
            }
        });
    }

    @Override
    public void setView(NewsDetailContract.View view) {
        newsDetailView = view;
    }


}
