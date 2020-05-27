package com.cvicse.stock.news.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.news.presenter.contract.NewsDetailContract;
import com.cvicse.stock.utils.ImageLoader;
import com.mitake.core.NewsList;

import butterknife.BindView;


/**
 * Created by ruan_ytai on 17-1-5.
 */

public class NewsDetailActivity extends PBaseActivity implements NewsDetailContract.View {

    @BindView(R.id.tev_title)
    TextView mTevTitle;
    @BindView(R.id.tev_source)
    TextView mTevSource;
    @BindView(R.id.tev_date)
    TextView mTevDate;
    @BindView(R.id.wev_newscontent)
    WebView mWevNewscontent;
    @BindView(R.id.img_news)
    ImageView imgNews;

    @MVPInject  NewsDetailContract.Presenter presenter;

    public static final String KEY_NEWS_ITEM = "news_item";
    public static final String KEY_NEWS_IMG = "image";

    private String mNewsContent;
    private NewsList news;
    private WebSettings settings;

    public static void actionIntent(Context context,NewsList news, boolean isHasImg){
        Intent intent = new Intent(context,NewsDetailActivity.class);
        intent.putExtra(KEY_NEWS_ITEM,news);
        intent.putExtra(KEY_NEWS_IMG,isHasImg);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_newsdetail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
       // DaggerNewsComponent.builder().newsModule(new NewsModule(this)).build().inject(this);
        news = getIntent().getParcelableExtra(KEY_NEWS_ITEM);

        //有ImgView时
        if(getIntent().getBooleanExtra(KEY_NEWS_IMG,false)){
            imgNews.setVisibility(View.VISIBLE);
            ImageLoader.loadId(imgNews,news.ID_);
        }
        mTevTitle.setText(news.REPORTTITLE_);
        mTevSource.setText(news.MEDIANAME_);
        mTevDate.setText(news.INIPUBDATE_);

        settings = mWevNewscontent.getSettings();
        //设置字体大小
        settings.setSupportZoom(true);
        settings.setTextSize(WebSettings.TextSize.LARGER);
        mWevNewscontent.getSettings().setJavaScriptEnabled(true);
        mWevNewscontent.setWebViewClient(new WebViewClient());
    }

    @Override
    protected void initData() {
        presenter.requestNewsDetail(news.ID_);
    }


    /**
     * 数据从presenter中拿到，通过调用View的此方法传进来
     */
    @Override
    public void onRequestSuccess(String content) {
        mNewsContent = content;
        mWevNewscontent.loadDataWithBaseURL(null,mNewsContent,"text/plain","utf-8",null);
    }

    @Override
    public void onRequestFail() {

    }
}
