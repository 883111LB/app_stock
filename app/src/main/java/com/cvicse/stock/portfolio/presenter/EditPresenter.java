package com.cvicse.stock.portfolio.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.portfolio.data.MyStockEditBean;
import com.cvicse.stock.portfolio.presenter.constract.EditConstract;
import com.cvicse.stock.utils.MyStocksUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自选股编辑页面
 * Created by ding_syi on 17-1-18.
 */
public class EditPresenter extends BasePresenter implements EditConstract.Presenter {
    EditConstract.View view;


    @Override
    public void getStock() {
       String code =  MyStocksUtils.getSelectCode();
       String name =  MyStocksUtils.getSelectName();

        if(code!=null && !"".equals(code)){
            String[] codes = code.split(",");
            String[] names = name.split(",");

            int length = codes.length;

            List<MyStockEditBean> myStockEditBeanList = new ArrayList<>();

            for(int i=0;i<length;i++){
                MyStockEditBean myStockEditBean = new MyStockEditBean();
                myStockEditBean.setIfChoose(false);
                myStockEditBean.setId(codes[i]);
                myStockEditBean.setName(names[i]);

                myStockEditBeanList.add(myStockEditBean);
            }
            view.getData(myStockEditBeanList);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getStock();
    }

    @Override
    public void setView(EditConstract.View view) {
        this.view = view;
    }

}
