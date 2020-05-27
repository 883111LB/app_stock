package com.cvicse.stock.view;

import com.cvicse.stock.markets.data.IndustrySection;

import java.util.Comparator;

/**
 * Created by ding_syi on 17-1-22.
 */
public class MyComparator implements Comparator {

    @Override
    public int compare(Object lhs, Object rhs) {
        IndustrySection s1 = (IndustrySection) lhs;
        IndustrySection s2 = (IndustrySection) rhs;

        return s2.getRangeRate().compareTo(s1.getRangeRate());
    }
}
