package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.TextUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/** 董秘问答
 * Created by liu_bin on 2019/10/10.
 */
public class StockIntearActiveAdapter extends PBaseAdapter {

    private List<HashMap<String, Object>> infoList;

    public StockIntearActiveAdapter(Context context) {
        super(context);
    }

    public void setData(List<HashMap<String, Object>> infoList) {
        this.infoList = infoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == infoList ? 0 : infoList.size();
    }

    @Override
    public Object getItem(int position) {
        return null == infoList ? null : infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return null == infoList ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StockIntearActiveAdapter.ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.item_intear_active, parent,false);
            viewHolder = new StockIntearActiveAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (StockIntearActiveAdapter.ViewHolder) convertView.getTag();
        }

        HashMap<String, Object> objectHashMap = infoList.get(position);

        String questiontime = (String) objectHashMap.get("QUESTIONTIME");
        String problem = (String) objectHashMap.get("PROBLEM");
        String answertime = (String) objectHashMap.get("ANSWERTIME");
        String reply = (String) objectHashMap.get("REPLY");

        TextUtils.setText(viewHolder.tevQuestionTime, questiontime);
        TextUtils.setText(viewHolder.tevProblem,FormatUtils.isStandard(problem) ? problem : FillConfig.DEFALUT);
        TextUtils.setText(viewHolder.tevAnswerTime,answertime);
        TextUtils.setText(viewHolder.tevReply,FormatUtils.isStandard(reply) ? reply : FillConfig.DEFALUT);
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tev_question_time)
        TextView tevQuestionTime;
        @BindView(R.id.tev_problem)
        TextView tevProblem;
        @BindView(R.id.tev_answer_time)
        TextView tevAnswerTime;
        @BindView(R.id.tev_reply)
        TextView tevReply;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
