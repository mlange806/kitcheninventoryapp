package com.example.kitcheninventory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity implements OnClickListener{
	Button bsubmit;
	EditText email, pass, vemail, cpass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		email = (EditText) findViewById(R.id.email);
		vemail = (EditText) findViewById(R.id.vemail);
		pass = (EditText) findViewById(R.id.pass);
		cpass = (EditText) findViewById(R.id.cpass);
		bsubmit=(Button) findViewById(R.id.bsubmit);
		bsubmit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String script = "http://www.treyyeager.com/php/Register.php";
				List<NameValuePair> kvPairs = new ArrayList<NameValuePair>();
				kvPairs.add(new BasicNameValuePair("reg_email1", email.getText().toString()));
				kvPairs.add(new BasicNameValuePair("reg_email2", vemail.getText().toString()));
				kvPairs.add(new BasicNameValuePair("reg_pwd1", pass.getText().toString()));
				kvPairs.add(new BasicNameValuePair("reg_pwd2", cpass.getText().toString()));
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
				case -1: Log.w("Please fill out all the fields", errorCode);break;
				case -2: Log.w("Email dont match", errorCode);break;
				case -3: Log.w("Password dont match", errorCode);break;
				case -4: Log.w("Email address is already in use", errorCode);break;
				case -5: Log.w("Invalid password", errorCode);break;
				case -6: Log.w("Invalid email", errorCode); break;
				case -99: Log.w("Cant establish the connection to databases", errorCode); break;
				case -100: Log.w("Cant establish the connection to the website", errorCode); break;
				}
			}

			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
