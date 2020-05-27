package com.cvicse.stock.chart.helper.new_helper;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.haitong.R;
import com.cvicse.stock.chart.data.MinuteEntity;
import com.cvicse.stock.chart.listener.OnChartListener;
import com.cvicse.stock.chart.theme.ThemeColor;
import com.cvicse.stock.chart.theme.ThemeManager;
import com.cvicse.stock.chart.ui.new_ui.StockMinuteChartNew;
import com.mitake.core.QuoteItem;
import com.stock.config.MSetting;

/**  分时图
 * Created by tang_xqing on 2018/3/19
 * 包括切换横屏的操作封装类( 指标数据 )
 */
public class MLineMiddleHelper {

    private Button btnSubChartType;  // 指标按钮
    private Button btnSettingValue;// 切换横屏按钮

    private TextView subText;
    private TextView subTextL;
    private TextView subTextM;
    private TextView subTextS;

    private OnChartListener onChartListener;

    private StockMinuteChartNew.MSubType type;

    private QuoteItem quoteItem;

    public void setOnChartListener(OnChartListener onChartListener) {
        this.onChartListener = onChartListener;
    }
    public MLineMiddleHelper(RelativeLayout relativeLayout) {
        btnSubChartType  = (Button) relativeLayout.findViewById(R.id.btn_sub_chart_type);
        btnSettingValue  = (Button) relativeLayout.findViewById(R.id.btn_setting_value);

        subText  = (TextView) relativeLayout.findViewById(R.id.sub_text);
        subTextL  = (TextView) relativeLayout.findViewById(R.id.sub_text_l);
        subTextM  = (TextView) relativeLayout.findViewById(R.id.sub_text_m);
        subTextS  = (TextView) relativeLayout.findViewById(R.id.sub_text_s);
        subText.setTextColor(ThemeColor.blackWhite());

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
        this.type = MSetting.getMTypeAsType();
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

    /**
     * 指标变动
     */
    public void setMSubType(StockMinuteChartNew.MSubType type){
        if( null == type){
            return;
        }
        btnSubChartType.setText(type.toString());
        this.type = type;
    }

    public void setMSubData(MinuteEntity mLineEntity){
        setDefault();
        if(null == mLineEntity){
            return;
        }
        subTextL.setTextColor(ThemeManager.colorWhiteBlack());

        switch (type){
            case VOLUME:
                updateVol(mLineEntity);
                break;
            case DDX:
                updateDDX(mLineEntity);
                break;
            case DDY:
                updateDDY(mLineEntity);
                break;
            case DDZ:
                updateDDZ(mLineEntity);
                break;
            case BBD:
                updateBBD(mLineEntity);
                break;
            case RATIOBS:
                updateRatioBS(mLineEntity);
                break;
            case CAPTIALGAME:
                updateCaptialGame(mLineEntity);
                break;
            case ORDERNUM:
                updateOrdernum(mLineEntity);
                break;
            case BIGNETVOLUME:
                updateBigNetVolume(mLineEntity);
                break;
            case VOLRatio:// 量比
                updateAmount(mLineEntity);
                break;
        }
    }
    //大单净量
    private void updateBigNetVolume(MinuteEntity mLineEntity) {
        subText.setVisibility(View.VISIBLE);
        subText.setTextColor(ThemeManager.colorWhiteBlack());
        subTextL.setVisibility(View.GONE);
        subTextM.setVisibility(View.GONE);
        subTextS.setVisibility(View.GONE);
        subText.setText(null == mLineEntity ? "--": mLineEntity.bigNetVolume+"");
    }
    //量比
    private void updateAmount(MinuteEntity mLineEntity) {
        subText.setVisibility(View.VISIBLE);
        subText.setTextColor(ThemeManager.colorWhiteBlack());
        subTextL.setVisibility(View.GONE);
        subTextM.setVisibility(View.GONE);
        subTextS.setVisibility(View.GONE);
        subText.setText(null == mLineEntity ? "--": mLineEntity.volRatio+"");
    }

    /**
     * 成交单数
     * @param mLineEntity
     */
    private void updateOrdernum(MinuteEntity mLineEntity) {
        subText.setTextColor(ThemeManager.colorWhiteBlack());
        subTextL.setTextColor(ThemeManager.colorYellow());
        subTextM.setTextColor(ThemeManager.colorFuchsia());
        subTextS.setTextColor(ThemeManager.colorGreen());

        subText.setVisibility(View.VISIBLE);
        subTextL.setVisibility(View.VISIBLE);
        subTextM.setVisibility(View.VISIBLE);
        subTextS.setVisibility(View.VISIBLE);

        subText.setText("超:"+(null == mLineEntity ? "--": mLineEntity.largeTradeNum));
        subTextL.setText("大:"+(null == mLineEntity ? "--": mLineEntity.bigTradeNum));
        subTextM.setText("中:"+(null == mLineEntity ? "--":  mLineEntity.midTradeNum));
        subTextS.setText("小："+(null == mLineEntity ? "--": mLineEntity.smallTradeNum));
    }

    private void updateVol(MinuteEntity mLineEntity) {
        subText.setTextColor(ThemeManager.colorWhiteBlack());
        subText.setVisibility(View.VISIBLE);
        subTextL.setVisibility(View.GONE);
        subTextM.setVisibility(View.GONE);
        subTextS.setVisibility(View.GONE);

        subText.setText(null == mLineEntity ? "--": String.valueOf(mLineEntity.volume));
    }

    private void updateDDX(MinuteEntity mLineEntity){
        subText.setVisibility(View.VISIBLE);
        subTextL.setVisibility(View.GONE);
        subTextM.setVisibility(View.GONE);
        subTextS.setVisibility(View.GONE);
        subText.setText(null == mLineEntity ? "--": mLineEntity.ddx+"");
    }

    private void updateDDY(MinuteEntity mLineEntity){
        subText.setVisibility(View.VISIBLE);
        subText.setTextColor(ThemeManager.colorWhiteBlack());
        subTextL.setVisibility(View.GONE);
        subTextM.setVisibility(View.GONE);
        subTextS.setVisibility(View.GONE);
        subText.setText(null == mLineEntity ? "--": mLineEntity.ddy+"");
    }

    private void updateDDZ(MinuteEntity mLineEntity){
        subText.setVisibility(View.VISIBLE);
        subText.setTextColor(ThemeManager.colorWhiteBlack());
        subTextL.setVisibility(View.GONE);
        subTextM.setVisibility(View.GONE);
        subTextS.setVisibility(View.GONE);
        subText.setText(null == mLineEntity ? "--": mLineEntity.ddz+"");

    }

    private void updateBBD(MinuteEntity mLineEntity){
        subText.setVisibility(View.VISIBLE);
        subText.setTextColor(ThemeManager.colorWhiteBlack());
        subTextL.setVisibility(View.GONE);
        subTextM.setVisibility(View.GONE);
        subTextS.setVisibility(View.GONE);
        subText.setText(null == mLineEntity ? "--": mLineEntity.bbd+"");
    }

    private void updateRatioBS(MinuteEntity mLineEntity){
        subText.setVisibility(View.VISIBLE);

        subText.setTextColor(ThemeManager.colorWhiteBlack());
        subTextL.setVisibility(View.GONE);
        subTextM.setVisibility(View.GONE);
        subTextS.setVisibility(View.GONE);
        subText.setText(null == mLineEntity ? "--": mLineEntity.ratioBs+"");
    }

    /**
     * 资金博弈
     */
    private void updateCaptialGame(MinuteEntity mLineEntity){
        subText.setTextColor(ThemeManager.colorWhiteBlack());
        subTextL.setTextColor(ThemeManager.colorYellow());
        subTextM.setTextColor(ThemeManager.colorFuchsia());
        subTextS.setTextColor(ThemeManager.colorGreen());

        subText.setVisibility(View.VISIBLE);
        subTextL.setVisibility(View.VISIBLE);
        subTextM.setVisibility(View.VISIBLE);
        subTextS.setVisibility(View.VISIBLE);

        subText.setText("超:"+(null == mLineEntity ? "--": mLineEntity.largeMoneyInflow));
        subTextL.setText("大:"+(null == mLineEntity ? "--": mLineEntity.bigMoneyInflow));
        subTextM.setText("中:"+(null == mLineEntity ? "--":  mLineEntity.midMoneyInflow));
        subTextS.setText("小："+(null == mLineEntity ? "--": mLineEntity.smallMoneyInflow));
    }

    /**
     * 默认设置
     */
    private void setDefault(){
        subText.setVisibility(View.VISIBLE);
        subTextL.setVisibility(View.GONE);
        subTextM.setVisibility(View.GONE);
        subTextS.setVisibility(View.GONE);

        subText.setText("");
        subTextL.setText("");
        subTextM.setText("");
        subTextS.setText("");

    }
}
