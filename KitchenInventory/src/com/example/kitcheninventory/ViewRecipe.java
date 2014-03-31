package com.example.kitcheninventory;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewRecipe extends Activity{
	
	//TO-DO implement OnClickListener for buttons

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_recipes);
		
		populateListView();
	}
	
	private void populateListView(){
		
		List<String> myRecipes = new ArrayList<String>();
		
		//TO-DO bring in recipes from database and load them
		for(int i = 0; i<1; i++){
			Recipe new_entry = new Recipe("Sandwitch", "Merge bread and meat.");
			myRecipes.add(new_entry.getName());
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            this, 
	        R.layout.recipe_entry, 
		    myRecipes
		);
		
		ListView list = (ListView) findViewById(R.id.listViewRecipes);
		
		list.setAdapter(adapter);
	}
}
