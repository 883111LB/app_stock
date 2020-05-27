package com.cvicse.stock.seachstock.dagger;


import com.cvicse.stock.seachstock.SearchHistoryActivity;

import dagger.Component;

/**
 * Created by liu_zlu on 2016/8/14 19:57
 */
@Component(modules = {SearchModule.class})
public interface SearchComponent {
    /**
     * 自选首页
     *
     * @return
     */
    SearchHistoryActivity inject(SearchHistoryActivity searchHistoryActivity);


}