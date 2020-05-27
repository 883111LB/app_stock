package com.cvicse.stock.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.base.utils.ToastUtils;
import com.cvicse.stock.BaseApplication;
import com.cvicse.stock.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class WLQQTimePicker extends LinearLayout{
    public static final String PICKED_TIME_EXT = "picked_time";
    private static final int UPDATE_TITLE_MSG = 0x111;
    private static final int UPDATE_WHEEL = 0x112;
    private static final int UPDATE_UpdateDay_MSG = 0x113;

    private final int START_YEAR = 0;
    private final int END_YEAR = 23;

    private TextView mPickerTitle;
    private WheelView mWheelYear;
    private WheelView mWheelMonth;
    private WheelView mWheelDay;
    private WheelView mWheelHour;
    private TextView mCancelBtn;
    private TextView mConfirmBtn;

    private int mBeginHour;
    private int mBeginMinute;
    private int mEndHour;
    private int mEndMinute;

    OnDataReturnListener onDataReturnListener;
    WLQQTimePicker mWLQQTimePicker;

    Dialog mDialog;

    private Context mContext = BaseApplication.getInstance();

    public WLQQTimePicker(Context context) {
        this(context, null);
    }

    public WLQQTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void  setObject(Dialog dialog){
        mDialog = dialog;
    }

    private Calendar mCalendar;
    private int mDefaultDayWhellIndex = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_TITLE_MSG:
                    updateTitle();
                    break;
                case UPDATE_WHEEL:
                    updateWheel();
                    break;
                case UPDATE_UpdateDay_MSG:
                    updateDay(mBeginMinute);
                    break;
                default:
                    break;
            }

        }
    };

    private WheelView.OnSelectListener mBeginHourListener = new WheelView.OnSelectListener() {
        @Override
        public void endSelect(int year, String text) {
            mBeginHour = year;
            mHandler.sendEmptyMessage(UPDATE_TITLE_MSG);
        }

        @Override
        public void selecting(int id, String text) {
        }
    };

    private WheelView.OnSelectListener mBeginMinuteListener = new WheelView.OnSelectListener() {
        @Override
        public void endSelect(int month, String text) {
            mBeginMinute = month;
            mHandler.sendEmptyMessage(UPDATE_TITLE_MSG);
            //updateDay()
            //mHandler.sendEmptyMessage(UPDATE_UpdateDay_MSG);
        }

        @Override
        public void selecting(int id, String text) {
        }
    };

    private WheelView.OnSelectListener mEndHourListener = new WheelView.OnSelectListener() {
        @Override
        public void endSelect(int day, String text) {
            mEndHour = day;
            mHandler.sendEmptyMessage(UPDATE_TITLE_MSG);
        }

        @Override
        public void selecting(int day, String text) {
        }
    };
    private WheelView.OnSelectListener mEndMinuteListener = new WheelView.OnSelectListener() {
        @Override
        public void endSelect(int hour, String text) {
            mEndMinute = hour;
            mHandler.sendEmptyMessage(UPDATE_TITLE_MSG);
        }

        @Override
        public void selecting(int day, String text) {
        }
    };

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mWLQQTimePicker = this;
        //mContext = (Activity) getContext();
        LayoutInflater.from(mContext).inflate(R.layout.time_picker, this);
        mPickerTitle = (TextView) findViewById(R.id.picker_title);
        mWheelYear = (WheelView) findViewById(R.id.year);
        mWheelMonth = (WheelView) findViewById(R.id.month);
        mWheelDay = (WheelView) findViewById(R.id.day);
        mWheelHour = (WheelView) findViewById(R.id.hour);
        mCancelBtn = (TextView) findViewById(R.id.cancel);
        mConfirmBtn = (TextView) findViewById(R.id.confirm);
        mWheelYear.setOnSelectListener(mBeginHourListener);
        mWheelMonth.setOnSelectListener(mBeginMinuteListener);
        mWheelDay.setOnSelectListener(mEndHourListener);
        mWheelHour.setOnSelectListener(mEndMinuteListener);
        mConfirmBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if((mBeginHour < mEndHour)|| (mBeginHour == mEndHour && mBeginMinute < mEndMinute)){

                    onDataReturnListener.DataReturn(mBeginHour,mBeginMinute,mEndHour,mEndMinute);
                    mDialog.dismiss();
                }else{
                    //时间段不合理
                    ToastUtils.showLongToast("时间段不合理,请重新选择");
                }
            }
        });

        mCancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //mContext.finish();
                mDialog.dismiss();
            }
        });
    }

    private void updateDay(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        mWheelDay.resetData(getHour());
        if (mEndHour > maxDay) {
            mWheelDay.setDefault(mDefaultDayWhellIndex);
            mEndHour = mDefaultDayWhellIndex + 1;
        } else {
            mWheelDay.setDefault(mEndHour - 1);
        }
    }

    /**
     * 设置时间选择器的初始时间
     * @param date
     */
    public void setDate(long date) {
        mCalendar = Calendar.getInstance(Locale.CHINA);
        mCalendar.setTimeInMillis(date);
        mBeginHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mBeginMinute = mCalendar.get(Calendar.MINUTE);
        mEndHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mEndMinute = mCalendar.get(Calendar.MINUTE);

        //时间选择器本身初始化，设置可供选择的数据
        mWheelYear.setData(getHour());
        mWheelMonth.setData(getMinute());
        //mWheelDay.setMSubData(getEndHour(mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)));
        mWheelDay.setData(getHour());
        mWheelHour.setData(getMinute());

        mHandler.sendEmptyMessage(UPDATE_TITLE_MSG);
        mHandler.sendEmptyMessage(UPDATE_WHEEL);
    }

    public void setIntString(int data1,int data2,int data3,int data4) {

        //isEditMode = true;

        //mPosition = position;
        mBeginHour = data1;
        mBeginMinute = data2;
        mEndHour = data3;
        mEndMinute = data4;

        //时间选择器本身初始化，设置可供选择的数据
        mWheelYear.setData(getHour());
        mWheelMonth.setData(getMinute());
        mWheelDay.setData(getHour());
        mWheelHour.setData(getMinute());

        mHandler.sendEmptyMessage(UPDATE_TITLE_MSG);
        mHandler.sendEmptyMessage(UPDATE_WHEEL);
    }

    /**
     * 更新上面显示的标题
     */
    private void updateTitle() {
        mPickerTitle.setText(mContext.getString(R.string.picker_title, dataConvert(mBeginHour),
                dataConvert(mBeginMinute), dataConvert(mEndHour), dataConvert(mEndMinute)));
    }

    private void updateWheel() {
        mWheelYear.setDefault(mBeginHour);
        mWheelMonth.setDefault(mBeginMinute);
        mWheelDay.setDefault(mEndHour);
        mWheelHour.setDefault(mEndMinute);
    }

    private ArrayList<String> getHour() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = START_YEAR; i <= END_YEAR; i++) {
            if(i<10){
                list.add("0"+i);
            }else{
                list.add(i+"");
            }
        }
        return list;
    }

    private ArrayList<String> getMinute() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i <= 59; i++) {
            if(i<10){
                list.add("0" + i);
            }else{
                list.add(i+"");
            }
        }
        return list;
    }

    /**
     * 拼成08:09的字符串形式
     */
    private String dataConvert(int data){
        String str;
        if(data < 10){
            str = "0"+data;
        }else{
            str = data +"";
        }
        return str;
    }

    public void setOnDataReturnListener(OnDataReturnListener onDataReturnListener){
        this.onDataReturnListener = onDataReturnListener;
        //onDataReturnListener.DataReturn(mBeginHour,mBeginMinute,mEndHour,mEndMinute);
    }

    /**
     * 数据回调
     */
    public interface OnDataReturnListener {
        /**
         *返回数据
         * @param data1
         * @param data2
         * @param data3
         * @param data4
         */
        void DataReturn(int data1,int data2,int data3,int data4);
    }
}