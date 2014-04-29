package com.example.kitcheninventory;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListActivity extends Activity implements OnClickListener {
	private String item_data;
	private ArrayList<Item> items;
	private String userid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		userid = getIntent().getExtras().getString("userid");
		String script = "http://www.treyyeager.com/php/retrieveData.php?email=trey@treyyeager.com&table=item";
	    List<NameValuePair> kvPairs = new ArrayList<NameValuePair>();  
	    kvPairs.add(new BasicNameValuePair("email", userid));
	    kvPairs.add(new BasicNameValuePair("table", "item"));
		  
	    PhpCommunicator comms = new PhpCommunicator(script, kvPairs, this);
	    comms.runScript();
	      
	    item_data = comms.getResponse();
	      
	    Log.w("OUTPUT", item_data);
	    items = jsonToArray();
		
		
	    Log.w("Item Size *: ", Integer.toHexString(items.size()));
		final Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(v.getContext(), AdditemsActivity.class);
            	myIntent.putExtra("userid", userid);
            	startActivity(myIntent);
            	/*try{
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
 	*/
            }
        });
		//Make sure the names are loaded.
		for (int i = 0; i<items.size(); i++) {
		  Log.w("Item Name: ", items.get(i).getName());
		}
		
		populate(items);
		
		final ListView ilist=(ListView) findViewById(R.id.list);
		ilist.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng){
				Item selectedItem = (Item) ilist.getItemAtPosition(myItemInt);
				
				Intent myIntent = new Intent(myView.getContext(), EdititemsActivity.class);
				myIntent.putExtra("item_name", selectedItem.getName());
				myIntent.putExtra("upc_num", selectedItem.getUpc());
				myIntent.putExtra("item_description", selectedItem.getDescription());
				myIntent.putExtra("item_count", selectedItem.getCount());
				myIntent.putExtra("userid", userid);
				startActivity(myIntent);
			}
		});
		
		
	}

private ArrayList<Item> jsonToArray() {
		  
		ArrayList<Item> items = new ArrayList<Item>(); 
		String name,upc,description,count;
		
		try {
			
		  JSONArray jArray = new JSONArray(this.item_data);
		  JSONObject resultrow;
		  
		  for (int i = 0; i<jArray.length(); i++) {
		    
			resultrow = jArray.getJSONObject(i);
			
			name  = resultrow.getString("item_name");
			upc = resultrow.getString("upc_num");
			description = resultrow.getString("item_description");
			count = resultrow.getString("item_count");
			  
			Item it = new Item(name, description, upc, count);
			  
		    items.add(it);	  
	 
		  }
		  
		}
		catch(JSONException e) {
		  Log.w("Exception", e);
		}	
		
		return items;
	    
	  } 

private void populate(ArrayList<Item> items) {
		// TODO Auto-generated method stub
		ArrayAdapter<Item> adapter = new MyListAdapter();	
	    ListView list = (ListView) findViewById(R.id.list);		
		list.setAdapter(adapter);
	}
	
private class MyListAdapter extends ArrayAdapter<Item> {
		  
	    public MyListAdapter() {
	      super(ListActivity.this, R.layout.activity_items,items);
	    }
		
	    @Override
		public View getView(int position, View convertView, ViewGroup parent) {
	    	
	    	View itemView = convertView;
	    	if (itemView == null) {
	    		itemView = getLayoutInflater().inflate(R.layout.activity_items, parent, false);
	    	}
	    	
	    	Item it = items.get(position);
	    	
	    	TextView name= (TextView) itemView.findViewById(R.id.name); 
	    	name.setText(it.getName());
	 
	    	//TextView upc = (TextView) itemView.findViewById(R.id.upc); 
	    	//upc.setText(it.getUpc());
	    	
	    	//TextView description = (TextView) itemView.findViewById(R.id.description); 
	    	//description.setText(it.getDescription());
	    	
	    	TextView count = (TextView) itemView.findViewById(R.id.count); 
	    	count.setText(it.getCount());
	    	 	
	    	return itemView;
	    }   
		
	    
	    
	  }
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
}

}