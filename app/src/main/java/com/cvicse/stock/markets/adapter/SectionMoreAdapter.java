package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.response.Bankuaisorting;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 模块更多
 * Created by ding_syi on 17-1-22.
 */
public class SectionMoreAdapter extends PBaseAdapter {
    private String bankuaiType;

    private List<Bankuaisorting> industrySectionList;

    private int colorRed = ColorUtils.UP;
    private int colorGreen = ColorUtils.DOWN;
    private int colorGray = ColorUtils.DEFALUT();

    public SectionMoreAdapter(Context context,String bankuaiType){
        super(context);
        this.bankuaiType = bankuaiType;
    }

    public void setData(List<Bankuaisorting> industryItemList) {
        this.industrySectionList = industryItemList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return industrySectionList == null ? 0 : industrySectionList.size();
    }

    @Override
    public Object getItem(int position) {
        return industrySectionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if ( null==convertView ) {
            convertView = mLayoutInflater.inflate(R.layout.item_sectionmore, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Bankuaisorting bankuaisorting = industrySectionList.get(position);
        TextUtils.setText(viewHolder.mTevN, bankuaisorting.n);
        TextUtils.setText(viewHolder.mTevScode, bankuaisorting.ns);
        TextUtils.setText(viewHolder.mTevLzgn, bankuaisorting.lzgn);
        String jzf = bankuaisorting.jzf;
        if( null!=jzf ){
            if(jzf.startsWith("-")){
                viewHolder.mTevJzf.setText(jzf + "%");
                viewHolder.mTevJzf.setTextColor(colorGreen);
            }else if(Float.parseFloat(jzf) > 0){
                viewHolder.mTevJzf.setText("+"+jzf + "%");
                viewHolder.mTevJzf.setTextColor(colorRed);
            }else{
                viewHolder.mTevJzf.setText(jzf + "%");
                viewHolder.mTevJzf.setTextColor(colorGray);
            }
        }else{
            TextUtils.setText(viewHolder.mTevJzf,"--");
        }
        if (null!=bankuaisorting.ggzfb){
            if(bankuaisorting.ggzfb.equals("一")){
                viewHolder.mTevGgzf.setText(bankuaisorting.ggzfb);
                viewHolder.mTevGgzf.setTextColor(colorGray);
            }else if (bankuaisorting.ggzfb.compareTo("0")>0){
                viewHolder.mTevGgzf.setText("+"+bankuaisorting.ggzfb+"%");
                viewHolder.mTevGgzf.setTextColor(colorRed);
            }else if (bankuaisorting.ggzfb.compareTo("0")<0){
                viewHolder.mTevGgzf.setText(bankuaisorting.ggzfb+"%");
                viewHolder.mTevGgzf.setTextColor(colorGreen);
            }else {
                viewHolder.mTevGgzf.setText(bankuaisorting.ggzfb+"%");
                viewHolder.mTevGgzf.setTextColor(colorGray);
            }
        }else {
            TextUtils.setText(viewHolder.mTevGgzf,"--");
        }
        if (bankuaiType.equals("Trade_szyp")||bankuaiType.equals("Area_szyp")||bankuaiType.equals("Notion_szyp")){
            viewHolder.mTevHot.setVisibility(View.VISIBLE);
            if (bankuaisorting.hot.equals("")){
                viewHolder.mTevHot.setText("—");
            }else {
                viewHolder.mTevHot.setText(bankuaisorting.hot);
            }
        }else {
            viewHolder.mTevHot.setVisibility(View.GONE);
        }
        TextUtils.setText(viewHolder.mTevZcje,FormatUtils.format(bankuaisorting.zcje));
        TextUtils.setText( viewHolder.mTevGgzfb, bankuaisorting.zdjs);
        TextUtils.setText(viewHolder.mTevHsl,bankuaisorting.hsl == null ? "--" : bankuaisorting.hsl +"%");
        TextUtils.setTextPercent(viewHolder.mTevQzf, bankuaisorting.qzf);
        TextUtils.setText(viewHolder.mTevZljlr, FormatUtils.format(bankuaisorting.netCapitalInflow));
        TextUtils.setText(viewHolder.mTevZlzjlr, FormatUtils.format(bankuaisorting.zlzjlr));
        TextUtils.setText(viewHolder.mTevZlzjlc, FormatUtils.format(bankuaisorting.zlzjlc));
        TextUtils.setText(viewHolder.mTevZlzjjlr5, FormatUtils.format(bankuaisorting.zlzjjlr5));
        TextUtils.setText(viewHolder.mTevZlzjjlr10, FormatUtils.format(bankuaisorting.zlzjjlr10));
        TextUtils.setText(viewHolder.mTevPresent,FormatUtils.format(bankuaisorting.present));
        TextUtils.setText(viewHolder.mTevTotalHand,FormatUtils.format(bankuaisorting.totalHand));
        TextUtils.setText(viewHolder.mTevLimitUpCount,FormatUtils.format(bankuaisorting.limitUpCount));
        TextUtils.setText(viewHolder.mTevLimitDownCount,FormatUtils.format(bankuaisorting.limitDownCount));

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tev_n) TextView mTevN;
        @BindView(R.id.tev_lzgn) TextView mTevLzgn;
        @BindView(R.id.tev_jzf) TextView mTevJzf;
        @BindView(R.id.tev_ggzf) TextView mTevGgzf;
        @BindView(R.id.tev_zcje) TextView mTevZcje;
        @BindView(R.id.tev_ggzfb) TextView mTevGgzfb;
        @BindView(R.id.tev_hsl) TextView mTevHsl;
        @BindView(R.id.tev_qzf) TextView mTevQzf;
        @BindView(R.id.tev_zljlr) TextView mTevZljlr;
        @BindView(R.id.tev_zlzjlr) TextView mTevZlzjlr;
        @BindView(R.id.tev_zlzjlc) TextView mTevZlzjlc;
        @BindView(R.id.tev_zlzjjlr5) TextView mTevZlzjjlr5;
        @BindView(R.id.tev_zlzjjlr10) TextView mTevZlzjjlr10;
        @BindView(R.id.tev_hot) TextView mTevHot;
        @BindView(R.id.tev_present) TextView mTevPresent;
        @BindView(R.id.tev_totalHand) TextView mTevTotalHand;
        @BindView(R.id.tev_limitUpCount) TextView mTevLimitUpCount;//涨停家数
        @BindView(R.id.tev_limitDownCount) TextView mTevLimitDownCount;//跌停家数
        @BindView(R.id.tev_scode) TextView mTevScode;//板块代码


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
