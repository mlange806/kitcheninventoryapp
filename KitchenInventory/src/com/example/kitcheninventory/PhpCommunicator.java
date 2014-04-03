package com.example.kitcheninventory;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class PhpCommunicator {
	private String script;
	private List<NameValuePair> kvPairs;
	private HttpResponse response;
	private Context context;
	private String result;
	
	public PhpCommunicator()
	{
		// Default
		script = null;
		kvPairs = null;
		response = null;
		context = null;
		result = null;
	}
	
	
	public PhpCommunicator(String script, List<NameValuePair> kvPairs, Context context)
	{
		this.script = script;
		this.kvPairs = kvPairs;
		this.context = context;
		
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
			}
			
			catch(IOException e){
				//Print error
				Log.w("PHP ERROR", "Couldn't do Http Execute: " + e.toString());
			}
			
			try {
				result = EntityUtils.toString(this.response.getEntity());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}
	
	public String getResponse()
	{
		return this.result;
	}
	
	public void setScript(String script){
		this.script = script;
	}
	
	public void setKvPairs(List<NameValuePair> kvPairs){
		this.kvPairs = kvPairs;
	}
	
}

