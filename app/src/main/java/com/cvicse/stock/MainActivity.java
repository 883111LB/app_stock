package com.cvicse.stock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.cvicse.base.ui.BaseFragment;
import com.cvicse.base.utils.ToastUtils;
import com.cvicse.base.widget.BottomTabbar;
import com.cvicse.stock.aspectj.annotation.CheckPermission;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.markets.ui.MarketsFragment;
import com.cvicse.stock.news.ui.NewsFragment;
import com.cvicse.stock.personal.PersonalFragment;
import com.cvicse.stock.portfolio.PortfolioMianFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends PBaseActivity {

    @BindView(R.id.main_fragment)
    FrameLayout mainFragment;
    @BindView(R.id.bottomNavigation)
    BottomTabbar bottomNavigation;
    private FragmentManager fragmentManager;

    private BaseFragment[] baseFragments = new BaseFragment[4];

    public static void startActivity(Activity activity){
        Intent intent = new Intent(activity,MainActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        setSkinable(true);
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        fragmentManager = getSupportFragmentManager();
        ArrayList<String[]> arrayList = new ArrayList<>();
        arrayList.add(new String[]{"自选","Portfolio"});
        arrayList.add(new String[]{"资讯","News"});
        arrayList.add(new String[]{"行情","Markets"});
        arrayList.add(new String[]{"个人","Me"});
        bottomNavigation.setTitles(arrayList);
        bottomNavigation.setOnSelectedListener(new BottomTabbar.OnSelectChangerdListener() {
            @Override
            public void onSelectChanged(View view, int position,int oldPosition) {
                selectPosition(position,oldPosition);
            }
        });
        selectPosition(0,-1);
    }

    @Override
    protected void initData() {

    }
    @CheckPermission({"dfdsfsfdf"})
    private void selectPosition(int position,int oldPosition) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(baseFragments[position] == null){
            String Tag;
            if( position == 0){
                Tag = "MySelectFragment";
                baseFragments[position] = PortfolioMianFragment.newInstance();

            } else if( position == 1) {
                Tag = "NewsFragment";
                baseFragments[position] = NewsFragment.newInstance();
            } else if( position == 2) {
                Tag = "MarketsFragment";
                baseFragments[position] = MarketsFragment.newInstance();
            } else {
                Tag = "PersonalFragment";
                baseFragments[position] = PersonalFragment.newInstance();
            }
            fragmentTransaction.add(R.id.main_fragment,baseFragments[position],Tag);
            if(oldPosition > -1){
                fragmentTransaction.hide(baseFragments[oldPosition]);
            }
        } else {
            fragmentTransaction.show(baseFragments[position]).hide(baseFragments[oldPosition]);
        }

        fragmentTransaction.commitAllowingStateLoss();
    }


    private long firstTime;
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if(currentTime - firstTime < 3000){
            finish();
        }else{
            ToastUtils.showLongToast("再按一次退出应用");
            firstTime = currentTime;
        }
    }
}
