package com.cvicse.stock.markets.helper;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.markets.adapter.StockBlockTradeAdapter;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/** 大宗交易
 * Created by tang_xqing on 2018/4/23.
 */
public class StockBlockTradeHelper {

    @BindView(R.id.lsv_block_trade)
    ListView lsvBlockTrade;
    @BindView(R.id.tev_title)
    TextView tevTitle;
    @BindView(R.id.tev_empty)
    TextView tevEmpty;

    private String name;
    private Context mContext;

    private StockBlockTradeAdapter blockTradeAdapter;

    public StockBlockTradeHelper(View view, Context context,String name){
        ButterKnife.bind(this, view);

        this.mContext = context;
        this.name = name;
        initView();
    }

    private void initView() {
        tevTitle.setText(name+"历史大宗交易数据");
        tevTitle.getPaint().setFakeBoldText(true);  // 动态设置字体加粗
        lsvBlockTrade.setEmptyView(tevEmpty);

        blockTradeAdapter = new StockBlockTradeAdapter(mContext);
        lsvBlockTrade.setAdapter(blockTradeAdapter);
    }

    public void setBlockTradeData(List<HashMap<String,Object>> infoList){
        blockTradeAdapter.setData(infoList);
    }
}
