package com.cvicse.stock.chart.helper;

/**
 * Created by liu_zlu on 2017/3/21 20:32
 */
/*
public class BottomKDialogHlper {
    Dialog dialog;
    StockKlineFragment stockFragment;
    @BindView(R.id.img_close) ImageView imgClose;
    @BindView(R.id.erdg_right_type) ExtendedRadioGroup erdgRightType;
    @BindView(R.id.tev_sure) TextView tevSure;

    public BottomKDialogHlper(StockKlineFragment stockFragment) {
        this.stockFragment = stockFragment;
    }

    public void show() {
        if (dialog == null) {
            createDialog();
        }
        refreshView();
        dialog.show();
    }

    private void refreshView() {
        erdgRightType.setSelectPosition(KSetting.getKBottomType());
    }

    private void createDialog() {
        dialog = new Dialog(stockFragment.getActivity(), R.style.Dialog);
        dialog.setContentView(R.layout.dialog_chart_ktype_setting);
        ButterKnife.bind(this, dialog);
        initViews();
    }

    private void initViews() {
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tevSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!saveSetting()) return;
                dialog.dismiss();
            }
        });
    }

    private boolean saveSetting() {
        KSetting.setKBottomType(erdgRightType.getSelectPosition());
        return true;
    }
}
*/
