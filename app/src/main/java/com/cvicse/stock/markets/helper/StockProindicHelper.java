package com.cvicse.stock.markets.helper;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.markets.adapter.StockProindicAdapter;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockProindicHelper {
    @BindView(R.id.lsv_proindic)
    ListView lsvProindic;
    @BindView(R.id.tev_title)
    TextView tevTitle;
    @BindView(R.id.tev_empty)
    TextView tevEmpty;

    private String name;
    private Context mContext;

    private StockProindicAdapter stockProindicAdapter;

    public StockProindicHelper(View view, Context context, String name){
        ButterKnife.bind(this,view);

        this.mContext = context;
        this.name = name;
        initView();
    }

    private void initView() {
        tevTitle.setText(name+"年报预披露");
        tevTitle.getPaint().setFakeBoldText(true);  // 动态设置字体加粗
        lsvProindic.setEmptyView(tevEmpty);

        stockProindicAdapter = new StockProindicAdapter(mContext);
        lsvProindic.setAdapter(stockProindicAdapter);
    }

    public void setProindicData(List<HashMap<String,Object>> infoList){
        stockProindicAdapter.setData(infoList);
    }
}
