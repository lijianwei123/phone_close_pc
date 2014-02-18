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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



public class ViewSourceActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.setContentView(R.layout.main);
        
        this.closePcButton = (Button)this.findViewById(R.id.close_pc_button);
        this.wakeupPcButton = (Button)this.findViewById(R.id.wakeup_pc_button);
        this.viewSourceButton = (Button)this.findViewById(R.id.view_source_button);
        this.initBind();
        
        gestureDetector = new GestureDetector(this, onGestureListener);
        
        Log.i("life", "onCreate");
    }
    
    private  GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener(){
    	
    	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,  
                float velocityY) {
    		float x = e2.getX() - e1.getX();
    		float y = e2.getY() - e1.getY(); 
    		
    		if(x > 0) {
    			doResult(RIGHT);
    		} else if(x < 0) {
    			doResult(LEFT);
    		}
    		return true;
    	}
    };
    public boolean onTouchEvent(MotionEvent e) {
    	return gestureDetector.onTouchEvent(e);
    }
    
    public void doResult(int action) {
    	switch(action){
    	case RIGHT:
    		Toast.makeText(this, "右边没有东东", Toast.LENGTH_LONG).show();
    		break;
    	case LEFT:
    		//显示pc列表
    		Intent intent = new Intent();
    		intent.setClass(this, ListPcActivity.class);
    		startActivity(intent);
    		break;
    	}
    }
    
    //生命周期 http://www.cnblogs.com/feisky/archive/2010/01/01/1637427.html
    public void onStart()
    {
    	super.onStart();
    	Log.i("life", "onStart");
    }
    
    public void onRestart()
    {
    	super.onRestart();
    	Log.i("life", "onReStart");
    }
    
    public void onResume()
    {
    	super.onResume();
    	Log.i("life", "onResume");
    }
    
    public void onPause()
    {
    	super.onPause();
    	Log.i("life", "onPause");
    }
    
    public void onStop()
    {
    	super.onStop();
    	Log.i("life", "onStop");
    }
    
    public void onDestory()
    {
    	super.onDestroy();
    	Log.i("life", "onDestory");
    }
    
    
    
 
    
    public void initBind()	{
    	this.closePcButton.setOnClickListener(new MyClickLister());
    	this.wakeupPcButton.setOnClickListener(new MyClickLister());
    	this.viewSourceButton.setOnClickListener(new MyClickLister());
    }
    
    class MyClickLister implements View.OnClickListener{
    	
		@Override
		public void onClick(View v) {
			
			Intent intent = null;
			
			switch(v.getId()) {
				case R.id.close_pc_button:
					
					//List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
					//params.add(new BasicNameValuePair("client", "android"));
					
					//String param = URLEncodedUtils.format(params, "UTF-8");
					
					HttpGet httpGet = new HttpGet(ViewSourceActivity.BASEURL);
					
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
						
						if(statusCode == ViewSourceActivity.OK_STATUS_CODE) {
							Toast.makeText(ViewSourceActivity.this, "关机成功", Toast.LENGTH_LONG).show();
						}else{
							Toast.makeText(ViewSourceActivity.this, "关机失败", Toast.LENGTH_LONG).show();
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
				
				case R.id.wakeup_pc_button:
					intent = new Intent();
					intent.setClass(ViewSourceActivity.this, WakeupPcActivity.class);
					startActivity(intent);
				break;
				
				case R.id.view_source_button:
					//显示窗口
					intent = new Intent();
					intent.setClass(ViewSourceActivity.this, ViewSourceActivity.class);
					startActivity(intent);
				break;
			}
			
		}
    }
    
    
}
