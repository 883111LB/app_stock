package com.cvicse.stock.markets.helper;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cvicse.base.utils.ScreenUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.chart.data.MKLineEntity;
import com.cvicse.stock.chart.listener.OnMKSelectedListener;
import com.cvicse.stock.chart.ui.FundValueFragment;
import com.cvicse.stock.chart.ui.StockFiveDayFragment;
import com.cvicse.stock.chart.ui.StockKlineFragment;
import com.cvicse.stock.chart.ui.StockMinuteFragment;
import com.cvicse.stock.chart.utils.StockTechType;
import com.cvicse.stock.markets.ui.StockDetailActivity;
import com.cvicse.stock.utils.StockType;
import com.mitake.core.AppInfo;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.QuoteResponse;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 图表帮助类
 * Created by liu_zlu on 2017/2/22 17:49
 */
public class StockChartHelper {

    @BindView(R.id.view_bottom_1) View viewBottom1;
    @BindView(R.id.frl_fundvalue) FrameLayout frlFundvalue;
    @BindView(R.id.view_bottom_0) View viewBottom0;
    @BindView(R.id.frl_mintime) FrameLayout frlMintime;
    @BindView(R.id.view_bottom_2) View viewBottom2;
    @BindView(R.id.frl_fiveday) FrameLayout frlFiveday;
    @BindView(R.id.view_bottom_3) View viewBottom3;
    @BindView(R.id.frl_day_key) FrameLayout frlDayKey;
    @BindView(R.id.view_bottom_4) View viewBottom4;
    @BindView(R.id.frl_week_k) FrameLayout frlWeekK;
    @BindView(R.id.view_bottom_5) View viewBottom5;
    @BindView(R.id.frl_month_k) FrameLayout frlMonthK;
    @BindView(R.id.minuteID) TextView minuteID;
    @BindView(R.id.underline) View underline;
    @BindView(R.id.minuteview) FrameLayout minuteview;
    @BindView(R.id.frl_year_k) FrameLayout frlYearK;
    @BindView(R.id.view_bottom_6) View viewBottom6;

    private PopupWindow popupWindow;
    private View[] views = new View[8];

    private FragmentManager fragmentManager;
    private Fragment fragment = null;

    private Fragment lastfragment = null;
    private QuoteItem quoteItem;
    private QuoteResponse quoteResponse;

    private boolean isLand;
    private int lastType;
    private Context mContext;

    //是否为港股基金
    private boolean isHKFund = false;

    private boolean isInit = false;

    /**
     *
     * @param isLand 是否是横屏
     * @param item
     * @param view
     * @param quoteItem
     * @param fragmentManager
     */
    public StockChartHelper(boolean isLand,int item,View view, QuoteItem quoteItem, FragmentManager fragmentManager) {
        ButterKnife.bind(this,view);
        this.isLand = isLand;
        this.fragmentManager = fragmentManager;
        this.quoteItem = quoteItem;

        views[0] = viewBottom1;
        views[1] = viewBottom2;
        views[2] = viewBottom3;
        views[3] = viewBottom4;
        views[4] = viewBottom5;

        views[5] = viewBottom6;
        views[6] = underline;
        views[7] = viewBottom0;

        showBottom(item);
//        init(quoteItem);
    }

    private void init(QuoteItem quoteItem) {
        this.quoteItem = quoteItem;

        //判断是否为基金，否-不显示基金净值图
        if (StockType.getType(quoteItem)!=null){
            if( !StockType.getType(quoteItem).isFund()){
                frlFundvalue.setVisibility(View.GONE);
            }
        }

        if( null != quoteItem.id ) {
            //香港基金也没有基金净值图
            if ("1100".equals(quoteItem.subtype) && "hk".equals(quoteItem.id.split("\\.")[1])) {
                isHKFund = true;
                frlFundvalue.setVisibility(View.GONE);
            }
        }
        initView();
//        showBottom(0);
    }

    private void initView(){

        frlMintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottom(0);
            }
        });
        frlFiveday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottom(1);
            }
        });
        frlDayKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottom(2);
            }
        });

        frlWeekK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottom(3);
            }
        });
        frlMonthK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottom  (4);
            }
        });
        minuteID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(minuteID);
            }
        });
        frlFundvalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottom(12);
            }
        });

        frlYearK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottom(5);
            }
        });
    }

    public void setSelected(int position){
        if(position == -2){
            //非基金或者港股基金
            if(null!= quoteItem.subtype && !quoteItem.subtype.equals("1100") || isHKFund){
                position = 0;
            }else{
                position = 12;
            }
        }
        showBottom(position);
    }

    /**
     * 显示不同的图
     * @param position
     */
    private void showBottom(int position){
        StockDetailActivity.type = position;
        for(View view:views){
            view.setVisibility(View.GONE);
        }
        //表示选择分钟
        if(position > 5 && position != 12){
            views[6].setVisibility(View.VISIBLE);
        } else if(position <= 5){
            minuteID.setText("分钟");
            views[position].setVisibility(View.VISIBLE);
        } else {
            views[7].setVisibility(View.VISIBLE);
        }

        switch (position){
            case 0:
                //分时图
                fragment = StockMinuteFragment.newInstance(quoteItem);
                break;
            case 1:
                //五日图
                fragment = StockFiveDayFragment.newInstance(quoteItem);
                break;
            case 2:
                //k线图，日k
                fragment = StockKlineFragment.newInstance(quoteItem, StockTechType.DAY);
                break;
            case 3:
                //k线图，周k
                fragment = StockKlineFragment.newInstance(quoteItem, StockTechType.WEEK);
                break;
            case 4:
                //k线图，月K
                fragment = StockKlineFragment.newInstance(quoteItem, StockTechType.MONTH);
                break;
            case 5:
                fragment = StockKlineFragment.newInstance(quoteItem, StockTechType.YEAR);
                break;
            case 6:
                // 1分钟K
                minuteID.setText("1分钟");
                fragment =  StockKlineFragment.newInstance(quoteItem,StockTechType.ONE_MINUTE);
                break;
            case 7:
                minuteID.setText("5分钟");
                fragment = StockKlineFragment.newInstance(quoteItem, StockTechType.FIVE_MINUTE);
                break;
            case 8:
                minuteID.setText("15分钟");
                fragment = StockKlineFragment.newInstance(quoteItem,StockTechType.FIFTEEN_MINUTE);
                break;
            case 9:
                //基金净值图
                minuteID.setText("30分钟");
                fragment = StockKlineFragment.newInstance(quoteItem,StockTechType.THIRTY_MINUTE);
                break;
            case 10:
                minuteID.setText("60分钟");
                fragment = StockKlineFragment.newInstance(quoteItem, StockTechType.SIXTY_MINUTE);
                break;
            case 11:
                minuteID.setText("120分钟");
                fragment = StockKlineFragment.newInstance(quoteItem, StockTechType.ONEHUNDREDTWENTY_MINUTE);
                break;
            case 12:
                //基金净值图
                fragment = FundValueFragment.newInstance(quoteItem);
                break;

        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if( null!= lastfragment){
            transaction.hide(lastfragment);
        }
        transaction.replace(R.id.content_frame_tick,fragment);
        transaction.commit();
        lastfragment = fragment;
        if( null!= quoteResponse ){
            setQuoteDetail(quoteResponse);
        }
    }

    /**
     * 更新图表的股票当前详细
     * @param quoteResponse
     */
    public void setQuoteDetail(QuoteResponse quoteResponse) {
        if( null != quoteResponse && null != this.quoteResponse ){
            String newId = quoteResponse.quoteItems.get(0).id;
            String oldId = this.quoteResponse.quoteItems.get(0).id;
            if( !newId.equals(oldId)  ){
                isInit = true;
                init(quoteResponse.quoteItems.get(0));
            }
        }else{
            if( !isInit ){
                isInit = true;
                init(quoteResponse.quoteItems.get(0));
            }
        }

        this.quoteResponse = quoteResponse;
        if( null != fragment  && fragment instanceof QuoteCallback){
            ((QuoteCallback) fragment).update(quoteResponse);
        }

        if( null != fragment && fragment instanceof StockKlineFragment ){
            ((StockKlineFragment) fragment).setOnMKSelectedListener(new OnMKSelectedListener() {
                @Override
                public void onValueSelected(MKLineEntity mkLineEntity,String lastClose) {
                    if( null != mkSelectedListener ){
                        mkSelectedListener.onValueSelected(mkLineEntity,lastClose);
                    }
                }
            });
        }
    }

    private MKSelectedListener mkSelectedListener;

    public void setMKLineSelectedLinseter(MKSelectedListener mkSelectedListener){
        this.mkSelectedListener = mkSelectedListener;
    }

    private void showPopupWindow(View view){
        if(popupWindow != null){
            showInner(view);
            return;
        }
        Context context = view.getContext();
        View contentView = LayoutInflater.from(context).inflate(R.layout.popup_kminute_layout, null);

        TextView tevOne = (TextView) contentView.findViewById(R.id.tev_one);
        tevOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottom(6);
                popupWindow.dismiss();
            }
        });

        TextView tevFive = (TextView) contentView.findViewById(R.id.tev_five);
        tevFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottom(7);
                popupWindow.dismiss();
            }
        });
        TextView tevFifteen = (TextView) contentView.findViewById(R.id.tev_fifteen);
        tevFifteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottom(8);
                popupWindow.dismiss();
            }
        });
        TextView tevThirty = (TextView) contentView.findViewById(R.id.tev_thirty);
        tevThirty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottom(9);
                popupWindow.dismiss();
            }
        });
        TextView tevSixty = (TextView) contentView.findViewById(R.id.tev_sixty);
        tevSixty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottom(10);
                popupWindow.dismiss();
            }
        });
        TextView tevOneHundredTwenty = (TextView) contentView.findViewById(R.id.tev_onehundredtwenty);
        tevOneHundredTwenty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottom(11);
                popupWindow.dismiss();
            }
        });

        popupWindow = new PopupWindow(contentView);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        contentView.setFocusable(true);
        contentView.setFocusableInTouchMode(true);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        showInner(view);
    }

    private void showInner(View view){
        if(isLand){
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            location = calculatePopWindowPos(view,popupWindow.getContentView());
            popupWindow.showAtLocation(view,Gravity.NO_GRAVITY, location[0], location[1]);
        } else {
            popupWindow.showAsDropDown(view);
        }
    }

    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
     * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
     * @param anchorView  呼出window的view
     * @param contentView   window的内容布局
     * @return window显示的左上角的xOff,yOff坐标
     */
    public static int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = ScreenUtils.getScreenHeight();
        final int screenWidth = ScreenUtils.getScreenWidth();
        // 测量contentView
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }
}
