package com.cvicse.stock.portfolio.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.portfolio.adapter.StockEditAdapter;
import com.cvicse.stock.portfolio.data.MyStockEditBean;
import com.cvicse.stock.portfolio.presenter.constract.EditConstract;
import com.cvicse.stock.seachstock.SearchHistoryActivity;
import com.cvicse.stock.utils.MyStocksUtils;
import com.cvicse.stock.view.MyDialog;
import com.cvicse.stock.view.dslv.DragSortListView;

import java.util.List;

import butterknife.BindView;

/**
 * 自选股编辑页面
 * @author liu_zlu
 */
public class StockEditActivity extends PBaseActivity implements EditConstract.View{
    /**
     * 拖拽列表
     */
    @BindView(R.id.lst_mystock_edit) DragSortListView lstMystockEdit;
    /**
     * 全选按钮
     */
    @BindView(R.id.tev_all) TextView tevAll;
    /**
     * 删除按钮
     */
    @BindView(R.id.tev_delete) TextView tevDelete;
    /** 涨跌按钮 */
    @BindView(R.id.topbar)ToolBar topBar;
    private StockEditAdapter mStockEditAdapter;
    @MVPInject
    EditConstract.Presenter myStockEditPresenter;

    /**
     * 启动自选股编辑页面
     * @param context Activity 实例
     */
    public static void actionIntent(Context context) {
        Intent intent = new Intent(context, StockEditActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_stock_edit;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mStockEditAdapter = new StockEditAdapter(this);
        lstMystockEdit.setAdapter(mStockEditAdapter);
        topBar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type){
                    case RIGHT_TYPE_1:
                        SearchHistoryActivity.startActivity(StockEditActivity.this);
                        break;
                }
            }
        });
        /**
         * 全部点击事件
         */
        tevAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStockEditAdapter.toggle();
                int size= mStockEditAdapter.integers.size();
                if(size==0){
                    tevDelete.setText("删除");
                }else{
                    tevDelete.setText("删除("+ mStockEditAdapter.integers.size()+")");
                }
            }
        });

        /**
         * 删除点击事件
         */
        tevDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!"删除".equals(tevDelete.getText())){
                    MyDialog myDialog = new MyDialog(StockEditActivity.this,tevDelete, mStockEditAdapter);
                    myDialog.show();
                }
            }
        });


        lstMystockEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mStockEditAdapter.toggleChange(position);
                if(mStockEditAdapter.integers.size()==0){
                    tevDelete.setText("删除");
                }else{
                    tevDelete.setText("删除("+ mStockEditAdapter.integers.size()+")");
                }
            }
        });
        lstMystockEdit.setDropListener(new DragSortListView.DragSortListener() {
            @Override
            public void drag(int from, int to) {

            }

            @Override
            public void drop(int from, int to) {
                mStockEditAdapter.drag(from, to);
            }

            @Override
            public void remove(int which) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyStocksUtils.removeAll();
        List<MyStockEditBean> myStockBeanList = mStockEditAdapter.getData();
        if(myStockBeanList == null){
            return;
        }
        StringBuffer code = new StringBuffer();
        StringBuffer name = new StringBuffer();
        boolean frist = true;
        for(MyStockEditBean myStockEditBean:myStockBeanList){
            if(!frist){
                code.append(",");
                name.append(",");
            }
            frist = false;
            code.append(myStockEditBean.getId());
            name.append(myStockEditBean.getName());
        }
        MyStocksUtils.saveSelect(code.toString(),name.toString());
    }

    @Override
    protected void initData() {

    }


    /**
     * 调用网络接口成功成功
     *
     * @param result
     */
    @Override
    public void getData(Object result) {
        mStockEditAdapter.setData((List<MyStockEditBean>) result);
    }
}
