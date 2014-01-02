package com.dfws.idea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

//ListView @see http://www.cnblogs.com/allin/archive/2010/05/11/1732200.html
public class ListPcActivity extends ListActivity {
	private List<Map<String, Object>> mData;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mData = getData();
		MyAdapter adapter = new MyAdapter(this);
		setListAdapter(adapter);
		
	}
	
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "title one");
		map.put("img", R.drawable.icon);
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("title", "G2");
		map.put("img", R.drawable.icon);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("img", R.drawable.icon);
		list.add(map);
		
		return list;
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Log.v("list-view-click",  (String)mData.get(position).get("title"));
		showPc(position);
	}
	
	public void showPc(int position) {
		Intent intent = new Intent();
		intent.setClass(this, WakeupPcActivity.class);
		intent.putExtra("ip", "192.168.18.102");
		intent.putExtra("port", "8709");
		intent.putExtra("mac", "ff-ff-ff-ff-ff-ff");
		startActivity(intent);
	}
	
	public final class ViewHolder {
		public ImageView img;
		public TextView title;
	}
	
	public class MyAdapter extends BaseAdapter{
		
		private LayoutInflater mInflater;
		
		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mData.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			
			if(convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.listpc, null);
				holder.img = (ImageView)(convertView.findViewById(R.id.icon_img));
				holder.title = (TextView)convertView.findViewById(R.id.pc_name);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			holder.img.setBackgroundResource((Integer)mData.get(position).get("img"));
			holder.title.setText((String)mData.get(position).get("title"));
			
			return convertView;
			
		}
		
	}
	
}
