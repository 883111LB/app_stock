package com.cvicse.stock.markets.helper;

import com.cvicse.stock.markets.data.FinanceChartData;
import com.cvicse.stock.markets.data.StockFinanceUtil;
import com.mitake.core.MainFinaDataNas;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.cvicse.stock.markets.data.StockFinanceUtil.HK_FINANCE_LRB_JLR;
import static com.cvicse.stock.markets.data.StockFinanceUtil.HK_FINANCE_LRB_YYLR;
import static com.cvicse.stock.markets.data.StockFinanceUtil.HK_FINANCE_LRB_YYSR;
import static com.cvicse.stock.markets.data.StockFinanceUtil.HK_FINANCE_ZCFZB_FZHJ;
import static com.cvicse.stock.markets.data.StockFinanceUtil.HK_FINANCE_ZCFZB_SYZHJ;
import static com.cvicse.stock.markets.data.StockFinanceUtil.HK_FINANCE_ZCFZB_ZCHJ;
import static com.cvicse.stock.markets.data.StockFinanceUtil.HK_FINANCE_ZYZB_JBMGGJJ;
import static com.cvicse.stock.markets.data.StockFinanceUtil.HK_FINANCE_ZYZB_JBMGSY;
import static com.cvicse.stock.markets.data.StockFinanceUtil.HK_FINANCE_ZYZB_JZCSYL;
import static com.cvicse.stock.markets.data.StockFinanceUtil.HK_FINANCE_ZYZB_MGJZC;
import static com.cvicse.stock.markets.data.StockFinanceUtil.HK_FINANCE_ZYZB_MGXJL;
import static com.cvicse.stock.markets.data.StockFinanceUtil.HK_FINANCE_ZYZB_ZZCSYL;
import static com.cvicse.stock.markets.data.StockFinanceUtil.HK_REPORTTITLE;

/** 财务报表，数值转换帮助类
 * Created by tang_xqing on 2018/4/19.
 */
public class StockFinanceDetailDataHelper {

    /**
     * 根据不同的请求类型，返回对应的数据
     * @param apiType 主要指标、利润表、资产负债表、现金流量表
     * @param infoList
     * @return
     */
    public HashMap<String,List<String>> getFinanceDetailData(String apiType,List<HashMap<String, Object>> infoList){
        HashMap<String,List<String>> dataMap = getProfinmainindexData(infoList);
        switch (apiType){
            case StockFinanceUtil.APITYPE_ZYZB: dataMap = getProfinmainindexData(infoList);break;
            case StockFinanceUtil.APITYPE_LRB: dataMap = getProincstateMentnew(infoList);break;
            case StockFinanceUtil.APITYPE_ZCFZB: dataMap = getProbalsheetnew(infoList);break;
            case StockFinanceUtil.APITYPE_XJLLB: dataMap = getProcfstatementnew(infoList);break;
        }
        return dataMap;
    }

    public HashMap<String,List<String>> getHKFinanceDetailData(String apiType, List<MainFinaDataNas> mainFinaDataNasList){
        HashMap<String,List<String>> dataMap = getHKProfinmainindexData(mainFinaDataNasList);
        switch (apiType){
            case StockFinanceUtil.APITYPE_ZYZB: dataMap = getHKProfinmainindexData(mainFinaDataNasList);break;
            case StockFinanceUtil.APITYPE_LRB: dataMap = getHKProincstateMentnew(mainFinaDataNasList);break;
            case StockFinanceUtil.APITYPE_ZCFZB: dataMap = getHKProbalsheetnew(mainFinaDataNasList);break;
            case StockFinanceUtil.APITYPE_XJLLB: dataMap = getHKProcfstatementnew(mainFinaDataNasList);break;
        }
        return dataMap;
    }

    /**
     * 沪深-- 主要指标
     * @param infoList
     * @return
     */
    public HashMap<String,List<String>> getProfinmainindexData(List<HashMap<String, Object>> infoList){
        HashMap<String,List<String>> listHashMap = new HashMap<>();
        for (HashMap<String, Object> objectHashMap : infoList) {
            String title = (String) objectHashMap.get(StockFinanceUtil.FINANCE_ZYZB_TITLE);
            String epsbasic = (String) objectHashMap.get(StockFinanceUtil.FINANCE_ZYZB_EPSBASIC);
            String epsdiluted = (String) objectHashMap.get(StockFinanceUtil.FINANCE_ZYZB_EPSDILUTED);
            String naps = (String) objectHashMap.get(StockFinanceUtil.FINANCE_ZYZB_NAPS);
            String upps = (String) objectHashMap.get(StockFinanceUtil.FINANCE_ZYZB_UPPS);
            String crps = (String) objectHashMap.get(StockFinanceUtil.FINANCE_ZYZB_CRPS);
            String sgpmargin = (String) objectHashMap.get(StockFinanceUtil.FINANCE_ZYZB_XSMLL);
            String opprort = (String) objectHashMap.get(StockFinanceUtil.FINANCE_ZYZB_OPPRORT);
            String snpmargin = (String) objectHashMap.get(StockFinanceUtil.FINANCE_ZYZB_JLRL);
            String roeweighted = (String) objectHashMap.get(StockFinanceUtil.FINANCE_ZYZB_JQJZCSYL);
            String roediluted = (String) objectHashMap.get(StockFinanceUtil.FINANCE_ZYZB_TBJZCSYL);
            String currentrt = (String) objectHashMap.get(StockFinanceUtil.FINANCE_ZYZB_CURRENTRT);
            String quickrt = (String) objectHashMap.get(StockFinanceUtil.FINANCE_ZYZB_QUICKRT);
            String opncfps = (String) objectHashMap.get(StockFinanceUtil.FINANCE_ZYZB_OPNCFPS);

            listHashMap.put(StockFinanceUtil.FINANCE_ZYZB_TITLE,  getList(StockFinanceUtil.FINANCE_ZYZB_TITLE,title,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZYZB_EPSBASIC,  getList(StockFinanceUtil.FINANCE_ZYZB_EPSBASIC,epsbasic,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZYZB_EPSDILUTED,  getList(StockFinanceUtil.FINANCE_ZYZB_EPSDILUTED,epsdiluted,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZYZB_NAPS,  getList(StockFinanceUtil.FINANCE_ZYZB_NAPS,naps,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZYZB_UPPS,  getList(StockFinanceUtil.FINANCE_ZYZB_UPPS,upps,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZYZB_CRPS,  getList(StockFinanceUtil.FINANCE_ZYZB_CRPS,crps,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZYZB_XSMLL,  getList(StockFinanceUtil.FINANCE_ZYZB_XSMLL,sgpmargin,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZYZB_OPPRORT,  getList(StockFinanceUtil.FINANCE_ZYZB_OPPRORT,opprort,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZYZB_JLRL,  getList(StockFinanceUtil.FINANCE_ZYZB_JLRL,snpmargin,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZYZB_JQJZCSYL,  getList(StockFinanceUtil.FINANCE_ZYZB_JQJZCSYL,roeweighted,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZYZB_TBJZCSYL,  getList(StockFinanceUtil.FINANCE_ZYZB_TBJZCSYL,roediluted,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZYZB_CURRENTRT,  getList(StockFinanceUtil.FINANCE_ZYZB_CURRENTRT,currentrt,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZYZB_QUICKRT,  getList(StockFinanceUtil.FINANCE_ZYZB_QUICKRT,quickrt,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZYZB_OPNCFPS,  getList(StockFinanceUtil.FINANCE_ZYZB_OPNCFPS,opncfps,listHashMap));
        }
        return listHashMap;
    }

    /**
     * 沪深-- 利润表
     * @param infoList
     * @return
     */
    public HashMap<String,List<String>> getProincstateMentnew(List<HashMap<String,Object>> infoList){
        HashMap<String,List<String>> listHashMap = new HashMap<>();
        for (HashMap<String, Object> objectHashMap : infoList) {
            String title = (String) objectHashMap.get(StockFinanceUtil.FINANCE_LRB_TITLE);
            String yysr = (String) objectHashMap.get(StockFinanceUtil.FINANCE_LRB_YYSR);
            String yycb = (String) objectHashMap.get(StockFinanceUtil.FINANCE_LRB_YYCB);
            String glfy =(String) objectHashMap.get(StockFinanceUtil.FINANCE_LRB_GLFY);
            String yyfy =(String) objectHashMap.get(StockFinanceUtil.FINANCE_LRB_YYFY);
            String cwfy =(String) objectHashMap.get(StockFinanceUtil.FINANCE_LRB_CWFY);
            String yylr =(String) objectHashMap.get(StockFinanceUtil.FINANCE_LRB_YYLR);
            String tzsy =(String) objectHashMap.get(StockFinanceUtil.FINANCE_LRB_TZSY);
            String yywszje =(String) objectHashMap.get(StockFinanceUtil.FINANCE_LRB_YYWSZJE);
            String lrze =(String) objectHashMap.get(StockFinanceUtil.FINANCE_LRB_LRZE);
            String jlr =(String) objectHashMap.get(StockFinanceUtil.FINANCE_LRB_JLR);

            listHashMap.put(StockFinanceUtil.FINANCE_LRB_TITLE,  getList(StockFinanceUtil.FINANCE_LRB_TITLE,title,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_LRB_YYSR,  getList(StockFinanceUtil.FINANCE_LRB_YYSR,yysr,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_LRB_YYCB,  getList(StockFinanceUtil.FINANCE_LRB_YYCB,yycb,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_LRB_GLFY,  getList(StockFinanceUtil.FINANCE_LRB_GLFY,glfy,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_LRB_YYFY,  getList(StockFinanceUtil.FINANCE_LRB_YYFY,yyfy,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_LRB_CWFY,  getList(StockFinanceUtil.FINANCE_LRB_CWFY,cwfy,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_LRB_YYLR,  getList(StockFinanceUtil.FINANCE_LRB_YYLR,yylr,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_LRB_TZSY,  getList(StockFinanceUtil.FINANCE_LRB_TZSY,tzsy,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_LRB_YYWSZJE,  getList(StockFinanceUtil.FINANCE_LRB_YYWSZJE,yywszje,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_LRB_LRZE,  getList(StockFinanceUtil.FINANCE_LRB_LRZE,lrze,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_LRB_JLR,  getList(StockFinanceUtil.FINANCE_LRB_JLR,jlr,listHashMap));
        }
        return listHashMap;
    }
    /**
     * 沪深-- 资产负债表
     * @param infoList
     * @return
     */
    public HashMap<String,List<String>> getProbalsheetnew(List<HashMap<String,Object>> infoList){
        HashMap<String,List<String>> listHashMap = new HashMap<>();
        for (HashMap<String, Object> objectHashMap : infoList) {
            String title = (String) objectHashMap.get(StockFinanceUtil.FINANCE_ZCFZB_TITLE);
            String zzc = (String)objectHashMap.get(StockFinanceUtil.FINANCE_ZCFZB_ZZC);
            String ldzc = (String)objectHashMap.get(StockFinanceUtil.FINANCE_ZCFZB_LDZC);
            String hbzj = (String)objectHashMap.get(StockFinanceUtil.FINANCE_ZCFZB_HBZJ);
            String jyzjrzc = (String)objectHashMap.get(StockFinanceUtil.FINANCE_ZCFZB_JYXJRZC);
            String ch = (String)objectHashMap.get(StockFinanceUtil.FINANCE_ZCFZB_CH);
            String yszk = (String)objectHashMap.get(StockFinanceUtil.FINANCE_ZCFZB_YSZK);
            String qtysk = (String)objectHashMap.get(StockFinanceUtil.FINANCE_ZCFZB_QTYSK);
            String gdzcje = (String) objectHashMap.get(StockFinanceUtil.FINANCE_ZCFZB_GDZCJE);
            String kgcsjezc = (String)objectHashMap.get(StockFinanceUtil.FINANCE_ZCFZB_KGCSJEZC);
            String wxzc = (String)objectHashMap.get(StockFinanceUtil.FINANCE_ZCFZB_WXZC);
            String dqjk = (String)objectHashMap.get(StockFinanceUtil.FINANCE_ZCFZB_DQJK);
            String yszkm = (String)objectHashMap.get(StockFinanceUtil.FINANCE_ZCFZB_YSZKM);
            String yfzk = (String)objectHashMap.get(StockFinanceUtil.FINANCE_ZCFZB_YFZK);
            String ldfz = (String)objectHashMap.get(StockFinanceUtil.FINANCE_ZCFZB_LDFZ);
            String cqfz = (String) objectHashMap.get(StockFinanceUtil.FINANCE_ZCFZB_CQFZ);
            String zfz = (String)objectHashMap.get(StockFinanceUtil.FINANCE_ZCFZB_ZFZ);
            String gdqy = (String)objectHashMap.get(StockFinanceUtil.FINANCE_ZCFZB_GDQY);
            String zcgjj = (String)objectHashMap.get(StockFinanceUtil.FINANCE_ZCFZB_ZCGJJ);
            String sy=(String)objectHashMap.get(StockFinanceUtil.FINANCE_ZCFZB_SY);

            listHashMap.put(StockFinanceUtil.FINANCE_ZCFZB_TITLE,  getList(StockFinanceUtil.FINANCE_ZCFZB_TITLE,title,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZCFZB_ZZC,  getList(StockFinanceUtil.FINANCE_ZCFZB_ZZC,zzc,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZCFZB_LDZC,  getList(StockFinanceUtil.FINANCE_ZCFZB_LDZC,ldzc,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZCFZB_HBZJ,  getList(StockFinanceUtil.FINANCE_ZCFZB_HBZJ,hbzj,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZCFZB_JYXJRZC,  getList(StockFinanceUtil.FINANCE_ZCFZB_JYXJRZC,jyzjrzc,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZCFZB_CH,  getList(StockFinanceUtil.FINANCE_ZCFZB_CH,ch,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZCFZB_YSZK,  getList(StockFinanceUtil.FINANCE_ZCFZB_YSZK,yszk,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZCFZB_QTYSK,  getList(StockFinanceUtil.FINANCE_ZCFZB_QTYSK,qtysk,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZCFZB_GDZCJE,  getList(StockFinanceUtil.FINANCE_ZCFZB_GDZCJE,gdzcje,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZCFZB_KGCSJEZC,  getList(StockFinanceUtil.FINANCE_ZCFZB_KGCSJEZC,kgcsjezc,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZCFZB_WXZC,  getList(StockFinanceUtil.FINANCE_ZCFZB_WXZC,wxzc,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZCFZB_DQJK,  getList(StockFinanceUtil.FINANCE_ZCFZB_DQJK,dqjk,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZCFZB_YSZKM,  getList(StockFinanceUtil.FINANCE_ZCFZB_YSZKM,yszkm,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZCFZB_YFZK,  getList(StockFinanceUtil.FINANCE_ZCFZB_YFZK,yfzk,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZCFZB_LDFZ,  getList(StockFinanceUtil.FINANCE_ZCFZB_LDFZ,ldfz,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZCFZB_CQFZ,  getList(StockFinanceUtil.FINANCE_ZCFZB_CQFZ,cqfz,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZCFZB_ZFZ,  getList(StockFinanceUtil.FINANCE_ZCFZB_ZFZ,zfz,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZCFZB_GDQY,  getList(StockFinanceUtil.FINANCE_ZCFZB_GDQY,gdqy,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZCFZB_ZCGJJ,  getList(StockFinanceUtil.FINANCE_ZCFZB_ZCGJJ,zcgjj,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_ZCFZB_SY, getList(StockFinanceUtil.FINANCE_ZCFZB_SY,sy,listHashMap));
        }
        return listHashMap;
    }
    /**
     * 沪深-- 现金流量表
     * @param infoList
     * @return
     */
    public HashMap<String,List<String>> getProcfstatementnew(List<HashMap<String,Object>> infoList){
        HashMap<String,List<String>> listHashMap = new HashMap<>();
        for (HashMap<String, Object> objectHashMap : infoList) {
            String title = (String) objectHashMap.get(StockFinanceUtil.FINANCE_XJLLB_TITLE);
            String jyxjlr = (String) objectHashMap.get(StockFinanceUtil.FINANCE_XJLLB_JYXJLR);
            String jyxjc = (String) objectHashMap.get(StockFinanceUtil.FINANCE_XJLLB_JYXJLC);
            String jyxjllje = (String) objectHashMap.get(StockFinanceUtil.FINANCE_XJLLB_JYXJLLJE);
            String tzxjlr = (String) objectHashMap.get(StockFinanceUtil.FINANCE_XJLLB_TZXJLR);
            String tzxjlc = (String) objectHashMap.get(StockFinanceUtil.FINANCE_XJLLB_TZXJLC);
            String tzxjllje = (String) objectHashMap.get(StockFinanceUtil.FINANCE_XJLLB_TZXJLLJE);
            String czxjlr = (String) objectHashMap.get(StockFinanceUtil.FINANCE_XJLLB_CZXJLR);
            String czxjlc = (String) objectHashMap.get(StockFinanceUtil.FINANCE_XJLLB_CZXJLC);
            String czxjllje = (String) objectHashMap.get(StockFinanceUtil.FINANCE_XJLLB_CZXJLLJE);
            String xjdjeje = (String) objectHashMap.get(StockFinanceUtil.FINANCE_XJLLB_XJDJEJE);

            listHashMap.put(StockFinanceUtil.FINANCE_XJLLB_TITLE,  getList(StockFinanceUtil.FINANCE_XJLLB_TITLE,title,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_XJLLB_JYXJLR,  getList(StockFinanceUtil.FINANCE_XJLLB_JYXJLR,jyxjlr,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_XJLLB_JYXJLC,  getList(StockFinanceUtil.FINANCE_XJLLB_JYXJLC,jyxjc,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_XJLLB_JYXJLLJE,  getList(StockFinanceUtil.FINANCE_XJLLB_JYXJLLJE,jyxjllje,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_XJLLB_TZXJLR,  getList(StockFinanceUtil.FINANCE_XJLLB_TZXJLR,tzxjlr,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_XJLLB_TZXJLC,  getList(StockFinanceUtil.FINANCE_XJLLB_TZXJLC,tzxjlc,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_XJLLB_TZXJLLJE,  getList(StockFinanceUtil.FINANCE_XJLLB_TZXJLLJE,tzxjllje,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_XJLLB_CZXJLR,  getList(StockFinanceUtil.FINANCE_XJLLB_CZXJLR,czxjlr,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_XJLLB_CZXJLC,  getList(StockFinanceUtil.FINANCE_XJLLB_CZXJLC,czxjlc,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_XJLLB_CZXJLLJE,  getList(StockFinanceUtil.FINANCE_XJLLB_CZXJLLJE,czxjllje,listHashMap));
            listHashMap.put(StockFinanceUtil.FINANCE_XJLLB_XJDJEJE,  getList(StockFinanceUtil.FINANCE_XJLLB_XJDJEJE,xjdjeje,listHashMap));
        }
        return listHashMap;
    }

    /**
     * 港股-- 现金流量表
     * @param mainFinaDataNasList
     * @return
     */
    private HashMap<String, List<String>> getHKProcfstatementnew(List<MainFinaDataNas> mainFinaDataNasList) {
        HashMap<String,List<String>> infoList = new HashMap<>();
        for (MainFinaDataNas mainFinaDataNas : mainFinaDataNasList) {
            infoList.put(HK_REPORTTITLE,getList(HK_REPORTTITLE,mainFinaDataNas.REPORTTITLE_,infoList));
            infoList.put(StockFinanceUtil.HK_FINANCE_XJLLB_JYXJLLJE,getList(StockFinanceUtil.HK_FINANCE_XJLLB_JYXJLLJE,mainFinaDataNas.TotalOperRevenue,infoList));
            infoList.put(StockFinanceUtil.HK_FINANCE_XJLLB_TZXJLLJE,getList(StockFinanceUtil.HK_FINANCE_XJLLB_TZXJLLJE,mainFinaDataNas.OperProfit,infoList));
            infoList.put(StockFinanceUtil.HK_FINANCE_XJLLB_CZXJLLJE,getList(StockFinanceUtil.HK_FINANCE_XJLLB_CZXJLLJE,mainFinaDataNas.NetProfit,infoList));
            infoList.put(StockFinanceUtil.HK_FINANCE_XJLLB_XJDJJZE,getList(StockFinanceUtil.HK_FINANCE_XJLLB_XJDJJZE,mainFinaDataNas.NetProfit,infoList));
        }
        return infoList;
    }

    /**
     * 港股-- 资产负债表
     * @param mainFinaDataNasList
     * @return
     */
    private HashMap<String, List<String>> getHKProbalsheetnew(List<MainFinaDataNas> mainFinaDataNasList) {
        HashMap<String,List<String>> infoList = new HashMap<>();
        for (MainFinaDataNas mainFinaDataNas : mainFinaDataNasList) {
            infoList.put(HK_REPORTTITLE,getList(HK_REPORTTITLE,mainFinaDataNas.REPORTTITLE_,infoList));
            infoList.put(HK_FINANCE_ZCFZB_ZCHJ,getList(HK_FINANCE_ZCFZB_ZCHJ,mainFinaDataNas.TotalOperRevenue,infoList));
            infoList.put(HK_FINANCE_ZCFZB_FZHJ,getList(HK_FINANCE_ZCFZB_FZHJ,mainFinaDataNas.OperProfit,infoList));
            infoList.put(HK_FINANCE_ZCFZB_SYZHJ,getList(HK_FINANCE_ZCFZB_SYZHJ,mainFinaDataNas.NetProfit,infoList));
        }
        return infoList;
    }

    /**
     * 港股-- 利润表
     * @param mainFinaDataNasList
     * @return
     */
    private HashMap<String, List<String>> getHKProincstateMentnew(List<MainFinaDataNas> mainFinaDataNasList) {
        HashMap<String,List<String>> infoList = new HashMap<>();
        for (MainFinaDataNas mainFinaDataNas : mainFinaDataNasList) {
            infoList.put(HK_REPORTTITLE,getList(HK_REPORTTITLE,mainFinaDataNas.REPORTTITLE_,infoList));
            infoList.put(HK_FINANCE_LRB_YYSR,getList(HK_FINANCE_LRB_YYSR,mainFinaDataNas.TotalOperRevenue,infoList));
            infoList.put(HK_FINANCE_LRB_YYLR,getList(HK_FINANCE_LRB_YYLR,mainFinaDataNas.OperProfit,infoList));
            infoList.put(HK_FINANCE_LRB_JLR,getList(HK_FINANCE_LRB_JLR,mainFinaDataNas.NetProfit,infoList));
        }
        return infoList;
    }

    /**
     * 港股-- 主要指标
     * @param mainFinaDataNasList
     * @return
     */
    private HashMap<String, List<String>> getHKProfinmainindexData(List<MainFinaDataNas> mainFinaDataNasList) {
        HashMap<String,List<String>> infoList = new HashMap<>();
        for (MainFinaDataNas mainFinaDataNas : mainFinaDataNasList) {
            infoList.put(HK_REPORTTITLE,getList(HK_REPORTTITLE,mainFinaDataNas.REPORTTITLE_,infoList));
            infoList.put(HK_FINANCE_ZYZB_JBMGSY,getList(HK_FINANCE_ZYZB_JBMGSY,mainFinaDataNas.BasicEPS,infoList));
            infoList.put(HK_FINANCE_ZYZB_JBMGGJJ,getList(HK_FINANCE_ZYZB_JBMGGJJ,mainFinaDataNas.RESERVEPS_,infoList));
            infoList.put(HK_FINANCE_ZYZB_MGJZC,getList(HK_FINANCE_ZYZB_MGJZC,mainFinaDataNas.BVPS_,infoList));
            infoList.put(HK_FINANCE_ZYZB_MGXJL,getList(HK_FINANCE_ZYZB_MGXJL,mainFinaDataNas.NETCASHFLOWOPERPS_,infoList));
            infoList.put(HK_FINANCE_ZYZB_JZCSYL,getList(HK_FINANCE_ZYZB_JZCSYL,mainFinaDataNas.WEIGHTEDROE_,infoList));
            infoList.put(HK_FINANCE_ZYZB_ZZCSYL,getList(HK_FINANCE_ZYZB_ZZCSYL,mainFinaDataNas.ROA_,infoList));
        }
        return infoList;
    }

    /**
     * 沪深财务对比，数值转换
     * @param type 基本每股收益、营业收入、营业成本等
     * @param infoList
     * @return
     */
    public List<FinanceChartData> getFinanceChartData(String type,List<HashMap<String,Object>> infoList){
        List<FinanceChartData> list = new ArrayList<>();
        for (int i = 0; i < infoList.size(); i++) {
            HashMap<String, Object> objectHashMap = infoList.get(i);
            String enddate = (String) objectHashMap.get("ENDDATE");
            enddate = (null == enddate ? (String) objectHashMap.get(HK_REPORTTITLE) : enddate);
            String value = (String) objectHashMap.get(type);
            String mom = (String) objectHashMap.get("MOM");
            FinanceChartData financeChartData = new FinanceChartData(enddate,value,mom);
            list.add(financeChartData);
        }
        return list;
    }

    /**
     * 港股财务对比，数值转换
     * @param type
     * @param mainFinaDataNasList
     * @return
     */
    public List<FinanceChartData> getHKFinanceChartData(String type,List<MainFinaDataNas> mainFinaDataNasList){
        List<FinanceChartData> list = new ArrayList<>();
        for (int i = 0; i < mainFinaDataNasList.size(); i++) {
            String hkField = StockFinanceUtil.getHKField(type);
            MainFinaDataNas mainFinaDataNas = mainFinaDataNasList.get(i);
            String value = "";
            try {
                // 字段
                Field declaredField = mainFinaDataNas.getClass().getDeclaredField(hkField);
                value = (String) declaredField.get(mainFinaDataNas);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            FinanceChartData financeChartData = new FinanceChartData(mainFinaDataNas.ENDDATE,value, mainFinaDataNas.MOM);
            list.add(financeChartData);
        }
        return list;
    }

    private List<String> getList(String key,String value,HashMap<String,List<String>> listHashMap){
        List<String> list = (null == listHashMap.get(key)) ? new ArrayList<String>() :listHashMap.get(key);
        list.add(value);
        listHashMap.put(key,  list);
        return list;
    }
}
