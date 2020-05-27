package com.cvicse.stock.markets.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.markets.presenter.contract.SHSZTopContract;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/** 沪深-指数模块
 * Created by ruan_ytai 2017-1-12.
 */
public class SHSZTopFragment extends PBaseFragment implements SHSZTopContract.View {
    @MVPInject
    SHSZTopContract.Presenter presenter;

    public static final String KEY_TOPF_TYPE = "type";
    @BindView(R.id.tev_name1) TextView mTevName1;
    @BindView(R.id.img_updown1) ImageView mImgUpdown1;
    @BindView(R.id.tev_index1) TextView mTevIndex1;
    @BindView(R.id.tev_change1) TextView mTevChange1;
    @BindView(R.id.tev_changerate1) TextView mTevChangerate1;

    @BindView(R.id.tev_name2) TextView mTevName2;
    @BindView(R.id.img_updown2) ImageView mImgUpdown2;
    @BindView(R.id.tev_index2) TextView mTevIndex2;
    @BindView(R.id.tev_change2) TextView mTevChange2;
    @BindView(R.id.tev_changerate2) TextView mTevChangerate2;

    @BindView(R.id.tev_name3) TextView mTevName3;
    @BindView(R.id.img_updown3) ImageView mImgUpdown3;
    @BindView(R.id.tev_index3) TextView mTevIndex3;
    @BindView(R.id.tev_change3) TextView mTevChange3;
    @BindView(R.id.tev_changerate3) TextView mTevChangerate3;


    private String type;

    private int[] imgUpOrDown = { R.drawable.up, R.drawable.down };

    public SHSZTopFragment() {

    }

    public static SHSZTopFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TOPF_TYPE, type);

        SHSZTopFragment fragment = new SHSZTopFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_marketes_shsztop;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        type = getArguments().getString(KEY_TOPF_TYPE);

    }

    @Override
    protected void initData() {
        presenter.requestIndex(type);
    }

    /**
     * 请求成功
     */
    @Override
    public void onIndexSuccess(ArrayList<QuoteItem> result) {
        updateIndex(result);
    }

    /**
     * 请求失败
     */
    @Override
    public void onIndexFail() {

    }

    /**
     * 指数实时更新
     * @param quoteItem
     */
    @Override
    public void onIndexTcp(QuoteItem quoteItem) {

    }

    /**
     * 更新指数
     * @param result
     */
    private void updateIndex(List<QuoteItem> result) {
        mTevIndex1.setText(result.get(0).lastPrice);
        if(result.get(0).change.startsWith("+")){
            mImgUpdown1.setImageResource(imgUpOrDown[0]);
            mTevIndex1.setTextColor(Color.parseColor("#d83f31"));
        }else{
            mImgUpdown1.setImageResource(imgUpOrDown[1]);
            mTevIndex1.setTextColor(Color.parseColor("#2d7c2d"));
        }
        mTevName1.setText(result.get(0).name);
        mTevChange1.setText(result.get(0).change);
        mTevChangerate1.setText(result.get(0).changeRate + "%");

        mTevIndex2.setText(result.get(1).lastPrice);
        if(result.get(1).change.startsWith("+")){
            mImgUpdown2.setImageResource(imgUpOrDown[0]);
            mTevIndex2.setTextColor(Color.parseColor("#d83f31"));
        }else{
            mImgUpdown2.setImageResource(imgUpOrDown[1]);
            mTevIndex2.setTextColor(Color.parseColor("#2d7c2d"));
        }
        mTevName2.setText(result.get(1).name);
        mTevChange2.setText(result.get(1).change);
        mTevChangerate2.setText(result.get(1).changeRate + "%");

        mTevIndex3.setText(result.get(2).lastPrice);
        if(result.get(2).change.startsWith("+")){
            mImgUpdown3.setImageResource(imgUpOrDown[0]);
            mTevIndex3.setTextColor(Color.parseColor("#d83f31"));
        }else{
            mImgUpdown3.setImageResource(imgUpOrDown[1]);
            mTevIndex3.setTextColor(Color.parseColor("#2d7c2d"));
        }
        mTevName3.setText(result.get(2).name);
        mTevChange3.setText(result.get(2).change);
        mTevChangerate3.setText(result.get(2).changeRate + "%");
    }
}
