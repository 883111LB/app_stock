package com.cvicse.stock;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.Toast;

import com.cvicse.base.skin.LoadTinable;
import com.cvicse.base.skin.SkinConfig;
import com.cvicse.base.skin.ThemeUtils;
import com.cvicse.base.skin.utils.SkinTintFactory;
import com.cvicse.base.utils.ActivityManager;
import com.cvicse.base.utils.ToastUtils;
import com.cvicse.base.widget.BottomTabbar;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.chart.theme.ThemeColor;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.StockConfig;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.cvicse.stock.skin.BottomBarHelper;
import com.cvicse.stock.skin.ColorImageViewHelper;
import com.cvicse.stock.skin.TintPullListViewHelper;
import com.cvicse.stock.skin.TintTabLayoutHelper;
import com.cvicse.stock.skin.ToolBarHelper;
import com.cvicse.stock.utils.CrashHandler;
import com.cvicse.stock.view.ColorImageView;
import com.mitake.core.listener.SSEActivityLifeCycleCallback;
import com.mitake.core.network.NetworkManager;
import com.mitake.variable.object.CommonInfo;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;

/**
 * Created by liu_zlu on 2016/12/22 15:21
 */
public class BaseApplication extends com.cvicse.base.ui.BaseApplication  implements Thread.UncaughtExceptionHandler{

    private String appKey;
    private String infoLevel;

    private final String TAG = "BaseApplication";
    private final int MSG_WHAT = 0x001;
    private final String MSG_MARKETTYPE = "marketType";
    private final String MSG_STATUS = "Status";

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_WHAT:
                    Bundle data = msg.getData();
                    String marketType = data.getString(MSG_MARKETTYPE);
                    String status = data.getString(MSG_STATUS);
                    Toast.makeText(BaseApplication.this, "行情市场"+marketType+"已切换到"+status, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        //设置异常捕获
        Thread.setDefaultUncaughtExceptionHandler(this);
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);

        CrashReport.initCrashReport(getApplicationContext(), "aa22f95151", true);

        // 全真的key
//        appKey = "gzreyQFcje5eWB8m+MSnRZKty3Ibsytt6fAfd2RCqAg=";
        appKey = "J6IPlk5AEU+2/Yi59rfYnsFQtdtOgAo9GAzysx8ciOM=";

        // 初始化注册
        initStockConfig();
        // 切换通知
//        sendInfoLevel();
        // 换肤设置
        setSkinConfig();

        ActivityManager.registerActivityLifecycleCallbacks(new ActivityManager.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity) {
                notifiStytle(activity);
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
        // SDK设置前后台状态监听
        registerActivityLifecycleCallbacks(new SSEActivityLifeCycleCallback());
    }

    /**
     * 初始化注册
     */
    private void initStockConfig() {
        StockConfig.init(this,appKey);
        StockConfig.initPermissions(Setting.getLevel(),Setting.getCffLevel(),Setting.getHKMode(),
                Setting.getHKZSMode(), Setting.getDCELevel(),Setting.getZCELevel(),Setting.getSHFELevel(),
                Setting.getINELevel(),"","",
                Setting.getOLL1(), Setting.getOLSHL1(), Setting.getOLSZL1(), Setting.getOLSHL2(), Setting.getOLSZL2());
        StockConfig.setListener(30000, new StockConfig.Listener() {
            @Override
            public void onRespone(int type) {
                if( StockConfig.success == type ){
                    /*************注册流程 范例*********************/
                    CommonInfo.prodID = getString(R.string.prod_id);
                    CommonInfo.productName = getString(R.string.app_name);
                    CommonInfo.developShowMode = getString(R.string.develop_show_mode);

                    sendInfoLevel(); // 切换通知
                }
            }
        });
    }

    /**
     * 换肤设置
     */
    private void setSkinConfig() {
        SkinConfig.setSkinTintFactory(new SkinTintFactory() {
            @Override
            public void create(ArrayList<LoadTinable> tintables, View view) {
                if(view instanceof ToolBar){
                    tintables.add(new ToolBarHelper((ToolBar) view));
                }
                if(view instanceof TabLayout){
                    tintables.add(new TintTabLayoutHelper((TabLayout) view));
                }
                if(view instanceof ColorImageView){
                    tintables.add(new ColorImageViewHelper((ColorImageView) view));
                }
                if(view instanceof BottomTabbar){
                    tintables.add(new BottomBarHelper((BottomTabbar) view));
                }
                if(view instanceof PullToRefreshListView){
                    tintables.add(new TintPullListViewHelper((PullToRefreshListView) view));
                }
            }
        });
    }

    /**
     * 当发生切换时，发送通知
     */
    private void sendInfoLevel() {
        NetworkManager.getInstance().SetInfoLevelListener(new NetworkManager.IInfoLevelListener() {
            @Override
            public void onChanged( String marketType, String Status) {
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString(MSG_MARKETTYPE,marketType);
                bundle.putString(MSG_STATUS,Status);
                msg.setData(bundle);
                handler.sendMessage(msg);
                ToastUtils.showShortToastSafe("行情市场"+marketType+"已切换到"+Status);
            }
        });
    }

    public void notifiStytle(){
        ArrayList<Activity> activities = ActivityManager.getActivitys();
        for(Activity activity:activities){
            notifiStytle(activity);
            ThemeUtils.refreshUI(activity);
        }
    }

    private void notifiStytle(Activity activity){
        if(!Setting.isDay()){
            ThemeColor.setNight(true);
            activity.setTheme(R.style.tint_defalut);
        } else {
            ThemeColor.setNight(false);
            activity.setTheme(R.style.tint_white);
        }
    }

    /**
     * 权限更新
     */
    public void notifiPermission(){
        StockConfig.initPermissions(Setting.getLevel(),Setting.getCffLevel(),Setting.getHKMode(),
                Setting.getHKZSMode(), Setting.getDCELevel(),Setting.getZCELevel(),
                Setting.getSHFELevel(),Setting.getINELevel(),Setting.getFELevel(),
                Setting.getGILevel(), Setting.getOLL1(), Setting.getOLSHL1(), Setting.getOLSZL1(),
                Setting.getOLSHL2(), Setting.getOLSZL2());
    }

    /**
     * 当有异常产生时执行该方法
     * @param thread 当前线程
     * @param ex 异常信息
     */
    @Override
    public void uncaughtException(final Thread thread, final Throwable ex) {
        SystemClock.sleep(2000);
        Process.killProcess(android.os.Process.myPid());
    }
}
