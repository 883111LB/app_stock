package com.cvicse.stock.markets.helper;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.cvicse.stock.R;
import com.cvicse.stock.markets.adapter.PlateAdapter;
import com.cvicse.stock.markets.ui.SectionDetailActivity;
import com.cvicse.stock.view.MyGridView;
import com.mitake.core.response.Bankuaisorting;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/** 所属板块帮助类
 * Created by tang_xueqing on 2017/11/29
 */
public class StockPlateHlper {

    // 板块
    @BindView(R.id.grd_plate)
    MyGridView mGrdPlate;

    private PlateAdapter mPlateAdapter;

    private Context mContext;

    public StockPlateHlper( Context context,View view){
        ButterKnife.bind(this,view);
        this.mContext = context;
        initView();
    }

    private void initView() {
        mPlateAdapter  = new PlateAdapter(mContext);
        mGrdPlate.setAdapter( mPlateAdapter );
        mGrdPlate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bankuaisorting bankuaisorting = (Bankuaisorting) mPlateAdapter.getItem(position);
                SectionDetailActivity.newIntent(mContext,bankuaisorting.ns,bankuaisorting.n);
            }
        });
    }

    public void setData(List<Bankuaisorting> bankuaisortingList){
        if (null == bankuaisortingList || bankuaisortingList.size() == 0) {
            return;
        }
        // 如果存过，只展示保存的
        mPlateAdapter.setData(bankuaisortingList);
    }
}
