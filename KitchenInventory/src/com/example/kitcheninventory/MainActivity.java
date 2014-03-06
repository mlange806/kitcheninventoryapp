package com.example.kitcheninventory;
//This is a test comment for commit purposes2

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener{
	EditText etuser, etpass;
	Button blogin, bregister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
			PhpCommunicator comms = new PhpCommunicator(script, kvPairs);
			comms.runScript();
			
			String errorCode = "-100";
			if(comms.getResponse() != null)
			{
				try {
					errorCode = EntityUtils.toString(comms.getResponse().getEntity());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.w("DEBUG", errorCode);
			}
			
			switch(Integer.parseInt(errorCode))
			{
			case 0: Log.w("Success", errorCode);break;
			case -1: Log.w("Username is not found", errorCode);break;
			case -2: Log.w("Incorrect Passwor", errorCode);break;
			case -99: Log.w("Cant establish the connection to databases", errorCode); break;
			case -100: Log.w("Cant establish the connection to the website", errorCode); break;
			}
		}
	
	}

