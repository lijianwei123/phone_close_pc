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
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Toast;



public class ClosePcActivity extends Activity {
	
	private Button closePcButton;
	
	public static final String BASEURL = "http://192.168.18.102/close_pc.php"; 
	
	public static final int OK_STATUS_CODE = 200;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.setContentView(R.layout.main);
        
        this.closePcButton = (Button)this.findViewById(R.id.close_pc_button);
        this.initBind();
    }
    
    public void initBind()	{
    	this.closePcButton.setOnClickListener(new MyClickLister());
    }
    
    class MyClickLister implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()) {
				case R.id.close_pc_button:
					
					//List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
					//params.add(new BasicNameValuePair("client", "android"));
					
					//String param = URLEncodedUtils.format(params, "UTF-8");
					
					HttpGet httpGet = new HttpGet(ClosePcActivity.BASEURL);
					
					HttpParams httpParams = new BasicHttpParams();
					
					 // 设置连接超时和 Socket 超时，以及 Socket 缓存大小  
			        HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);  
			        HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);  
			        HttpConnectionParams.setSocketBufferSize(httpParams, 8192);  
			        // 设置重定向，缺省为 true  
			        HttpClientParams.setRedirecting(httpParams, true);  
			        // 设置 user agent  
			        String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";  
			        HttpProtocolParams.setUserAgent(httpParams, userAgent);  
					
					HttpClient httpClient = new DefaultHttpClient(httpParams);
					
					try {
						HttpResponse response = httpClient.execute(httpGet);
						
						int statusCode = response.getStatusLine().getStatusCode();
						
						//Log.i("statusCode", String.valueOf(statusCode));
						
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
						String strResult = e.getMessage().toString();  
						Log.i("test", strResult);
						e.printStackTrace();
					}
				break;
			}
			
		}
    }
    
    
}
