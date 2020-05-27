package com.cvicse.stock.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.AppInfo;
import com.mitake.core.network.Network;
import com.mitake.core.permission.MarketPermission;

import butterknife.BindView;

/**
 * Created by ruan_ytai on 17-2-8.
 */

public class SettingLoginIPShowActivity extends PBaseActivity {

    @BindView(R.id.topbar) ToolBar topbar;
    @BindView(R.id.tev_sh_ip) TextView tevShIp;
    @BindView(R.id.tev_sz_ip) TextView tevSzIp;
    @BindView(R.id.tev_hk_ip) TextView tevHkIp;
    @BindView(R.id.tev_pb_ip) TextView tevPbIp;
    @BindView(R.id.tev_nf_ip) TextView tevNfIp;
    @BindView(R.id.tev_sh_tcp_ip) TextView tevShTcpIp;
    @BindView(R.id.tev_sz_tcp_ip) TextView tevSzTcpIp;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            updateView();
            handler.sendEmptyMessageDelayed(1,2000);
        }
    };
    public static void actionStart(Context context) {
        Intent i = new Intent(context, SettingLoginIPShowActivity.class);
        context.startActivity(i);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_loginip_show;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.sendEmptyMessage(1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    private void updateView() {

        Network network = Network.getInstance();
//        String shMarket = network.getServerType("sh");
//        String szMarket = network.getServerType("sz");
//        String hkMarket = network.getServerType("hk");

        String shMarket = MarketPermission.getInstance().getMarket("sh");
        String szMarket = MarketPermission.getInstance().getMarket("sz");
        String hkMarket = MarketPermission.getInstance().getMarket("hk10");

        String gbMarket = MarketPermission.getInstance().getMarket("GB");
        String bjMarket = MarketPermission.getInstance().getMarket("BJ");
        StringBuffer text = new StringBuffer();

        if(AppInfo.getInfoLevel().equals("2")) {
            text.append("SZTCP: "+Network.getInstance().getServerIP("tcpszl2")+"\n");
            text.append("SHTCP: "+Network.getInstance().getServerIP("tcpshl2"));
            TextUtils.setText(tevShTcpIp,text.toString());
        } else {
            TextUtils.setText(tevShTcpIp,"SHTCP: "+Network.getInstance().getServerIP("tcpsh"));
        }
        TextUtils.setText(tevShIp,"SH: "+network.getServerIP(shMarket, "v2"));
        TextUtils.setText(tevSzIp,"SZ: "+network.getServerIP(szMarket, "v2"));
        TextUtils.setText(tevHkIp,"HK10: "+network.getServerIP(hkMarket, "v2"));
        TextUtils.setText(tevPbIp,"PB: "+network.getServerIP("pb", "v2"));

        text = new StringBuffer();
        text.append("NF: " + network.getServerIP("nf", "v1"));
        text.append("\nGB: " + network.getServerIP(gbMarket, "v2"));
        text.append("\nBJ: " + network.getServerIP(bjMarket, "v2"));
        TextUtils.setText(tevNfIp, text.toString());

    }
}
