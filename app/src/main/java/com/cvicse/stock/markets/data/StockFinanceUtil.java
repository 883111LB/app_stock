package com.cvicse.stock.markets.data;

import com.mitake.core.QuoteItem;
import com.mitake.core.request.F10Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** 财务报表，静态字段帮助类
 * Created by tang_xqing on 2018/4/19.
 */
public class StockFinanceUtil {

    public static final String EPSBASIC_0 = "EPSBASIC_0";  // 所有报告期
    public static final String EPSBASIC_1 = "EPSBASIC_1";  // 一季度
    public static final String EPSBASIC_2 = "EPSBASIC_2";  // 二季度
    public static final String EPSBASIC_3 = "EPSBASIC_3";  // 三季度
    public static final String EPSBASIC_4 = "EPSBASIC_4";  // 四季度

    public static final String APITYPE_ZYZB = F10Type.D_PROFINMAININDEX;  // 主要指标
    public static final String APITYPE_LRB = F10Type.D_PROINCSTATEMENTNEW;  // 利润表
    public static final String APITYPE_ZCFZB = F10Type.D_PROBALSHEETNEW;  // 资产负债表
    public static final String APITYPE_XJLLB = F10Type.D_PROCFSTATEMENTNEW;  // 现金流量表

    private  HashMap<String,List<Param>> paramMap = new HashMap<>();
    private  HashMap<String,List<Param>> paramMapHK = new HashMap<>();
    public static StockFinanceUtil financeUtil;

    // 主要指标
    public String[] titleZYZB = {StockFinanceUtil.FINANCE_ZYZB_EPSBASIC_NAME,StockFinanceUtil.FINANCE_ZYZB_EPSDILUTED_NAME,StockFinanceUtil.FINANCE_ZYZB_NAPS_NAME,
            StockFinanceUtil.FINANCE_ZYZB_UPPS_NAME,StockFinanceUtil.FINANCE_ZYZB_CRPS_NAME,StockFinanceUtil.FINANCE_ZYZB_XSMLL_NAME,StockFinanceUtil.FINANCE_ZYZB_OPPRORT_NAME,StockFinanceUtil.FINANCE_ZYZB_JLRL_NAME,
            StockFinanceUtil.FINANCE_ZYZB_JQJZCSYL_NAME,StockFinanceUtil.FINANCE_ZYZB_TBJZCSYL_NAME,StockFinanceUtil.FINANCE_ZYZB_CURRENTRT_NAME,StockFinanceUtil.FINANCE_ZYZB_QUICKRT_NAME,StockFinanceUtil.FINANCE_ZYZB_OPNCFPS_NAME};

    public String[] codeZYZB = {StockFinanceUtil.FINANCE_ZYZB_TITLE,StockFinanceUtil.FINANCE_ZYZB_EPSBASIC,StockFinanceUtil.FINANCE_ZYZB_EPSDILUTED,
            StockFinanceUtil.FINANCE_ZYZB_NAPS,StockFinanceUtil.FINANCE_ZYZB_UPPS,StockFinanceUtil.FINANCE_ZYZB_CRPS,StockFinanceUtil.FINANCE_ZYZB_XSMLL,
            StockFinanceUtil.FINANCE_ZYZB_OPPRORT,StockFinanceUtil.FINANCE_ZYZB_JLRL,StockFinanceUtil.FINANCE_ZYZB_JQJZCSYL,StockFinanceUtil.FINANCE_ZYZB_TBJZCSYL,
            StockFinanceUtil.FINANCE_ZYZB_CURRENTRT,StockFinanceUtil.FINANCE_ZYZB_QUICKRT,StockFinanceUtil.FINANCE_ZYZB_OPNCFPS};

    public static final String FINANCE_ZYZB_TITLE = "REPORTTITLE";
    public static final String FINANCE_ZYZB_TITLE_NAME = "标题";
    public static final String FINANCE_ZYZB_EPSBASIC = "EPSBASIC";
    public static final String FINANCE_ZYZB_EPSBASIC_NAME = "基本每股收益(元)";
    public static final String FINANCE_ZYZB_EPSDILUTED= "EPSDILUTED";
    public static final String FINANCE_ZYZB_EPSDILUTED_NAME = "摊薄每股收益(元)";
    public static final String FINANCE_ZYZB_NAPS = "NAPS";
    public static final String FINANCE_ZYZB_NAPS_NAME = "每股净资产(元)";
    public static final String FINANCE_ZYZB_UPPS = "UPPS";
    public static final String FINANCE_ZYZB_UPPS_NAME = "每股未分配利润(元)";
    public static final String FINANCE_ZYZB_CRPS = "CRPS";
    public static final String FINANCE_ZYZB_CRPS_NAME = "每股公积金(元)";
    public static final String FINANCE_ZYZB_XSMLL = "SGPMARGIN";
    public static final String FINANCE_ZYZB_XSMLL_NAME = "销售毛利润(元)";
    public static final String FINANCE_ZYZB_OPPRORT = "OPPRORT";
    public static final String FINANCE_ZYZB_OPPRORT_NAME = "营业利润率";
    public static final String FINANCE_ZYZB_JLRL = "SNPMARGIN";
    public static final String FINANCE_ZYZB_JLRL_NAME = "净利润率";
    public static final String FINANCE_ZYZB_JQJZCSYL = "ROEWEIGHTED";
    public static final String FINANCE_ZYZB_JQJZCSYL_NAME = "加权净资产收益率";
    public static final String FINANCE_ZYZB_TBJZCSYL = "ROEDILUTED";
    public static final String FINANCE_ZYZB_TBJZCSYL_NAME = "摊薄净资产收益率";
    public static final String FINANCE_ZYZB_CURRENTRT = "CURRENTRT";
    public static final String FINANCE_ZYZB_CURRENTRT_NAME = "流动比率";
    public static final String FINANCE_ZYZB_QUICKRT = "QUICKRT";
    public static final String FINANCE_ZYZB_QUICKRT_NAME = "速冻比率";
    public static final String FINANCE_ZYZB_OPNCFPS = "OPNCFPS";
    public static final String FINANCE_ZYZB_OPNCFPS_NAME = "每股经营现金流量(元)";

    // 利润表
    public String[] titleLRB = {StockFinanceUtil.FINANCE_LRB_YYSR_NAME,StockFinanceUtil.FINANCE_LRB_YYCB_NAME,StockFinanceUtil.FINANCE_LRB_GLFY_NAME,StockFinanceUtil.FINANCE_LRB_YYFY_NAME,StockFinanceUtil.FINANCE_LRB_CWFY_NAME,
            StockFinanceUtil.FINANCE_LRB_YYLR_NAME,StockFinanceUtil.FINANCE_LRB_TZSY_NAME,StockFinanceUtil.FINANCE_LRB_YYWSZJE_NAME,StockFinanceUtil.FINANCE_LRB_LRZE_NAME,StockFinanceUtil.FINANCE_LRB_JLR_NAME};

    public String[] codeLRB = {FINANCE_LRB_TITLE,FINANCE_LRB_YYSR,FINANCE_LRB_YYCB,FINANCE_LRB_GLFY,FINANCE_LRB_YYFY,FINANCE_LRB_CWFY,FINANCE_LRB_YYLR,
            FINANCE_LRB_TZSY,FINANCE_LRB_YYWSZJE,FINANCE_LRB_LRZE,FINANCE_LRB_JLR,};

    public static final String FINANCE_LRB_TITLE = "REPORTTITLE";
    public static final String FINANCE_LRB_TITLE_NAME = "标题";
    public static final String FINANCE_LRB_YYSR = "BIZINCO";
    public static final String FINANCE_LRB_YYSR_NAME = "营业收入";
    public static final String FINANCE_LRB_YYCB= "BIZCOST";
    public static final String FINANCE_LRB_YYCB_NAME= "营业成本";
    public static final String FINANCE_LRB_GLFY = "MANAEXPE";
    public static final String FINANCE_LRB_GLFY_NAME = "管理费用";
    public static final String FINANCE_LRB_YYFY = "SALESEXPE";
    public static final String FINANCE_LRB_YYFY_NAME = "营业费用";
    public static final String FINANCE_LRB_CWFY = "FINEXPE";
    public static final String FINANCE_LRB_CWFY_NAME = "财务费用";
    public static final String FINANCE_LRB_YYLR = "PERPROFIT";
    public static final String FINANCE_LRB_YYLR_NAME = "营业利润";
    public static final String FINANCE_LRB_TZSY = "INVEINCO";
    public static final String FINANCE_LRB_TZSY_NAME = "投资收益";
    public static final String FINANCE_LRB_YYWSZJE = "NONOPERINCOMEN";
    public static final String FINANCE_LRB_YYWSZJE_NAME = "营业外收支净额";
    public static final String FINANCE_LRB_LRZE = "TOTPROFIT";
    public static final String FINANCE_LRB_LRZE_NAME = "利润总额";
    public static final String FINANCE_LRB_JLR = "PARENETP";
    public static final String FINANCE_LRB_JLR_NAME = "净利润";

    // 资产负债表
    public  String[]  titleZCFZB = {StockFinanceUtil.FINANCE_ZCFZB_ZZC_NAME, StockFinanceUtil.FINANCE_ZCFZB_LDZC_NAME, StockFinanceUtil.FINANCE_ZCFZB_HBZJ_NAME, StockFinanceUtil.FINANCE_ZCFZB_JYXJRZC_NAME,
            StockFinanceUtil.FINANCE_ZCFZB_CH_NAME, StockFinanceUtil.FINANCE_ZCFZB_YSZK_NAME, StockFinanceUtil.FINANCE_ZCFZB_QTYSK_NAME, StockFinanceUtil.FINANCE_ZCFZB_GDZCJE_NAME, StockFinanceUtil.FINANCE_ZCFZB_KGCSJEZC_NAME,
            StockFinanceUtil.FINANCE_ZCFZB_WXZC_NAME, StockFinanceUtil.FINANCE_ZCFZB_DQJK_NAME, FINANCE_ZCFZB_YSZKM_NAME,StockFinanceUtil.FINANCE_ZCFZB_YFZK_NAME, StockFinanceUtil.FINANCE_ZCFZB_LDFZ_NAME, StockFinanceUtil.FINANCE_ZCFZB_CQFZ_NAME,
            StockFinanceUtil.FINANCE_ZCFZB_ZFZ_NAME, StockFinanceUtil.FINANCE_ZCFZB_GDQY_NAME, StockFinanceUtil.FINANCE_ZCFZB_ZCGJJ_NAME,StockFinanceUtil.FINANCE_ZCFZB_SY_NAME};

    public String[] codeZCFZB = {FINANCE_ZCFZB_TITLE,FINANCE_ZCFZB_ZZC,FINANCE_ZCFZB_LDZC,FINANCE_ZCFZB_HBZJ,FINANCE_ZCFZB_JYXJRZC,FINANCE_ZCFZB_CH,
            FINANCE_ZCFZB_YSZK,FINANCE_ZCFZB_QTYSK,FINANCE_ZCFZB_GDZCJE,FINANCE_ZCFZB_KGCSJEZC,FINANCE_ZCFZB_WXZC,FINANCE_ZCFZB_DQJK,FINANCE_ZCFZB_YSZKM,
            FINANCE_ZCFZB_YFZK,FINANCE_ZCFZB_LDFZ,FINANCE_ZCFZB_CQFZ,FINANCE_ZCFZB_ZFZ,FINANCE_ZCFZB_GDQY,FINANCE_ZCFZB_ZCGJJ,FINANCE_ZCFZB_SY};
    public static final String FINANCE_ZCFZB_TITLE = "REPORTTITLE";
    public static final String FINANCE_ZCFZB_ZZC = "TOTLIABSHAREQUI";
    public static final String FINANCE_ZCFZB_ZZC_NAME = "总资产";
    public static final String FINANCE_ZCFZB_LDZC= "TOTCURRASSET";
    public static final String FINANCE_ZCFZB_LDZC_NAME= "流动资产";
    public static final String FINANCE_ZCFZB_HBZJ = "CURFDS";
    public static final String FINANCE_ZCFZB_HBZJ_NAME = "货币资金";
    public static final String FINANCE_ZCFZB_JYXJRZC = "TRADFINASSET";
    public static final String FINANCE_ZCFZB_JYXJRZC_NAME = "交易性金融资产";
    public static final String FINANCE_ZCFZB_CH = "INVE";
    public static final String FINANCE_ZCFZB_CH_NAME = "存货";
    public static final String FINANCE_ZCFZB_YSZK = "ACCORECE";
    public static final String FINANCE_ZCFZB_YSZK_NAME = "应收账款";
    public static final String FINANCE_ZCFZB_QTYSK = "OTHERRECE";
    public static final String FINANCE_ZCFZB_QTYSK_NAME = "其他应收款";
    public static final String FINANCE_ZCFZB_GDZCJE = "FIXEDASSENET";
    public static final String FINANCE_ZCFZB_GDZCJE_NAME = "固定资产净额";
    public static final String FINANCE_ZCFZB_KGCSJEZC = "AVAISELLASSE";
    public static final String FINANCE_ZCFZB_KGCSJEZC_NAME = "可供出售金额资产";
    public static final String FINANCE_ZCFZB_WXZC = "INTAASSET";
    public static final String FINANCE_ZCFZB_WXZC_NAME = "无形资产";
    public static final String FINANCE_ZCFZB_DQJK = "SHORTTERMBORR";
    public static final String FINANCE_ZCFZB_DQJK_NAME = "短期借款";
    public static final String FINANCE_ZCFZB_YSZKM = "ADVAPAYM";
    public static final String FINANCE_ZCFZB_YSZKM_NAME = "预收账款";
    public static final String FINANCE_ZCFZB_YFZK = "ACCOPAYA";
    public static final String FINANCE_ZCFZB_YFZK_NAME = "应收账款";
    public static final String FINANCE_ZCFZB_LDFZ = "TOTALCURRLIAB";
    public static final String FINANCE_ZCFZB_LDFZ_NAME = "流动负债";
    public static final String FINANCE_ZCFZB_CQFZ = "SUNEVENNONCLIAB";
    public static final String FINANCE_ZCFZB_CQFZ_NAME = "长期负债";
    public static final String FINANCE_ZCFZB_ZFZ = "TOTLIAB";
    public static final String FINANCE_ZCFZB_ZFZ_NAME = "总负债";
    public static final String FINANCE_ZCFZB_GDQY = "PARESHARRIGH";
    public static final String FINANCE_ZCFZB_GDQY_NAME = "股东权益";
    public static final String FINANCE_ZCFZB_ZCGJJ = "CAPISURP";
    public static final String FINANCE_ZCFZB_ZCGJJ_NAME = "资产公积金";
    public static final String FINANCE_ZCFZB_SY="GOODWILL";
    public static final String FINANCE_ZCFZB_SY_NAME="商誉";

    // 现金流量表
    public  String[] titleXJLLB = {StockFinanceUtil.FINANCE_XJLLB_JYXJLR_NAME, StockFinanceUtil.FINANCE_XJLLB_JYXJLC_NAME, StockFinanceUtil.FINANCE_XJLLB_JYXJLLJE_NAME, StockFinanceUtil.FINANCE_XJLLB_TZXJLR_NAME,
            StockFinanceUtil.FINANCE_XJLLB_TZXJLC_NAME, StockFinanceUtil.FINANCE_XJLLB_TZXJLLJE_NAME, StockFinanceUtil.FINANCE_XJLLB_CZXJLR_NAME, StockFinanceUtil.FINANCE_XJLLB_CZXJLC_NAME, StockFinanceUtil.FINANCE_XJLLB_CZXJLLJE_NAME,
            StockFinanceUtil.FINANCE_XJLLB_XJDJEJE_NAME};
    public String[] codeXJLLB = {FINANCE_XJLLB_TITLE,FINANCE_XJLLB_JYXJLR,FINANCE_XJLLB_JYXJLC,FINANCE_XJLLB_JYXJLLJE,FINANCE_XJLLB_TZXJLR,FINANCE_XJLLB_TZXJLC,
            FINANCE_XJLLB_TZXJLLJE,FINANCE_XJLLB_CZXJLR,FINANCE_XJLLB_CZXJLC,FINANCE_XJLLB_CZXJLLJE,FINANCE_XJLLB_XJDJEJE};

    public static final String FINANCE_XJLLB_TITLE = "REPORTTITLE";
    public static final String FINANCE_XJLLB_JYXJLR = "BIZCASHINFL";
    public static final String FINANCE_XJLLB_JYXJLR_NAME = "经营现金流入小计";
    public static final String FINANCE_XJLLB_JYXJLC = "BIZCASHOUTF";
    public static final String FINANCE_XJLLB_JYXJLC_NAME = "经营现金流出小计 ";
    public static final String FINANCE_XJLLB_JYXJLLJE = "MANANETR";
    public static final String FINANCE_XJLLB_JYXJLLJE_NAME = "经营现金流量净额";
    public static final String FINANCE_XJLLB_TZXJLR = "INVCASHINFL";
    public static final String FINANCE_XJLLB_TZXJLR_NAME = "投资现金流入小计";
    public static final String FINANCE_XJLLB_TZXJLC = "INVCASHOUTF";
    public static final String FINANCE_XJLLB_TZXJLC_NAME = "投资现金流出小计";
    public static final String FINANCE_XJLLB_TZXJLLJE = "INVNETCASHFLOW";
    public static final String FINANCE_XJLLB_TZXJLLJE_NAME = "投资现金流量净额";
    public static final String FINANCE_XJLLB_CZXJLR = "FINCASHINFL";
    public static final String FINANCE_XJLLB_CZXJLR_NAME = "筹资现金流入小计";
    public static final String FINANCE_XJLLB_CZXJLC = "FINCASHOUTF";
    public static final String FINANCE_XJLLB_CZXJLC_NAME = "筹资现金流出小计";
    public static final String FINANCE_XJLLB_CZXJLLJE = "FINNETCFLOW";
    public static final String FINANCE_XJLLB_CZXJLLJE_NAME = "筹资现金流量净额";
    public static final String FINANCE_XJLLB_XJDJEJE = "CASHNETR";
    public static final String FINANCE_XJLLB_XJDJEJE_NAME = "现金等的净额加额";


    public String[] hkTitleZYZB = {HK_FINANCE_ZYZB_JBMGSY_NAME,HK_FINANCE_ZYZB_JBMGGJJ_NAME,HK_FINANCE_ZYZB_MGJZC_NAME,HK_FINANCE_ZYZB_MGXJL_NAME,HK_FINANCE_ZYZB_JJZCSYL_NAME,HK_FINANCE_ZYZB_ZZCSYL_NAME};
    public String[] hkCodeZYZB = {HK_REPORTTITLE,HK_FINANCE_ZYZB_JBMGSY,HK_FINANCE_ZYZB_JBMGGJJ,HK_FINANCE_ZYZB_MGJZC,HK_FINANCE_ZYZB_MGXJL,HK_FINANCE_ZYZB_JZCSYL,HK_FINANCE_ZYZB_ZZCSYL};
    public static final String HK_REPORTTITLE = "REPORTTITLE";
    public static final String HK_FINANCE_ZYZB_JBMGSY = "BASICEPS";
    public static final String HK_FINANCE_ZYZB_JBMGSY_NAME = "基本每股收益";
    public static final String HK_FINANCE_ZYZB_JBMGGJJ= "RESERVEPS";
    public static final String HK_FINANCE_ZYZB_JBMGGJJ_NAME = "基本每股公积金";
    public static final String HK_FINANCE_ZYZB_MGJZC = "BVPS";
    public static final String HK_FINANCE_ZYZB_MGJZC_NAME = "每股净资产";
    public static final String HK_FINANCE_ZYZB_MGXJL = "NETCASHFLOWOPERPS";
    public static final String HK_FINANCE_ZYZB_MGXJL_NAME = "每股现金流";
    public static final String HK_FINANCE_ZYZB_JZCSYL = "WEIGHTEDROE";
    public static final String HK_FINANCE_ZYZB_JJZCSYL_NAME = "净资产收益率";
    public static final String HK_FINANCE_ZYZB_ZZCSYL = "ROA";
    public static final String HK_FINANCE_ZYZB_ZZCSYL_NAME = "总资产收益率";

    public String[] hkTitleLRB = {HK_FINANCE_LRB_YYSR_NAME,HK_FINANCE_LRB_YYLR_NAME,HK_FINANCE_LRB_JLR_NAME};
    public String[] hkCodeLRB = {HK_REPORTTITLE,HK_FINANCE_LRB_YYSR,HK_FINANCE_LRB_YYLR,HK_FINANCE_LRB_JLR};
    public static final String HK_FINANCE_LRB_YYSR = "TOTALOPERREVENUE";
    public static final String HK_FINANCE_LRB_YYSR_NAME = "营业收入";
    public static final String HK_FINANCE_LRB_YYLR = "OPERPROFIT";
    public static final String HK_FINANCE_LRB_YYLR_NAME = "营业利润";
    public static final String HK_FINANCE_LRB_JLR = "NETPROFIT";
    public static final String HK_FINANCE_LRB_JLR_NAME = "净利润";

    public String[] hkTitleZCFZB = {HK_FINANCE_ZCFZB_ZCHJ_NAME,HK_FINANCE_ZCFZB_FZHJ_NAME,HK_FINANCE_ZCFZB_SYZHJ_NAME};
    public String[] hkCodeZCFZB = {HK_REPORTTITLE,HK_FINANCE_ZCFZB_ZCHJ,HK_FINANCE_ZCFZB_FZHJ,HK_FINANCE_ZCFZB_SYZHJ};
    public static final String HK_FINANCE_ZCFZB_ZCHJ = "TOTALASSET";
    public static final String HK_FINANCE_ZCFZB_ZCHJ_NAME = "资产合计";
    public static final String HK_FINANCE_ZCFZB_FZHJ = "TOTALLIAB";
    public static final String HK_FINANCE_ZCFZB_FZHJ_NAME = "负债合计";
    public static final String HK_FINANCE_ZCFZB_SYZHJ = "TOTALSHEQUITY";
    public static final String HK_FINANCE_ZCFZB_SYZHJ_NAME = "所有者权益合计";

    public String[] hkTitleXJLLB = {HK_FINANCE_XJLLB_JYXJLLJE_NAME,HK_FINANCE_XJLLB_TZXJLLJE_NAME,HK_FINANCE_XJLLB_CZXJLLJE_NAME,HK_FINANCE_XJLLB_XJDJJZE_NAME};
    public String[] hkCodeXJLLB = {HK_REPORTTITLE,HK_FINANCE_XJLLB_JYXJLLJE,HK_FINANCE_XJLLB_TZXJLLJE,HK_FINANCE_XJLLB_CZXJLLJE,HK_FINANCE_XJLLB_XJDJJZE};
    public static final String HK_FINANCE_XJLLB_JYXJLLJE = "NETCASHFLOWOPER";
    public static final String HK_FINANCE_XJLLB_JYXJLLJE_NAME = "经营现金流量净额";
    public static final String HK_FINANCE_XJLLB_TZXJLLJE = "NETCASHFLOWINV";
    public static final String HK_FINANCE_XJLLB_TZXJLLJE_NAME = "投资现金流量净额";
    public static final String HK_FINANCE_XJLLB_CZXJLLJE = "NETCASHFLOWFINA";
    public static final String HK_FINANCE_XJLLB_CZXJLLJE_NAME = "筹资现金流量净额";
    public static final String HK_FINANCE_XJLLB_XJDJJZE = "CASHEQUINETINCR";
    public static final String HK_FINANCE_XJLLB_XJDJJZE_NAME = "现金及现金等价净增额";

    private static QuoteItem quoteItem;

    private StockFinanceUtil(){
        // 主要指标
        List<Param> list = new ArrayList<>();
        list.add(new Param(FINANCE_ZYZB_EPSBASIC,FINANCE_ZYZB_EPSBASIC_NAME));
        list.add(new Param(FINANCE_ZYZB_EPSDILUTED,FINANCE_ZYZB_EPSDILUTED_NAME));
        list.add(new Param(FINANCE_ZYZB_NAPS,FINANCE_ZYZB_NAPS_NAME));
        list.add(new Param(FINANCE_ZYZB_UPPS,FINANCE_ZYZB_UPPS_NAME));
        list.add(new Param(FINANCE_ZYZB_CRPS,FINANCE_ZYZB_CRPS_NAME));
        list.add(new Param(FINANCE_ZYZB_XSMLL,FINANCE_ZYZB_XSMLL_NAME));
        list.add(new Param(FINANCE_ZYZB_OPPRORT,FINANCE_ZYZB_OPPRORT_NAME));
        list.add(new Param(FINANCE_ZYZB_JLRL,FINANCE_ZYZB_JLRL_NAME));
        list.add(new Param(FINANCE_ZYZB_JQJZCSYL,FINANCE_ZYZB_JQJZCSYL_NAME));
        list.add(new Param(FINANCE_ZYZB_TBJZCSYL,FINANCE_ZYZB_TBJZCSYL_NAME));
        list.add(new Param(FINANCE_ZYZB_CURRENTRT,FINANCE_ZYZB_CURRENTRT_NAME));
        list.add(new Param(FINANCE_ZYZB_QUICKRT,FINANCE_ZYZB_QUICKRT_NAME));
        list.add(new Param(FINANCE_ZYZB_OPNCFPS,FINANCE_ZYZB_OPNCFPS_NAME));
        paramMap.put(F10Type.D_PROFINMAININDEX,list);

        // 利润表
        list = new ArrayList<>();
        list.add(new Param(FINANCE_LRB_YYSR,FINANCE_LRB_YYSR_NAME));
        list.add(new Param(FINANCE_LRB_YYCB,FINANCE_LRB_YYCB_NAME));
        list.add(new Param(FINANCE_LRB_GLFY,FINANCE_LRB_GLFY_NAME));
        list.add(new Param(FINANCE_LRB_YYFY,FINANCE_LRB_YYFY_NAME));
        list.add(new Param(FINANCE_LRB_CWFY,FINANCE_LRB_CWFY_NAME));
        list.add(new Param(FINANCE_LRB_YYLR,FINANCE_LRB_YYLR_NAME));
        list.add(new Param(FINANCE_LRB_TZSY,FINANCE_LRB_TZSY_NAME));
        list.add(new Param(FINANCE_LRB_YYWSZJE,FINANCE_LRB_YYWSZJE_NAME));
        list.add(new Param(FINANCE_LRB_LRZE,FINANCE_LRB_LRZE_NAME));
        list.add(new Param(FINANCE_LRB_JLR,FINANCE_LRB_JLR_NAME));
        paramMap.put(F10Type.D_PROINCSTATEMENTNEW,list);

        // 资产负债表
        list = new ArrayList<>();
        list.add(new Param(FINANCE_ZCFZB_ZZC,FINANCE_ZCFZB_ZZC_NAME));
        list.add(new Param(FINANCE_ZCFZB_LDZC,FINANCE_ZCFZB_LDZC_NAME));
        list.add(new Param(FINANCE_ZCFZB_HBZJ,FINANCE_ZCFZB_HBZJ_NAME));
        list.add(new Param(FINANCE_ZCFZB_JYXJRZC,FINANCE_ZCFZB_JYXJRZC_NAME));
        list.add(new Param(FINANCE_ZCFZB_CH,FINANCE_ZCFZB_CH_NAME));
        list.add(new Param(FINANCE_ZCFZB_YSZK,FINANCE_ZCFZB_YSZK_NAME));
        list.add(new Param(FINANCE_ZCFZB_QTYSK,FINANCE_ZCFZB_QTYSK_NAME));
        list.add(new Param(FINANCE_ZCFZB_GDZCJE,FINANCE_ZCFZB_GDZCJE_NAME));
        list.add(new Param(FINANCE_ZCFZB_KGCSJEZC,FINANCE_ZCFZB_KGCSJEZC_NAME));
        list.add(new Param(FINANCE_ZCFZB_WXZC,FINANCE_ZCFZB_WXZC_NAME));
        list.add(new Param(FINANCE_ZCFZB_DQJK,FINANCE_ZCFZB_DQJK_NAME));
        list.add(new Param(FINANCE_ZCFZB_YSZKM,FINANCE_ZCFZB_YSZKM_NAME));
        list.add(new Param(FINANCE_ZCFZB_YFZK,FINANCE_ZCFZB_YFZK_NAME));
        list.add(new Param(FINANCE_ZCFZB_LDFZ,FINANCE_ZCFZB_LDFZ_NAME));
        list.add(new Param(FINANCE_ZCFZB_CQFZ,FINANCE_ZCFZB_CQFZ_NAME));
        list.add(new Param(FINANCE_ZCFZB_ZFZ,FINANCE_ZCFZB_ZFZ_NAME));
        list.add(new Param(FINANCE_ZCFZB_GDQY,FINANCE_ZCFZB_GDQY_NAME));
        list.add(new Param(FINANCE_ZCFZB_ZCGJJ,FINANCE_ZCFZB_ZCGJJ_NAME));
        list.add(new Param(FINANCE_ZCFZB_SY,FINANCE_ZCFZB_SY_NAME));
        paramMap.put(F10Type.D_PROBALSHEETNEW,list);

        //现金流量表
        list = new ArrayList<>();
        list.add(new Param(FINANCE_XJLLB_JYXJLR,FINANCE_XJLLB_JYXJLR_NAME));
        list.add(new Param(FINANCE_XJLLB_JYXJLC,FINANCE_XJLLB_JYXJLC_NAME));
        list.add(new Param(FINANCE_XJLLB_JYXJLLJE,FINANCE_XJLLB_JYXJLLJE_NAME));
        list.add(new Param(FINANCE_XJLLB_TZXJLR,FINANCE_XJLLB_TZXJLR_NAME));
        list.add(new Param(FINANCE_XJLLB_TZXJLC,FINANCE_XJLLB_TZXJLC_NAME));
        list.add(new Param(FINANCE_XJLLB_TZXJLLJE,FINANCE_XJLLB_TZXJLLJE_NAME));
        list.add(new Param(FINANCE_XJLLB_CZXJLR,FINANCE_XJLLB_CZXJLR_NAME));
        list.add(new Param(FINANCE_XJLLB_CZXJLC,FINANCE_XJLLB_CZXJLC_NAME));
        list.add(new Param(FINANCE_XJLLB_CZXJLLJE,FINANCE_XJLLB_CZXJLLJE_NAME));
        list.add(new Param(FINANCE_XJLLB_XJDJEJE,FINANCE_XJLLB_XJDJEJE_NAME));
        paramMap.put(F10Type.D_PROCFSTATEMENTNEW,list);

        list = new ArrayList<>();
        list.add(new Param(HK_FINANCE_ZYZB_JBMGSY,HK_FINANCE_ZYZB_JBMGSY_NAME));
        list.add(new Param(HK_FINANCE_ZYZB_JBMGGJJ,HK_FINANCE_ZYZB_JBMGGJJ_NAME));
        list.add(new Param(HK_FINANCE_ZYZB_MGJZC,HK_FINANCE_ZYZB_MGJZC_NAME));
        list.add(new Param(HK_FINANCE_ZYZB_MGXJL,HK_FINANCE_ZYZB_MGXJL_NAME));
        list.add(new Param(HK_FINANCE_ZYZB_JZCSYL,HK_FINANCE_ZYZB_JJZCSYL_NAME));
        list.add(new Param(HK_FINANCE_ZYZB_ZZCSYL,HK_FINANCE_ZYZB_ZZCSYL_NAME));
        paramMapHK.put(F10Type.D_PROFINMAININDEX,list);

        list = new ArrayList<>();
        list.add(new Param(HK_FINANCE_LRB_YYSR,HK_FINANCE_LRB_YYSR_NAME));
        list.add(new Param(HK_FINANCE_LRB_YYLR,HK_FINANCE_LRB_YYLR_NAME));
        list.add(new Param(HK_FINANCE_LRB_JLR,HK_FINANCE_LRB_JLR_NAME));
        paramMapHK.put(F10Type.D_PROINCSTATEMENTNEW,list);

        list = new ArrayList<>();
        list.add(new Param(HK_FINANCE_ZCFZB_ZCHJ,HK_FINANCE_ZCFZB_ZCHJ_NAME));
        list.add(new Param(HK_FINANCE_ZCFZB_FZHJ,HK_FINANCE_ZCFZB_FZHJ_NAME));
        list.add(new Param(HK_FINANCE_ZCFZB_SYZHJ,HK_FINANCE_ZCFZB_SYZHJ_NAME));
        paramMapHK.put(F10Type.D_PROBALSHEETNEW,list);

        list = new ArrayList<>();
        list.add(new Param(HK_FINANCE_XJLLB_JYXJLLJE,HK_FINANCE_XJLLB_JYXJLLJE_NAME));
        list.add(new Param(HK_FINANCE_XJLLB_TZXJLLJE,HK_FINANCE_XJLLB_TZXJLLJE_NAME));
        list.add(new Param(HK_FINANCE_XJLLB_CZXJLLJE,HK_FINANCE_XJLLB_CZXJLLJE_NAME));
        list.add(new Param(HK_FINANCE_XJLLB_XJDJJZE,HK_FINANCE_XJLLB_XJDJJZE_NAME));
        paramMapHK.put(F10Type.D_PROCFSTATEMENTNEW,list);
    }

    public static StockFinanceUtil getInstance(QuoteItem quote){
        if( null == financeUtil ){
            financeUtil = new StockFinanceUtil();
        }
        quoteItem = quote;
        return financeUtil;
    }


    public static String getHKField(String type){
        switch (type){
            case "BASICEPS": return "BasicEPS";
            case "RESERVEPS": return "RESERVEPS_";
            case "BVPS": return "BVPS_";
            case "NETCASHFLOWOPERPS": return "NETCASHFLOWOPERPS_";
            case "WEIGHTEDROE": return "WEIGHTEDROE_";
            case "ROA": return "ROA_";
            case "TOTALOPERREVENUE": return "TotalOperRevenue";
            case "OPERPROFIT": return "OperProfit";
            case "NETPROFIT": return "NetProfit";
            case "TOTALASSET": return "TotalAsset";
            case "TOTALLIAB": return "TotalLiab";
            case "TOTALSHEQUITY": return "TotalSHEquity";
            case "NETCASHFLOWOPER": return "NetCashFlowOper";
            case "NETCASHFLOWINV": return "NetCashFlowInv";
            case "NETCASHFLOWFINA": return "NetCashFlowFina";
            case "CASHEQUINETINCR": return "CashEquiNetIncr";
        }
        return "BasicEPS";
    }

    /**
     * 根据请求类型，返回子类型标题
     */
    public String[] getTitleByQuarterType(String type){
        String[] title = titleZYZB;
        switch (type){
            case StockFinanceUtil.APITYPE_ZYZB: title = "hk".equals(quoteItem.market)? hkTitleZYZB: titleZYZB;break;
            case StockFinanceUtil.APITYPE_LRB: title = "hk".equals(quoteItem.market)? hkTitleLRB: titleLRB;break;
            case StockFinanceUtil.APITYPE_ZCFZB: title = "hk".equals(quoteItem.market)? hkTitleZCFZB: titleZCFZB;break;
            case StockFinanceUtil.APITYPE_XJLLB: title = "hk".equals(quoteItem.market)? hkTitleXJLLB: titleXJLLB;break;
        }
        return title;
    }

    /**
     * 根据请求类型，返回限定字段
     * @param type
     * @return
     */
    public String[] getCodeByQuarterType(String type){
        String[] code = codeZYZB;
        switch (type){
            case StockFinanceUtil.APITYPE_ZYZB: code ="hk".equals(quoteItem.market)? hkCodeZYZB: codeZYZB;break;
            case StockFinanceUtil.APITYPE_LRB: code = "hk".equals(quoteItem.market)? hkCodeLRB: codeLRB;break;
            case StockFinanceUtil.APITYPE_ZCFZB: code = "hk".equals(quoteItem.market)? hkCodeZCFZB: codeZCFZB;break;
            case StockFinanceUtil.APITYPE_XJLLB: code = "hk".equals(quoteItem.market)? hkCodeXJLLB: codeXJLLB;break;
        }
        return code;
    }

    public HashMap<String,List<Param>> getParamMap(){
        return paramMap;
    }

    public List<Param> getDataListByType(String type){
        if( null != quoteItem && null != quoteItem.market && "hk".equals(quoteItem.market)){
            return paramMapHK.get(type);
        }
        return paramMap.get(type);
    }

    /**
     * 根据请求类型，返回标题
     * @param apiType
     * @return
     */
    public String getNameByApiType(String apiType){
        String name = "主要指标";
        switch (apiType){
            case StockFinanceUtil.APITYPE_ZYZB: name = "主要指标";break;
            case StockFinanceUtil.APITYPE_LRB: name = "利润表";break;
            case StockFinanceUtil.APITYPE_ZCFZB: name = "资产负债表";break;
            case StockFinanceUtil.APITYPE_XJLLB: name = "现金流量表";break;
        }
        return name;
    }
}
