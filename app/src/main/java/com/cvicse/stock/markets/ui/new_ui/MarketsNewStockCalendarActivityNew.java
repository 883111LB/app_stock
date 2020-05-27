package com.cvicse.stock.markets.ui.new_ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.markets.presenter.contract.NewStockCalendarContract;
import com.mitake.newshare.NewShareDateFram;
import com.mitake.newshare.util.NewShareDateSkinHelper;
import com.mitake.newshare.widget.OnNewShareItemClickListener;

import javax.inject.Inject;

import butterknife.BindView;
import cn.aigestudio.datepicker.bizs.themes.DPCNTheme;
import cn.aigestudio.datepicker.bizs.themes.DPTManager;
import cn.aigestudio.datepicker.bizs.themes.WhiteDPTheme;


/**
 * Created by ruan_ytai on 17-2-22.
 */

public class MarketsNewStockCalendarActivityNew extends PBaseActivity implements  OnNewShareItemClickListener {

    @BindView(R.id.fry_calendar)
    FrameLayout fryCalendar;

    private FragmentManager fragmentManager;

    private NewShareDateFram fragmentNewShareDate;

    @Inject NewStockCalendarContract.Presenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.calendar_selector_new;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        if (Setting.isNight()) {
            DPTManager.getInstance().initCalendar(new DPCNTheme());
        } else {
            DPTManager.getInstance().initCalendar(new WhiteDPTheme());
        }
        fragmentManager = getSupportFragmentManager();
        /*************注册流程 范例*********************/
//        CommonInfo.prodID = this.getString(R.string.prod_id);
//        CommonInfo.productName = this.getString(R.string.app_name);
//        CommonInfo.developShowMode = this.getString(R.string.develop_show_mode);

        intoNewShareDemo();
    }

    private void intoNewShareDemo() {
        fragmentNewShareDate = new NewShareDateFram();
        NewShareDateSkinHelper.setCalendarDefaultColor( ColorUtils.CALENDER_DEFALUT);
        NewShareDateSkinHelper.setCalendarholidayColor( ColorUtils.CALENDER_HOLIDAY);
        NewShareDateSkinHelper.setCalendarSelectedColor(ColorUtils.CALENDER_SELECTED);
        NewShareDateSkinHelper.setCalendarTextSize(10);   // 日历下文字的大小
        NewShareDateSkinHelper.setCalenderDateTextColor(ColorUtils.BLACK);  // 日历下方日期文字颜色
        NewShareDateSkinHelper.setCodeTextSize(10);  //股票代码文字大小
        NewShareDateSkinHelper.setCanlendarTextColor(ColorUtils.BLACK);  // 日历下文字颜色(除日期)
        NewShareDateSkinHelper.setNewShareCodeTextColor(ColorUtils.BLACK);  // 股票代码文字颜色

        NewShareDateSkinHelper.setNewShareListTitleTextColor(ColorUtils.TEXT_BLUE); // 设置新股列表分类title文字颜色
        NewShareDateSkinHelper.setTitleTextSize(14);  // 分类title文字大小
        NewShareDateSkinHelper.setSubTitleTextSize(12); // 副标题文字大小
        NewShareDateSkinHelper.setNewShareSubTitleTextColor(ColorUtils.BLACK); //副标题文字颜色
        NewShareDateSkinHelper.setNormalTextColor(ColorUtils.BLACK); // 设置一般文字颜色，列表里的内容
        NewShareDateSkinHelper.setNormalTextSize(12); // 设置一般文字大小，列表里的内容

        // NewShareDateSkinHelper.setNewShareDetailTitleBG(0xffFF4081); // 设置新股详情title背景颜色
//        NewShareDateSkinHelper.setNewShareListLineColor(0xff181D21); // 新股列表分割线颜色
//        NewShareDateSkinHelper.setNewShareListTitleBG(0xffFF4081);
        fragmentNewShareDate.setOffSetindex(2); //设置日历红点定位位置  0-最左边 2-中间

        fragmentNewShareDate.refreshView();  // 更新界面
        fragmentNewShareDate.setOnNewShareItemClickListener(this);  // item的点击事件监听
        fragmentManager.beginTransaction().replace(R.id.fry_calendar, fragmentNewShareDate, "NewShareDateFram").addToBackStack("NewShareDateFram").commit();
    }

    @Override
    protected void initData() {
        fragmentNewShareDate.refreshData();  // 更新数据
    }

    /**
     *设置item点击事件的监听
     * @param code
     * @param name
     */
    @Override
    public void setOnNewShareItemClick(String code, String name) {
        NewStockDetailActivityNew.actionStart(this,code,name);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
