package com.cvicse.stock.portfolio.dagger;


import com.cvicse.base.mvp.IView;

/**
 * Created by liu_zlu on 2016/9/13 10:11
 */
public class MyStockDaggerHelper {


 public static MyStockComponent getComponent(IView baseView){
      return DaggerMyStockComponent.builder().myStockModule(new MyStockModule(baseView))
              .build();
  }


}
