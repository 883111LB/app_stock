package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.BaseApplication;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.mitake.core.response.Bankuaisorting;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ding_syi on 17-1-20.
 */
public class SectionAdapter extends PBaseAdapter {
    List<Bankuaisorting> hotSotckList = new ArrayList<>();

    int colorRed = ContextCompat.getColor(BaseApplication.getInstance(),R.color.text_red);
    int colorGreen = ContextCompat.getColor(BaseApplication.getInstance(),R.color.text_green);
    int colorGray = ContextCompat.getColor(BaseApplication.getInstance(),R.color.text_gray);

    public SectionAdapter(Context context) {
        super(context);
    }

    public void setData(List<Bankuaisorting> hotSotckList) {
        this.hotSotckList = hotSotckList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return hotSotckList == null ? 0:hotSotckList.size();
    }

    @Override
    public Object getItem(int position) {
        return hotSotckList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = mLayoutInflater.inflate(R.layout.item_markets_section, parent, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tevType.setText(hotSotckList.get(position).n);
        String jzf =  hotSotckList.get(position).jzf;
        String zgb = hotSotckList.get(position).ggzfb;
        if(jzf != null){
            if(jzf.startsWith("-")){
                viewHolder.tevRate.setText(jzf + "%");
                viewHolder.tevRate.setTextColor(colorGreen);

                viewHolder.tevChange.setText(zgb +"%");
            }else if(Float.parseFloat(jzf) > 0){
                viewHolder.tevRate.setText("+"+jzf + "%");
                viewHolder.tevRate.setTextColor(colorRed);

                viewHolder.tevChange.setText("+"+zgb+"%");
            }else{
                viewHolder.tevRate.setText(jzf + "%");
                viewHolder.tevRate.setTextColor(colorGray);

                viewHolder.tevChange.setText(zgb+"%");
            }
        }else{
            viewHolder.tevRate.setText("--");
            viewHolder.tevChange.setText("--");
        }
        viewHolder.tevCompany.setText(TextUtils.isEmpty(hotSotckList.get(position).lzgn)?"--":hotSotckList.get(position).lzgn);
        viewHolder.tevNum.setText(TextUtils.isEmpty(hotSotckList.get(position).ggzf)?"--":hotSotckList.get(position).ggzf);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tev_type)
        TextView tevType;
        @BindView(R.id.tev_rate)
        TextView tevRate;
        @BindView(R.id.tev_company)
        TextView tevCompany;
        @BindView(R.id.tev_num)
        TextView tevNum;
        @BindView(R.id.tev_change)
        TextView tevChange;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
