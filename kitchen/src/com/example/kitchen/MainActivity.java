package com.example.kitchen;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),  "Sumbit has been pressed!",  Toast.LENGTH_LONG).show();
				
				//Perform action
				EditText username = (EditText) findViewById(R.id.editText2);
				EditText password = (EditText) findViewById(R.id.editText1);
				String un = username.toString();
				String pwd = password.toString();
				
				//Encrypt Password
				
				String url = "http://www.treyyeager.com/php/Register.php";
				url += "?username_register_1=" + un + "&username_register_2=" + un + "&password1=" + pwd + "&password2=abc";

				HttpClient client = new DefaultHttpClient();
				
				try{
					client.execute(new HttpGet(url));
				}
				catch(IOException e)
				{
					
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
