package com.example.kitcheninventory;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdditemsActivity extends Activity implements OnClickListener{
	Button save;
	EditText name,upc,count,des;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		final String userid = getIntent().getExtras().getString("userid");
		name = (EditText) findViewById(R.id.name);
		upc = (EditText) findViewById(R.id.upc);
		count = (EditText) findViewById(R.id.count);
		des = (EditText) findViewById(R.id.des);
		save=(Button) findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String script = "http://www.treyyeager.com/php/addObject.php";
				List<NameValuePair> kvPairs = new ArrayList<NameValuePair>();
				kvPairs.add(new BasicNameValuePair("name", name.getText().toString()));
				kvPairs.add(new BasicNameValuePair("upc", upc.getText().toString()));
				kvPairs.add(new BasicNameValuePair("count", count.getText().toString()));
				kvPairs.add(new BasicNameValuePair("desc", des.getText().toString()));
				kvPairs.add(new BasicNameValuePair("type", "ITEM"));
				kvPairs.add(new BasicNameValuePair("fromAndroid", "1"));
				kvPairs.add(new BasicNameValuePair("userid", userid));
				PhpCommunicator comms = new PhpCommunicator(script, kvPairs, v.getContext());
				comms.runScript();
			
			}
		});
		final Button scan = (Button) findViewById(R.id.scan);
        scan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	try{
            		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            		intent.putExtra("SCAN_MODE","PRODUCT_MODE");
            		intent.putExtra("SAVE_HISTORY",  false);
            		startActivityForResult(intent,0);
            	} catch(Exception e)
            	{
            		Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
            		Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
            		startActivity(marketIntent);
            	}
 	
            }
        });
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 0){
			if(resultCode == RESULT_OK){
				String contents = data.getStringExtra("SCAN_RESULT");
				Log.w("Scan",contents);
				Toast.makeText(this, contents, Toast.LENGTH_LONG).show();
				
            	//String key = "d63a075d6b3220ac5d4b51cb2bedf387717ee886";
            	//String script = "http://www.upcdatabase.com/xmlprc";
            	upc.setText(data.getStringExtra("SCAN_RESULT"));
            	count.setText("1");
            	
            	//List<NameValuePair> kvPairs = new ArrayList<NameValuePair>();
				//kvPairs.add(new BasicNameValuePair("rpc_key", key));
				//kvPairs.add(new BasicNameValuePair("upc", contents));
				
            	//PhpCommunicator comms = new PhpCommunicator(script, kvPairs, this);
				//comms.runScript();
				//Log.w("Test", comms.getResponse());
			}else
				if(resultCode ==RESULT_CANCELED){
					
				}
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.additems, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

}
