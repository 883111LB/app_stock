package com.cvicse.stock.markets.data;


import com.mitake.core.AppInfo;
import com.mitake.core.CateType;
import com.mitake.core.keys.FuturesQuoteBaseField;
//import com.mitake.core.keys.KeysQuoteCff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ruan_ytai on 17-1-19.
 */
public class ParamUtil {
    /**
     * 页码，笔数，排序栏位，正倒序(0倒序，1顺序)，是否显示停牌股(0不显示，1显示)
     *  0,10,12,1,0(涨幅榜); 0,10,12,0,0(跌幅榜); 0,10,15,1,0(换手率);0,10,20,1,0(成交额)
     */
    public static final String STOCK_NAME_SHSZ = "沪深A股";
    public static final String STOCK_NAME_HK = "港股";

    //沪深个股
    public static final String STOCK_NAME_SH_A = "上证A股";
    public static final String STOCK_NAME_SH_A_CODE = CateType.SH1001;
    public static final String STOCK_NAME_SH_B = "上证B股";
    public static final String STOCK_NAME_SH_B_CODE = CateType.SH1002;
    public static final String STOCK_NAME_SZ_A = "深证A股";
    public static final String STOCK_NAME_SZ_A_CODE = CateType.SZ1001;
    public static final String STOCK_NAME_SZ_B = "深证B股";
    public static final String STOCK_NAME_SZ_B_CODE = CateType.SZ1002;
    public static final String STOCK_NAME_ZXB = "中小板";
    public static final String STOCK_NAME_ZXB_CODE = CateType.SZ1003;
    public static final String STOCK_NAME_CYB = "创业板";
    public static final String STOCK_NAME_CYB_CODE = CateType.SZ1004;
    public static final String STOCK_NAME_FXJS = "风险警示";
    public static final String STOCK_NAME_FXJS_CODE = "SHS";
    public static final String STOCK_NAME_TSZL = "退市整理";
    public static final String STOCK_NAME_TSZL_CODE = "SHP";

    public static final String STOCK_NAME_CDR_CTPZ="(CDR) 存托凭证";
    public static final String STOCK_NAME_CDR_CTPZ_CODE = CateType.SH1520;
//    public static final String STOCK_NAME_KCBCDR="科创板CDR";
//    public static final String STOCK_NAME_KCBCDR_CODE = CateType.SH1521;
    public static final String STOCK_NAME_KCB="科创板";
    public static final String STOCK_NAME_KCB_CODE = CateType.SH1006;
    public static final String STOCK_NAME_ZBAG="主板A股";
    public static final String STOCK_NAME_ZBAG_CODE = CateType.SH1005;
    public static final String STOCK_NAME_YY="要约收购";
    public static final String STOCK_NAME_YY_CODE = CateType.SH1005;

    //沪深指数
    public static final String STOCK_NAME_DPZS = "大盘指数";
    public static final String STOCK_NAME_DPZS_CODE = CateType.SHSZ1400;
    public static final String STOCK_NAME_SHZS = "上证指数";
    public static final String STOCK_NAME_SHZS_CODE = CateType.SH1400;
    public static final String STOCK_NAME_SZZS = "深证指数";
    public static final String STOCK_NAME_SZZS_CODE = CateType.SZ1400;

    //香港指数
    public static final String STOCK_NAME_HKZB = "港股主板";
    public static final String STOCK_NAME_HKZB_CODE = CateType.HK1000;
    public static final String STOCK_NAME_HKCYB = "港股创业板";
    public static final String STOCK_NAME_HKCYB_CODE = CateType.HK1004;
    public static final String STOCK_NAME_HKTSH = "港股通(沪)";
    public static final String STOCK_NAME_HKTSH_CODE = "HKUA2301";
    public static final String STOCK_NAME_HKTSZ = "港股通(深)";
    public static final String STOCK_NAME_HKTSZ_CODE = "SZHK";
    public static final String STOCK_NAME_HKJJ = "香港基金";
    public static final String STOCK_NAME_HKJJ_CODE = "HK1100";
    public static final String STOCK_NAME_HKZQ = "香港债券";
    public static final String STOCK_NAME_HKZQ_CODE = "HK1300";
    public static final String STOCK_NAME_HKLCG = "蓝筹股";
    public static final String STOCK_NAME_HKLCG_CODE = CateType.HKLC;
    public static final String STOCK_NAME_AHG = "AH股";
    public static final String STOCK_NAME_AHG_CODE = CateType.HKAHG;
    public static final String STOCK_NAME_AHGPRE = "AH股溢价";
    public static final String STOCK_NAME_AHGPRE_CODE = CateType.HKAHG;
    public static final String STOCK_NAME_HGT = "沪股通";
    public static final String STOCK_NAME_HGT_CODE = CateType.HKHGT;
    public static final String STOCK_NAME_SGT = "深股通";
    public static final String STOCK_NAME_SGT_CODE = CateType.HKSGT;
    public static final String STOCK_NAME_HKT = "港股通(沪深)";
    public static final String STOCK_NAME_HKT_CODE = CateType.HKTong;

    public static final String STOCK_NAME_HKNX = "牛熊";
    public static final String STOCK_NAME_HKNX_CODE = CateType.HK1500;
    public static final String STOCK_NAME_HKYS = "衍生";
    public static final String STOCK_NAME_HKYS_CODE = CateType.HK1600;

    public static final String STOCK_NAME_SHHGT = "沪港通";
    public static final String STOCK_NAME_SHHGT_CODE = "SHHGT";
    public static final String STOCK_NAME_SZSGT = "深港通";
    public static final String STOCK_NAME_SZSGT_CODE = "SZSGT";
    public static final String STOCK_NAME_HKGGT = "沪深港通";
    public static final String STOCK_NAME_HKGGT_CODE ="HKGGT";

    //期权(185,212)
    public static final String STOCK_NAME_SHQQ = "上证期权";
    public static final String STOCK_NAME_SHQQ_CODE = "SH3002";

    public static final String STOCK_NAME_SZQQ = "深圳期权";
    public static final String STOCK_NAME_SZQQ_CODE = "SZ3002";

    public static final String STOCK_NAME_DCEQQ = "大商所期权";
    public static final String STOCK_NAME_DCE_CODE = CateType.DCE_OPTION;

    public static final String STOCK_NAME_ZCEQQ = "郑商所期权";
    public static final String STOCK_NAME_ZCEQQ_CODE = CateType.CZCE_OPTION;


    //基金
    public static final String STOCK_NAME_SHJJ = "上证基金";
    public static final String STOCK_NAME_SHJJ_CODE = CateType.SH1100;
    public static final String STOCK_NAME_SZJJ = "深证基金";
    public static final String STOCK_NAME_SZJJ_CODE = CateType.SZ1100;
    public static final String STOCK_NAME_LOF = "LOF";
    public static final String STOCK_NAME_LOF_CODE = CateType.SHSZ1110;
    public static final String STOCK_NAME_ETF = "ETF";
    public static final String STOCK_NAME_ETF_CODE = CateType.SHSZ1120;
    public static final String STOCK_NAME_FJJJ = "分级基金";
    public static final String STOCK_NAME_FJJJ_CODE = CateType.SHSZ1130;
    public static final String STOCK_NAME_FJA = "分级A";
    public static final String STOCK_NAME_FJA_CODE = CateType.SHSZ1131;
    public static final String STOCK_NAME_FJB = "分级B";
    public static final String STOCK_NAME_FJB_CODE = CateType.SHSZ1132;

    //债券
    public static final String STOCK_NAME_SHZQ = "上证债券";
    public static final String STOCK_NAME_SHZQ_CODE = CateType.SH1300;
    public static final String STOCK_NAME_SZZQ = "深证债券";
    public static final String STOCK_NAME_SZZQ_CODE = CateType.SZ1300;
    public static final String STOCK_NAME_SHSZZQ="沪深债券";
    public static final String STOCK_NAME_SHSZZQ_CODE = CateType.SHSZ1300;

    public static final String STOCK_NAME_SHGZNHG="上证国债逆回购";
    public static final String STOCK_NAME_SHGZNHG_CODE=CateType.SH1311;
    public static final String STOCK_NAME_SHKZZ="上证可转债";
    public static final String STOCK_NAME_SHKZZ_CODE=CateType.SH1312;
    public static final String STOCK_NAME_SHGZ="上证国债";
    public static final String STOCK_NAME_SHGZ_CODE=CateType.SH1313;
    public static final String STOCK_NAME_SHQYZ="上证企业债";
    public static final String STOCK_NAME_SHQYZ_CODE=CateType.SH1314;

    public static final String STOCK_NAME_SZGZNHG="深圳国债逆回购";
    public static final String STOCK_NAME_SZGZNHG_CODE=CateType.SZ1311;
    public static final String STOCK_NAME_SZKZZ="深圳可转债";
    public static final String STOCK_NAME_SZKZZ_CODE=CateType.SZ1312;
    public static final String STOCK_NAME_SZGZ="深圳国债";
    public static final String STOCK_NAME_SZGZ_CODE=CateType.SZ1313;
    public static final String STOCK_NAME_SZQYZ="深圳企业债";
    public static final String STOCK_NAME_SZQYZ_CODE=CateType.SZ1314;

    public static final String STOCK_NAME_SHSZGZNHG="沪深国债逆回购";
    public static final String STOCK_NAME_SHSZGZNHG_CODE=CateType.SHSZ1311;
    public static final String STOCK_NAME_SHSZKZZ="沪深可转债";
    public static final String STOCK_NAME_SHSZKZZ_CODE=CateType.SHSZ1312;
    public static final String STOCK_NAME_SHSZGZ="沪深国债";
    public static final String STOCK_NAME_SHSZGZ_CODE=CateType.SHSZ1313;
    public static final String STOCK_NAME_SHSZQYZ="沪深企业债";
    public static final String STOCK_NAME_SHSZQYZ_CODE=CateType.SHSZ1314;


    //新三板
    public static final String STOCK_NAME_XSB = "新三板";
    public static final String STOCK_NAME_XSB_CODE = "BJ1000";
    public static final String STOCK_NAME_CXC = "创新层";
    public static final String STOCK_NAME_CXC_CODE = "BJ1005";
    public static final String STOCK_NAME_JCC= "基础层";
    public static final String STOCK_NAME_JCC_CODE = "BJ1006";
    public static final String STOCK_NAME_LWTS = "两网及退市";
    public static final String STOCK_NAME_LWTS_CODE = "BJ1001";
    public static final String STOCK_NAME_YXG = "优先股";
    public static final String STOCK_NAME_YXG_CODE = "BJ1002";
    public static final String STOCK_NAME_SRGP = "首日挂牌";
    public static final String STOCK_NAME_SRGP_CODE = "BJ1003";
    public static final String STOCK_NAME_ZFGP = "增发挂牌";
    public static final String STOCK_NAME_ZFGP_CODE = "BJ1004";
    public static final String STOCK_NAME_JHJJZR = "集合竞价转让";
    public static final String STOCK_NAME_JHJJZR_CODE = "BJ1007";
    public static final String STOCK_NAME_ZSZR = "做市转让";
    public static final String STOCK_NAME_ZSZR_CODE = "BJ1008";
    public static final String STOCK_NAME_XYZR = "协议转让";
    public static final String STOCK_NAME_XYZR_CODE = "BJ1009";
    public static final String STOCK_NAME_LXJJZR = "连续竞价转让";
    public static final String STOCK_NAME_LXJJZR_CODE = "BJ1010";
    public static final String STOCK_NAME_GQJLQQ = "股权激励期权";
    public static final String STOCK_NAME_GQJLQQ_CODE = "BJ1011";
    public static final String STOCK_NAME_GPGS = "挂牌公司";
    public static final String STOCK_NAME_GPGS_CODE = "BJ1012";
    public static final String STOCK_NAME_SBZS = "三板指数";
    public static final String STOCK_NAME_SBZS_CODE = "BJ1400";
    public static final String STOCK_NAME_YYHSG = "要约回收购";
    public static final String STOCK_NAME_YYHSG_CODE = "BJ1013";
    public static final String STOCK_NAME_YYSG = "要约收购 ";
    public static final String STOCK_NAME_YYSG_CODE = "BJ101301";
    public static final String STOCK_NAME_YYHG = "要约回购";
    public static final String STOCK_NAME_YYHG_CODE = "BJ101302";
    public static final String STOCK_NAME_FXYW = "发行业务";
    public static final String STOCK_NAME_FXYW_CODE = "BJ1014";
    public static final String STOCK_NAME_XJ = "询价";
    public static final String STOCK_NAME_XJ_CODE = "BJ101401";
    public static final String STOCK_NAME_SG = "申购";
    public static final String STOCK_NAME_SG_CODE = "BJ101402";
    public static final String STOCK_NAME_JXC = "精选层";
    public static final String STOCK_NAME_JXC_CODE = "BJ1015";
    public static final String STOCK_NAME_JXC_JHJJZR = "精选层集合竞价转让";
    public static final String STOCK_NAME_JXC_JHJJZR_CODE = "BJ101501";
    public static final String STOCK_NAME_JXC_ZSZR = "精选层做市转让";
    public static final String STOCK_NAME_JXC_ZSZR_CODE = "BJ101502";
    public static final String STOCK_NAME_JXC_GP = "精选层挂牌公司连续竞价股票";
    public static final String STOCK_NAME_JXC_GP_CODE = "BJ101503";
    // 外汇
    public static final String STOCK_NAME_QQZS = "全球指数";
    public static final String STOCK_NAME_QQZS_CODE = "GI1400";
    public static final String STOCK_NAME_WHZS = "全球外汇";
    public static final String STOCK_NAME_WHZS_CODE = "FE1000";
    public static final String STOCK_NAME_WHQJ = "期价";
    public static final String STOCK_NAME_WHQJ_CODE = "FE1100";
    public static final String STOCK_NAME_WHRMBZJJ = "人民币中间价";
    public static final String STOCK_NAME_WHRMBZJJ_CODE = "FE1200";

    // 沪伦通
    public static final String STOCK_NAME_CDR = "沪伦通CDR";
    public static final String STOCK_NAME_CDR_CODE = "SH1530";
    public static final String STOCK_NAME_CDRGDR = "CDR基础证券";
    public static final String STOCK_NAME_CDRGDR_CODE = "UK1530";
    public static final String STOCK_NAME_GDR = "沪伦通GDR";
    public static final String STOCK_NAME_GDR_CODE = "UK1540";
    public static final String STOCK_NAME_GDRCDR="GDR基础证券";
    public static final String STOCK_NAME_GDRCDR_CODE="SH1540";
    public static final String STOCK_NAME_CDRPRE="CDR溢价";
    public static final String STOCK_NAME_CDRPRE_CODE=CateType.SH1530;
    public static final String STOCK_NAME_GDRPRE="GDR溢价";
    public static final String STOCK_NAME_GDRPRE_CODE=CateType.UK1540;


    public static final String STOCK_NAME_ADDVALUE_FIVE = "5分钟涨幅";

    // 期货分类
    public static final String STOCK_NAME_QHZJS = "中金所";
    public static final String STOCK_NAME_QHDSS = "大商所";
    public static final String STOCK_NAME_QHZSS = "郑商所";
    public static final String STOCK_NAME_QHSQS = "上期所";
    public static final String STOCK_NAME_QHSQSYY = "上期所原油";


    //中金所
    public static final String STOCK_NAME_ZSQH = "指数期货";
    public static final String STOCK_NAME_GZQH = "国债期货";

    //RankingType
    public static final String RANKING_TYPE_RAISE ="涨幅榜";
    public static final String RANKING_TYPE_RAISE_NO_CODE ="0,20,12,1,0";//1顺序,最后一位0不显示停盘
    public static final String RANKING_TYPE_RAISE_HSSC ="涨幅榜_沪深市场";
    //全部排序栏位1（沪深）
    public static final String RANKING_TYPE_All_HS ="全部沪深";
    public static final String RANKING_TYPE_RAISE_NO_All_HS ="0,20,12,1,0";//1顺序,最后一位0不显示停盘
    public static final String RANKING_TYPE_All_HS_KCB ="全部科创板";
    public static final String RANKING_TYPE_All_HLT ="全部沪伦通";
    public static final String RANKING_TYPE_All_XSB ="全部新三板";
    //涨幅榜参数,显示停盘股(更多模块)
    public static final String RANKING_TYPE_RAISE_CODE ="0,20,12,1,1";//1显示停盘
    public static final String RANKING_TYPE_RAISE_CODE_AHG ="0,12,2,0";//1显示停盘

    public static final String RANKING_TYPE_DECLINE ="跌幅榜";
    public static final String RANKING_TYPE_DECLINE_CODE ="0,20,12,0,0";
    public static final String RANKING_TYPE_DECLINE_HSSC ="跌幅榜_沪深市场";
    public static final String RANKING_TYPE_TURNOVERRATE_HSSC ="换手率_沪深市场";
    public static final String RANKING_TYPE_TURNOVERRATE_CODE ="0,20,15,1,0";
    public static final String RANKING_TYPE_TURNOVERRATE ="换手率";
    public static final String RANKING_TYPE_AMOUNT ="成交额";
    public static final String RANKING_TYPE_AMOUNT_CODE ="0,20,20,1,0";
    public static final String RANKING_TYPE_AMOUNT_HSSC ="成交额_沪深市场";


    // 全球指数，外汇-- 排序方式
    public static final String RANKING_TYPE_RAISE_CODE_DZQW ="0,20,300,1,1";//1显示停盘
    //大商，郑商
    // 中金所-- 排序方式
//    public static final String RANKING_TYPE_RAISE_CODE_CFF ="0,20,"+KeysQuoteCff.last+",1,1";//1显示停盘
    public static final String RANKING_TYPE_RAISE_CODE_CFF ="0,20," + FuturesQuoteBaseField.last + ",1,1";//1显示停盘
    public static final String RANKING_TYPE_RAISE_CODE_ADDVALUE = "0,20,7,1,1";

    private static  Map<String,ArrayList<Param>> SHSZMap= new HashMap<>();
    private static Map<String,ArrayList<Param>> sListMap = new HashMap<>();   // 期货,增值指标
    private static Map<String,Param> paramMap = new HashMap<>();
    private static ParamUtil util;

    public ParamUtil(){
        paramMap.put(STOCK_NAME_SH_A,new Param(STOCK_NAME_SH_A_CODE,RANKING_TYPE_RAISE_CODE,"sh"));
        paramMap.put(STOCK_NAME_SH_B,new Param(STOCK_NAME_SH_B_CODE,RANKING_TYPE_RAISE_CODE,"sh"));
        paramMap.put(STOCK_NAME_SZ_A,new Param(STOCK_NAME_SZ_A_CODE,RANKING_TYPE_RAISE_CODE,"sz"));
        paramMap.put(STOCK_NAME_SZ_B,new Param(STOCK_NAME_SZ_B_CODE,RANKING_TYPE_RAISE_CODE,"sz"));
        paramMap.put(STOCK_NAME_ZXB,new Param(STOCK_NAME_ZXB_CODE,RANKING_TYPE_RAISE_CODE,"shsz"));
        paramMap.put(STOCK_NAME_CYB,new Param(STOCK_NAME_CYB_CODE,RANKING_TYPE_RAISE_CODE,"shsz"));
        paramMap.put(STOCK_NAME_FXJS,new Param(STOCK_NAME_FXJS_CODE,RANKING_TYPE_RAISE_CODE,"shsz"));
        paramMap.put(STOCK_NAME_TSZL,new Param(STOCK_NAME_TSZL_CODE,RANKING_TYPE_RAISE_CODE,"shsz"));

        paramMap.put(STOCK_NAME_CDR_CTPZ,new Param(STOCK_NAME_CDR_CTPZ_CODE,RANKING_TYPE_RAISE_CODE,"sh"));
//        paramMap.put(STOCK_NAME_KCBCDR,new Param(STOCK_NAME_KCBCDR_CODE,RANKING_TYPE_RAISE_CODE,"sh"));
        paramMap.put(STOCK_NAME_KCB,new Param(STOCK_NAME_KCB_CODE,RANKING_TYPE_RAISE_CODE,"sh"));
        paramMap.put(STOCK_NAME_ZBAG,new Param(STOCK_NAME_ZBAG_CODE,RANKING_TYPE_RAISE_CODE,"sh"));

        paramMap.put(STOCK_NAME_DPZS,new Param(STOCK_NAME_DPZS_CODE,RANKING_TYPE_RAISE_CODE,"shsz"));
        paramMap.put(STOCK_NAME_SHZS,new Param(STOCK_NAME_SHZS_CODE,RANKING_TYPE_RAISE_CODE,"sh"));
        paramMap.put(STOCK_NAME_SZZS,new Param(STOCK_NAME_SZZS_CODE,RANKING_TYPE_RAISE_CODE,"sz"));

        // 港股
        paramMap.put(STOCK_NAME_HKZB,new Param(STOCK_NAME_HKZB_CODE,RANKING_TYPE_RAISE_CODE,"hk"));
        paramMap.put(STOCK_NAME_HKCYB,new Param(STOCK_NAME_HKCYB_CODE,RANKING_TYPE_RAISE_CODE,"hk"));
        paramMap.put(STOCK_NAME_HKTSH,new Param(STOCK_NAME_HKTSH_CODE,RANKING_TYPE_RAISE_CODE,"hk"));
        paramMap.put(STOCK_NAME_HKTSZ,new Param(STOCK_NAME_HKTSZ_CODE,RANKING_TYPE_RAISE_CODE,"hk"));
        paramMap.put(STOCK_NAME_HKJJ,new Param(STOCK_NAME_HKJJ_CODE,RANKING_TYPE_RAISE_CODE,"hk"));
        paramMap.put(STOCK_NAME_HKZQ,new Param(STOCK_NAME_HKZQ_CODE,RANKING_TYPE_RAISE_CODE,"hk"));
        paramMap.put(STOCK_NAME_HKLCG,new Param(STOCK_NAME_HKLCG_CODE,RANKING_TYPE_RAISE_CODE,"hk"));
        paramMap.put(STOCK_NAME_AHG,new Param(STOCK_NAME_AHG_CODE,RANKING_TYPE_RAISE_CODE,"hk"));
        paramMap.put(STOCK_NAME_HGT,new Param(STOCK_NAME_HGT_CODE,RANKING_TYPE_RAISE_CODE,"hk"));
        paramMap.put(STOCK_NAME_SGT,new Param(STOCK_NAME_SGT_CODE,RANKING_TYPE_RAISE_CODE,"hk"));
        paramMap.put(STOCK_NAME_HKT,new Param(STOCK_NAME_HKT_CODE,RANKING_TYPE_RAISE_CODE,"hk"));
        paramMap.put(STOCK_NAME_AHGPRE,new Param(STOCK_NAME_AHGPRE_CODE,RANKING_TYPE_RAISE_CODE_AHG,"hk"));

        paramMap.put(STOCK_NAME_HKNX,new Param(STOCK_NAME_HKNX_CODE,RANKING_TYPE_RAISE_CODE,"hk"));
        paramMap.put(STOCK_NAME_HKYS,new Param(STOCK_NAME_HKYS_CODE,RANKING_TYPE_RAISE_CODE,"hk"));

        paramMap.put(STOCK_NAME_SHHGT,new Param(STOCK_NAME_SHHGT_CODE,RANKING_TYPE_RAISE_CODE,"hk"));
        paramMap.put(STOCK_NAME_SZSGT,new Param(STOCK_NAME_SZSGT_CODE,RANKING_TYPE_RAISE_CODE,"hk"));
        paramMap.put(STOCK_NAME_HKGGT,new Param(STOCK_NAME_HKGGT_CODE,RANKING_TYPE_RAISE_CODE,"hk"));
        // 期权
        paramMap.put(STOCK_NAME_SHQQ,new Param(STOCK_NAME_SHQQ_CODE,RANKING_TYPE_RAISE_CODE,"sh"));
        paramMap.put(STOCK_NAME_SHQQ,new Param(STOCK_NAME_SZQQ_CODE,RANKING_TYPE_RAISE_CODE,"sz"));
        paramMap.put(STOCK_NAME_DCEQQ,new Param(STOCK_NAME_DCE_CODE,RANKING_TYPE_RAISE_CODE_DZQW,"dce"));
        paramMap.put(STOCK_NAME_ZCEQQ,new Param(STOCK_NAME_ZCEQQ_CODE,RANKING_TYPE_RAISE_CODE_DZQW,"zce"));
        //债券
        paramMap.put(STOCK_NAME_SHZQ,new Param(STOCK_NAME_SHZQ_CODE,RANKING_TYPE_RAISE_CODE,"sh"));
        paramMap.put(STOCK_NAME_SZZQ ,new Param(STOCK_NAME_SZZQ_CODE,RANKING_TYPE_RAISE_CODE,"sz"));
        paramMap.put(STOCK_NAME_SHSZZQ,new Param(STOCK_NAME_SHSZZQ_CODE,RANKING_TYPE_RAISE_CODE,"shsz"));

        paramMap.put(STOCK_NAME_SHGZNHG,new Param(STOCK_NAME_SHGZNHG_CODE,RANKING_TYPE_RAISE_CODE,"sh"));
        paramMap.put(STOCK_NAME_SHKZZ,new Param(STOCK_NAME_SHKZZ_CODE,RANKING_TYPE_RAISE_CODE,"sh"));
        paramMap.put(STOCK_NAME_SHGZ,new Param(STOCK_NAME_SHGZ_CODE,RANKING_TYPE_RAISE_CODE,"sh"));
        paramMap.put(STOCK_NAME_SHQYZ,new Param(STOCK_NAME_SHQYZ_CODE,RANKING_TYPE_RAISE_CODE,"sh"));

        paramMap.put(STOCK_NAME_SZGZNHG,new Param(STOCK_NAME_SZGZNHG_CODE,RANKING_TYPE_RAISE_CODE,"sz"));
        paramMap.put(STOCK_NAME_SZKZZ,new Param(STOCK_NAME_SZKZZ_CODE,RANKING_TYPE_RAISE_CODE,"sz"));
        paramMap.put(STOCK_NAME_SZGZ,new Param(STOCK_NAME_SZGZ_CODE,RANKING_TYPE_RAISE_CODE,"sz"));
        paramMap.put(STOCK_NAME_SZQYZ,new Param(STOCK_NAME_SZQYZ_CODE,RANKING_TYPE_RAISE_CODE,"sz"));

        paramMap.put(STOCK_NAME_SHSZGZNHG,new Param(STOCK_NAME_SHSZGZNHG_CODE,RANKING_TYPE_RAISE_CODE,"shsz"));
        paramMap.put(STOCK_NAME_SHSZKZZ,new Param(STOCK_NAME_SHSZKZZ_CODE,RANKING_TYPE_RAISE_CODE,"shsz"));
        paramMap.put(STOCK_NAME_SHSZGZ,new Param(STOCK_NAME_SHSZGZ_CODE,RANKING_TYPE_RAISE_CODE,"shsz"));
        paramMap.put(STOCK_NAME_SHSZQYZ,new Param(STOCK_NAME_SHSZQYZ_CODE,RANKING_TYPE_RAISE_CODE,"shsz"));
        //基金
        paramMap.put(STOCK_NAME_SHJJ ,new Param(STOCK_NAME_SHJJ_CODE,RANKING_TYPE_RAISE_CODE,"sh"));
        paramMap.put(STOCK_NAME_SZJJ ,new Param(STOCK_NAME_SZJJ_CODE,RANKING_TYPE_RAISE_CODE,"sz"));
        paramMap.put(STOCK_NAME_LOF ,new Param(STOCK_NAME_LOF_CODE,RANKING_TYPE_RAISE_CODE,"shsz"));
        paramMap.put(STOCK_NAME_ETF ,new Param(STOCK_NAME_ETF_CODE,RANKING_TYPE_RAISE_CODE,"shsz"));
        paramMap.put(STOCK_NAME_FJJJ ,new Param(STOCK_NAME_FJJJ_CODE,RANKING_TYPE_RAISE_CODE,"shsz"));
        paramMap.put(STOCK_NAME_FJA ,new Param(STOCK_NAME_FJA_CODE,RANKING_TYPE_RAISE_CODE,"shsz"));
        paramMap.put(STOCK_NAME_FJB ,new Param(STOCK_NAME_FJB_CODE,RANKING_TYPE_RAISE_CODE,"shsz"));

        // 新三板
        paramMap.put(STOCK_NAME_XSB ,new Param(STOCK_NAME_XSB_CODE,RANKING_TYPE_RAISE_CODE,"bj"));
        paramMap.put(STOCK_NAME_CXC ,new Param(STOCK_NAME_CXC_CODE,RANKING_TYPE_RAISE_CODE,"bj"));
        paramMap.put(STOCK_NAME_JCC ,new Param(STOCK_NAME_JCC_CODE,RANKING_TYPE_RAISE_CODE,"bj"));
        paramMap.put(STOCK_NAME_YXG ,new Param(STOCK_NAME_YXG_CODE,STOCK_NAME_YXG_CODE,"bj"));
        paramMap.put(STOCK_NAME_SRGP ,new Param(STOCK_NAME_SRGP_CODE,RANKING_TYPE_RAISE_CODE,"bj"));
        paramMap.put(STOCK_NAME_ZFGP ,new Param(STOCK_NAME_ZFGP_CODE,RANKING_TYPE_RAISE_CODE,"bj"));
        paramMap.put(STOCK_NAME_LWTS ,new Param(STOCK_NAME_LWTS_CODE,RANKING_TYPE_RAISE_CODE,"bj"));
        paramMap.put(STOCK_NAME_JHJJZR ,new Param(STOCK_NAME_JHJJZR_CODE,RANKING_TYPE_RAISE_CODE,"bj"));
        paramMap.put(STOCK_NAME_ZSZR ,new Param(STOCK_NAME_ZSZR_CODE,RANKING_TYPE_RAISE_CODE,"bj"));
        paramMap.put(STOCK_NAME_XYZR ,new Param(STOCK_NAME_XYZR_CODE,RANKING_TYPE_RAISE_CODE,"bj"));
        paramMap.put(STOCK_NAME_GPGS ,new Param(STOCK_NAME_GPGS_CODE,RANKING_TYPE_RAISE_CODE,"bj"));
        paramMap.put(STOCK_NAME_LXJJZR ,new Param(STOCK_NAME_LXJJZR_CODE,RANKING_TYPE_RAISE_CODE,"bj"));
        paramMap.put(STOCK_NAME_GQJLQQ ,new Param(STOCK_NAME_GQJLQQ_CODE,RANKING_TYPE_RAISE_CODE,"bj"));
        paramMap.put(STOCK_NAME_SBZS ,new Param(STOCK_NAME_SBZS_CODE,RANKING_TYPE_RAISE_CODE,"bj"));
        paramMap.put(STOCK_NAME_YYHSG ,new Param(STOCK_NAME_YYHSG_CODE,RANKING_TYPE_RAISE_CODE,"bj"));
        paramMap.put(STOCK_NAME_YYSG ,new Param(STOCK_NAME_YYSG_CODE,RANKING_TYPE_RAISE_CODE,"bj"));
        paramMap.put(STOCK_NAME_YYHG ,new Param(STOCK_NAME_YYHG_CODE,RANKING_TYPE_RAISE_CODE,"bj"));
        paramMap.put(STOCK_NAME_FXYW ,new Param(STOCK_NAME_FXYW_CODE,RANKING_TYPE_RAISE_CODE,"bj"));
        paramMap.put(STOCK_NAME_XJ ,new Param(STOCK_NAME_XJ_CODE,RANKING_TYPE_RAISE_CODE,"bj"));
        paramMap.put(STOCK_NAME_SG ,new Param(STOCK_NAME_SG_CODE,RANKING_TYPE_RAISE_CODE,"bj"));
        paramMap.put(STOCK_NAME_JXC ,new Param(STOCK_NAME_JXC_CODE,RANKING_TYPE_RAISE_CODE,"bj"));
        paramMap.put(STOCK_NAME_JXC_JHJJZR ,new Param(STOCK_NAME_JXC_JHJJZR_CODE,RANKING_TYPE_RAISE_CODE,"bj"));
        paramMap.put(STOCK_NAME_JXC_ZSZR ,new Param(STOCK_NAME_JXC_ZSZR_CODE,RANKING_TYPE_RAISE_CODE,"bj"));
        paramMap.put(STOCK_NAME_JXC_GP ,new Param(STOCK_NAME_JXC_GP_CODE,RANKING_TYPE_RAISE_CODE,"bj"));


        // 外汇
        paramMap.put(STOCK_NAME_WHZS,new Param(STOCK_NAME_WHZS_CODE,RANKING_TYPE_RAISE_CODE_DZQW,"fe"));
        paramMap.put(STOCK_NAME_QQZS,new Param(STOCK_NAME_QQZS_CODE,RANKING_TYPE_RAISE_CODE_DZQW,"gi"));
        paramMap.put(STOCK_NAME_WHQJ,new Param(STOCK_NAME_WHQJ_CODE,RANKING_TYPE_RAISE_CODE_DZQW,"fe"));
        paramMap.put(STOCK_NAME_WHRMBZJJ,new Param(STOCK_NAME_WHRMBZJJ_CODE,RANKING_TYPE_RAISE_CODE_DZQW,"fe"));

        // 沪伦通
        paramMap.put(STOCK_NAME_CDR,new Param(STOCK_NAME_CDR_CODE,RANKING_TYPE_RAISE_CODE,"shuk"));
        paramMap.put(STOCK_NAME_CDRGDR,new Param(STOCK_NAME_CDRGDR_CODE,RANKING_TYPE_RAISE_CODE,"shuk"));
        paramMap.put(STOCK_NAME_GDR,new Param(STOCK_NAME_GDR_CODE,RANKING_TYPE_RAISE_CODE,"shuk"));
        paramMap.put(STOCK_NAME_GDRCDR,new Param(STOCK_NAME_GDRCDR_CODE,RANKING_TYPE_RAISE_CODE,"shuk"));
        paramMap.put(STOCK_NAME_CDRPRE,new Param(STOCK_NAME_CDRPRE_CODE,RANKING_TYPE_RAISE_CODE,"shuk"));
        paramMap.put(STOCK_NAME_GDRPRE,new Param(STOCK_NAME_GDRPRE_CODE,RANKING_TYPE_RAISE_CODE,"shuk"));

        /************************ 期货**************************/
         //中金所
        ArrayList zjsList = new ArrayList();
        zjsList.add(new Param("全部",CateType.CFF_ALL,RANKING_TYPE_RAISE_CODE_CFF,"cff"));
//        zjsList.add(new Param("中金所期权合约",CateType.CFF_OPTION,RANKING_TYPE_RAISE_CODE_CFF,"cff"));
        zjsList.add(new Param("中金所期货合约",CateType.CFF_FUTURE,RANKING_TYPE_RAISE_CODE_CFF,"cff"));
        zjsList.add(new Param("中证",CateType.CFF_IC,RANKING_TYPE_RAISE_CODE_CFF,"cff"));
        zjsList.add(new Param("沪深",CateType.CFF_IF,RANKING_TYPE_RAISE_CODE_CFF,"cff"));
        zjsList.add(new Param("上证",CateType.CFF_IH,RANKING_TYPE_RAISE_CODE_CFF,"cff"));
        zjsList.add(new Param("10年国债",CateType.CFF_T,RANKING_TYPE_RAISE_CODE_CFF,"cff"));
        zjsList.add(new Param("5年国债",CateType.CFF_TF,RANKING_TYPE_RAISE_CODE_CFF,"cff"));
        zjsList.add(new Param("2年国债",CateType.CFF_TS,RANKING_TYPE_RAISE_CODE_CFF,"cff"));
        zjsList.add(new Param("中证沪深上证的合约",CateType.CFF_INDEX_FUTURE,RANKING_TYPE_RAISE_CODE_CFF,"cff"));
        zjsList.add(new Param("2年5年10年国债的合约",CateType.CFF_TREASURY_FUTURE,RANKING_TYPE_RAISE_CODE_CFF,"cff"));
        zjsList.add(new Param("中证沪深上证的当月及下月合约",CateType.CFF_IF_MONTH,RANKING_TYPE_RAISE_CODE_CFF,"cff"));
        zjsList.add(new Param("指数期货",CateType.CFFIF,RANKING_TYPE_RAISE_CODE_CFF,"cff"));
        zjsList.add(new Param("国债期货",CateType.CFFTF,RANKING_TYPE_RAISE_CODE_CFF,"cff"));

         //大商所
        ArrayList dceList = new ArrayList();
        dceList.add(new Param("全部",CateType.DCE_ALL,RANKING_TYPE_RAISE_CODE_CFF,"dce"));
        dceList.add(new Param("大商所期货合约",CateType.DCE_FUTURE,RANKING_TYPE_RAISE_CODE_CFF,"dce"));
        dceList.add(new Param("大商所期权合约",CateType.DCE_OPTION,RANKING_TYPE_RAISE_CODE_CFF,"dce"));
        dceList.add(new Param("胶合板",CateType.DCE_BB,RANKING_TYPE_RAISE_CODE_CFF,"dce"));
        dceList.add(new Param("豆一",CateType.DCE_A,RANKING_TYPE_RAISE_CODE_CFF,"dce"));
        dceList.add(new Param("豆二",CateType.DCE_B,RANKING_TYPE_RAISE_CODE_CFF,"dce"));
        dceList.add(new Param("玉米淀粉",CateType.DCE_CS,RANKING_TYPE_RAISE_CODE_CFF,"dce"));
        dceList.add(new Param("玉米",CateType.DCE_C,RANKING_TYPE_RAISE_CODE_CFF,"dce"));
        dceList.add(new Param("聚乙烯",CateType.DCE_L,RANKING_TYPE_RAISE_CODE_CFF,"dce"));
        dceList.add(new Param("豆粕",CateType.DCE_M,RANKING_TYPE_RAISE_CODE_CFF,"dce"));
        dceList.add(new Param("聚丙烯",CateType.DCE_PP,RANKING_TYPE_RAISE_CODE_CFF,"dce"));
        dceList.add(new Param("棕榈油",CateType.DCE_P,RANKING_TYPE_RAISE_CODE_CFF,"dce"));
        dceList.add(new Param("聚氯乙烯",CateType.DCE_V,RANKING_TYPE_RAISE_CODE_CFF,"dce"));
        dceList.add(new Param("鸡蛋",CateType.DCE_JD,RANKING_TYPE_RAISE_CODE_CFF,"dce"));
        dceList.add(new Param("鸡蛋",CateType.DCE_JM,RANKING_TYPE_RAISE_CODE_CFF,"dce"));
        dceList.add(new Param("焦炭",CateType.DCE_J,RANKING_TYPE_RAISE_CODE_CFF,"dce"));
        dceList.add(new Param("铁矿石",CateType.DCE_I,RANKING_TYPE_RAISE_CODE_CFF,"dce"));
        dceList.add(new Param("纤维板",CateType.DCE_FB,RANKING_TYPE_RAISE_CODE_CFF,"dce"));
        dceList.add(new Param("乙二醇",CateType.DCE_EG,RANKING_TYPE_RAISE_CODE_CFF,"dce"));

//        dceList.add(new Param("全部",CateType.DCEALL,RANKING_TYPE_RAISE_CODE_DZQW,"dce"));
//        dceList.add(new Param("棕榈油",CateType.DCEZLY,RANKING_TYPE_RAISE_CODE_DZQW,"dce"));
//        dceList.add(new Param("玉米淀粉",CateType.DCEYMDF,RANKING_TYPE_RAISE_CODE_DZQW,"dce"));
//        dceList.add(new Param("玉米",CateType.DCEYM,RANKING_TYPE_RAISE_CODE_DZQW,"dce"));
//        dceList.add(new Param("纤维板",CateType.DCEXWB,RANKING_TYPE_RAISE_CODE_DZQW,"dce"));
//        dceList.add(new Param("铁矿石",CateType.DCETKS,RANKING_TYPE_RAISE_CODE_DZQW,"dce"));
//        dceList.add(new Param("聚乙烯",CateType.DCEJYX,RANKING_TYPE_RAISE_CODE_DZQW,"dce"));
//        dceList.add(new Param("聚氯乙烯",CateType.DCEJLYX,RANKING_TYPE_RAISE_CODE_DZQW,"dce"));
//        dceList.add(new Param("聚丙烯",CateType.DCEJBX,RANKING_TYPE_RAISE_CODE_DZQW,"dce"));
//        dceList.add(new Param("焦炭",CateType.DCEJT,RANKING_TYPE_RAISE_CODE_DZQW,"dce"));
//        dceList.add(new Param("焦煤",CateType.DCEJM,RANKING_TYPE_RAISE_CODE_DZQW,"dce"));
//        dceList.add(new Param("胶合板",CateType.DCEJHB,RANKING_TYPE_RAISE_CODE_DZQW,"dce"));
//        dceList.add(new Param("大豆",CateType.DCEDD,RANKING_TYPE_RAISE_CODE_DZQW,"dce"));
//        dceList.add(new Param("豆粕",CateType.DCEDP,RANKING_TYPE_RAISE_CODE_DZQW,"dce"));
//        dceList.add(new Param("豆油",CateType.DCEDY,RANKING_TYPE_RAISE_CODE_DZQW,"dce"));
//        dceList.add(new Param("鸡蛋",CateType.DCEJD,RANKING_TYPE_RAISE_CODE_DZQW,"dce"));


        // 郑商所
        ArrayList zceList = new ArrayList();

        zceList.add(new Param("全部",CateType.CZCE_ALL,RANKING_TYPE_RAISE_CODE_CFF,"zce"));
        zceList.add(new Param("郑商所期货合约",CateType.CZCE_FUTURE,RANKING_TYPE_RAISE_CODE_CFF,"zce"));
        zceList.add(new Param("郑商所期权合约",CateType.CZCE_OPTION,RANKING_TYPE_RAISE_CODE_CFF,"zce"));
        zceList.add(new Param("苹果",CateType.CZCE_AP,RANKING_TYPE_RAISE_CODE_CFF,"zce"));
        zceList.add(new Param("郑棉",CateType.CZCE_CF,RANKING_TYPE_RAISE_CODE_CFF,"zce"));
        zceList.add(new Param("早籼稻",CateType.CZCE_RI,RANKING_TYPE_RAISE_CODE_CFF,"zce"));
        zceList.add(new Param("晚籼稻",CateType.CZCE_LR,RANKING_TYPE_RAISE_CODE_CFF,"zce"));
        zceList.add(new Param("粳稻",CateType.CZCE_JR,RANKING_TYPE_RAISE_CODE_CFF,"zce"));
        zceList.add(new Param("菜籽油",CateType.CZCE_OI,RANKING_TYPE_RAISE_CODE_CFF,"zce"));
        zceList.add(new Param("PTA",CateType.CZCE_TA,RANKING_TYPE_RAISE_CODE_CFF,"zce"));
        zceList.add(new Param("普麦",CateType.CZCE_PM,RANKING_TYPE_RAISE_CODE_CFF,"zce"));
        zceList.add(new Param("甲醇",CateType.CZCE_MA,RANKING_TYPE_RAISE_CODE_CFF,"zce"));
        zceList.add(new Param("玻璃",CateType.CZCE_FG,RANKING_TYPE_RAISE_CODE_CFF,"zce"));
        zceList.add(new Param("油菜籽",CateType.CZCE_RS,RANKING_TYPE_RAISE_CODE_CFF,"zce"));
        zceList.add(new Param("菜籽粕",CateType.CZCE_RM,RANKING_TYPE_RAISE_CODE_CFF,"zce"));
        zceList.add(new Param("动力煤",CateType.CZCE_ZC,RANKING_TYPE_RAISE_CODE_CFF,"zce"));
        zceList.add(new Param("硅铁",CateType.CZCE_SF,RANKING_TYPE_RAISE_CODE_CFF,"zce"));
        zceList.add(new Param("锰硅",CateType.CZCE_SM,RANKING_TYPE_RAISE_CODE_CFF,"zce"));
        zceList.add(new Param("棉纱",CateType.CZCE_CY,RANKING_TYPE_RAISE_CODE_CFF,"zce"));
        zceList.add(new Param("白糖",CateType.CZCE_SR,RANKING_TYPE_RAISE_CODE_CFF,"zce"));
        zceList.add(new Param("强麦",CateType.CZCE_WH,RANKING_TYPE_RAISE_CODE_CFF,"zce"));


//        zceList.add(new Param("全部",CateType.ZCEALL,RANKING_TYPE_RAISE_CODE_DZQW,"zce"));
//        zceList.add(new Param("油菜籽",CateType.ZCEYCZ,RANKING_TYPE_RAISE_CODE_DZQW,"zce"));
//        zceList.add(new Param("早籼稻",CateType.ZCEZXD,RANKING_TYPE_RAISE_CODE_DZQW,"zce"));
//        zceList.add(new Param("晚籼稻",CateType.ZCEWXD,RANKING_TYPE_RAISE_CODE_DZQW,"zce"));
//        zceList.add(new Param("小麦",CateType.ZCEXM,RANKING_TYPE_RAISE_CODE_DZQW,"zce"));
//        zceList.add(new Param("锰硅",CateType.ZCEMG,RANKING_TYPE_RAISE_CODE_DZQW,"zce"));
//        zceList.add(new Param("粳稻",CateType.ZCEJD,RANKING_TYPE_RAISE_CODE_DZQW,"zce"));
//        zceList.add(new Param("硅铁",CateType.ZCEGT,RANKING_TYPE_RAISE_CODE_DZQW,"zce"));
//        zceList.add(new Param("菜籽油",CateType.ZCECZY,RANKING_TYPE_RAISE_CODE_DZQW,"zce"));
//        zceList.add(new Param("玻璃",CateType.ZCEBL,RANKING_TYPE_RAISE_CODE_DZQW,"zce"));
//        zceList.add(new Param("白糖",CateType.ZCEBT,RANKING_TYPE_RAISE_CODE_DZQW,"zce"));
//        zceList.add(new Param("PTA",CateType.ZCEPTA,RANKING_TYPE_RAISE_CODE_DZQW,"zce"));
//        zceList.add(new Param("棉花",CateType.ZCEMH,RANKING_TYPE_RAISE_CODE_DZQW,"zce"));
//        zceList.add(new Param("甲醇",CateType.ZCEJC,RANKING_TYPE_RAISE_CODE_DZQW,"zce"));
//        zceList.add(new Param("菜籽粕",CateType.ZCECZP,RANKING_TYPE_RAISE_CODE_DZQW,"zce"));
//        zceList.add(new Param("动力煤",CateType.ZCEDLM,RANKING_TYPE_RAISE_CODE_DZQW,"zce"));
//        zceList.add(new Param("棉纱",CateType.ZCEMS,RANKING_TYPE_RAISE_CODE_DZQW,"zce"));
//        zceList.add(new Param("苹果",CateType.ZCEPG,RANKING_TYPE_RAISE_CODE_DZQW,"zce"));
        //上期所
        ArrayList sqsList=new ArrayList();
        sqsList.add(new Param("全部",CateType.SHFE_ALL,RANKING_TYPE_RAISE_CODE_CFF,"shfe"));
        sqsList.add(new Param("上期所期货合约",CateType.SHFE_FUTURE,RANKING_TYPE_RAISE_CODE_CFF,"shfe"));
        sqsList.add(new Param("上期所期权合约",CateType.SHFE_OPTION,RANKING_TYPE_RAISE_CODE_CFF,"shfe"));
        sqsList.add(new Param("上期所指数合约",CateType.SHFE_INDEX,RANKING_TYPE_RAISE_CODE_CFF,"shfe"));
        sqsList.add(new Param("沪铜",CateType.SHFE_CU,RANKING_TYPE_RAISE_CODE_CFF,"shfe"));
        sqsList.add(new Param("沪铝",CateType.SHFE_AL,RANKING_TYPE_RAISE_CODE_CFF,"shfe"));
        sqsList.add(new Param("燃料油",CateType.SHFE_FU,RANKING_TYPE_RAISE_CODE_CFF,"shfe"));
        sqsList.add(new Param("天然橡胶",CateType.SHFE_RU,RANKING_TYPE_RAISE_CODE_CFF,"shfe"));
        sqsList.add(new Param("沪锌",CateType.SHFE_ZN,RANKING_TYPE_RAISE_CODE_CFF,"shfe"));
        sqsList.add(new Param("黄金",CateType.SHFE_AU,RANKING_TYPE_RAISE_CODE_CFF,"shfe"));
        sqsList.add(new Param("螺纹钢",CateType.SHFE_RB,RANKING_TYPE_RAISE_CODE_CFF,"shfe"));
        sqsList.add(new Param("线材",CateType.SHFE_WR,RANKING_TYPE_RAISE_CODE_CFF,"shfe"));
        sqsList.add(new Param("沪铅",CateType.SHFE_PB,RANKING_TYPE_RAISE_CODE_CFF,"shfe"));
        sqsList.add(new Param("白银",CateType.SHFE_AG,RANKING_TYPE_RAISE_CODE_CFF,"shfe"));
        sqsList.add(new Param("石油沥青",CateType.SHFE_BU,RANKING_TYPE_RAISE_CODE_CFF,"shfe"));
        sqsList.add(new Param("热轧卷板",CateType.SHFE_HC,RANKING_TYPE_RAISE_CODE_CFF,"shfe"));
        sqsList.add(new Param("沪镍",CateType.SHFE_NI,RANKING_TYPE_RAISE_CODE_CFF,"shfe"));
        sqsList.add(new Param("沪锡",CateType.SHFE_SN,RANKING_TYPE_RAISE_CODE_CFF,"shfe"));
        sqsList.add(new Param("纸浆",CateType.SHFE_SP,RANKING_TYPE_RAISE_CODE_CFF,"shfe"));
        sqsList.add(new Param("上期有色金属指数",CateType.SHFE_IMCI,RANKING_TYPE_RAISE_CODE_CFF,"shfe"));
        //上期所原油
        ArrayList sqsyyList=new ArrayList();
        sqsyyList.add(new Param("全部",CateType.INE_ALL,RANKING_TYPE_RAISE_CODE_CFF,"ine"));
        sqsyyList.add(new Param("上期所原油市场期货合约",CateType.INE_FUTURE,RANKING_TYPE_RAISE_CODE_CFF,"ine"));
        sqsyyList.add(new Param("原油",CateType.INE_SC,RANKING_TYPE_RAISE_CODE_CFF,"ine"));
        sListMap.put(STOCK_NAME_QHZJS,zjsList);
        sListMap.put(STOCK_NAME_QHDSS,dceList);
        sListMap.put(STOCK_NAME_QHZSS,zceList);
        sListMap.put(STOCK_NAME_QHSQS,sqsList);
        sListMap.put(STOCK_NAME_QHSQSYY,sqsyyList);

        // 五分钟涨幅榜，增值指标
        ArrayList addvalueList = new ArrayList();
        addvalueList.add(new Param("沪深A股",CateType.SHSZ1001,RANKING_TYPE_RAISE_CODE_ADDVALUE,"addvalue"));
        addvalueList.add(new Param("上证A股",CateType.SH1001,RANKING_TYPE_RAISE_CODE_ADDVALUE,"addvalue"));
        addvalueList.add(new Param("深证A股",CateType.SZ1001,RANKING_TYPE_RAISE_CODE_ADDVALUE,"addvalue"));
        addvalueList.add(new Param("上证B股",CateType.SH1002,RANKING_TYPE_RAISE_CODE_ADDVALUE,"addvalue"));
        addvalueList.add(new Param("深证B股",CateType.SZ1002,RANKING_TYPE_RAISE_CODE_ADDVALUE,"addvalue"));
        addvalueList.add(new Param("创业板",CateType.SZ1004,RANKING_TYPE_RAISE_CODE_ADDVALUE,"addvalue"));
        addvalueList.add(new Param("中小板",CateType.SZ1003,RANKING_TYPE_RAISE_CODE_ADDVALUE,"addvalue"));
        addvalueList.add(new Param("上证基金",CateType.SH1100,RANKING_TYPE_RAISE_CODE_ADDVALUE,"addvalue"));
        addvalueList.add(new Param("深证基金",CateType.SZ1100,RANKING_TYPE_RAISE_CODE_ADDVALUE,"addvalue"));
        addvalueList.add(new Param("ETF基金",CateType.SHSZ1120,RANKING_TYPE_RAISE_CODE_ADDVALUE,"addvalue"));
        addvalueList.add(new Param("LOF基金",CateType.SHSZ1110,RANKING_TYPE_RAISE_CODE_ADDVALUE,"addvalue"));
        addvalueList.add(new Param("上证债券",CateType.SH1300,RANKING_TYPE_RAISE_CODE_ADDVALUE,"addvalue"));
        addvalueList.add(new Param("深证债券",CateType.SZ1300,RANKING_TYPE_RAISE_CODE_ADDVALUE,"addvalue"));
        sListMap.put(STOCK_NAME_ADDVALUE_FIVE,addvalueList);

    }

    public static ParamUtil getInstance(){
       if( null== util){
           util = new ParamUtil();
       }
        return util;
    }

    public  Map<String,Param> getParamMap(){
        return paramMap;
    }

    public Map<String,ArrayList<Param>> getQHParamMap(){
        return sListMap;
    }

    public String getTypeById(String id){
        String type = null;
        switch (id){
            case RANKING_TYPE_RAISE:
                type = RANKING_TYPE_RAISE_NO_CODE;
                break;
            case RANKING_TYPE_DECLINE:
                type = RANKING_TYPE_DECLINE_CODE;
                break;
            case RANKING_TYPE_TURNOVERRATE:
                type = RANKING_TYPE_TURNOVERRATE_CODE;
                break;
            case RANKING_TYPE_AMOUNT:
                type = RANKING_TYPE_AMOUNT_CODE;
                break;
        }
        return type;
    }
}
