package com.cvicse.stock.markets.ui.stockdetail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.utils.LogUtils;
import com.cvicse.base.utils.ToastUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.markets.helper.StockBarCharCharHelper;
import com.cvicse.stock.markets.helper.StockPieCharHelper;
import com.cvicse.stock.markets.helper.StockPlateHlper;
import com.cvicse.stock.markets.presenter.marketdetail.contract.StockPKContract;
import com.cvicse.stock.utils.sqliteutils.StockTurnoverBo;
import com.cvicse.stock.utils.sqliteutils.StockTurnoverDBDao;
import com.google.gson.Gson;
import com.mitake.core.AddValueModel;
import com.mitake.core.response.Bankuaisorting;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 成交页面
 * Created by Lenovo on 2017/8/16.
 */

public class StockTurnoverFragment extends PBaseFragment implements StockPKContract.View {
    private static final String STOCKID = "STOCKID";
    private static final String MARKET = "MARKET";
    private static final String SUBTYPE = "SUBTYPE";

    private String stockId;   // 股票代码
    private String market;   // 市场
    private String subtype;   // 子类别
    private ArrayList<AddValueModel> mAddValueModels;

    // 饼图帮助类( 成交统计 )
    private StockPieCharHelper mStockPieCharHelper;
    // 柱状图帮助类( 资金流向 )
    private StockBarCharCharHelper mStockBarCharCharHelper;
    // 所属板块帮助类
    private StockPlateHlper mStockPlateHlper;
    // 所有板块类型
    private List<String> allList;
    // 请求个股所属板块的第二个参数（地区板块）
    private String param = "trade,area,notion,trade_sw,trade_sw1,notion_szyp,area_szyp,trade_szyp";

    @BindView(R.id.tev_setting)
    TextView tev_setting;// 个股所属板块组合配置按钮

    @MVPInject
    StockPKContract.Presenter presenter;

    public static StockTurnoverFragment newInstance(String stockId, String market, String subtype) {
        StockTurnoverFragment stockTurnoverFragment = new StockTurnoverFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STOCKID, stockId);
        bundle.putString(MARKET, market);
        bundle.putString(SUBTYPE, subtype);
        stockTurnoverFragment.setArguments(bundle);
        return stockTurnoverFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stock_pk;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        stockId = getArguments().getString(STOCKID);
        market = getArguments().getString(MARKET);
        subtype = getArguments().getString(SUBTYPE);

        mStockPieCharHelper = new StockPieCharHelper(getActivity(),getView());
        mStockBarCharCharHelper = new StockBarCharCharHelper(getActivity(),getView());
        mStockPlateHlper = new StockPlateHlper(getActivity(),getView());
        settingBk();
        presenter.init(stockId, null, null);
    }

    /**
     * 个股所属板块组合配置按钮
     */
    private void settingBk() {
        // 所有板块类型添加
        allList = new ArrayList<>();
        allList.add("trade");// 证监会行业
        allList.add("area");// 地区板块
        allList.add("notion");// 概念板块
        allList.add("trade_sw");// 申万二级行业
        allList.add("trade_sw1");// 申万一级行业
        allList.add("notion_szyp");// 优品概念板块
        allList.add("area_szyp");// 优品地区板块
        allList.add("trade_szyp");// 优品行业板块
        // 默认保存全部板块类型
        String[] ids = {stockId};
        List<StockTurnoverBo> list = StockTurnoverDBDao.getInstance().query(ids);
        if (null == list || list.size() == 0) {
            saveAll();
        }
        tev_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    @Override
    protected void initData() {
         presenter.requestAddValue(market, subtype);
         // 查询
        final String[] ids = {stockId};
        List<StockTurnoverBo> list = StockTurnoverDBDao.getInstance().query(ids);
        presenter.requestBankuaiQuote(stockId, null);
    }

    /**
     * 获取增值指标成功
     */
    @Override
    public void onRequestAddValueSuccess(ArrayList<AddValueModel> addValueModel) {
        this.mAddValueModels = addValueModel;

        // 成交统计
        mStockPieCharHelper.setData(mAddValueModels);

        // 资金流向
        mStockBarCharCharHelper.setData(mAddValueModels);
    }

    /**
     * 获取增值指标失败
     */
    @Override
    public void onRequestAddValueFail() {

    }

    /**
     * 请求个股所属板块成功
     */
    @Override
    public void onRequestBankuaiQuoteSuccess(List<Bankuaisorting> bankuaisortingList) {
        if( null != bankuaisortingList && !bankuaisortingList.isEmpty() ){
          mStockPlateHlper.setData(bankuaisortingList);
        }
    }

    /**
     * 请求个股所属板块失败
     */
    @Override
    public void onRequestBankuaiQuoteFail(int code, String message) {
        LogUtils.e("code = "+code+" message = "+message);
    }

    /**
     * 个股所属板块支持任意组合搭配, 默认保存本地全部展示
     */
    private void saveAll() {
        StockTurnoverDBDao.getInstance().clearOne(stockId);
        for (int i = 0; i < allList.size(); i++) {
            StockTurnoverDBDao.getInstance().insert(new StockTurnoverBo(stockId, allList.get(i)));
        }
    }

    /**
     * 选择展示的所属板块-弹框
     */
    boolean[] itemsIsChecked;
    public void showDialog() {
        if (null == allList || allList.size() == 0) {
            return;
        }
        String[] items = {"证监会行业", "地区板块", "概念板块", "申万二级行业", "申万一级行业", "优品概念板块", "优品地区板块", "优品行业板块"};
        itemsIsChecked = new boolean[allList.size()];// 板块名称对应的“是否已选中”
        final String[] ids = {stockId};
        List<StockTurnoverBo> list = StockTurnoverDBDao.getInstance().query(ids);
        for (int i = 0; i < allList.size(); i++) {
            String code = allList.get(i);
            itemsIsChecked[i] = true;
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getCode().equals(code)) {
                    break;
                }
                if (j == list.size() - 1) {
                    // 如果遍历结束没有匹配的，就说明未选中
                    itemsIsChecked[i] = false;
                }
            }
        }

        new AlertDialog.Builder(getContext(), R.style.AlertDialog)
                .setTitle("所属板块配置").setMultiChoiceItems(
                items, itemsIsChecked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        itemsIsChecked[which] = isChecked;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!new Gson().toJson(itemsIsChecked).contains("true")) {
                            ToastUtils.showLongToast("请至少选择一项");
                            return;
                        }
                        // 更新存本地的数据
                        StockTurnoverDBDao.getInstance().clearOne(stockId);
                        String params = "";
                        for (int i = 0; i < allList.size(); i++) {
                            if (itemsIsChecked[i]) {
                                params = params + allList.get(i);
                                StockTurnoverDBDao.getInstance().insert(new StockTurnoverBo(stockId, allList.get(i)));
                            }
                        }
                        // 取到保存在本地数据库的list
                        List<StockTurnoverBo> list = StockTurnoverDBDao.getInstance().query(ids);
                        // 更新所属板块列表
                        presenter.requestBankuaiQuote(stockId, params);
                    }
                })
                .setNegativeButton("取消", null).show();
    }
}
