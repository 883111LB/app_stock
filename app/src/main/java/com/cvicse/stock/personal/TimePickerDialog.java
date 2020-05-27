package com.cvicse.stock.personal;

import android.app.Dialog;
import android.content.Context;

import com.cvicse.stock.R;
import com.cvicse.stock.view.WLQQTimePicker;

import java.util.Date;

/**
 * Created by ruan_ytai on 17-2-9.
 */

public class TimePickerDialog {
    Context context;
    Dialog dialog;

    private int mPosition;
    private int mBeginHour;
    private int mBeginMinute;
    private int mEndHour;
    private int mEndMinute;

    OnDialogDataListener onDialogData;

    public TimePickerDialog(Context context){
        this.context = context;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_main_time_picker);
        WLQQTimePicker timePicker = (WLQQTimePicker) dialog.findViewById(R.id.timepicker);
        timePicker.setObject(dialog);
        timePicker.setOnDataReturnListener(new WLQQTimePicker.OnDataReturnListener() {
            @Override
            public void DataReturn(int data1, int data2, int data3, int data4) {
                onDialogData.onDialogDataCallBack(data1,data2,data3,data4);
            }

        });

            timePicker.setDate(new Date().getTime());


    }

    public TimePickerDialog(Context context,int[] data){
        this.context = context;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_main_time_picker);
        WLQQTimePicker timePicker = (WLQQTimePicker) dialog.findViewById(R.id.timepicker);
        timePicker.setObject(dialog);
        timePicker.setOnDataReturnListener(new WLQQTimePicker.OnDataReturnListener() {
            @Override
            public void DataReturn(int data1, int data2, int data3, int data4) {
                onDialogData.onDialogDataCallBack(data1,data2,data3,data4);
            }
        });

            timePicker.setIntString(data[0],data[1],data[2],data[3]);
    }

    public void show() {
        dialog.show();
    }
    public void hide() {
        dialog.hide();
    }
    public void dismiss() {
        dialog.dismiss();
    }

    public void setCancelable(boolean isCancelable){
        dialog.setCancelable(isCancelable);
    }

    public void setOnDialogDataCallBack(OnDialogDataListener onDialogData){
        this.onDialogData = onDialogData;
    }

    public interface OnDialogDataListener{
        void onDialogDataCallBack(int beginHour,int beginMinute,int endHour,int endMinute);
    }


}
