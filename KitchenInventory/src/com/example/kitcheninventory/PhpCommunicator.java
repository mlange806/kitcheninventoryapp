package com.example.kitcheninventory;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class PhpCommunicator {
	private String script;
	private List<NameValuePair> kvPairs;
	private HttpResponse response;
	
	public PhpCommunicator()
	{
		// Default
		script = null;
		kvPairs = null;
		response = null;
	}
	public PhpCommunicator(String script, List<NameValuePair> kvPairs)
	{
		this.script = script;
		this.kvPairs = kvPairs;
		
	}
	public void runScript()
	{
		if(script != null || kvPairs.isEmpty() == false)
		{
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httppost= new HttpPost(this.script);
			
			try{
				httppost.setEntity(new UrlEncodedFormEntity(kvPairs));
				
				this.response = httpClient.execute(httppost);			
			}catch(IOException e){
				//Print error
				Log.w("PHP ERROR", "Couldn't do Http Execute: " + e.toString());
			}
		}
	}
	
	public HttpResponse getResponse()
	{
		return this.response;
	}
}

