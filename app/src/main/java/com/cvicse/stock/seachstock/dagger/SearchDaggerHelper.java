package com.cvicse.stock.seachstock.dagger;


import com.cvicse.base.mvp.IView;

/**
 * Created by liu_zlu on 2016/9/13 10:11
 */
public class SearchDaggerHelper {

   public static SearchComponent getComponent(IView baseView){
       return DaggerSearchComponent.builder().searchModule(new SearchModule(baseView))
              .build();
    }
}
