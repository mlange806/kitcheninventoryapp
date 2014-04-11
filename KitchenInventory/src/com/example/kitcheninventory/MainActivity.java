package com.example.kitcheninventory;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener{
	EditText etuser, etpass;
	Button blogin, bregister;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.w("On Create","Main Activity has been created");
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		initialise();
	}
	private void initialise(){
		etuser = (EditText) findViewById(R.id.etuser);
		etpass = (EditText) findViewById(R.id.etpass);
		blogin = (Button) findViewById(R.id.bsubmit);
		blogin.setOnClickListener(this);
		bregister = (Button) findViewById(R.id.bregister);
		bregister.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view){
				Intent myIntent = new Intent(view.getContext(), RegisterActivity.class);
				startActivity(myIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String script = "http://www.treyyeager.com/php/Login.php";
		List<NameValuePair> kvPairs = new ArrayList<NameValuePair>();
		kvPairs.add(new BasicNameValuePair("email_login", etuser.getText().toString()));
		kvPairs.add(new BasicNameValuePair("password_login", etpass.getText().toString()));
		kvPairs.add(new BasicNameValuePair("fromAndroid", "1"));
		PhpCommunicator comms = new PhpCommunicator(script, kvPairs, v.getContext());
		comms.runScript();
		
		String errorCode = "-100";
		if(comms.getResponse() != null)
		{
			errorCode = comms.getResponse();
			
			Log.w("DEBUG", errorCode);
		}
		
		switch(Integer.parseInt(errorCode))
		{
		case 0: Log.w("Success", errorCode);
		
			   Intent myIntent = new Intent(v.getContext(), MainMenuActivity.class);
			   myIntent.putExtra("email", etuser.getText().toString());
			   
		       startActivity(myIntent);
		
		       break;
		        
		case -1: Log.w("Username is not found", errorCode);break;
		case -2: Log.w("Incorrect Password", errorCode);break;
		case -99: Log.w("Cant establish the connection to databases", errorCode); break;
		case -100: Log.w("Cant establish the connection to the website", errorCode); break;
		}
		
		
	}
	
	
	

	
}

