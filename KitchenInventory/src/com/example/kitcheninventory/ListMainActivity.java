package com.example.kitcheninventory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ListView;

public class ListMainActivity extends Activity {
	ArrayList<Items> arrayOfWebData = new ArrayList<Items>();
	class Items{
		public String Items_id;
		public String name;
		public String count;
	}
	Adapter aa=null;
	static ArrayList<String> resultRow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_main);
		String result="";
		try {
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost("website");
	    HttpResponse response = httpclient.execute(httppost);
	    HttpEntity entity = response.getEntity();
	    InputStream webs = entity.getContent();
	    try {
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(webs));
	    	StringBuilder sb = new StringBuilder();
	    	String line = null;
	    	while((line = reader.readLine()) != null){
	    		sb.append(line+"\n");
	    	}
	    	webs.close();
	    	result=sb.toString();
	    }catch(Exception e){
	    	Log.e("log_tag","Error"+e.toString());
	    }
		}catch(Exception e){
			Log.e("log_tag","Error"+e.toString());
		}
		try{
			JSONArray jArray = new JSONArray(result);
			for(int i=0;i<jArray.length();i++){
				JSONObject json_data = jArray.getJSONObject(i);
				//create a new item
				Items resultRow = new Items();
				resultRow.Items_id = json_data.getString("Items_id");
				resultRow.name = json_data.getString("name");
				resultRow.count = json_data.getString("count");
				arrayOfWebData.add(resultRow);
			}
		}
		catch(JSONException e){
			Log.w("log_tag","Error" + e.toString());
		}
		ListView mylistview = (ListView) findViewById(R.id.listview);
		aa=new Adapter();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
	