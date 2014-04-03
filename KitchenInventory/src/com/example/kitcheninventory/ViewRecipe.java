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

/**
 * View Recipe List Activity 
 * 
 * Class for loading and displaying a user's recipe table.
 * 
 * Author: Mark Lange
*/
public class ViewRecipe extends Activity {
	
  //TO-DO implement OnClickListener for buttons
	
  private String recipe_data;
  private ArrayList<Recipe> recipes;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	  
    super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_view_recipes);
	
	getRecipes();
	
	System.out.println("Test");
	
	recipes = jsonToArray();
	
	
	//Make sure the names are loaded.
	for (int i = 0; i<recipes.size(); i++) {
	  Log.w("Recipe Name: ", recipes.get(i).getName());
	}
	
    populateListView(recipes);
    
  }
  
  //Converts recipe_data into a list of Recipe objects
  private ArrayList<Recipe> jsonToArray() {
	  
	ArrayList<Recipe> recipes = new ArrayList<Recipe>(); 
	String name, desc, instr;
	
	try {
	  JSONArray jArray = new JSONArray(this.recipe_data);
	  JSONObject row;
	  
	  //Log.w("JSON TEST", firstRow);
	  //Log.w("Size", Integer.toString(jArray.length()));
	  
	  for (int i = 0; i<jArray.length(); i++) {
	    
		row = jArray.getJSONObject(i);
		  
		name  = row.getString("recipe_name"); 
		desc  = row.getString("recipe_description");
	    instr = row.getString("recipe_direction");
		  
		Recipe r = new Recipe(name, desc, instr);
		  
	    recipes.add(r);	  
 
	  }
	  
	}
	catch(JSONException e) {
	  Log.w("Exception", e);
	}	
	
	return recipes;
    
  } 
  
  /** 
   * Get Recipes
   * 
   * Loads a user's recipes table from the data base into the variable recipes. 
   * */
  private void getRecipes() {
	  
    String script = "http://www.treyyeager.com/php/retrieveData.php";
    String email = getIntent().getExtras().getString("email");
	  
    List<NameValuePair> kvPairs = new ArrayList<NameValuePair>();  
    kvPairs.add(new BasicNameValuePair("email", email));
    kvPairs.add(new BasicNameValuePair("table", "recipe"));
	  
    PhpCommunicator comms = new PhpCommunicator(script, kvPairs, this);
    comms.runScript();
      
    recipe_data = comms.getResponse();
      
    Log.w("OUTPUT", recipe_data);
    
  }
  
  /** 
   * Populate List View
   * 
   * Loads the recipe array into the ListView for Display
   * */
  private void populateListView(ArrayList<Recipe> recipes) {
		
	ArrayAdapter<Recipe> adapter = new MyListAdapter();	
    ListView list = (ListView) findViewById(R.id.listViewRecipes);		
	list.setAdapter(adapter);

  }
  
  
  //Adapter used for displaying a Recipe Class
  private class MyListAdapter extends ArrayAdapter<Recipe> {
	  
    public MyListAdapter() {
      super(ViewRecipe.this, R.layout.recipe_entry, recipes);
    }
	
    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
    	
    	View itemView = convertView;
    	if (itemView == null) {
    		itemView = getLayoutInflater().inflate(R.layout.recipe_entry, parent, false);
    	}
    	
    	Recipe r = recipes.get(position);
    	
    	TextView nameText = (TextView) itemView.findViewById(R.id.textName); 
    	nameText.setText(r.getName());
    	
    	TextView descText = (TextView) itemView.findViewById(R.id.textDesc); 
    	descText.setText(r.getDescription());
    	 	
    	return itemView;
    }   
	
    
    
  }
  
}
