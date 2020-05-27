package com.cvicse.stock.markets.helper;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.markets.adapter.StockBlockTradeAdapter;
import com.cvicse.stock.markets.adapter.StockIntearActiveAdapter;
import com.cvicse.stock.markets.ui.stockdetail.StockSummaryBigEventActivity;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/** 董秘问答
 * Created by liu_bin on 2019/10/10.
 */
public class StockIntearActiveHelper {
    @BindView(R.id.lsv_intear_active)
    ListView lsvIntearActive;
    @BindView(R.id.tev_empty)
    TextView tevEmpty;

    private String name;
    private Context mContext;

    private StockIntearActiveAdapter intearActiveAdapter;

    public StockIntearActiveHelper(View view, Context context, String name){
        ButterKnife.bind(this, view);

        this.mContext = context;
        this.name = name;
        initView();
    }

    private void initView() {
        lsvIntearActive.setEmptyView(tevEmpty);

        intearActiveAdapter = new StockIntearActiveAdapter(mContext);
        lsvIntearActive.setAdapter(intearActiveAdapter);
    }

    public void setIntearActiveData(List<HashMap<String,Object>> infoList){
        intearActiveAdapter.setData(infoList);
    }
}
