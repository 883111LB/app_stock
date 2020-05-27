package com.cvicse.stock.markets.helper;

import android.view.View;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.HKOthersInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 港股其他
 * Created by liu_zlu on 2017/5/8 12:18
 */
public class HKStockInfoHelper {
    /**
     * vcm时间
     */
    @BindView(R.id.tev_vcm_datetime) TextView tevVcmDatetime;
    /**
     * 市场调节机制起始时间
     */
    @BindView(R.id.tev_vcm_starttime) TextView tevVcmStarttime;
    /**
     * 市场调节机制结束时间
     */
    @BindView(R.id.tev_vcm_endtime) TextView tevVcmEndtime;
    /**
     * 市场调节机制参考价
     */
    @BindView(R.id.tev_vcm_reffprice) TextView tevVcmReffprice;
    /**
     * 市场调节上限价
     */
    @BindView(R.id.tev_vcm_upperprice) TextView tevVcmUpperprice;
    /**
     * 市场调节下限价
     */
    @BindView(R.id.tev_vcm_lowerprice) TextView tevVcmLowerprice;
    /**
     * cas时间
     */
    @BindView(R.id.tev_cas_datetime) TextView tevCasDatetime;
    /**
     * 未能配对买卖盘方向
     */
    @BindView(R.id.tev_cas_ordimb_direction) TextView tevCasOrdimbDirection;
    /**
     * 未能配对买卖盘数量
     */
    @BindView(R.id.tev_cas_ordimbqty) TextView tevCasOrdimbqty;
    /**
     * 参考价格
     */
    @BindView(R.id.tev_cas_reffprice) TextView tevCasReffprice;
    /**
     * 上限价
     */
    @BindView(R.id.tev_cas_upperprice) TextView tevCasUpperprice;
    /**
     * 下限价
     */
    @BindView(R.id.tev_cas_lowerprice) TextView tevCasLowerprice;

    public HKStockInfoHelper(View view) {
        ButterKnife.bind(this, view);
    }

    /**
     * 港股其他数据赋值
     *
     * @param hkOthersInfo
     */
    public void onHKStockInfoSuccess(HKOthersInfo hkOthersInfo) {
        TextUtils.setText(tevVcmDatetime,hkOthersInfo.vcmDataTimestamp,"--");
        TextUtils.setText(tevVcmStarttime,hkOthersInfo.vcmStartTime,"--");
        TextUtils.setText(tevVcmEndtime,hkOthersInfo.vcmEndTime,"--");
        TextUtils.setText(tevVcmReffprice,hkOthersInfo.vcmReffPrice,"--");
        TextUtils.setText(tevVcmUpperprice,hkOthersInfo.vcmUpperPrice,"--");
        TextUtils.setText(tevVcmLowerprice,hkOthersInfo.vcmLowerPrice,"--");

        //时间日期格式化
        if(hkOthersInfo.casDataTimestamp != null){
            String timestamp = hkOthersInfo.casDataTimestamp;
            timestamp = timestamp.substring(0,2)+":"+timestamp.substring(2,4)+":"
                    + timestamp.substring(4,6);
            tevCasDatetime.setText(timestamp);
        }else{
            TextUtils.setText(tevCasDatetime,hkOthersInfo.casDataTimestamp,"--");
        }

        TextUtils.setText(tevCasOrdimbDirection,hkOthersInfo.casOrdImbDirection,"--");
        TextUtils.setText(tevCasOrdimbqty,hkOthersInfo.casOrdImbQty,"--");
        TextUtils.setText(tevCasReffprice,hkOthersInfo.casReffPrice,"--");
        TextUtils.setText(tevCasUpperprice,hkOthersInfo.casUpperPrice,"--");
        TextUtils.setText(tevCasLowerprice,hkOthersInfo.casLowerPrice,"--");
    }
}
