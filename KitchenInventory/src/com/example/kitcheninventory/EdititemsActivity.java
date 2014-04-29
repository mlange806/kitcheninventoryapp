package com.example.kitcheninventory;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EdititemsActivity extends Activity {
	EditText name1, upc1, desc1, count1;
	String name, desc, upc, count, userid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edititems);
		name = getIntent().getExtras().getString("item_name");
		desc= getIntent().getExtras().getString("item_description");
		upc = getIntent().getExtras().getString("upc_num");
		count = getIntent().getExtras().getString("item_count");
		setView(name,upc,desc,count);
		final String userid = getIntent().getExtras().getString("userid");
		
		Button update = (Button) findViewById(R.id.update);
		update.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				updateItem();
			  //End activity and return to recipe list.
        finish();  
			}
		
		});
		Button delete = (Button) findViewById(R.id.delete);
		delete.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				deleteItem();
			  //End activity and return to recipe list.
        finish();  
			}
		});
	}
	public void updateItem(){
		String nname = name1.getText().toString();
		String ndesc = desc1.getText().toString();
		String ncount = count1.getText().toString();
		
		List<NameValuePair> kvPairs = new ArrayList<NameValuePair>();
		kvPairs.add(new BasicNameValuePair("name", nname));
		kvPairs.add(new BasicNameValuePair("upc", upc));
		kvPairs.add(new BasicNameValuePair("desc", ndesc));
		kvPairs.add(new BasicNameValuePair("count", ncount));
		kvPairs.add(new BasicNameValuePair("type", "ITEM"));
		kvPairs.add(new BasicNameValuePair("fromAndroid", "1"));
		kvPairs.add(new BasicNameValuePair("userid", userid));
		
		String script = "http://www.treyyeager.com/php/addObject.php";
		PhpCommunicator comms = new PhpCommunicator(script, kvPairs, this);
		comms.runScript();
		
		}
	public void deleteItem(){
		String nname = name1.getText().toString();
		String ndesc = desc1.getText().toString();
		String ncount = count1.getText().toString();
		
		List<NameValuePair> kvPairs = new ArrayList<NameValuePair>();

		kvPairs.add(new BasicNameValuePair("id", upc));

		kvPairs.add(new BasicNameValuePair("type", "ITEM"));
		kvPairs.add(new BasicNameValuePair("fromAndroid", "1"));
		kvPairs.add(new BasicNameValuePair("user_id", "2"));
		
		String script = "http://www.treyyeager.com/php/delete.php";
		PhpCommunicator comms = new PhpCommunicator(script, kvPairs, this);
		comms.runScript();
	}
	private void setView(String name, String upc, String description,
		String count) {
		Toast.makeText(this, this.name, Toast.LENGTH_LONG).show();
		name1=(EditText) findViewById(R.id.name1);
		name1.setText(this.name);
		desc1=(EditText) findViewById(R.id.desc1);
		desc1.setText(this.desc);
		count1=(EditText) findViewById(R.id.count1);
		count1.setText(this.count);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edititems, menu);
		return true;
	}

}
