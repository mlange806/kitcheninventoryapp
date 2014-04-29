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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/*View Recipe List Activity
 Activity view for loading and displaying a user's recipe table.*/
public class ViewRecipe extends Activity {	
  private String recipe_data;
  private ArrayList<Recipe> recipes;
  private String userid;
  
  @Override
  //This method is called when View Recipe starts.
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_recipes);
    
    //This recipe list belongs to the user who just logged in.
    userid = getIntent().getExtras().getString("userid");
    
    getRecipes();	
    recipes = jsonToArray();
    populateListView(recipes);
    
    //Clicking on a recipe takes the user to that recipe's entry page
    final ListView rList = (ListView) findViewById(R.id.listViewRecipes);
    rList.setOnItemClickListener(new OnItemClickListener() {
      public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
        Recipe selectedRecipe = (Recipe)rList.getItemAtPosition(myItemInt);
        
        //Declare intent and store attributes and userID as extras  
        Intent myIntent = new Intent(myView.getContext(), ViewRecipeEntry.class);
        myIntent.putExtra("name", selectedRecipe.getName()); 
        myIntent.putExtra("description", selectedRecipe.getDescription()); 
        myIntent.putExtra("directions", selectedRecipe.getInstructions()); 
        myIntent.putExtra("userid", userid);
        myIntent.putExtra("id", selectedRecipe.getID());
        
        //Go to ViewRecipeEntry Activity
        startActivityForResult(myIntent, 0);
      }                 
    });
    
    //Pushing the "Add Recipe" button will take the user to the add recipe page.
    final Button add_button = (Button) findViewById(R.id.goToAddRecipeButton);   
    add_button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        //Declare intent and store the userID as an extra 
        Intent myIntent = new Intent(v.getContext(), AddRecipe.class);
        myIntent.putExtra("userid", userid);
        
        //Go to Add Recipe Activity
        startActivity(myIntent);   
      }
    });
  }
  
  @Override
  //This method is called after an activity has been closed. 
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    //Refresh the list on request code "0".
    if(requestCode == 0) {
      getRecipes(); 
      recipes = jsonToArray();
      populateListView(recipes);
    }
  }
  
  //Converts recipe_data into a list of Recipe objects
  private ArrayList<Recipe> jsonToArray(){
    ArrayList<Recipe> recipes = new ArrayList<Recipe>(); 
    String name, desc, instr, id;
	
    try {
      JSONArray jArray = new JSONArray(recipe_data);
      JSONObject row;
	    
      //Parse the recipes and store them in the recipe list.
  	  for (int i = 0; i<jArray.length(); i++) {
  	    row = jArray.getJSONObject(i);
  		  
  	    //Load attributes from database.
  		  name  = row.getString("recipe_name"); 
  		  desc  = row.getString("recipe_description");
  	    instr = row.getString("recipe_direction");
  	    id    = row.getString("recipe_id");
  	    
  	    //Create recipe object.
  		  Recipe r = new Recipe(id, name, desc, instr);
  		  
  		  //Store recipe in list.
  	    recipes.add(r);	  
  	  }
	  }
    
  	catch(JSONException e){
  	  //Could not parse the JSON.
  	  Log.w("Exception", e);
  	}	
  	
  	return recipes;
  } 
  
  //Loads a user's recipe table from the data base into the variable recipes. 
  private void getRecipes(){
    String script = "http://www.treyyeager.com/php/retrieveData.php";
     
    List<NameValuePair> kvPairs = new ArrayList<NameValuePair>();  
    kvPairs.add(new BasicNameValuePair("email", userid));
    kvPairs.add(new BasicNameValuePair("table", "recipe"));
	  
    PhpCommunicator comms = new PhpCommunicator(script, kvPairs, this);
    comms.runScript();  
    recipe_data = comms.getResponse();
  }
  
  //Loads the recipe array into the ListView for Display
  private void populateListView(ArrayList<Recipe> recipes) {		
    ArrayAdapter<Recipe> adapter = new MyListAdapter();	
    ListView list = (ListView) findViewById(R.id.listViewRecipes);
    list.setAdapter(adapter);
  }
  
  /*Custom adaptor that displays a recipe object's 
    name and description in the view using recipe_entry.*/ 
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
