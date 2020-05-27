package com.cvicse.stock.utils;

import com.mitake.core.QuoteItem;

/**
 * Created by Administrator on 2017/6/14.
 *  消息事件类
 */

public class MessageEvent {

    private String message;

    private QuoteItem quoteItem;


    public MessageEvent(String message) {
        this.message = message;
    }

    public MessageEvent(QuoteItem quoteItem) {
        this.quoteItem = quoteItem;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public QuoteItem getQuoteItem() {
        return quoteItem;
    }

    public void setQuoteItem(QuoteItem quoteItem) {
        this.quoteItem = quoteItem;
    }
}
