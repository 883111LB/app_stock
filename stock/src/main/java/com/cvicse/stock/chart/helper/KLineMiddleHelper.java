package com.cvicse.stock.chart.helper;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.haitong.R;
import com.cvicse.stock.chart.data.KLineEntity;
import com.cvicse.stock.chart.data.TechChartType;
import com.cvicse.stock.chart.listener.OnChartListener;
import com.cvicse.stock.chart.theme.ThemeColor;
import com.cvicse.stock.chart.theme.ThemeManager;
import com.mitake.core.QuoteItem;
import com.stock.config.KSetting;

/**
 * Created by liu_zlu on 2017/3/26 17:52
 * 包括切换横屏的操作封装类( 指标数据 )
 */
public class KLineMiddleHelper {
    private Button btnSubChartType;  // 指标按钮
    private Button btnSettingValue; // 切换横屏按钮
    private TextView subText;
    private TextView subTextMa1;
    private TextView subTextMa2;
    private TextView subTextMa3;
    private TextView subTextMa4;
    private TextView subTextMa5;

    private OnChartListener onChartListener;
    private TechChartType.KType type;
    private QuoteItem quoteItem;

    public void setOnChartListener(OnChartListener onChartListener) {
        this.onChartListener = onChartListener;
    }
    public KLineMiddleHelper(RelativeLayout relativeLayout) {
        btnSubChartType  = (Button) relativeLayout.findViewById(R.id.btn_sub_chart_type);

        btnSettingValue  = (Button) relativeLayout.findViewById(R.id.btn_setting_value);

        subText  = (TextView) relativeLayout.findViewById(R.id.sub_text);
        subText.setTextColor(ThemeColor.blackWhite());
        subTextMa1  = (TextView) relativeLayout.findViewById(R.id.sub_text_ma_1);
        subTextMa2  = (TextView) relativeLayout.findViewById(R.id.sub_text_ma_2);
        subTextMa3  = (TextView) relativeLayout.findViewById(R.id.sub_text_ma_3);
        subTextMa4  = (TextView) relativeLayout.findViewById(R.id.sub_text_ma_4);
        subTextMa5  = (TextView) relativeLayout.findViewById(R.id.sub_text_ma_5);
        initViews();
    }

    private void initViews(){
        btnSettingValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( null!= onChartListener){
                    onChartListener.onChangeLanded();
                }
            }
        });

        btnSubChartType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( null!= onChartListener){
                    onChartListener.onKTypeClick();
                }
            }
        });
        this.type = KSetting.getKTypeAsType();
        btnSubChartType.setText(type.toString());
    }

    public void setQuoteItem(QuoteItem quoteItem) {
        this.quoteItem = quoteItem;
    }

    public void setLand(boolean isLand){
        btnSubChartType.setVisibility(View.VISIBLE);
        //竖屏状态下
        if(!isLand){
            btnSettingValue.setVisibility(View.VISIBLE);
            btnSettingValue.setText("切换横屏");
        }
    }
    public void setKType(TechChartType.KType type){
        if( null== type){
            return;
        }
        btnSubChartType.setText(type.toString());
        this.type = type;
    }

    public void setData(KLineEntity kLineEntity){
        if( null==kLineEntity ){
            return;
        }
        subTextMa1.setTextColor(ThemeManager.colorWhiteBlack());
        switch (type){
            case VOLUME:
                updateVol(kLineEntity);
                break;
            case MACD:
                updateMacd(kLineEntity);
                break;
            case DMA:
                updateDMA(kLineEntity);
                break;
            case WR:
                updateWR(kLineEntity);
                break;
            case BOLL:
                updateBOLL(kLineEntity);
                break;
            case KDJ:
                updateKDJ(kLineEntity);
                break;
            case RSI:
                updateRSI(kLineEntity);
                break;
            case VR:
                updateVR(kLineEntity);
                break;
            case CR:
                updateCR(kLineEntity);
                break;
            case DMI:
                updateDMI(kLineEntity);
                break;
            case BIAS:
                updateBIAS(kLineEntity);
                break;
            case OBV:
                updateOBV(kLineEntity);
                break;
            case BBI:
                updateBBI(kLineEntity);
                break;
            case AMO:
                updateAMO(kLineEntity);
                break;
            case CCI:
                updateCCI(kLineEntity);
                break;
            case MTM:
                updateMTM(kLineEntity);
                break;
            case ROC:
                updateROC(kLineEntity);
                break;
            case BRAR:
                updateBRAR(kLineEntity);
                break;
            case NVIPVI:
                updateNVIPVI(kLineEntity);
                break;
            case PSY:
                updatePSY(kLineEntity);
                break;
        }
    }

    private void updateCCI(KLineEntity kLineEntity) {
        subTextMa2.setVisibility(View.GONE);
        subTextMa3.setVisibility(View.GONE);
        subTextMa4.setVisibility(View.GONE);
        subTextMa5.setVisibility(View.GONE);
        subTextMa1.setText("CCI:"+String.format("%.2f",kLineEntity.cci));
    }

    private void updatePSY(KLineEntity kLineEntity) {
        subTextMa2.setVisibility(View.VISIBLE);
        subTextMa2.setTextColor(ThemeManager.colorYellow());
        subTextMa3.setVisibility(View.GONE);
        subTextMa4.setVisibility(View.GONE);
        subTextMa5.setVisibility(View.GONE);
        subTextMa1.setText("PSY:"+String.format("%.2f",kLineEntity.psy));
        subTextMa2.setText("MAPSY:"+String.format("%.2f",kLineEntity.psyMA));
    }

    private void updateNVIPVI(KLineEntity kLineEntity) {
        subTextMa2.setVisibility(View.VISIBLE);
        subTextMa2.setTextColor(ThemeManager.colorYellow());
        subTextMa3.setVisibility(View.GONE);
        subTextMa4.setVisibility(View.GONE);
        subTextMa5.setVisibility(View.GONE);
        subTextMa1.setText("PVI:"+String.format("%.2f",kLineEntity.pvi));
        subTextMa2.setText("NVI:"+String.format("%.2f",kLineEntity.nvi));
    }

    private void updateBRAR(KLineEntity kLineEntity) {
        subTextMa2.setVisibility(View.VISIBLE);
        subTextMa2.setTextColor(ThemeManager.colorYellow());
        subTextMa3.setVisibility(View.GONE);
        subTextMa4.setVisibility(View.GONE);
        subTextMa5.setVisibility(View.GONE);
        subTextMa1.setText("BR:"+String.format("%.2f",kLineEntity.br));
        subTextMa2.setText("AR:"+String.format("%.2f",kLineEntity.ar));
    }

    private void updateROC(KLineEntity kLineEntity) {
        subTextMa2.setVisibility(View.VISIBLE);
        subTextMa2.setTextColor(ThemeManager.colorYellow());
        subTextMa3.setVisibility(View.GONE);
        subTextMa4.setVisibility(View.GONE);
        subTextMa5.setVisibility(View.GONE);
        subTextMa1.setText("ROC:"+String.format("%.2f",kLineEntity.roc));
        subTextMa2.setText("ROCMA:"+String.format("%.2f",kLineEntity.rocMA));
    }

    private void updateMTM(KLineEntity kLineEntity) {
        subTextMa2.setVisibility(View.VISIBLE);
        subTextMa2.setTextColor(ThemeManager.colorYellow());
        subTextMa3.setVisibility(View.GONE);
        subTextMa4.setVisibility(View.GONE);
        subTextMa5.setVisibility(View.GONE);
        subTextMa1.setText("MTM:"+String.format("%.2f",kLineEntity.mtm));
        subTextMa2.setText("MTMMA:"+String.format("%.2f",kLineEntity.mtmMA));
    }

    private void updateAMO(KLineEntity kLineEntity) {
        subTextMa2.setVisibility(View.VISIBLE);
        subTextMa2.setTextColor(ThemeManager.colorYellow());
        subTextMa3.setVisibility(View.VISIBLE);
        subTextMa3.setTextColor(ThemeManager.colorFuchsia());
        subTextMa4.setVisibility(View.VISIBLE);
        subTextMa4.setTextColor(ThemeManager.colorGreen());
        subTextMa5.setVisibility(View.GONE);
        subTextMa1.setText(kLineEntity.tranPriceS);
        subTextMa2.setText("MA1:"+String.format("%.2f",kLineEntity.amoM1));
        subTextMa3.setText("MA2:"+String.format("%.2f",kLineEntity.amoM2));
        subTextMa4.setText("MA3:"+String.format("%.2f",kLineEntity.amoM3));
    }

    private void updateBBI(KLineEntity kLineEntity) {
        subTextMa2.setVisibility(View.GONE);
        subTextMa3.setVisibility(View.GONE);
        subTextMa4.setVisibility(View.GONE);
        subTextMa5.setVisibility(View.GONE);
        subTextMa1.setText("BBI:"+String.format("%.2f",kLineEntity.bbi));
    }

    private void updateOBV(KLineEntity kLineEntity) {
        subTextMa2.setVisibility(View.GONE);
        subTextMa3.setVisibility(View.GONE);
        subTextMa4.setVisibility(View.GONE);
        subTextMa5.setVisibility(View.GONE);
        subTextMa1.setText("OBV:"+String.format("%.2f",kLineEntity.obv));
    }

    private void updateBIAS(KLineEntity kLineEntity) {
        subTextMa2.setVisibility(View.VISIBLE);
        subTextMa2.setTextColor(ThemeManager.colorYellow());
        subTextMa3.setVisibility(View.VISIBLE);
        subTextMa3.setTextColor(ThemeManager.colorFuchsia());
        subTextMa4.setVisibility(View.GONE);
        subTextMa5.setVisibility(View.GONE);
        subTextMa1.setText("BIAS1:"+String.format("%.2f",kLineEntity.bias6));
        subTextMa2.setText("BIAS2:"+String.format("%.2f",kLineEntity.bias12));
        subTextMa3.setText("BIAS3:"+String.format("%.2f",kLineEntity.bias24));
    }

    private void updateDMI(KLineEntity kLineEntity) {
        subTextMa2.setVisibility(View.VISIBLE);
        subTextMa2.setTextColor(ThemeManager.colorYellow());
        subTextMa3.setVisibility(View.VISIBLE);
        subTextMa3.setTextColor(ThemeManager.colorFuchsia());
        subTextMa4.setVisibility(View.VISIBLE);
        subTextMa4.setTextColor(ThemeManager.colorGreen());
        subTextMa5.setVisibility(View.GONE);
        subTextMa1.setText("PDI"+String.format("%.2f",kLineEntity.pdi));
        subTextMa2.setText("-MDI:"+String.format("%.2f",kLineEntity.mdi));
        subTextMa3.setText("ADX:"+String.format("%.2f",kLineEntity.adx));
        subTextMa4.setText("ADXR:"+String.format("%.2f",kLineEntity.adxr));
    }

    private void updateVol(KLineEntity kLineEntity) {
        subTextMa2.setVisibility(View.GONE);
        subTextMa3.setVisibility(View.GONE);
        subTextMa4.setVisibility(View.GONE);
        subTextMa5.setVisibility(View.GONE);
        subTextMa1.setText(kLineEntity.volumeStr);
    }

    private void updateMacd(KLineEntity kLineEntity) {
        subTextMa2.setVisibility(View.VISIBLE);
        subTextMa2.setTextColor(ThemeManager.colorYellow());
        subTextMa3.setVisibility(View.VISIBLE);
        subTextMa3.setTextColor(ThemeManager.colorFuchsia());

        subTextMa4.setVisibility(View.GONE);
        subTextMa5.setVisibility(View.GONE);
        subTextMa1.setText("DIF:"+String.format("%.2f",kLineEntity.dif));
        subTextMa2.setText("DEA:"+String.format("%.2f",kLineEntity.dea));
        subTextMa3.setText("M:"+String.format("%.2f",kLineEntity.macd));
    }

    private void updateKDJ(KLineEntity kLineEntity) {
        subTextMa2.setVisibility(View.VISIBLE);
        subTextMa2.setTextColor(ThemeManager.colorYellow());
        subTextMa3.setVisibility(View.VISIBLE);
        subTextMa3.setTextColor(ThemeManager.colorFuchsia());

        subTextMa4.setVisibility(View.GONE);
        subTextMa5.setVisibility(View.GONE);
        subTextMa1.setText("K9:"+String.format("%.2f",kLineEntity.k));
        subTextMa2.setText("D9:"+String.format("%.2f",kLineEntity.d));
        subTextMa3.setText("J9:"+String.format("%.2f",kLineEntity.j));
    }

    private void updateDMA(KLineEntity kLineEntity) {
        subTextMa2.setVisibility(View.VISIBLE);
        subTextMa2.setTextColor(ThemeManager.colorYellow());

        subTextMa3.setVisibility(View.GONE);
        subTextMa4.setVisibility(View.GONE);
        subTextMa5.setVisibility(View.GONE);
        subTextMa1.setText("DIF10:"+String.format("%.2f",kLineEntity.dif10));
        subTextMa2.setText("DIFMA50:"+String.format("%.2f",kLineEntity.difma50));
    }

    private void updateBOLL(KLineEntity kLineEntity) {
        subTextMa2.setVisibility(View.VISIBLE);
        subTextMa2.setTextColor(ThemeManager.colorYellow());
        subTextMa3.setVisibility(View.VISIBLE);
        subTextMa3.setTextColor(ThemeManager.colorGreen());

        subTextMa4.setVisibility(View.GONE);
        subTextMa5.setVisibility(View.GONE);
        subTextMa1.setText("MID:"+String.format("%.2f",kLineEntity.mb));
        subTextMa2.setText("UP:"+String.format("%.2f",kLineEntity.up));
        subTextMa3.setText("DN:"+String.format("%.2f",kLineEntity.dn));
    }

    private void updateRSI(KLineEntity kLineEntity) {
        subTextMa2.setVisibility(View.VISIBLE);
        subTextMa2.setTextColor(ThemeManager.colorYellow());
        subTextMa3.setVisibility(View.VISIBLE);
        subTextMa3.setTextColor(ThemeManager.colorGreen());

        subTextMa4.setVisibility(View.GONE);
        subTextMa5.setVisibility(View.GONE);
        subTextMa1.setText("RSI6:"+String.format("%.2f",kLineEntity.rsi1));
        subTextMa2.setText("RSI12:"+String.format("%.2f",kLineEntity.rsi2));
        subTextMa3.setText("RSI24:"+String.format("%.2f",kLineEntity.rsi3));
    }

    private void updateWR(KLineEntity kLineEntity) {
        subTextMa2.setVisibility(View.VISIBLE);
        subTextMa2.setTextColor(ThemeManager.colorYellow());
        subTextMa3.setVisibility(View.GONE);

        subTextMa4.setVisibility(View.GONE);
        subTextMa5.setVisibility(View.GONE);
        subTextMa1.setText("WR10:"+String.format("%.2f",kLineEntity.wr10));
        subTextMa2.setText("WR5:"+String.format("%.2f",kLineEntity.wr5));
    }

    private void updateVR(KLineEntity kLineEntity) {
        subTextMa2.setVisibility(View.VISIBLE);
        subTextMa2.setTextColor(Color.YELLOW);
        subTextMa3.setVisibility(View.GONE);

        subTextMa4.setVisibility(View.GONE);
        subTextMa5.setVisibility(View.GONE);
        subTextMa1.setText("VR13:"+String.format("%.2f",kLineEntity.vr13));
        subTextMa2.setText("VR26:"+String.format("%.2f",kLineEntity.vr26));
    }

    private void updateCR(KLineEntity kLineEntity) {
        subTextMa2.setVisibility(View.VISIBLE);
        subTextMa2.setTextColor(ThemeManager.colorRed());
        subTextMa3.setVisibility(View.VISIBLE);
        subTextMa3.setTextColor(ThemeManager.colorGreen());
        subTextMa4.setVisibility(View.VISIBLE);
        subTextMa4.setTextColor(ThemeManager.colorYellow());
        subTextMa5.setVisibility(View.VISIBLE);
        subTextMa5.setTextColor(ThemeManager.colorFuchsia());
        subTextMa1.setText("CR 26:"+String.format("%.2f",kLineEntity.cr));
        subTextMa2.setText("MA 10:"+String.format("%.2f",kLineEntity.crMa1));
        subTextMa3.setText("MA 20:"+String.format("%.2f",kLineEntity.crMa2));
        subTextMa4.setText("MA 40:"+String.format("%.2f",kLineEntity.crMa3));
        subTextMa5.setText("MA 62:"+String.format("%.2f",kLineEntity.crMa4));
    }
}
