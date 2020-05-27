package com.cvicse.stock.personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cvicse.base.ui.BaseApplication;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;


/**
 * Created by ruan_ytai on 17-1-4.
 */

public class KLineSetActivity extends PBaseActivity implements View.OnClickListener{

    public static final String KEY_SUB_TYPE = "sub";
    public static final String KEY_INDEX_TYPE = "index";

    private ImageView imgNoSubscription;
    private ImageView imgFront;
    private ImageView imgBehind;

    private ImageView imgVolume;
    private ImageView imgMacd;
    private ImageView imgDmi;
    private ImageView imgWr;
    private ImageView imgBoll;
    private ImageView imgKdj;
    private ImageView imgObv;
    private ImageView imgRsi;
    private ImageView imgSar;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_klineset;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("K线设置");

        imgNoSubscription = (ImageView) findViewById(R.id.img_nosubscription);
        imgFront = (ImageView) findViewById(R.id.img_front);
        imgBehind = (ImageView) findViewById(R.id.img_behind);

        imgVolume = (ImageView) findViewById(R.id.img_volume);
        imgMacd = (ImageView) findViewById(R.id.img_macd);
        imgDmi = (ImageView) findViewById(R.id.img_dmi);
        imgWr = (ImageView) findViewById(R.id.img_wr);
        imgBoll = (ImageView) findViewById(R.id.img_boll);
        imgKdj = (ImageView) findViewById(R.id.img_kdj);
        imgObv = (ImageView) findViewById(R.id.img_obv);
        imgRsi = (ImageView) findViewById(R.id.img_rsi);
        imgSar = (ImageView) findViewById(R.id.img_sar);

        findViewById(R.id.rel_nosubscription).setOnClickListener(this);
        findViewById(R.id.rel_frontsubscription).setOnClickListener(this);
        findViewById(R.id.rel_behindsubscription).setOnClickListener(this);

        findViewById(R.id.rel_volume).setOnClickListener(this);
        findViewById(R.id.rel_macd).setOnClickListener(this);
        findViewById(R.id.rel_dmi).setOnClickListener(this);
        findViewById(R.id.rel_wr).setOnClickListener(this);
        findViewById(R.id.rel_boll).setOnClickListener(this);
        findViewById(R.id.rel_kdj).setOnClickListener(this);
        findViewById(R.id.rel_obv).setOnClickListener(this);
        findViewById(R.id.rel_rsi).setOnClickListener(this);
        findViewById(R.id.rel_sar).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.rel_nosubscription:
                updateSubUI(R.id.rel_nosubscription);
                break;

            case R.id.rel_frontsubscription:
                updateSubUI(R.id.rel_frontsubscription);
                break;

            case R.id.rel_behindsubscription:
                updateSubUI(R.id.rel_behindsubscription);
                break;

            case R.id.rel_volume:
                updateIndexUI(R.id.rel_volume);
                break;

            case R.id.rel_macd:
                updateIndexUI(R.id.rel_macd);
                break;

            case R.id.rel_dmi:
                updateIndexUI(R.id.rel_dmi);
                break;
            case R.id.rel_wr:
                updateIndexUI(R.id.rel_wr);
                break;
            case R.id.rel_boll:
                updateIndexUI(R.id.rel_boll);
                break;
            case R.id.rel_kdj:
                updateIndexUI(R.id.rel_kdj);
                break;
            case R.id.rel_obv:
                updateIndexUI(R.id.rel_obv);
                break;
            case R.id.rel_rsi:
                updateIndexUI(R.id.rel_rsi);
                break;
            case R.id.rel_sar:
                updateIndexUI(R.id.rel_sar);
                break;
        }
    }

    private void updateSubUI(int id) {
        imgNoSubscription.setVisibility(id == R.id.rel_nosubscription ? View.VISIBLE : View.GONE);
        imgFront.setVisibility(id == R.id.rel_frontsubscription ? View.VISIBLE : View.GONE);
        imgBehind.setVisibility(id == R.id.rel_behindsubscription ? View.VISIBLE : View.GONE);
    }

    private void updateIndexUI(int id){

        imgVolume.setVisibility(id == R.id.rel_volume ? View.VISIBLE : View.GONE);
        imgMacd.setVisibility(id == R.id.rel_macd ? View.VISIBLE : View.GONE);
        imgDmi.setVisibility(id == R.id.rel_dmi ? View.VISIBLE : View.GONE);
        imgWr.setVisibility(id == R.id.rel_wr ? View.VISIBLE : View.GONE);
        imgBoll.setVisibility(id == R.id.rel_boll ? View.VISIBLE : View.GONE);
        imgKdj.setVisibility(id == R.id.rel_kdj ? View.VISIBLE : View.GONE);
        imgObv.setVisibility(id == R.id.rel_obv ? View.VISIBLE : View.GONE);
        imgRsi.setVisibility(id == R.id.rel_rsi? View.VISIBLE : View.GONE);
        imgSar.setVisibility(id == R.id.rel_sar ? View.VISIBLE : View.GONE);

    }

    @Override
    public void onBackPressed() {
        storeChoose();//存下选择的两种类型
        finish();
    }

    private String checkVisible(String type)
    {
        if(type.equals(KEY_SUB_TYPE)){
            if(imgNoSubscription.getVisibility() == View.VISIBLE) {
                return "不复权";
            }
            if(imgFront.getVisibility() == View.VISIBLE) {
                return "前复权";
            }
            if(imgBehind.getVisibility() == View.VISIBLE) {
                return "后复权";
            }
        }else if(type.equals(KEY_INDEX_TYPE)){
            if(imgVolume.getVisibility() == View.VISIBLE) {
                return "成交量";
            }
            if(imgMacd.getVisibility() == View.VISIBLE) {
                return "MACD";
            }
            if(imgDmi.getVisibility() == View.VISIBLE) {
                return "DMI";
            }
            if(imgWr.getVisibility() == View.VISIBLE) {
                return "WR";
            }
            if(imgBoll.getVisibility() == View.VISIBLE) {
                return "BOLL";
            }
            if(imgKdj.getVisibility() == View.VISIBLE) {
                return "KDJ";
            }
            if(imgObv.getVisibility() == View.VISIBLE) {
                return "OBV";
            }
            if(imgRsi.getVisibility() == View.VISIBLE) {
                return "RSI";
            }
            if(imgSar.getVisibility() == View.VISIBLE) {
                return "SAR";
            }
        }
        return null;
    }

    private void storeChoose() {
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_SUB_TYPE,checkVisible(KEY_SUB_TYPE));
        editor.putString(KEY_INDEX_TYPE,checkVisible(KEY_INDEX_TYPE));
        editor.apply();
    }

    private void init(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting",Context.MODE_PRIVATE);
        String subChoose = pref.getString(KEY_SUB_TYPE,"");
        String indexChoose = pref.getString(KEY_INDEX_TYPE,"");
        switch(subChoose){
            case "不复权":
                updateSubUI(R.id.rel_nosubscription );
                break;
            case "前复权":
                updateSubUI(R.id.rel_frontsubscription);
                break;
            case "后复权":
                updateSubUI(R.id.rel_behindsubscription);
                break;
            default:
                break;
        }
        switch(indexChoose){
            case "成交量":
                updateIndexUI(R.id.rel_volume);
                break;
            case "MACD":
                updateIndexUI(R.id.rel_macd);
                break;
            case "DMI":
                updateIndexUI(R.id.rel_dmi);
                break;
            case "WR":
                updateIndexUI(R.id.rel_wr);
                break;
            case "BOLL":
                updateIndexUI(R.id.rel_boll);
                break;
            case "KDJ":
                updateIndexUI(R.id.rel_kdj);
                break;
            case "OBV":
                updateIndexUI(R.id.rel_obv);
                break;
            case "RSI":
                updateIndexUI(R.id.rel_rsi);
                break;
            case "SAR":
                updateIndexUI(R.id.rel_sar);
                break;
            default:
                break;
        }
    }
}
