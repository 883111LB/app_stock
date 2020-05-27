package com.cvicse.stock.markets.helper;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.cvicse.stock.R;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.MessageEvent;
import com.cvicse.stock.utils.TextUtils;
import com.cvicse.stock.view.AutofitTextView;
import com.mitake.core.QuoteItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 股票列表头部操作
 * Created by liu_zlu on 2017/3/17 16:52
 */
public class StockToolBarHelper {
    //左箭头
    @BindView(R.id.rel_leftarrow) RelativeLayout relLeftarrow;
    //右箭头
    @BindView(R.id.rl_rightarrow) RelativeLayout rlRightarrow;
    @BindView(R.id.tev_name) AutofitTextView tevName;
    @BindView(R.id.tev_rong) TextView tevRong;
    @BindView(R.id.tv_tong) TextView tvTong;
    @BindView(R.id.tev_down_right) TextView tevDownRight;
    @BindView(R.id.tev_upText) TextView tevUpText;
    @BindView(R.id.vs_stockcode) ViewSwitcher vsStockcode;

    private ViewPager vpStockdetail;
    private ArrayList<QuoteItem> quoteItems;
    private int count = 0;
    private int selected = 0;
    public StockToolBarHelper(View view,ViewPager vpStockdetail,ArrayList<QuoteItem> quoteItems) {
        ButterKnife.bind(this,view);
        this.quoteItems = quoteItems;
        count = quoteItems == null ? 0 :quoteItems.size();
        this.vpStockdetail = vpStockdetail;
        initView();
    }

    private void initView(){
        //注册事件
        EventBus.getDefault().register(StockToolBarHelper.this);

        vpStockdetail.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        relLeftarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpStockdetail.setCurrentItem(vpStockdetail.getCurrentItem()-1);
            }
        });

        rlRightarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpStockdetail.setCurrentItem(vpStockdetail.getCurrentItem()+1);
            }
        });
    }

    /**
     * 当前选中的股票位置，list 的position
     */
    public void setSelected(int position){
        selected = position;
        QuoteItem quoteItem = quoteItems.get(position);
        TextUtils.setText(tevName,quoteItem.name, FillConfig.SIGNLE_LINE);

        //设置状态
        setTevDownRight(quoteItem);

        if(position == 0){
            relLeftarrow.setVisibility(View.INVISIBLE);
        } else {
            relLeftarrow.setVisibility(View.VISIBLE);
        }

        if(position == count-1){
            rlRightarrow.setVisibility(View.INVISIBLE);
        } else {
            rlRightarrow.setVisibility(View.VISIBLE);
        }
    }

    private void setTevDownRight(QuoteItem quoteItem) {
        if(null == quoteItem || null == quoteItem.id){
            return;
        }
        String[] split = quoteItem.id.split("\\.");
        String substring = split[0];
        TextUtils.setText(tevDownRight,
                StockStatusHelper.statusConvert(quoteItem.status)
                        +" " + substring,FillConfig.SIGNLE_LINE);
    }

    /**
     * 头部走马灯效果
     * @param scrollY 当前展示的Fragment的ScrollView滑动距离
     */
    public void updateViewSwitcher(int scrollY,int position){
        /**
         * up 表示当前处于上部展示状态，以后的滑动大于180不再执行，下面类似
         */
        if(scrollY >= 180 && vsStockcode.getTag().equals("up")){
            vsStockcode.setTag("dw");
            vsStockcode.setInAnimation(vsStockcode.getContext(), R.anim.slide_in_from_bottom);
            vsStockcode.setOutAnimation(vsStockcode.getContext(), R.anim.slide_out_to_top);
            vsStockcode.showPrevious();
        }
        if(scrollY < 180 && vsStockcode.getTag().equals("dw")){
            vsStockcode.setTag("up");
            vsStockcode.setInAnimation(vsStockcode.getContext(), R.anim.slide_in_from_bottom);
            vsStockcode.setOutAnimation(vsStockcode.getContext(), R.anim.slide_out_to_top);
            vsStockcode.showNext();
        }
    }

    /**
     * 修改股票头部当前信息
     */
    public void updateToolBar(QuoteItem quoteItem,int position){
        if( null==quoteItem  || selected != position){
            return;
        }
        TextUtils.setText(tevName,quoteItem.name, FillConfig.SIGNLE_LINE);

        //设置状态
        setTevDownRight(quoteItem);

        tevDownRight.setTextColor(ColorUtils.WHITE);
        quoteItems.set(selected,quoteItem);

        if(FormatUtils.isStandard(quoteItem.change)){
            if( quoteItem.change.startsWith("+") ){
                tevUpText.setTextColor(ColorUtils.UP);
                TextUtils.setText(tevUpText,quoteItem.lastPrice+" +"+quoteItem.changeRate+"%");
            }else if(quoteItem.change.startsWith("-")){
                tevUpText.setTextColor(ColorUtils.DOWN);
                TextUtils.setText(tevUpText,quoteItem.lastPrice+" -"+quoteItem.changeRate+"%");
            }else{
                TextUtils.setText(tevUpText,quoteItem.lastPrice+" "+quoteItem.changeRate+"%");
                tevUpText.setTextColor(ColorUtils.WHITE);
            }
            //日间模式下
            if(Setting.isDay()){
                tevUpText.setTextColor(ColorUtils.WHITE);
            }
        }else{
            TextUtils.setText(tevUpText,FillConfig.DEFALUT+" "+FillConfig.DEFALUT+"%");
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING,sticky = true)
    public void ononMoonStickyEvent(MessageEvent messageEvent){
        QuoteItem quoteItem = messageEvent.getQuoteItem();
        if(quoteItem != null){
            //设置状态
            setTevDownRight(quoteItem);
        }
    }
}
