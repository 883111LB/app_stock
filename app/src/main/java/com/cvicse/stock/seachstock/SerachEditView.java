package com.cvicse.stock.seachstock;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by liu_zlu on 2017/1/12 21:26
 */
public class SerachEditView extends EditText {

    private ITextChangedLister watcher;

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(watcher != null){
                watcher.onTextChanged((Editable) msg.obj);
            }
        }
    };
    public SerachEditView(Context context) {
        super(context);
        init(context);
    }

    public SerachEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public SerachEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.removeCallbacksAndMessages(null);
                Message message = Message.obtain();
                message.what = 0;
                message.obj = s;
                handler.sendMessageDelayed(message,200);
            }
        });
    }


    public void setITextChangedLister(ITextChangedLister watcher) {
        this.watcher = watcher;
    }


    public interface ITextChangedLister {

        void onTextChanged(Editable s);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        watcher = null;
    }
}
