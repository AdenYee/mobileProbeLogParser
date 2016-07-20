package com.douwifi.yiks.tz;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class DisplayResultAdapter extends BaseAdapter {

	public ArrayList<String> stamacs;
	private LayoutInflater mLayoutInflater = null;



	public DisplayResultAdapter(Context context, ArrayList<String> stamacs) {
		super();
		this.stamacs = stamacs;
		mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return stamacs.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return stamacs.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
		ViewHolder holder = null;
		if (convertView == null || convertView.getTag() == null) {
			view = mLayoutInflater.inflate(R.layout.activity_display_result_item, null);
			holder = new ViewHolder(view);
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) convertView.getTag();
		}

		String stamac = getItem(position);
		holder.tvStamac.setText(stamac);
		holder.tvStamac.setTextColor(Color.GRAY);

		return view;
	}

	public static class ViewHolder {
		TextView tvStamac;

		public ViewHolder(View view) {
			tvStamac = (TextView) view.findViewById(R.id.tvStamac);
		}
	}
}
