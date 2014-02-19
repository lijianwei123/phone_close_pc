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
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ViewSourceActivity extends Activity {

	private Button sumbitButton;

	private EditText sourceUrl, sourceCode;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.view_source);

		this.sumbitButton = (Button) this.findViewById(R.id.sumbit);
		this.sourceUrl = (EditText) this.findViewById(R.id.source_url);
		this.sourceCode = (EditText) this.findViewById(R.id.source_code);

		this.initBind();

		Log.i("life", "onCreate");
	}

	protected void initBind() {
		this.sumbitButton.setOnClickListener(new MyClickLister());
	}

	class MyClickLister implements View.OnClickListener {

		@Override
		public void onClick(View v) {

			Intent intent = null;

			switch (v.getId()) {
			// 提交
			case R.id.sumbit:
					String url = ViewSourceActivity.this.sourceUrl.toString();
					if(url.equals("") || url == null) {
						Toast.makeText(ViewSourceActivity.this, "请输入url", Toast.LENGTH_SHORT).show();
					} else {
						String content = ViewSourceActivity.this.fileGetContents(url);
						ViewSourceActivity.this.sourceCode.setText(content);
					}

				break;

			}

		}
	}

	protected String fileGetContents(String url) {

		String returnStr = "";

		HttpGet httpGet = new HttpGet(url);

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

			if (statusCode == ClosePcActivity.OK_STATUS_CODE) {
				// 获取内容
				returnStr = EntityUtils.toString(response.getEntity());
			} else {
				Toast.makeText(ViewSourceActivity.this, "获取源码失败",
						Toast.LENGTH_SHORT).show();
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
		return returnStr;
	}

}
