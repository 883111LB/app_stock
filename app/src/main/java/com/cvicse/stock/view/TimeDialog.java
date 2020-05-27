package com.cvicse.stock.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.markets.ui.CallBackImpl;
import com.cvicse.stock.markets.ui.SHQQTFragment;

import static com.cvicse.stock.markets.data.DateUtil.OptionExpireDateConvert;

/**
 * Created by ding_syi on 17-2-13.
 */
public class TimeDialog {
    Context context;
    Dialog dialog;


    public TimeDialog(Context context, final String[] list, final TextView tevData, final TextView tevLeftData, final CallBackImpl mCallBack) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_time);

        final RadioGroup radioGroup = (RadioGroup)dialog.findViewById(R.id.rg_data);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View checkView = dialog.findViewById(checkedId);
                //如果是默认选中的则不做处理
                if (!checkView.isPressed()){
                    return;
                }
                for(int i=0;i<group.getChildCount();i++){
                    if(group.getChildAt(i).getId() == checkedId){
                        SHQQTFragment.selectedData = list[i];

                        Handler handler = new Handler();
                        final int finalI = i;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    /*tevLeftData.setText("剩余"+SHQQTFragment.getDistanceDays(list[finalI]+"01")+"天");
                                    tevData.setText(SHQQTFragment.transform(list[finalI]));
                                    mCallBack.messDialog2Activity(list[finalI]);*/
                                    //tevLeftData.setText("剩余"+ DateUtil.getRemainDaysForOption()+"天");
                                    tevData.setText(OptionExpireDateConvert(list[finalI]));
                                    mCallBack.messDialog2Activity(list[finalI]);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                dialog.dismiss();
                            }
                        },300);
                    }
                }
            }
        });

            for(int i=0;i<list.length;i++){
                RadioButton radioButton;
                radioButton  = new RadioButton(context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                radioButton.setTextSize(18);

                radioButton.setText(OptionExpireDateConvert(list[i]));
                layoutParams.setMargins(0,80,0,0);
                radioButton.setLayoutParams(layoutParams);

                radioButton.setTextColor(context.getResources().getColor(R.color.withe));
                radioGroup.addView(radioButton);
            }

        if(!isSelected(radioGroup,list)){
            radioGroup.check(radioGroup.getChildAt(0).getId());
        }

        }


    /**
     * 判断以前是否有过选中的
     * @param radioGroup
     * @param list
     * @return
     */
    private boolean isSelected(RadioGroup radioGroup,String[] list){
        for(int i=0;i<list.length;i++){
            if(SHQQTFragment.selectedData.equals(list[i])){
                radioGroup.check(radioGroup.getChildAt(i).getId());
                return true;
            }
        }
        return false;
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


}
