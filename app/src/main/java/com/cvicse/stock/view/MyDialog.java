package com.cvicse.stock.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.portfolio.adapter.StockEditAdapter;

/**
 * Created by ding_syi on 17-1-17.
 */
public class MyDialog {
    Context context;
    Dialog dialog;

    /** 确定按钮*/
    TextView btnComfire;

    /** 取消按钮*/
    TextView btnCancle;

    public MyDialog(Context context,final TextView tevDelete, final StockEditAdapter myStockEditAdapter){
        this.context = context;
        dialog = new Dialog(context,R.style.Dialog);
        dialog.setContentView(R.layout.dialog_custom);
        btnComfire = (TextView)dialog.findViewById(R.id.btn_comfire);
        btnCancle = (TextView)dialog.findViewById(R.id.btn_cancel);

        btnComfire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStockEditAdapter.deleteChose();

                tevDelete.setText("删除");
                dismiss();
            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


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
