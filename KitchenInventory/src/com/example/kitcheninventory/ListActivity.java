package com.example.kitcheninventory;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListActivity extends Activity {
	private String item_data;
	private ArrayList<Item> items;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
			
		String script = "http://www.treyyeager.com/php/retrieveData.php?email=trey@treyyeager.com&table=item";
	    String email = getIntent().getExtras().getString("email");
		  
	    List<NameValuePair> kvPairs = new ArrayList<NameValuePair>();  
	    kvPairs.add(new BasicNameValuePair("email", email));
	    kvPairs.add(new BasicNameValuePair("table", "item"));
		  
	    PhpCommunicator comms = new PhpCommunicator(script, kvPairs, this);
	    comms.runScript();
	      
	    item_data = comms.getResponse();
	      
	    Log.w("OUTPUT", item_data);
	    items = jsonToArray();
		
		
	    Log.w("Item Size *: ", Integer.toHexString(items.size()));
	    
		//Make sure the names are loaded.
		for (int i = 0; i<items.size(); i++) {
		  Log.w("Item Name: ", items.get(i).getName());
		}
		
		populate(items);
		
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
			Log.w("Name **", name);
			upc = resultrow.getString("upc_num");
			description = resultrow.getString("item_description");
			count = resultrow.getString("item_count");
			  
			Item it = new Item(name, upc, description, count);
			  
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

}