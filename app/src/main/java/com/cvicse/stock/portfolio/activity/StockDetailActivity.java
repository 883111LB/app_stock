package com.cvicse.stock.portfolio.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.portfolio.data.StockNewsBean;
import com.cvicse.stock.portfolio.presenter.constract.DetailConstract;
import com.cvicse.stock.portfolio.util.BASE64Encoder;
import com.cvicse.stock.portfolio.util.DownLoadUtil;

import java.io.UnsupportedEncodingException;

import butterknife.BindView;

/**
 * 新闻详情页面
 * Created by ding_syi on 17-1-11.
 */
public class StockDetailActivity extends PBaseActivity implements DetailConstract.View, View.OnClickListener {
    /**
     * 新闻标题
     */
    @BindView(R.id.tev_detail_title)
    TextView tevDetailTitle;
    /**
     * 新闻发布日期
     */
    @BindView(R.id.tev_detail_date)
    TextView tevDetailDate;
    /**
     * 新闻内容
     */
    @BindView(R.id.wev_detail_newscontent)
    WebView wevDetailNewscontent;

    /**
     * 新闻来源
     */
    @BindView(R.id.tev_source)
    TextView tevSource;

    /**
     * 标题栏
     */
    @BindView(R.id.topbar)
    ToolBar topbar;
    @MVPInject
    DetailConstract.PresenterDetail presenter;

    public static final String KEY_NEWS_ID = "id";
    public static final String KEY_NEWS_TITLE = "title";
    public static final String KEY_NEWS_DATE = "datetime";
    public static final String KEY_NEWS_SOURCE = "source";
    public static final String TYPE = "type";

    /**
     * 公告标示
     */
    public static final String TYPE_BULLETIN = "type_bulletin";
    /**
     * 研报标示
     */
    public static final String TYPE_REPORT = "type_report";
    /**
     * 新闻标示
     */
    public static final String TYPE_NEWS = "type_news";

    /**
     * 通知标示
     */
    public static final String TYPE_NOTICE = "type_notice";

    @BindView(R.id.tev_content)
    TextView tevContent;
    @BindView(R.id.tev_download)
    TextView tevDownload;

    private StockNewsBean stockNewsBean;

    private DownLoadUtil downLoadUtil;

    /**
     * 启动新闻、公告、研报、通知详情页面
     *
     * @param context Activity实例
     * @param id      新闻、公告、研报、通知 ID
     * @param title   新闻、公告、研报、通知标题
     * @param date    该资讯发布日期
     * @param type    资讯类型 公告标示{@value TYPE_BULLETIN}、研报标示{@value TYPE_REPORT}、
     *                新闻标示{@value TYPE_NEWS}、通知标示{@value TYPE_NOTICE}
     */
    public static void actionIntent(Context context, String id, String title, String date,
                                    String source, String type) {
        Intent intent = new Intent(context, StockDetailActivity.class);
        intent.putExtra(KEY_NEWS_ID, id);
        intent.putExtra(KEY_NEWS_TITLE, title);
        intent.putExtra(KEY_NEWS_DATE, date);
        intent.putExtra(KEY_NEWS_SOURCE, source);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mystock_detail;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String mId = intent.getStringExtra(KEY_NEWS_ID);
        String type = intent.getStringExtra(TYPE);
        if (type.equals(TYPE_BULLETIN)) {
            topbar.setTitle("公告");
        } else if (type.equals(TYPE_NEWS)) {
            topbar.setTitle("新闻");
        } else if (type.equals(TYPE_REPORT)) {
            topbar.setTitle("研报");
        } else {
            topbar.setTitle("通知");
        }
        presenter.init(mId, type);

        String title = intent.getStringExtra(KEY_NEWS_TITLE);
        String data = intent.getStringExtra(KEY_NEWS_DATE);
        String source = intent.getStringExtra(KEY_NEWS_SOURCE);

        tevDetailTitle.setText(title);
        tevDetailDate.setText(data);
        tevSource.setText(source);

        tevDownload.setOnClickListener(this);

        WebSettings settings = wevDetailNewscontent.getSettings();
        //设置字体大小
        settings.setSavePassword(false);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setDefaultFontSize(18);
        wevDetailNewscontent.getSettings().setJavaScriptEnabled(true);
        wevDetailNewscontent.setWebViewClient(new WebViewClient());
        wevDetailNewscontent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void initData() {
        presenter.queryDetail();
    }

    @Override
    public void onTastSuccess(StockNewsBean result) {
        this.stockNewsBean = result;
        String purl = result.getPurl();
        if (!TextUtils.isEmpty(purl)) {
            tevContent.setText(result.getContent());
            tevDownload.setTag(purl);
            tevContent.setVisibility(View.VISIBLE);
            tevDownload.setVisibility(View.VISIBLE);
            // 标题（新闻）
            String reportTitle = result.getReportTitle();
            if (null != reportTitle && !"".equals(reportTitle)) {
                tevDetailTitle.setText(reportTitle);
            }
            // 标题（公告）
            String title = result.getTitle();
            if (null != title && !"".equals(title)) {
                tevDetailTitle.setText(title);
            }
            // 来源（公告、研报）
            String datasouce = result.getDatasouce();
            if (null != datasouce && !"".equals(datasouce)) {
                tevSource.setText(datasouce);
            }

//            if (purl.endsWith("pdf")) {
//                if (!"".equals(result)) {
//                    byte[] bytes = null;
//                    try {// 获取以字符编码为utf-8的字符
//                        bytes = purl.getBytes("UTF-8");
//                    } catch (UnsupportedEncodingException e1) {
//                        e1.printStackTrace();
//                    }
//                    if (bytes != null) {
//                        purl = new BASE64Encoder().encode(bytes);// BASE64转码
//                    }
//                }
//                wevDetailNewscontent
//                        .loadUrl("file:///android_asset/pdfjs/web/viewer.html?file="
//                                + purl);
//            } else {
//                wevDetailNewscontent.loadUrl(result.getPurl());
//            }
        } else {
            wevDetailNewscontent.loadDataWithBaseURL(null, result.getContent(), "text/plain", "utf-8", null);
        }
    }

    @Override
    public void onTaskFail() {

    }

    /**
     * 点击下载文件
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.tev_download:
                // TODO: 2017/11/28  下载文件
                downLoadFile(v.getTag().toString());
                break;
            default:
                break;
        }
    }

    private void downLoadFile(String path) {
        if( TextUtils.isEmpty(path) ){
            return;
        }
        if( null == downLoadUtil ){
            downLoadUtil = new DownLoadUtil(this);
        }

        downLoadUtil.downLoad(path);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if( null != downLoadUtil ){
            downLoadUtil.destory();
        }
    }
}
