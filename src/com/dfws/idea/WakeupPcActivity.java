package com.dfws.idea;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import com.dfws.idea.ClosePcActivity.MyClickLister;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WakeupPcActivity extends Activity {
	
	 private EditText ipText;
	 private EditText portText;
	 private EditText macText;
	 
	 private Button editBtn;
	 
	 private  static final String SPKEY = "com.dfws.idea.WakeupPc";
	 
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 this.setContentView(R.layout.wakeup);
		 
		 this.ipText = (EditText)this.findViewById(R.id.ip_or_host);
		 this.portText = (EditText)this.findViewById(R.id.port);
		 this.macText = (EditText)this.findViewById(R.id.mac);
		 
		 this.editBtn = (Button)this.findViewById(R.id.form_submit_btn);
		 
		 this.initBind();
		 
		 this.initData();
	 }
	 
	public void initBind() {
		this.editBtn.setOnClickListener(new MyClickLister());
	
	}
	
	//初始数据
	public void initData()
	{
		Bundle bundle = this.getIntent().getExtras();
		if(bundle != null) {
			this.ipText.setText(bundle.getString("ip"));
			this.portText.setText(bundle.getString("port"));
			this.macText.setText(bundle.getString("mac"));
		}
	}
	
	 class MyClickLister implements View.OnClickListener{

			@Override
			public void onClick(View v) {
				switch(v.getId()) {
					case R.id.close_pc_button:
						//保存到sharepreferce
						SharedPreferences sp = getSharedPreferences(WakeupPcActivity.SPKEY, MODE_PRIVATE);
						Editor editor = sp.edit();
						
						editor.putString("ip", WakeupPcActivity.this.ipText.toString());
						editor.putString("port", WakeupPcActivity.this.portText.toString());
						editor.putString("mac", WakeupPcActivity.this.macText.toString());
						
						editor.commit();
					
				}
			}
	    }
	    
}
