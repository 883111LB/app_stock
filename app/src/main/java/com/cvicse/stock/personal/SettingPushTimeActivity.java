package com.cvicse.stock.personal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.cvicse.base.ui.BaseApplication;
import com.cvicse.base.utils.ToastUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.common.Setting.PushSetting;
import com.cvicse.stock.personal.adapter.PushTimeListAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 推送时间段设置
 * Created by ruan_ytai on 17-2-8.
 */

public class SettingPushTimeActivity extends PBaseActivity implements AdapterView.OnItemClickListener{

    @BindView(R.id.topbar) ToolBar mTopbar;
    @BindView(R.id.lsv_push_time) ListView mLsvPushTime;
    @BindView(R.id.frl_default_remind) FrameLayout mFrlDefaultRemind;

    private boolean isEidtMode = false;
    private int mPosition;
    private PushTimeListAdapter adapter;
    private ArrayList<String> dataList = new ArrayList<>();

    public static void actionStart(Context context){
        Intent intent = new Intent(context,SettingPushTimeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_pushtime;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        adapter = new PushTimeListAdapter(this);
        adapter.setView(mFrlDefaultRemind);
        mLsvPushTime.setAdapter(adapter);

        mLsvPushTime.setOnItemClickListener(this);
        /**
         * dialog相关设置
         */
        final TimePickerDialog dialog  = new TimePickerDialog(SettingPushTimeActivity.this);
        dialog.setCancelable(false);
        dialog.setOnDialogDataCallBack(new TimePickerDialog.OnDialogDataListener() {
            @Override
            public void onDialogDataCallBack(int beginHour, int beginMinute, int endHour, int endMinute) {
                int[] backData = { beginHour,beginMinute,endHour,endMinute };
                if(isEidtMode){
                    dataList.set(mPosition,dataConvert(backData));
                }else{
                    dataList.add(dataConvert(backData));
                }
                updateListView();
                isEidtMode = false;
            }
        });

        mTopbar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                isEidtMode = false;
                switch (type) {
                    case RIGHT_TYPE_1:
                        if (adapter.getCount() >= 3){
                            ToastUtils.showLongToast("亲，最多只能添加3个推送时间哦！");
                        }else{
                            dialog.show();
                        }
                        break;

                    case LEFT_TYPE:
                        storePushTimes();
                        break;

                    default:
                        break;
                }

            }
        });
        restorePushTime();
        isHideRemind();

    }

    @Override
    protected void initData() {
        if(dataList != null){
            updateListView();
        }

    }

    private void updateListView(){
        adapter.setData(dataList);
        isHideRemind();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        isEidtMode = true;
        mPosition = position;

        String str = adapter.getItem(position);
        /**
         * dialog构造函数的第二个参数代表是编辑时间段
         */
        String[] temp1 = str.split("--");
        String[] begin = temp1[0].split(":");
        String[] end = temp1[1].split(":");
        if(begin.length == 2 && end.length == 2){
            int[] defaultTime = {Integer.parseInt(begin[0]),Integer.parseInt(begin[1]),
                    Integer.parseInt(end[0]),Integer.parseInt(end[1])};
            TimePickerDialog dialog  = new TimePickerDialog(SettingPushTimeActivity.this,defaultTime);
            dialog.setCancelable(false);
            dialog.setOnDialogDataCallBack(new TimePickerDialog.OnDialogDataListener() {
                @Override
                public void onDialogDataCallBack(int beginHour, int beginMinute, int endHour, int endMinute) {
                    int[] backData = { beginHour,beginMinute,endHour,endMinute };
                    if(isEidtMode){
                        dataList.set(mPosition,dataConvert(backData));
                    }else{
                        dataList.add(dataConvert(backData));
                    }
                    updateListView();
                    isEidtMode = false;
                }
            });
            dialog.show();
        }

    }

    @Override
    public void onBackPressed() {
        storePushTimes();
        super.onBackPressed();
    }

    /**
     * 拼成08:09--09:30的字符串形式
     */
    private String dataConvert(int[] data){
        int length = data.length;
        String[] str = new String[length];
        String returnString = null;
        for(int i=0;i<length;i++){
            if(data[i] < 10){
                str[i] = "0"+data[i];
            }else{
                str[i] = data[i] +"";
            }
        }
        //拼接字符串
        returnString = str[0] + ":"+ str[1] + "--"+ str[2] + ":" + str[3];

        return returnString;
    }

    /**
     * 存储添加的时间段
     */
    private void storePushTimes(){
        if(dataList == null){
            return;
        }
        PushSetting.setPushTime(dataList);
    }

    /**
     * 恢复添加的时间段
     */
    private void restorePushTime(){
        dataList.addAll(PushSetting.getPushTimeList());
        updateListView();
    }

    /**
     * 设置中间的提示显示
     */
    private void isHideRemind(){
        if(dataList.size() <= 0){
            mFrlDefaultRemind.setVisibility(View.VISIBLE);
        }else{
            mFrlDefaultRemind.setVisibility(View.GONE);
        }
    }
}
