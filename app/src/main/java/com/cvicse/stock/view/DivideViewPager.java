package com.cvicse.stock.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;

import java.util.ArrayList;

/**
 * Created by liu_zlu on 2017/3/13 16:44
 */
public class DivideViewPager extends LinearLayout implements View.OnClickListener{
    private ViewPager viewPager;

    private int divide = 30;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public DivideViewPager(Context context) {
        this(context,null);
    }

    public DivideViewPager(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DivideViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        viewPager = new ViewPager(context);
        this.addView(viewPager,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setAdapter(PBaseAdapter baseAdapter){
        viewPager.setAdapter(new DividePagerAdapter(baseAdapter));
    }

    public void setDivide(int divide){
        this.divide = divide;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        if(view.getTag(R.id.divideviewpager_tag) == null){
            return;
        }
        if(onItemClickListener != null){
            onItemClickListener.onItemClick(this,view, (Integer) view.getTag(R.id.divideviewpager_tag));
        }
    }

     class DividePagerAdapter extends PagerAdapter {

        private ArrayList<View> views = new ArrayList<>();
        PBaseAdapter baseAdapter;
        DividePagerAdapter(PBaseAdapter baseAdapter){
            this.baseAdapter = baseAdapter;
            baseAdapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    DividePagerAdapter.this.notifyDataSetChanged();
                }

                @Override
                public void onInvalidated() {
                    super.onInvalidated();
                }
            });
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            for(int i= 0;i<views.size();i++){
                baseAdapter.getView(i,views.get(i), (ViewGroup) views.get(i).getParent());
            }
        }

        @Override
        public int getCount() {
            if(baseAdapter == null){
                return 0;
            }
            if(baseAdapter.getCount()%3 == 0){
                return baseAdapter.getCount()/3;
            }

            return baseAdapter.getCount()/3+1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LinearLayout linearLayout = new LinearLayout(container.getContext());
            linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            for(int i = 1;i < 4;i++){
                if(i+position*3 <= baseAdapter.getCount()){
                    View view = baseAdapter.getView(i+position*3-1,null,linearLayout);
                    views.add(view);
                    if(view == null){
                        view = new View(container.getContext());
                    }
                    view.setTag(R.id.divideviewpager_tag,i+position*3-1);
                    LinearLayout.LayoutParams layoutParams = new LayoutParams(0,LayoutParams.MATCH_PARENT);
                    layoutParams.weight = 1;
                    linearLayout.addView(view,layoutParams);
                    view.setOnClickListener(DivideViewPager.this);
                }
            }
            container.addView(linearLayout);
            return linearLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    public static interface OnItemClickListener{
        void onItemClick(DivideViewPager parent,View view,int position);
    }
}
