package com.cvicse.stock.markets.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cvicse.base.ui.BaseFragment;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.seachstock.SearchHistoryActivity;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 上证期权详细页面
 */
public class SHQQActivity extends PBaseActivity {

    public static final String STOCK_NAME = "50ETF";

    @BindView(R.id.topbar) ToolBar mTopBar;

    @BindView(R.id.lel_option) LinearLayout mLelOption;
    @BindView(R.id.tev_name) TextView mTevName;
    @BindView(R.id.tev_lastPrice) TextView mTevLastPrice;
    @BindView(R.id.tev_change) TextView mTevChange;
    @BindView(R.id.tev_changerate) TextView mTevChangerate;
    @BindView(R.id.right_arraow) ImageView mRightArraow;

    @BindView(R.id.tev_tpice) RadioButton mTevTpice;
    @BindView(R.id.tev_allqq) RadioButton mTevAllqq;
    @BindView(R.id.tev_buyqq) RadioButton mTevBuyqq;
    @BindView(R.id.tev_rgqq) RadioButton mTevRgqq;

    @BindView(R.id.fragment_container) FrameLayout mFragmentContainer;
    @BindView(R.id.rdg_option)
    RadioGroup rdg_option;// 上方T型报价/全部/认购/认沽的选项

    private BaseFragment[] baseFragments = new BaseFragment[4];
    private FragmentManager fragmentManager;

    private int currentPosition = -1;

    //标的证券实例
    private QuoteItem quoteItem;

    private static final String KEY_QUOTE_ITEM = "quoteitem";

    /**
     * 期权T型报价页面
     * @param context
     * @param quoteItem 股票模型实例
     */
    public static void actionIntent(Context context, QuoteItem quoteItem) {
        Intent intent = new Intent(context, SHQQActivity.class);
        intent.putExtra(KEY_QUOTE_ITEM, quoteItem);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shqq;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        quoteItem = getIntent().getParcelableExtra(KEY_QUOTE_ITEM);
        setUI();

        fragmentManager = getSupportFragmentManager();
        selectPosition(0);
        mTopBar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type){
                    case RIGHT_TYPE_1:
                        SearchHistoryActivity.startActivity(SHQQActivity.this);
                        break;
                }
            }
        });
    }

    /**
     * 初始化期权上面的数据
     */
    private void setUI() {
        mTopBar.setTitle(quoteItem.name);//设置标题
        mTevName.setText(quoteItem.name);

        if(TextUtils.setText(mTevLastPrice,quoteItem.lastPrice,"--")){
            if(quoteItem.change.startsWith("-")){
                mTevLastPrice.setTextColor(ColorUtils.DOWN);
            }else if(quoteItem.change.startsWith("+")){
                mTevLastPrice.setTextColor(ColorUtils.UP);
            }
        }

        if(TextUtils.setText(mTevChange,quoteItem.change,"--")){
            if(quoteItem.change.startsWith("-")){
                mTevChange.setTextColor(ColorUtils.DOWN);
            }else if(quoteItem.change.startsWith("+")){
                mTevChange.setTextColor(ColorUtils.UP);
            }
        }

        if(TextUtils.setTextPercent(mTevChangerate,quoteItem.changeRate)){
            if(quoteItem.changeRate.startsWith("-")){
                mTevChangerate.setTextColor(ColorUtils.DOWN);
            }else if(quoteItem.changeRate.startsWith("+")){
                mTevChangerate.setTextColor(ColorUtils.UP);
            }
        }
        // 大商所、郑商所期权点进来的，隐藏上方T型报价/全部/认购/认沽的选项
        if (quoteItem.market.equals("dce") || quoteItem.market.equals("czce")) {
            rdg_option.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {

    }

    ///如果从t型fragment跳转到其他fragment，则替换replace，如果其他跳转到其他，则只需要调用另外的接口
    @OnClick({ R.id.tev_tpice, R.id.tev_allqq, R.id.tev_buyqq, R.id.tev_rgqq,R.id.lel_option })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tev_tpice:
                selectPosition(0);
                break;

            case R.id.tev_allqq:
                selectPosition(1);
                break;

            case R.id.tev_buyqq:
                selectPosition(2);
                break;

            case R.id.tev_rgqq:
                selectPosition(3);
                break;

            case R.id.lel_option:
                ArrayList<QuoteItem> arrayList = new ArrayList<>();
                arrayList.add(quoteItem);
                StockDetailActivity.startActivity(this,arrayList,0);
                break;
        }
    }

    /**
     * fragment统一管理
     */
    private void selectPosition(int position) {
        if(currentPosition == position){
            return;
        }
        currentPosition = position;

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        /**
         *  隐藏不在当前页显示的fragment
         */
        for(int i=0;i<baseFragments.length;i++){
            if(i!=position && baseFragments[i] != null){
                transaction.hide(baseFragments[i]);
            }
        }

        if(baseFragments[position] == null){
            String Tag = "";

            if(position == 0){
                Tag = "Tpice";
                baseFragments[position] = SHQQTFragment.newInstance(quoteItem.id, quoteItem.name);
            }else if(position == 1){
                Tag = "Allqq";
                baseFragments[position] = SHQQAllFragment.newInstance(quoteItem.id);
            }else if(position == 2){
                Tag = "Callqq";
                baseFragments[position] = SHQQCallFragment.newInstance(quoteItem.id);
            }else if(position == 3){
                Tag = "Putqq";
                baseFragments[position] = SHQQPutFragment.newInstance(quoteItem.id);
            }

            transaction.add(R.id.fragment_container,baseFragments[position],Tag);
        }else {
            transaction.show(baseFragments[position]);
        }

        transaction.commitAllowingStateLoss();
    }

}
