package com.cvicse.stock.markets.helper;

import com.mitake.core.response.QuoteResponse;

/**
 * Created by liu_zlu on 2017/2/27 09:42
 */
public interface QuoteCallback {
    void update(QuoteResponse quoteResponse);
}
