package com.cvicse.stock.chart.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.chart.data.TechChartType;
import com.cvicse.stock.chart.theme.ThemeColor;
import com.stock.config.KSetting;

/**
 * Created by liu_zlu on 2017/1/10 13:54
 */
public class KChoseView extends LinearLayout{
    private TextView tev_subscription;
    private TextView tev_pre_subscription;
    private TextView tev_no_subscription;

    private TextView tev_volume;
    private TextView tev_macd;
    private TextView tev_dma;
    private TextView tev_wr;
    private TextView tev_boll;
    private TextView tev_kdj;
    private TextView tev_vr;
    private TextView tev_rsi;
    private TextView tev_cr;
    private TextView tev_dmi;
    private TextView tev_bias;
    private TextView tev_obv;
    private TextView tev_bbi;
    private TextView tev_amo;
    private TextView tev_cci;
    private TextView tev_mtm;
    private TextView tev_roc;
    private TextView tev_brar;
    private TextView tev_nvipvi;
    private TextView tev_psy;

    private TechChartType.KType kType;

    //复权，默认为0
    private int subscription;

    public KChoseView(Context context) {
        super(context);
        init(context);
    }

    public KChoseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public KChoseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.setOrientation(VERTICAL);
        View view = LayoutInflater.from(context).inflate(R.layout.graphic_type_list,this);

        tev_subscription = (TextView) view.findViewById(R.id.tev_subscription_k);
        tev_pre_subscription = (TextView) view.findViewById(R.id.tev_pre_subscription_k);
        tev_no_subscription = (TextView) view.findViewById(R.id.tev_no_subscription_k);
        tev_subscription.setTextColor(ThemeColor.blackWhite());
        tev_pre_subscription.setTextColor(ThemeColor.blackWhite());
        tev_no_subscription.setTextColor(ThemeColor.blackWhite());

        tev_volume = (TextView) view.findViewById(R.id.tev_volume_k);
        tev_macd = (TextView) view.findViewById(R.id.tev_macd_k);
        tev_dma = (TextView) view.findViewById(R.id.tev_dma_k);
        tev_wr = (TextView) view.findViewById(R.id.tev_wr_k);
        tev_boll = (TextView) view.findViewById(R.id.tev_boll_k);
        tev_kdj = (TextView) view.findViewById(R.id.tev_kdj_k);
        tev_vr = (TextView) view.findViewById(R.id.tev_vr_k);
        tev_rsi = (TextView) view.findViewById(R.id.tev_rsi_k);
        tev_cr = (TextView) view.findViewById(R.id.tev_cr_k);
        tev_dmi = (TextView) view.findViewById(R.id.tev_dmi_k);
        tev_bias = (TextView) view.findViewById(R.id.tev_bias_k);
        tev_obv = (TextView) view.findViewById(R.id.tev_obv_k);
        tev_bbi = (TextView) view.findViewById(R.id.tev_bbi_k);
        tev_amo = (TextView) view.findViewById(R.id.tev_amo_k);
        tev_cci = (TextView) view.findViewById(R.id.tev_cci_k);
        tev_mtm = (TextView) view.findViewById(R.id.tev_mtm_k);
        tev_roc = (TextView) view.findViewById(R.id.tev_roc_k);
        tev_brar = (TextView) view.findViewById(R.id.tev_brar_k);
        tev_nvipvi = (TextView) view.findViewById(R.id.tev_nvipvi_k);
        tev_psy = (TextView) view.findViewById(R.id.tev_psy_k);
        tev_volume.setTextColor(Color.BLUE);
        tev_subscription.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(null,0,v);
            }
        });
        tev_pre_subscription.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(null,1,v);
            }
        });
        tev_no_subscription.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(null,2,v);
            }
        });

        tev_volume.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(TechChartType.KType.VOLUME,-1,v);
            }
        });
        tev_macd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(TechChartType.KType.MACD,-1,v);
            }
        });
        tev_dma.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(TechChartType.KType.DMA,-1,v);
            }
        });
        tev_wr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(TechChartType.KType.WR,-1,v);
            }
        });
        tev_boll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(TechChartType.KType.BOLL,-1,v);
            }
        });
        tev_kdj.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(TechChartType.KType.KDJ,-1,v);
            }
        });
        tev_cr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(TechChartType.KType.CR,-1,v);
            }
        });
        tev_rsi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(TechChartType.KType.RSI,-1,v);
            }
        });
        tev_vr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(TechChartType.KType.VR,-1,v);
            }
        });
        tev_dmi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(TechChartType.KType.DMI,-1,v);
            }
        });
        tev_bias.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(TechChartType.KType.BIAS,-1,v);
            }
        });
        tev_obv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(TechChartType.KType.OBV,-1,v);
            }
        });
        tev_bbi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(TechChartType.KType.BBI,-1,v);
            }
        });
        tev_amo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(TechChartType.KType.AMO,-1,v);
            }
        });
        tev_cci.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(TechChartType.KType.CCI,-1,v);
            }
        });
        tev_mtm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(TechChartType.KType.MTM,-1,v);
            }
        });
        tev_roc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(TechChartType.KType.ROC,-1,v);
            }
        });
        tev_brar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(TechChartType.KType.BRAR,-1,v);
            }
        });
        tev_nvipvi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(TechChartType.KType.NVIPVI,-1,v);
            }
        });
        tev_psy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dealWith(TechChartType.KType.PSY,-1,v);
            }
        });
        updateView(true);
    }

    private void dealWith(TechChartType.KType kType, int i, View view,boolean ...inner) {
        //kType不变时，直接return
        if( null!= kType && this.kType == kType){
            return;
        }

        if(choseListener != null){
            if(inner == null || inner.length == 0){
                if(kType != null){
                    KSetting.setKType(kType);
                }
                choseListener.onChanged(kType,i);
            } else if(inner[0]){
                if(kType != null){
                    KSetting.setKType(kType);
                }
                choseListener.onChanged(kType,i);
            }
        }

        if( null==kType ){
            //i值赋给复权类型
            subscription = i;
            KSetting.setRightType(subscription);
            tev_subscription.setTextColor(ThemeColor.blackWhite());
            tev_pre_subscription.setTextColor(ThemeColor.blackWhite());
            tev_no_subscription.setTextColor(ThemeColor.blackWhite());
            ((TextView)view).setTextColor(Color.BLUE);
            return;
        }
        this.kType = kType;
        tev_volume.setTextColor(ThemeColor.blackWhite());
        tev_macd.setTextColor(ThemeColor.blackWhite());
        tev_dma.setTextColor(ThemeColor.blackWhite());
        tev_wr.setTextColor(ThemeColor.blackWhite());
        tev_boll.setTextColor(ThemeColor.blackWhite());
        tev_kdj.setTextColor(ThemeColor.blackWhite());
        tev_cr.setTextColor(ThemeColor.blackWhite());
        tev_rsi.setTextColor(ThemeColor.blackWhite());
        tev_vr.setTextColor(ThemeColor.blackWhite());
        tev_dmi.setTextColor(ThemeColor.blackWhite());
        tev_bias.setTextColor(ThemeColor.blackWhite());
        tev_obv.setTextColor(ThemeColor.blackWhite());
        tev_bbi.setTextColor(ThemeColor.blackWhite());
        tev_amo.setTextColor(ThemeColor.blackWhite());
        tev_cci.setTextColor(ThemeColor.blackWhite());
        tev_mtm.setTextColor(ThemeColor.blackWhite());
        tev_roc.setTextColor(ThemeColor.blackWhite());
        tev_brar.setTextColor(ThemeColor.blackWhite());
        tev_nvipvi.setTextColor(ThemeColor.blackWhite());
        tev_psy.setTextColor(ThemeColor.blackWhite());

        ((TextView)view).setTextColor(Color.BLUE);
    }

    /**
     * 初始化设置选择的复权类型和macd类型
     * @param inner
     */
    public void updateView(boolean inner){
        View view1 = null;
        TechChartType.KType kType;
        switch (kType = KSetting.getKTypeAsType()){
            case VOLUME:
                view1 = tev_volume;break;
            case MACD:
                view1 = tev_macd;break;
            case DMA:
                view1 = tev_dma;break;
            case WR:
                view1 = tev_wr;break;
            case BOLL:
                view1 = tev_boll;break;
            case KDJ:
                view1 = tev_kdj;break;
            case CR:
                view1 = tev_cr ;break;
            case RSI:
                view1 = tev_rsi;break;
            case VR:
                view1 = tev_vr;break;
            case DMI:
                view1 = tev_dmi;break;
            case BIAS:
                view1 = tev_bias;break;
            case OBV:
                view1 = tev_obv ;break;
            case BBI:
                view1 = tev_bbi;break;
            case AMO:
                view1 = tev_amo;break;
            case CCI:
                view1 = tev_cci;break;
            case MTM:
                view1 = tev_mtm;break;
            case ROC:
                view1 = tev_roc;break;
            case BRAR:
                view1 = tev_brar ;break;
            case NVIPVI:
                view1 = tev_nvipvi;break;
            case PSY:
                view1 = tev_psy;break;
        }
        dealWith(kType,-1,view1,inner);

        //初始化复权类型
        switch(KSetting.getKRightSubType()){
            case 0:view1 =  tev_subscription;break;
            case 1:view1 =  tev_pre_subscription;break;
            case 2:view1 = tev_no_subscription;break;
            default:
                break;
        }
        dealWith(null,KSetting.getKRightSubType(),view1,inner);
    }
    private ChoseListener choseListener;

    public void setChoseListener(ChoseListener choseListener) {
        this.choseListener = choseListener;
    }

    public interface ChoseListener{
       void onChanged(TechChartType.KType kType, int subscriptionType);
    }
}
