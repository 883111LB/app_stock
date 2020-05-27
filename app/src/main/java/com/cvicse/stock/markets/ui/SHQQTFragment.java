package com.cvicse.stock.markets.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.utils.StringUtils;
import com.cvicse.base.utils.ToastUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.markets.adapter.SHQQAdapter;
import com.cvicse.stock.markets.data.DateUtil;
import com.cvicse.stock.markets.presenter.contract.SHQQTContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.cvicse.stock.view.CHScrollView;
import com.cvicse.stock.view.CHScrollViewHlper;
import com.cvicse.stock.view.TimeDialog;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * T型价位fragment
 * Created by ding_syi on 17-2-16.
 */
public class SHQQTFragment extends PBaseFragment implements SHQQTContract.View {

    @MVPInject
    SHQQTContract.Presenter mPresenter;

    @BindView(R.id.gou_peninterest) TextView mGouPeninterest;
    @BindView(R.id.gou_amount) TextView mGouAmount;
    @BindView(R.id.gou_volume) TextView mGouVolume;
    @BindView(R.id.gou_sellprice) TextView mGouSellprice;
    @BindView(R.id.gou_buyprice) TextView mGouBuyprice;
    @BindView(R.id.gou_change) TextView mGouChange;
    @BindView(R.id.gou_changerate) TextView mGouChangerate;
    @BindView(R.id.gou_lastprice) TextView mGouLastprice;

    @BindView(R.id.gu_lastprice) TextView mGuLastprice;
    @BindView(R.id.gu_changerate) TextView mGuChangerate;
    @BindView(R.id.gu_change) TextView mGuChange;
    @BindView(R.id.gu_buyprice) TextView mGuBuyprice;
    @BindView(R.id.gu_sellprice) TextView mGuSellprice;
    @BindView(R.id.gu_volume) TextView mGuVolume;
    @BindView(R.id.gu_amount) TextView mGuAmount;
    @BindView(R.id.gu_peninterest) TextView mGuPeninterest;

    @BindView(R.id.left_scroll) CHScrollView leftScrollTitle;//左边的滑动标题
    @BindView(R.id.right_scroll) CHScrollView rightScrollTitle;//右边的滑动标题
    @BindView(R.id.ptrlst_shqq) PullToRefreshListView ptrlstShqq;

    /**
     * 选择的日期
     */
    @BindView(R.id.tev_date) TextView mTevDate;

    /**
     * 剩余的时间
     */
    @BindView(R.id.tev_left_day) TextView mTevLeftDay;

    /**
     * 行权价
     */
    @BindView(R.id.tev_exeprice) TextView tevExePrice;

    public static final String KEY_STOCK_ID = "stockid";
    public static final String KEY_STOCK_NAME = "stockname";

    //默认的
    private static final String DEFAULT_STRING = "510050.sh";

    private SHQQAdapter shqqAdapter;

    //滑动帮助类
    private CHScrollViewHlper chScrollViewHlper;

    private int viewWidth; //一个view的宽度

    /**
     * 日期数组
     */
    private String[] strsDate;

    public static String selectedData = "";//选中的日期

    private ArrayList<QuoteItem> shqqBoListGou = new ArrayList<>(); //认购

    private ArrayList<QuoteItem> shqqBoListGu = new ArrayList<>(); //认沽

    private  String stockId;

    private String currentChooseTime;

    /**
     * 初始化
     *
     *@param stockID 标的证券代码
     *  @param name  股票名称
     */
    public static SHQQTFragment newInstance(String stockID, String name) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_STOCK_ID,stockID);
        bundle.putString(KEY_STOCK_NAME, name);

        SHQQTFragment shqqtFragment = new SHQQTFragment();
        shqqtFragment.setArguments(bundle);

        return shqqtFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shqqt;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        stockId = getArguments().getString(KEY_STOCK_ID,DEFAULT_STRING);

        chScrollViewHlper = new CHScrollViewHlper();
        getViewsWidth();
        chScrollViewHlper.addHViews(rightScrollTitle);
        rightScrollTitle.setLeftOrRight("right");

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                //改变初始位置
                leftScrollTitle.setLeftOrRight("left");
                //左边的Scroll向左移动6个view的单位，使最后两个view在屏幕上
                //leftScrollTitle.scrollBy(viewWidth * 6, 0);
                leftScrollTitle.scrollTo(1000, 0);
            }
        });

       /* //改变初始位置
        leftScrollTitle.setLeftOrRight("left");
        //左边的Scroll向左移动6个view的单位，使最后两个view在屏幕上
        //leftScrollTitle.scrollBy(viewWidth * 6, 0);
        leftScrollTitle.scrollTo(1000, 0);*/

        chScrollViewHlper.addHViews(leftScrollTitle);

        shqqAdapter = new SHQQAdapter(getActivity(), chScrollViewHlper, viewWidth);
        ptrlstShqq.setAdapter(shqqAdapter);

        ptrlstShqq.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//                String[] stockIdSp = stockId.split("\\.");
//                if (stockIdSp.length > 1 && (stockIdSp[1].equals("dce") || stockIdSp[1].equals("czce"))) {
//                    return;
//                }
                mPresenter.requestSHQQT(stockId,currentChooseTime);  //查询T型期权数据
            }
        });
        ptrlstShqq.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int tag = (int) view.getTag();
                if(tag == 1){
                    StockDetailActivity.startActivity(getActivity(),shqqBoListGu,position-1);
                } else {
                    StockDetailActivity.startActivity(getActivity(),shqqBoListGou,position-1);
                }
            }
        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        String[] stockIdSp = stockId.split("\\.");
        if (stockIdSp.length > 1 && (stockIdSp[1].equals("dce") || stockIdSp[1].equals("czce"))) {
            // 大商所、郑商所期权不请求交割月
            String stockName = getArguments().getString(KEY_STOCK_NAME,"");
            if (!"".equals(stockName)) {
                // 时间取名称的后四位
                currentChooseTime = stockName.substring(stockName.length() - 4);
                mPresenter.requestSHQQT(stockId,currentChooseTime);  //查询T型期权数据
            }
            return;
        }
        mPresenter.requestExpire(stockId);  //获取时间
    }


    /**
     * 时间选择单击事件
     */
    @OnClick(R.id.lel_datachooce)
    public void onClick() {
        if (strsDate != null) {
            TimeDialog timeDialog = new TimeDialog(getActivity(), strsDate, mTevDate, mTevLeftDay, new CallBackImpl() {
                @Override
                public void messDialog2Activity(String date) {
                    //date形如"1703"
                    mPresenter.requestSHQQT(stockId,date);  //获取时间
                    currentChooseTime = date;
                }
            });
            timeDialog.show();
        }
    }

    /**
     * 查询T型期权成功
     *
     * @param
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onRequestSHQQTSuccess(ArrayList<QuoteItem> list) {
        ptrlstShqq.onRefreshComplete();
        if(list != null && list.size() >0){
            //期权到期日，expDate，选择的月份 形如"20170322"
            QuoteItem quoteItem = null;
            String[] stockIdSp = stockId.split("\\.");
            if (stockIdSp.length > 1 && (stockIdSp[1].equals("dce") || stockIdSp[1].equals("czce"))) {
                // 大商所期权、郑商所期权
                for(QuoteItem item:list){
                    if(item != null && !StringUtils.isEmpty(item.remainDate)){
                        quoteItem = item;
                        break ;
                    }
                }
                if(quoteItem != null &&quoteItem.remainDate !=null){
                    mTevLeftDay.setText("剩余" + quoteItem.remainDate + "天");
                }else{
                    mTevLeftDay.setText(FillConfig.DEFALUT);
                }
                // 最后交易日
                if (null != quoteItem) {
                    String endDate = quoteItem.endDate;
                    if(endDate != null && endDate.length() == 8){
                        String endDateStr = endDate.substring(0,4) + "年" + endDate.substring(4, 6) + "月" + endDate.substring(6) + "日";
                        mTevDate.setText(endDateStr);
                    }else{
                        mTevDate.setText(FillConfig.DEFALUT);
                    }
                }
            } else {
                // 上证期权、深圳期权
                for(QuoteItem item:list){
                    if(item != null && !StringUtils.isEmpty(item.expDate)){
                        quoteItem = item;
                        break ;
                    }
                }
                if(quoteItem != null &&quoteItem.expDate !=null){
                    mTevLeftDay.setText("剩余" + DateUtil.getRemainDaysForOption(quoteItem.expDate) + "天");
                }else{
                    mTevLeftDay.setText(FillConfig.DEFALUT);
                }
            }
            spiltData(list);
            shqqAdapter.setData(shqqBoListGou,shqqBoListGu);
        }
    }

    /**
     * 查询T型期权失败
     */
    @Override
    public void onRequestSHQQTFail() {

    }

    /**
     * 查询交割月成功
     *
     * @param list
     */
    @Override
    public void requestExpireSuccess(String[] list, String[][] listD) {
        if (null != list && null != listD) {
            strsDate = new String[list.length + listD.length];
            // 放入list的数据
            for (int i = 0; i < list.length; i++) {
                strsDate[i] = list[i];
            }
            // 放入listD的数据
            for (int i = 0; i < listD.length; i++) {
                strsDate[i + list.length] = listD[i][0];
            }
        } else if (null != list) {
            strsDate = list;
        } else if (null != listD) {
            strsDate = new String[listD.length];
            for (int i = 0; i < listD.length; i++) {
                strsDate[i] = listD[i][0];
            }
        } else {
            requestExpireFail();
            return;
        }
        //选择的到期日期,接口返回的第一个时间
        currentChooseTime = strsDate[0];
        mTevDate.setText(DateUtil.OptionExpireDateConvert(strsDate[0]));

        /**
         * 请求T型数据，
         */
        mPresenter.requestSHQQT(stockId,strsDate[0]);
        currentChooseTime = strsDate[0];
    }

    /**
     * 查询交割月失败
     */
    @Override
    public void requestExpireFail() {
        ToastUtils.showLongToast("查询交割月失败");
    }


    /**
     * 按照需求给控件设置宽度
     */
    public void getViewsWidth() {
        //获取屏幕宽度
        WindowManager wm = getActivity().getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();

        chScrollViewHlper.setDisPalyout(width);

        //按照需求将它5等分
        viewWidth = width / 5;
        LinearLayout.LayoutParams linearParams;

        //取控件textView当前的布局参数
        linearParams = new LinearLayout.LayoutParams(viewWidth, LinearLayout.LayoutParams.WRAP_CONTENT);

        mGouPeninterest.setLayoutParams(linearParams);
        mGouAmount.setLayoutParams(linearParams);
        mGouVolume.setLayoutParams(linearParams);
        mGouSellprice.setLayoutParams(linearParams);
        mGouBuyprice.setLayoutParams(linearParams);
        mGouChange.setLayoutParams(linearParams);
        mGouChangerate.setLayoutParams(linearParams);
        mGouLastprice.setLayoutParams(linearParams);
        mGuLastprice.setLayoutParams(linearParams);

        mGuChangerate.setLayoutParams(linearParams);
        mGuChange.setLayoutParams(linearParams);
        mGuBuyprice.setLayoutParams(linearParams);
        mGuSellprice.setLayoutParams(linearParams);
        mGuVolume.setLayoutParams(linearParams);
        mGuAmount.setLayoutParams(linearParams);
        mGuPeninterest.setLayoutParams(linearParams);
        tevExePrice.setLayoutParams(linearParams);

        //取控件textView当前的布局参数
        linearParams = new LinearLayout.LayoutParams(viewWidth * 2, LinearLayout.LayoutParams.WRAP_CONTENT);
        leftScrollTitle.setLayoutParams(linearParams);
        rightScrollTitle.setLayoutParams(linearParams);
    }

    /**
     *  分割认购、认沽数据
     */
    private void spiltData(List<QuoteItem> shqqBoList) {
        shqqBoListGou.clear();
        shqqBoListGu.clear();

        if (shqqBoList != null) {
            for (int i = 0; i < shqqBoList.size(); i++) {
                String gouName = shqqBoList.get(i).name;
                String guName = shqqBoList.get(i).name;
                if (gouName != null && gouName.contains("购")) {
                    shqqBoListGou.add(shqqBoList.get(i));
                } else if (guName!=null && guName.contains("沽"))
                    shqqBoListGu.add(shqqBoList.get(i));
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        selectedData = "";
    }
}