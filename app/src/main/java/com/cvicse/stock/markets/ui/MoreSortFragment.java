package com.cvicse.stock.markets.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.markets.adapter.GrdAdapter;
import com.cvicse.stock.markets.data.ParamUtil;
import com.cvicse.stock.markets.ui.other_option.DCEOption1Activity;
import com.cvicse.stock.markets.ui.yaoyue.YYActivity;
import com.cvicse.stock.view.MyGridView;
import com.mitake.core.CateType;

import butterknife.BindView;

import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_All_HLT;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_All_HS;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_All_HS_KCB;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_All_XSB;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_RAISE;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_AHG;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_AHGPRE;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_CDR;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_CDRGDR;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_CDRPRE;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_CDR_CTPZ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_CXC;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_CYB;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_DCEQQ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_DPZS;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_FXJS;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_FXYW;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_GDR;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_GDRCDR;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_GDRPRE;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_GPGS;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_GQJLQQ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_HGT;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_HKCYB;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_HKGGT;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_HKJJ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_HKLCG;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_HKNX;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_HKT;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_HKTSH;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_HKTSZ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_HKYS;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_HKZB;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_HKZQ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_JCC;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_JHJJZR;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_JXC;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_JXC_GP;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_JXC_JHJJZR;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_JXC_ZSZR;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_KCB;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_LWTS;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_LXJJZR;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_QHDSS;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_QHSQS;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_QHSQSYY;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_QHZJS;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_QHZSS;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_QQZS;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SBZS;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SG;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SGT;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SHGZ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SHGZNHG;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SHHGT;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SHKZZ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SHQQ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SHQYZ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SHSZGZ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SHSZGZNHG;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SHSZKZZ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SHSZQYZ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SHSZZQ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SHZQ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SHZS;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SH_A;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SH_B;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SRGP;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SZGZ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SZGZNHG;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SZKZZ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SZQQ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SZQYZ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SZSGT;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SZZQ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SZZS;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SZ_A;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SZ_B;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_TSZL;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_WHQJ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_WHRMBZJJ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_WHZS;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_XJ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_XSB;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_XYZR;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_YXG;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_YY;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_YYHG;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_YYHSG;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_YYSG;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_ZBAG;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_ZCEQQ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_ZFGP;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_ZSZR;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_ZXB;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_RAISE_HSSC;

/** 更多模块
 * Created by ruan_ytai on 17-1-22.
 */

public class MoreSortFragment extends PBaseFragment implements AdapterView.OnItemClickListener {


    @BindView(R.id.grd_hsstock)
    MyGridView mGrdHsStock;
    @BindView(R.id.grd_hsindex)
    MyGridView mGrdHsIndex;
    @BindView(R.id.grd_hk)
    MyGridView mGrdHK;
    //新三板
    @BindView(R.id.grd_xsb)
    MyGridView mGrdXsb;

    @BindView(R.id.grd_option)
    MyGridView mGrdOption;

    @BindView(R.id.grd_bond)
    MyGridView mGrdBond;
    // 期货
    @BindView(R.id.grd_futures)
    MyGridView mGrdFutures;
    // 海外市场
    @BindView(R.id.grd_foreign)
    MyGridView mGrdForeign;

    // 沪深港通
    @BindView(R.id.grd_shszhk_tong)
    MyGridView mGrdHKTong;
    //沪伦通
    @BindView(R.id.grd_cdr)
    MyGridView mGrdCDR;

    //沪深个股名称
    private String[] shName = {STOCK_NAME_SH_A, STOCK_NAME_SH_B, STOCK_NAME_SZ_A, STOCK_NAME_SZ_B,
            STOCK_NAME_ZXB, STOCK_NAME_CYB, STOCK_NAME_FXJS, STOCK_NAME_TSZL,STOCK_NAME_CDR_CTPZ,
//            STOCK_NAME_KCBCDR,
            STOCK_NAME_KCB,STOCK_NAME_ZBAG, STOCK_NAME_YY};

    //沪深指数名称
    private String[] shIndex = {STOCK_NAME_DPZS, STOCK_NAME_SHZS, STOCK_NAME_SZZS};
    // 沪深港通
    private String[] hkTongIndex = {STOCK_NAME_HKT, STOCK_NAME_HKTSH,STOCK_NAME_HKTSZ,STOCK_NAME_HGT,STOCK_NAME_SGT,STOCK_NAME_AHGPRE
    ,STOCK_NAME_SHHGT,STOCK_NAME_SZSGT,STOCK_NAME_HKGGT};

    //港股名称
    private String[] hkName = {STOCK_NAME_HKZB, STOCK_NAME_HKCYB,
            STOCK_NAME_HKJJ, STOCK_NAME_HKZQ,STOCK_NAME_HKLCG,STOCK_NAME_AHG
    ,STOCK_NAME_HKNX,STOCK_NAME_HKYS};

    //新三板名称
    private String[] xsbName = {STOCK_NAME_XSB, STOCK_NAME_CXC, STOCK_NAME_JCC,STOCK_NAME_YXG,STOCK_NAME_SRGP,STOCK_NAME_ZFGP,
            STOCK_NAME_LWTS,STOCK_NAME_JHJJZR,STOCK_NAME_ZSZR,STOCK_NAME_XYZR,
            STOCK_NAME_GPGS,STOCK_NAME_LXJJZR,STOCK_NAME_GQJLQQ,STOCK_NAME_SBZS,
            STOCK_NAME_YYHSG,STOCK_NAME_YYSG,STOCK_NAME_YYHG,STOCK_NAME_FXYW,
            STOCK_NAME_XJ,STOCK_NAME_SG,STOCK_NAME_JXC,STOCK_NAME_JXC_JHJJZR,
            STOCK_NAME_JXC_ZSZR,STOCK_NAME_JXC_GP};

    //期权名称
    private String[] optionName = {STOCK_NAME_SHQQ, STOCK_NAME_SZQQ,
            STOCK_NAME_DCEQQ,STOCK_NAME_ZCEQQ
    };
    // 期货
    private String[] futuresName = {STOCK_NAME_QHZJS, STOCK_NAME_QHDSS,STOCK_NAME_QHZSS,STOCK_NAME_QHSQS,STOCK_NAME_QHSQSYY};

    //债券名称
    private String[] bondName = {STOCK_NAME_SHZQ, STOCK_NAME_SZZQ,STOCK_NAME_SHSZZQ,STOCK_NAME_SHGZNHG,STOCK_NAME_SZGZNHG,
            STOCK_NAME_SHSZGZNHG,
            STOCK_NAME_SHKZZ,STOCK_NAME_SZKZZ
            ,STOCK_NAME_SHSZKZZ,STOCK_NAME_SHGZ,STOCK_NAME_SZGZ,
            STOCK_NAME_SHSZGZ,STOCK_NAME_SHQYZ, STOCK_NAME_SZQYZ,STOCK_NAME_SHSZQYZ
    };
    // 海外市场
    private String[] foreignName = {STOCK_NAME_QQZS, STOCK_NAME_WHZS,STOCK_NAME_WHQJ,STOCK_NAME_WHRMBZJJ};
    //沪伦通
    private String[] cdrName = {STOCK_NAME_CDR, STOCK_NAME_GDR,STOCK_NAME_CDRGDR,STOCK_NAME_GDRCDR,STOCK_NAME_CDRPRE,STOCK_NAME_GDRPRE};
    public static MoreSortFragment newInstance() {
        return new MoreSortFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_markets_moresort;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        GrdAdapter shAdapter = new GrdAdapter(getActivity(), shName);
        GrdAdapter shIndexAdapter = new GrdAdapter(getActivity(), shIndex);
        GrdAdapter hkAdapter = new GrdAdapter(getActivity(), hkName);
        GrdAdapter xsbAdapter = new GrdAdapter(getActivity(), xsbName);
        GrdAdapter optionAdapter = new GrdAdapter(getActivity(), optionName);
        GrdAdapter bondAdapter = new GrdAdapter(getActivity(), bondName);
        GrdAdapter bourseAdapter = new GrdAdapter(getActivity(), futuresName);
        GrdAdapter foreignAdapter = new GrdAdapter(getActivity(), foreignName);
        GrdAdapter hktAdapter = new GrdAdapter(getActivity(), hkTongIndex);
        GrdAdapter CDRAdapter = new GrdAdapter(getActivity(), cdrName);

        mGrdHsStock.setAdapter(shAdapter);
        mGrdHsStock.setOnItemClickListener(this);

        mGrdHsIndex.setAdapter(shIndexAdapter);
        mGrdHsIndex.setOnItemClickListener(this);

        mGrdHK.setAdapter(hkAdapter);
        mGrdHK.setOnItemClickListener(this);

        mGrdHKTong.setAdapter(hktAdapter);
        mGrdHKTong.setOnItemClickListener(this);

        mGrdXsb.setAdapter(xsbAdapter);
        mGrdXsb.setOnItemClickListener(this);

        mGrdOption.setAdapter(optionAdapter);
        mGrdOption.setOnItemClickListener(this);

        mGrdBond.setAdapter(bondAdapter);
        mGrdBond.setOnItemClickListener(this);
        // 中金所
        mGrdFutures.setAdapter(bourseAdapter);
        mGrdFutures.setOnItemClickListener(this);

        mGrdForeign.setAdapter(foreignAdapter);
        mGrdForeign.setOnItemClickListener(this);

        mGrdCDR.setAdapter(CDRAdapter);
        mGrdCDR.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String stockType = "";
        switch (parent.getId()) {
            case R.id.grd_hsstock:
                stockType = shName[position];break;

            case R.id.grd_hsindex:
                stockType = shIndex[position];break;

            case R.id.grd_hk:
                stockType = hkName[position];break;

            case R.id.grd_xsb:
                stockType = xsbName[position];break;

            case R.id.grd_option:
                stockType = optionName[position];break;

            case R.id.grd_bond:
                stockType = bondName[position];break;

            case R.id.grd_futures:
                stockType = futuresName[position];break;

            case R.id.grd_foreign:
                stockType = foreignName[position];break;

            case R.id.grd_shszhk_tong:
                stockType = hkTongIndex[position];break;

            case R.id.grd_cdr:
                stockType = cdrName[position];break;

            default:
                break;
        }
//        String code = ParamUtil.getInstance().getParamMap().get(stockType).getStockCode();
            // 期权（上证期权、深圳期权）
        if(stockType.equals(optionName[0]) || stockType.equals(optionName[1])) {
            OptionListActivity.actionIntent(parent.getContext(), stockType);

        } else if(stockType.equals(optionName[2]) || stockType.equals(optionName[3])) {
            // 期权（大商所期权、郑商所期权）
            DCEOption1Activity.actionIntent(parent.getContext(), stockType, null);
        }
        else if(STOCK_NAME_QHZJS.equals(stockType) || STOCK_NAME_QHDSS.equals(stockType)|| STOCK_NAME_QHZSS.equals(stockType)|| STOCK_NAME_QHSQS.equals(stockType)||
                STOCK_NAME_QHSQSYY.equals(stockType)||
                STOCK_NAME_QQZS.equals(stockType) || STOCK_NAME_WHZS.equals(stockType)|| STOCK_NAME_WHQJ.equals(stockType)||STOCK_NAME_WHRMBZJJ.equals(stockType)) {

            // 中金所、期货、全球、外汇
            RankingListFuturesActivity.actionStart(parent.getContext(),stockType);
        }else if(STOCK_NAME_AHGPRE.equals(stockType)){
            // AH股
            AHQuoteListActivity.startActivity(parent.getContext(),STOCK_NAME_AHGPRE);
        }else if (STOCK_NAME_CDRPRE.equals(stockType)){
            // CDR溢价
            DRQuoteListActivity.startActivity(parent.getContext(),stockType,STOCK_NAME_CDRPRE);
        }
        else if (STOCK_NAME_GDRPRE.equals(stockType)){
            // GDR溢价
            DRQuoteListActivity.startActivity(parent.getContext(),stockType,STOCK_NAME_GDRPRE);
        } else if (STOCK_NAME_YY.equals(stockType)) {
            // 要约
            YYActivity.actionStart(parent.getContext(), stockType, RANKING_TYPE_RAISE);
        }
        else if (stockType.equals(shName[0]) || stockType.equals(shName[1]) ||
                stockType.equals(shName[10]) || stockType.equals(shName[2]) ||
                stockType.equals(shName[3]) || stockType.equals(shName[4]) ||
                stockType.equals(shName[5])) {
            // sh1001/sh1002/sh1005;sz1001-1005 展示全部通用字段+增值指标
            RankingListAllActivity.actionStart(parent.getContext(), stockType, RANKING_TYPE_All_HS);
        } else if (stockType.equals(shName[9])) {
            // SH1006 展示全部通用字段+增值指标+科创版
            RankingListAllActivity.actionStart(parent.getContext(), stockType, RANKING_TYPE_All_HS_KCB);
        } else if (stockType.equals(cdrName[0]) || stockType.equals(cdrName[1]) ||
                stockType.equals(cdrName[2]) || stockType.equals(cdrName[3])) {
            // 沪伦通
            RankingListAllActivity.actionStart(parent.getContext(), stockType, RANKING_TYPE_All_HLT);
        } else if (parent.getId() == R.id.grd_xsb) {
            // 新三板
            RankingListAllActivity.actionStart(parent.getContext(), stockType, RANKING_TYPE_All_XSB);
        }
//        else if (parent.getId() == R.id.grd_hsstock) {
//            // 沪深个股新增4个字段
//            RankingListActivity.actionStart(parent.getContext(), stockType, RANKING_TYPE_RAISE_HSSC);
//        }
        else{
            RankingListAllActivity.actionStart(parent.getContext(), stockType, RANKING_TYPE_RAISE);
        }
    }
}
