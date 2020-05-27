package com.cvicse.stock.personal;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvicse.base.utils.ToastUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.FileHelper;
import com.cvicse.stock.personal.data.CleanMessageUtil;
import com.mitake.core.bean.log.ErrorInfo;
import com.mitake.core.request.RegisterRequest;
import com.mitake.core.response.IResponseInfoCallback;
import com.mitake.core.response.RegisterResponse;
import com.mitake.core.util.Permissions;

import butterknife.BindView;


/**
 * Created by ruan_ytai on 16-12-30.
 */

public class SettingDetailActivity extends PBaseActivity implements View.OnClickListener {

    @BindView(R.id.tev_refreshrate) TextView mTevRefreshrate;
    @BindView(R.id.rel_refreshrate) RelativeLayout mRelRefreshrate;

    @BindView(R.id.tev_requestmode) TextView mTevMode;
    @BindView(R.id.rel_requestmode) RelativeLayout mRelRequestmode;

    @BindView(R.id.tev_ollevel) TextView tev_ollevel;// 境外权限设置
    @BindView(R.id.rel_ollevel) RelativeLayout rel_ollevel;// 境外权限设置

    @BindView(R.id.tev_timeout_reminder) TextView mTevTimeoutReminder;
    @BindView(R.id.rel_timeout_reminder) RelativeLayout mRelTimeoutReminder;

    @BindView(R.id.rel_push_time) RelativeLayout mRelPushTime;

    @BindView(R.id.tev_login_ip) TextView mTevLoginIp;
    @BindView(R.id.rel_login_ip) RelativeLayout mRelLogin;

    @BindView(R.id.tev_skin_type) TextView mTevSkinType;
    @BindView(R.id.rel_skin_type) RelativeLayout mRelChangeSkin;

    @BindView(R.id.tev_cache_size) TextView mTevCacheSize;
    @BindView(R.id.rel_cache_size) RelativeLayout mRelCacheSize;

    @BindView(R.id.rel_upload_net) RelativeLayout relUpload;
    @BindView(R.id.tev_net_size) TextView tevNetSize;

    @BindView(R.id.tev_datasource) TextView mTevDatasource;
    @BindView(R.id.rel_datasource) RelativeLayout relDatasource;

    // 中金所
    @BindView(R.id.tev_cff_levelmode) TextView mTevCffLevelmode;
    @BindView(R.id.rel_cff_levelmode) RelativeLayout relCffLevelmode;
    // 大商所
    @BindView(R.id.tev_dce_levelmode) TextView mTevDCELevelmode;
    @BindView(R.id.rel_dce_levelmode) RelativeLayout relDCELevelmode;
    // 郑商所
    @BindView(R.id.tev_zce_levelmode) TextView mTevZCELevelmode;
    @BindView(R.id.rel_zce_levelmode) RelativeLayout relZCELevelmode;
    //上期所
    @BindView(R.id.tev_shfe_levelmode) TextView mTevSHFELevelmode;
    @BindView(R.id.rel_shfe_levelmode) RelativeLayout relSHFELevelmode;
    //上期所原油
    @BindView(R.id.tev_ine_levelmode) TextView mTevINELevelmode;
    @BindView(R.id.rel_ine_levelmode) RelativeLayout relINELevelmode;
    // 全球
    @BindView(R.id.tev_gi_levelmode) TextView mTevGILevelmode;
    @BindView(R.id.rel_gi_levelmode) RelativeLayout relGILevelmode;
    // 外汇
    @BindView(R.id.tev_fe_levelmode) TextView mTevFELevelmode;
    @BindView(R.id.rel_fe_levelmode) RelativeLayout relFELevelmode;

    // 港股
    @BindView(R.id.tev_hk_levelmode) TextView tevHkLevelmode;
    @BindView(R.id.rel_hk_levelmode) RelativeLayout relHkLevelmode;
    // 港股指数
    @BindView(R.id.tev_hkzs_levelmode) TextView tevHkzsLevelmode;
    @BindView(R.id.rel_hkzs_levelmode) RelativeLayout relHkzsLevelmode;
    // 重新认证
    @BindView(R.id.tev_forceauth) TextView tevForceauth;
    @BindView(R.id.rel_forceauth) RelativeLayout relForceauth;

    // 版本号
    @BindView(R.id.tev_version) TextView tevVersion;

    public static final int RATE_REQUEST_CODE = 0;
    public static final int LEV_REQUEST_CODE = 1;
    public static final int SKIN_REQUEST_CODE = 2;
    public static final int TIMEOUT_REQUEST_CODE = 3;
    public static final int DATA_SOURCE_CODE = 4;
    public static final int CFF_LEVEL_REQUEST_CODE = 5;
    public static final int HK_LEVEL_REQUEST_CODE = 6;
    public static final int DCE_LEVEL_REQUEST_CODE = 7;
    public static final int ZCE_LEVEL_REQUEST_CODE = 8;
    public static final int GI_LEVEL_REQUEST_CODE = 9;
    public static final int FE_LEVEL_REQUEST_CODE = 10;
    public static final int SHFE_LEVEL_REQUEST_CODE = 11;
    public static final int INE_LEVEL_REQUEST_CODE = 12;
    public static final int HKZS_LEVEL_REQUEST_CODE = 13;// 港股指数
    public static final int OLLEVEL_REQUEST_CODE = 14;// 境外权限

    private String tevModeString;//存在SharedPreferences中默认选择
    private String tevTimeoutReminderStr;
    private String tevDataSource;
    private String tevCffLevel; // 中金所
    private String tevDCELevel; // 中金所
    private String tevZCELevel; // 中金所
    private String tevGILevel; // 中金所
    private String tevFELevel; // 中金所
    private String tevSHFELevel; // 上期所
    private String tevINELevel; // 上期所原油

    @Override
    protected int getLayoutId() {
        setSkinable(true);
        return R.layout.activity_settingdetail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mRelRefreshrate.setOnClickListener(this);
        mRelRequestmode.setOnClickListener(this);
        rel_ollevel.setOnClickListener(this);// 境外权限

        mRelTimeoutReminder.setOnClickListener(this);
        mRelPushTime.setOnClickListener(this);
        mRelLogin.setOnClickListener(this);

        mRelChangeSkin.setOnClickListener(this);
        mRelCacheSize.setOnClickListener(this);
        relUpload.setOnClickListener(this);
        relDatasource.setOnClickListener(this);

        relCffLevelmode.setOnClickListener(this);
        relHkLevelmode.setOnClickListener(this);
        relHkzsLevelmode.setOnClickListener(this);// 港股指数
        relDCELevelmode.setOnClickListener(this);
        relZCELevelmode.setOnClickListener(this);
        relGILevelmode.setOnClickListener(this);
        relFELevelmode.setOnClickListener(this);
        relSHFELevelmode.setOnClickListener(this);
        relINELevelmode.setOnClickListener(this);
        relForceauth.setOnClickListener(this);// 重新认证

        try {
            mTevCacheSize.setText(CleanMessageUtil.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tevNetSize.setText(FileHelper.getSize());
    }

    @Override
    protected void initData() {
        setVersionName();
    }

    private void setVersionName() {
        // 版本号
        PackageManager packageManager = this.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
            String versionName = packageInfo.versionName;
            tevVersion.setText("V"+versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDefaultSetting();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //行情刷新频率
            case R.id.rel_refreshrate:
                Intent intent = new Intent(this, RefreshRateActivity.class);
                startActivityForResult(intent, RATE_REQUEST_CODE);
                break;

            //Level设置
            case R.id.rel_requestmode:
                Intent intentMode = new Intent(this, SettingRequestModeActivity.class);
                startActivityForResult(intentMode, LEV_REQUEST_CODE);
                break;
            // 境外权限设置
            case R.id.rel_ollevel:
                Intent intentoll = new Intent(this, SettingOLLActivity.class);
                startActivityForResult(intentoll, OLLEVEL_REQUEST_CODE);
                break;

            //超时提醒
            case R.id.rel_timeout_reminder:
                Intent intentTimeout = new Intent(this, SettingTimeoutReminderActivity.class);
                startActivityForResult(intentTimeout, TIMEOUT_REQUEST_CODE);
                break;

            //推送时间段
            case R.id.rel_push_time:
                SettingPushTimeActivity.actionStart(this);
                break;

            //登录IP
            case R.id.rel_login_ip:
                SettingLoginIPShowActivity.actionStart(this);
                break;

            //换肤
            case R.id.rel_skin_type:
                Intent intentSkin = new Intent(this, SettingChangeSkinActivity.class);
                startActivityForResult(intentSkin, SKIN_REQUEST_CODE);
                break;

            //清除缓存
            case R.id.rel_cache_size:
                onClickCleanCache();
                break;

            case R.id.rel_upload_net:
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("上传监测数据");
                progressDialog.show();
                try{
                    FileHelper.upload(new FileHelper.UploadCallBack() {
                        @Override
                        public void onCallback(boolean success) {
                            ToastUtils.showLongToast(success ? "上传成功！" : "上传失败！");
                            SettingDetailActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    tevNetSize.setText(FileHelper.getSize());
                                }
                            });
                        }
                    });
                }catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showLongToast("上传失败！"+e.getMessage());
                }
                break;

            // F10数据源
            case R.id.rel_datasource:
                Intent intentData = new Intent(this, SettingDataSourceActivity.class);
                startActivityForResult(intentData, DATA_SOURCE_CODE);
                break;

            // 中金所
            case R.id.rel_cff_levelmode:
                Intent intentCffLevel = new Intent(this, SettingCffModeActivity.class);
                startActivityForResult(intentCffLevel, CFF_LEVEL_REQUEST_CODE);
                break;
            // 大商所
            case R.id.rel_dce_levelmode:
                Intent intentDCELevel = new Intent(this, SettingDCEModeActivity.class);
                startActivityForResult(intentDCELevel, DCE_LEVEL_REQUEST_CODE);
                break;
            // 郑商所
            case R.id.rel_zce_levelmode:
                Intent intentZCELevel = new Intent(this, SettingZCEModeActivity.class);
                startActivityForResult(intentZCELevel, ZCE_LEVEL_REQUEST_CODE);
                break;
            // 全球
            case R.id.rel_gi_levelmode:
                Intent intentGILevel = new Intent(this, SettingGIModeActivity.class);
                startActivityForResult(intentGILevel, GI_LEVEL_REQUEST_CODE);
                break;
            // 外汇
            case R.id.rel_fe_levelmode:
                Intent intentFELevel = new Intent(this, SettingFEModeActivity.class);
                startActivityForResult(intentFELevel, FE_LEVEL_REQUEST_CODE);
                break;

            // 港股
            case R.id.rel_hk_levelmode:
                Intent intentHkLevel = new Intent(this, SettingHKModeActivity.class);
                startActivityForResult(intentHkLevel, HK_LEVEL_REQUEST_CODE);
                break;
            // 港股指数
            case R.id.rel_hkzs_levelmode:
                Intent intentHkzsLevel = new Intent(this, SettingHKZSModeActivity.class);
                startActivityForResult(intentHkzsLevel, HKZS_LEVEL_REQUEST_CODE);
                break;
            //上期所
            case R.id.rel_shfe_levelmode:
                Intent intentSHFELevel = new Intent(this, SettingSHFEModeActivity.class);
                startActivityForResult(intentSHFELevel, SHFE_LEVEL_REQUEST_CODE);
                break;

            //上期所原油
            case R.id.rel_ine_levelmode:
                Intent intentINELevel = new Intent(this, SettingINEModeActivity.class);
                startActivityForResult(intentINELevel, INE_LEVEL_REQUEST_CODE);
                break;
            //重新认证
            case R.id.rel_forceauth:
                forceauth();
                break;
            default:
                break;
        }
    }

    /**
     * 重新认证
     */
    private void forceauth() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.send(new IResponseInfoCallback<RegisterResponse>()
        {
            public void callback(RegisterResponse registerResponse)
            {
                /**
                 * 重新认证-注册成功
                 */
                ToastUtils.showLongToast("重新认证成功");
            }

            public void exception(ErrorInfo errorInfo)
            {
                /**
                 *  失败,错误处理
                 */
                ToastUtils.showLongToast(errorInfo.getErr_code() + "," + errorInfo.getMessage());
            }
        }, true);

    }

    /**
     * 获取本地存储的设置的选项
     */
    private void getDefaultSetting() {
        tevModeString = Setting.getLevel().equals("1") ? "level-1" : "level-2";
        tevDataSource = Setting.getDataSource().equals("g") ? "港澳" : "财汇";
        tevTimeoutReminderStr = Setting.getTimeOut() + "秒";
        tevCffLevel = Setting.getCffLevel().equals(Permissions.CFF_LEVEL_1) ? "CFF_LEVEL_1" : "CFF_LEVEL_2";
        tevDCELevel = Setting.getDCELevel().equals("1") ? "level-1" : "level-2";
        tevZCELevel = Setting.getZCELevel().equals("1") ? "level-1" : "level-2";
        tevGILevel = Setting.getGILevel().equals("1") ? "level-1" : "level-2";
        tevFELevel = Setting.getFELevel().equals("1") ? "level-1" : "level-2";
        tevSHFELevel = Setting.getSHFELevel().equals("1") ? "level-1" : "level-2";
        tevINELevel = Setting.getINELevel().equals("1") ? "level-1" : "level-2";

        mTevRefreshrate.setText(Setting.getRefreshRateStr());
        mTevMode.setText(tevModeString);
        mTevTimeoutReminder.setText(tevTimeoutReminderStr);
        mTevSkinType.setText(Setting.getSkinType());
        mTevDatasource.setText(tevDataSource);
        mTevCffLevelmode.setText(tevCffLevel);
        mTevDCELevelmode.setText(tevDCELevel);
        mTevZCELevelmode.setText(tevZCELevel);
        mTevGILevelmode.setText(tevGILevel);
        mTevFELevelmode.setText(tevFELevel);
        mTevSHFELevelmode.setText(tevSHFELevel);
        mTevINELevelmode.setText(tevINELevel);
        tevHkLevelmode.setText(Setting.getHKMode());
        String hkzs = Setting.getHKZSMode();
        if ("".equals(hkzs)) {
            hkzs = "无权限";
        }
        tevHkzsLevelmode.setText(hkzs);
        // 境外权限
        String ollevel = "";
        boolean chooseFlag = false;// 是否有权限被选中（如果没有的话就默认选中oll1）
        if(Setting.isOLL1()){
            chooseFlag = true;
            ollevel = Permissions.OL_LEVEL_1 + ",";
        } else {
            if(Setting.isOLSHL1()){
                chooseFlag = true;
                ollevel = ollevel + Permissions.OL_SH_LEVEL_1 + ",";
            }
            if(Setting.isOLSZL1()){
                chooseFlag = true;
                ollevel = ollevel + Permissions.OL_SZ_LEVEL_1 + ",";
            }
        }
        if(Setting.isOLSHL2()){
            chooseFlag = true;
            ollevel = ollevel + Permissions.OL_SH_LEVEL_2 + ",";
        }
        if(Setting.isOLSZL2()){
            chooseFlag = true;
            ollevel = ollevel + Permissions.OL_SZ_LEVEL_2;
        }
        // 是否有权限被选中（如果没有的话就默认选中oll1）
        if (!chooseFlag) {
            ollevel = Permissions.OL_LEVEL_1;
        } else {
            if (ollevel.endsWith(",")) {
                ollevel = ollevel.substring(0, ollevel.length() - 1);
            }
        }
        tev_ollevel.setText(ollevel);
    }

    /***************************************
     * 清除缓存相关方法
     **********************************************/
    private void onClickCleanCache() {
        getConfirmDialog(this, "是否清空缓存?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CleanMessageUtil.clearAllCache(getApplicationContext());
                mTevCacheSize.setText("0 KB");
                ToastUtils.showLongToast("清除成功");
            }
        }).show();
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(Html.fromHtml(message));
        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消", null);
        return builder;
    }

    public static AlertDialog.Builder getDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder;
    }
}
