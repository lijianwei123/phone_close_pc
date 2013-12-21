/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dfws.idea;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Toast;



public class ClosePcActivity extends Activity {
	
	private Button closePcButton;
	
	public static final String BASEURL = "http://192.168.18.102:30051/idea/"; 
	
	public static final int OK_STATUS_CODE = 200;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.setContentView(R.layout.main);
        
        this.closePcButton = (Button)this.findViewById(R.id.close_pc_button);
    }
    
    public void initBind()	{
    	this.closePcButton.setOnClickListener(new MyClickLister());
    }
    
    class MyClickLister implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()) {
				case R.id.close_pc_button:
					List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
					params.add(new BasicNameValuePair("client", "android"));
					
					String param = URLEncodedUtils.format(params, "UTF-8");
					
					HttpGet httpGet = new HttpGet(ClosePcActivity.BASEURL + "?" + param);
					
					HttpClient httpClient = new DefaultHttpClient();
					
					try {
						HttpResponse response = httpClient.execute(httpGet);
						
						int statusCode = response.getStatusLine().getStatusCode();
						
						if(statusCode == ClosePcActivity.OK_STATUS_CODE) {
							Toast.makeText(ClosePcActivity.this, "关机成功", Toast.LENGTH_LONG).show();
						}else{
							Toast.makeText(ClosePcActivity.this, "关机失败", Toast.LENGTH_LONG).show();
						}
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				break;
			}
			
		}
    }
    
    
}
