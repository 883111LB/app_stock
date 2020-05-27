package com.cvicse.stock.markets.helper;

import android.app.Dialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.common.FillConfig;
import com.mitake.core.bean.quote.ConvertibleBoundItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/** 可转债溢价查询
 * Created by tang_xqing on 2018/9/13.
 */
public class BoundDialogHelper {
    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.lly_bound_list)
    LinearLayout mLayoutBountList;

    private Dialog mDialog;
    private List<ConvertibleBoundItem> mBoundItemList;
    private StockViewHelper mStockViewHelper;

    public BoundDialogHelper(StockViewHelper stockViewHelper) {
        this.mStockViewHelper = stockViewHelper;
    }

    public void setConverBoundItemList(List<ConvertibleBoundItem> convertibleBoundItemList){
        if( null == convertibleBoundItemList || convertibleBoundItemList.isEmpty()){
            return;
        }
        this.mBoundItemList = convertibleBoundItemList;
    }

    public void show(){
        if( null == mDialog ){
            createDialog();
        }
        refreshView();
        mDialog.show();
    }

    private void refreshView() {
        mLayoutBountList.removeAllViews();
        if( null == mBoundItemList || mBoundItemList.isEmpty()){
            return;
        }
        for (final ConvertibleBoundItem boundItem : mBoundItemList) {
            View view = View.inflate(mStockViewHelper.getFragmentActivity(), R.layout.dialog_bound_item, null);
            TextView tevName = (TextView) view.findViewById(R.id.tev_bound_name);
            TextView tevLastPrice = (TextView) view.findViewById(R.id.tev_bound_lastprice);
            TextView tevChangeRate = (TextView) view.findViewById(R.id.tev_bound_changeRate);
            TextView tevPremium = (TextView) view.findViewById(R.id.tev_bound_premium);
            tevName.setText(boundItem.name);
            tevLastPrice.setText(boundItem.lastPrice);
            tevChangeRate.setText(boundItem.changeRate+"%");
            tevPremium.setText(boundItem.premium+"%");

            int colorChangeRate =ColorUtils.DEFALUT() ;
            if( null != boundItem.changeRate ){
                if( boundItem.upDownFlag.contains("+") ){
                    colorChangeRate = ColorUtils.UP;
                }else if( boundItem.upDownFlag.contains("-") ){
                    colorChangeRate = ColorUtils.DOWN;
                }
                tevLastPrice.setTextColor(colorChangeRate);
                tevChangeRate.setTextColor(colorChangeRate);
            }
            if( null != boundItem.premium ){
                if(FillConfig.SUBTRACT.equals(boundItem.premium)){
                    tevPremium.setTextColor(ColorUtils.DOWN);
                }else{
                    tevPremium.setTextColor(ColorUtils.UP);
                }
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissDialog();
                    // 点击跳转到详情页
                    mStockViewHelper.requestQuote( boundItem.code);
                }
            });
            mLayoutBountList.addView(view);
        }
    }

    private void createDialog() {
        mDialog = new Dialog(mStockViewHelper.getFragmentActivity(), R.style.Dialog);
        mDialog.setContentView(R.layout.dialog_bound_list);
        ButterKnife.bind(this,mDialog);

        initView();
    }

    private void initView(){
        setDialogWidth();
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDialog();
            }
        });
    }

    /**
     * 设置对话框宽度
     */
    private void setDialogWidth(){
        if( null == mDialog ){
            return;
        }

        WindowManager.LayoutParams attributes = mDialog.getWindow().getAttributes();
        int widthPixels = mStockViewHelper.getFragmentActivity().getResources().getDisplayMetrics().widthPixels;
        attributes.width = (int) (widthPixels * 0.9);
        mDialog.getWindow().setAttributes(attributes);
    }

    public void hideDialog(){
        if( null != mDialog ){
            mDialog.dismiss();
        }
    }

    public void dismissDialog(){
        if( null != mDialog ){
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
