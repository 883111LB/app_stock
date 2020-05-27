package com.cvicse.stock.markets.helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.markets.data.AHQuoteBo;
import com.cvicse.stock.markets.data.DRQuoteBo;
import com.cvicse.stock.markets.ui.StockDetailFragment;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.StockType;
import com.cvicse.stock.utils.TextUtils;
import com.cvicse.stock.utils.UpDownUtils;
import com.cvicse.stock.view.AutofitTextView;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.UpdownsItem;
import com.mitake.core.bean.quote.ConvertibleBoundItem;
import com.mitake.core.util.FormatUtility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 股票行情头部
 * Created by liu_zlu on 2017/2/7 15:00
 */
public class StockViewHelper {
    private StockViewIndex stockViewIndex;  // 指数
    private StockViewMore stockViewMore;  // 盘口数据

    //最新价
    @BindView(R.id.tev_lastPrice) AutofitTextView tevLastPrice;
    // 涨跌
    @BindView(R.id.tev_change) TextView tevChange;
    //涨跌比率
    @BindView(R.id.tev_changeRate) TextView tevChangeRate;
    //最高
    @BindView(R.id.tev_max_high) TextView tevMaxHigh;
    //最低
    @BindView(R.id.tev_max_low) TextView tevMaxLow;
    //今天开盘价
    @BindView(R.id.tev_open) TextView tevOpen;
    //换手率或昨天收盘数
    @BindView(R.id.tev_turnover_rate) TextView tevTurnoverRate;
    // 成交量
    @BindView(R.id.tev_volume) TextView tevVolume;
    // 成交额
    @BindView(R.id.tev_turnover) TextView tevTurnover;
    @BindView(R.id.vsb_second) ViewStub vsbSecond;
    @BindView(R.id.vsb_threen) ViewStub vsbThreen;
    @BindView(R.id.frl_hidevalue) FrameLayout frlHidevalue;
    @BindView(R.id.img_hideshow) ImageView imgHideshow;
    @BindView(R.id.tev_rateClose) TextView tevRateClose;
    @BindView(R.id.tev_ah_name) TextView mTevAhName;
    @BindView(R.id.tev_ah_changeRate) TextView mTevAhChangeRate;
    @BindView(R.id.tev_ah_premium) TextView mTevAhPremium;
    @BindView(R.id.lly_ah) LinearLayout llyAh;
    @BindView(R.id.tev_bond_name) TextView mTevBondName;
    @BindView(R.id.tev_bond_changeRate) TextView mTevBondChangeRate;
    @BindView(R.id.tev_bond_premium) TextView mTevBondPremium;
    @BindView(R.id.lly_bond) LinearLayout llyBond;

    @BindView(R.id.tev_dr_name) TextView mTevDRName;
    @BindView(R.id.tev_dr_changeRate) TextView mTevDRChangeRate;
    @BindView(R.id.tev_dr_premium) TextView mTevDRPremium;
    @BindView(R.id.lly_dr) LinearLayout llyDR;

    private LinearLayout lelMarketOrdinary,lelIndex;
    private List<ConvertibleBoundItem> mBoundItemList;
    private Fragment mFragment;
    private BoundDialogHelper mBoundDialogHelper;

    public StockViewHelper(View view, Fragment fragment, final QuoteItem quoteItem) {
        ButterKnife.bind(this, view);
        this.mFragment = fragment;
        mBoundDialogHelper = new BoundDialogHelper(this);
        llyAh.setVisibility(View.GONE);  // 默认
        llyBond.setVisibility(View.GONE);

        if (null != quoteItem) {
            // AH股
            if(!android.text.TextUtils.isEmpty(quoteItem.ah) ){
                llyAh.setVisibility(View.VISIBLE);
                TextUtils.setText(mTevAhName,quoteItem.id.contains("hk") ? "A股报价":"H股报价"+"\t");
            }
            if(!android.text.TextUtils.isEmpty(quoteItem.zgConvertCodes) ){
                llyBond.setVisibility(View.VISIBLE);
            }
        }
        //判断是否为指数
//        if( StockType.getType(quoteItem).isIndex()){
        if("INDEX".equals(StockType.getType(quoteItem))){
            lelIndex = (LinearLayout) vsbThreen.inflate();
            tevRateClose.setText("昨收");
            stockViewIndex = new StockViewIndex(lelIndex);
            frlHidevalue.setVisibility(View.GONE);
        } else {
            lelMarketOrdinary = (LinearLayout) vsbSecond.inflate();
            stockViewMore = new StockViewMore(lelMarketOrdinary);
            frlHidevalue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(lelMarketOrdinary.getVisibility() == View.GONE){
                        lelMarketOrdinary.setVisibility(View.VISIBLE);
                        imgHideshow.setImageResource(R.drawable.img_totop );
                    } else {
                        lelMarketOrdinary.setVisibility(View.GONE);
                        imgHideshow.setImageResource(R.drawable.img_tobottom);
                    }
                }
            });
        }

        llyAh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = (String) v.getTag();
                if( null != code ){
                    requestQuote(code);
                }
            }
        });

        llyBond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBoundDialogHelper.show();
            }
        });
    }

    /**
     * 更新股票数据
     * @param quoteItem
     */
    public void setQuoteDetail(QuoteItem quoteItem){
        if(TextUtils.setText(tevMaxHigh,quoteItem.highPrice)){
            UpDownUtils.compare(quoteItem.preClosePrice,quoteItem.highPrice,tevMaxHigh);
        }
        if(TextUtils.setText(tevMaxLow,quoteItem.lowPrice)){
            UpDownUtils.compare(quoteItem.preClosePrice,quoteItem.lowPrice,tevMaxLow);
        }
        if(TextUtils.setText(tevOpen,quoteItem.openPrice)){
            UpDownUtils.compare(quoteItem.preClosePrice,quoteItem.openPrice,tevOpen);
        }
        TextUtils.setText(tevVolume, FormatUtility.formatVolume(quoteItem.volume));
        TextUtils.setText(tevTurnover, FormatUtility.formatVolume(quoteItem.amount));  // new

        if(null != quoteItem.subtype && quoteItem.subtype.equals("1400")){
            TextUtils.setText(tevTurnoverRate,quoteItem.preClosePrice);
        } else {
            TextUtils.setText(tevTurnoverRate,FormatUtils.formatPercent(quoteItem.turnoverRate));
        }
        //动态设置最新价字体大小
        tevLastPrice.setTextSize(TypedValue.COMPLEX_UNIT_DIP,FormatUtils.setDynamicSize(quoteItem.lastPrice));
        TextUtils.setText(tevLastPrice,quoteItem.lastPrice);

        setChange(quoteItem);
        setChangeRate(quoteItem);
        if( null!= stockViewMore){
            stockViewMore.setData(quoteItem);
        }
    }

    /**
     * 设置涨跌
     * @param quoteItem
     */
    private void setChange( QuoteItem quoteItem) {
        String change = quoteItem.change;
        if( null != change && null != quoteItem.upDownFlag
                && !"=".equals(quoteItem.upDownFlag) && !"!".equals(quoteItem.upDownFlag)
                && !change.startsWith("-") && !change.startsWith("+")){
            change = quoteItem.upDownFlag+change;
        }
        int color = ColorUtils.DEFALUT();
        if (TextUtils.setText(tevChange, change) &&  null!= change) {
            if ("-".equals(quoteItem.upDownFlag)) {
                color = ColorUtils.DOWN;
            } else if ("+".equals(quoteItem.upDownFlag)) {
                color = ColorUtils.UP;
            }
        }
        tevLastPrice.setTextColor(color);
        tevChange.setTextColor(color);
    }

    /**
     * 涨幅
     * @param quoteItem
     */
    private void setChangeRate( QuoteItem quoteItem) {
        String changeRate = quoteItem.changeRate;
        if( null != changeRate && null != quoteItem.upDownFlag
                && !"=".equals(quoteItem.upDownFlag)&& !"!".equals(quoteItem.upDownFlag)
                && !changeRate.startsWith("-") && !changeRate.startsWith("+")){
            changeRate = quoteItem.upDownFlag+changeRate;
        }

        int color = ColorUtils.DEFALUT();
        if (TextUtils.setTextPercent(tevChangeRate, changeRate) && null != changeRate  ) {
            if ("-".equals(quoteItem.upDownFlag)) {
                color = ColorUtils.DOWN;
            } else if ("+".equals(quoteItem.upDownFlag)) {
                color = ColorUtils.UP;
            }
        }
        tevChangeRate.setTextColor(color);
    }

    public void setUpdownsItem(UpdownsItem updownsItem){
        if( null!= stockViewIndex){
            stockViewIndex.setData(updownsItem);
        }
    }

    /**
     * 更新AH股行情数据
     * @param ahQuoteBo
     */
    public void setAHQuote(AHQuoteBo ahQuoteBo){
        if( null == ahQuoteBo  || null == ahQuoteBo.getCode()){
            return;
        }
        String code = ahQuoteBo.getCode();
        String lastPrice = ahQuoteBo.getLastPrice();
        String premiun = ahQuoteBo.getPremiun();

        StringBuilder text = new StringBuilder();
        text.append(code.contains("hk") ? "H股报价":"A股报价").append("\t");
        text.append(lastPrice);
        mTevAhName.setText(text.toString());

        text = new StringBuilder();
        text.append(code.contains("hk") ? "溢价(A/H)":"溢价(H/A)").append("\t");
        text.append(premiun).append("%");
        mTevAhPremium.setText(text.toString());

        TextUtils.setText(mTevAhChangeRate,ahQuoteBo.getChangeRate());
        if( null != ahQuoteBo.getChangeRate() ){
            if( ahQuoteBo.getChangeRate().contains("+") ){
                mTevAhChangeRate.setTextColor(ColorUtils.UP);
            }else if( ahQuoteBo.getChangeRate().contains("-") ){
                mTevAhChangeRate.setTextColor(ColorUtils.DOWN);
            }
        }
        llyAh.setTag(code);
    }
    /**
     * 更新DR行情数据
     * @param drQuoteBo
     */
    public void setDRQuote(DRQuoteBo drQuoteBo) {
        String code=drQuoteBo.getCode();
        String subType=drQuoteBo.getSubType();
        if (null==drQuoteBo||null==code){
            llyDR.setVisibility(View.GONE);
        }else {
            llyDR.setVisibility(View.VISIBLE);
            String lastPrice=drQuoteBo.getLastPrice();
            String premium=drQuoteBo.getPremiun();
            StringBuilder text=new StringBuilder();
            if ("1530".equals(subType)){
                text.append(code.contains("uk")?"H股报价":"DR报价").append("\t");
            }else if ("1540".equals(subType)){
                text.append(code.contains("uk")?"H股报价":"DR报价").append("\t");
            }
            text.append(lastPrice);
            mTevDRName.setText(text.toString());
            text=new StringBuilder();
            text.append("溢价(DR/H)").append("\t");
            text.append(premium).append("%");
            mTevDRPremium.setText(text.toString());
            TextUtils.setText(mTevDRChangeRate,drQuoteBo.getChangeRate());
            if (null!=drQuoteBo.getChangeRate()){
                if (drQuoteBo.getChangeRate().contains("+")){
                    mTevDRChangeRate.setTextColor(ColorUtils.UP);
                }else if (drQuoteBo.getChangeRate().contains("-")){
                    mTevDRChangeRate.setTextColor(ColorUtils.DOWN);
                }
            }
            llyDR.setTag(code);
        }
    }

    /**
     * 设置可转债溢价查询数据
     * @param convertibleBoundItemList
     */
    public void setConvertibleBound(List<ConvertibleBoundItem> convertibleBoundItemList){
        if( null == convertibleBoundItemList || convertibleBoundItemList.isEmpty() || null == convertibleBoundItemList.get(0) ){
            return;
        }
        mBoundItemList = convertibleBoundItemList;
        ConvertibleBoundItem boundItem = mBoundItemList.get(0);
        StringBuilder text = new StringBuilder();
        text.append(boundItem.name).append("\t");
        text.append(boundItem.lastPrice);
        TextUtils.setText(mTevBondName,text.toString());

        text = new StringBuilder();
        text.append("溢价").append("\t");
        text.append(boundItem.premium).append("%");
        mTevBondPremium.setText(text.toString());

        if( null != boundItem.changeRate ){
            if( boundItem.upDownFlag.contains("+") ){
                TextUtils.setText(mTevBondChangeRate, FillConfig.PLUS+boundItem.changeRate+FillConfig.PRECENT);
                mTevBondChangeRate.setTextColor(ColorUtils.UP);
            }else if( boundItem.upDownFlag.contains("-") ){
                TextUtils.setText(mTevBondChangeRate, FillConfig.SUBTRACT+boundItem.changeRate+FillConfig.PRECENT);
                mTevBondChangeRate.setTextColor(ColorUtils.DOWN);
            }
        }

        llyBond.setTag(null);
        // 为对话框设置数据
        mBoundDialogHelper.setConverBoundItemList(convertibleBoundItemList);
    }

    public void requestQuote(String code){
        // 请求行情快照，跳转到行情页
        ((StockDetailFragment)mFragment).requestQuote(code);
    }

    public FragmentActivity getFragmentActivity(){
        return mFragment.getActivity();
    }

    public void dismissDialog(){
       mBoundDialogHelper.dismissDialog();
    }
}
