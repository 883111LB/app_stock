package com.cvicse.stock.utils;

import android.view.View;
import android.widget.ListView;

/**
 * Created by liu_zlu on 2017/3/16 12:01
 */
public class ListViewUtils {
    public static View getViewByPosition(int pos, ListView listView) {

        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = listView.getLastVisiblePosition();  // new

        //getChildCount（）  返回的是显示在屏幕上可见的item的数量
//        final int lastListItemPosition = firstListItemPosition + listView.getChildCount()-1;  // old

        if(pos < firstListItemPosition || pos > lastListItemPosition) {
            return null;
        }else{
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    public static void updatePosition(int position,ListView listView){
        position =position + listView.getHeaderViewsCount();
        View view = getViewByPosition(position,listView);

        /** 添加到自选页面后，最后一支股票不更新。
         * 原因：如果listview存在headerView，那么position = position < headerViewCount ? position : (position - headerViewCount)
         * 会调用HeaderViewListAdapter，看源码line 207。
         */
        if( null!= view && null != listView.getAdapter() ){
            listView.getAdapter().getView( position,view,listView);   // .getView() 刷新单个itermview界面
        }
    }
}
