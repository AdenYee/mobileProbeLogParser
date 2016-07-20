package com.douwifi.yiks.tz;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.douwifi.yiks.tz.model.History;
import com.douwifi.yiks.tz.util.TimeUtil;

import java.util.ArrayList;

/**
 * Created by YiKS on 2016/7/12.
 */
public class HistoryAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater = null;
    private ArrayList<History> histories;


    public HistoryAdapter(Context context, ArrayList<History> histories) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.histories = histories;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return histories.size();
    }

    @Override
    public History getItem(int position) {
        // TODO Auto-generated method stub
        return histories.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder holder = null;
        if (convertView == null || convertView.getTag() == null) {
            view = mLayoutInflater.inflate(R.layout.activity_history_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) convertView.getTag();
        }

        History history = getItem(position);
        holder.tvTime.setText(history.getTitle());
        holder.tvTime.setTextColor(Color.GRAY);
        return view;
    }

    public static class ViewHolder {
        TextView tvTime;

        public ViewHolder(View view) {
            tvTime = (TextView) view.findViewById(R.id.tvTime);
        }
    }
}
