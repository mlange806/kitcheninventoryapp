package com.example.kitcheninventory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends Activity {
	Button items, recipes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	  
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainmenu);
		final String etuser = getIntent().getExtras().getString("email");

        final Button button = (Button) findViewById(R.id.items);   
        button.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
            Intent myIntent = new Intent(v.getContext(), ListActivity.class);
            myIntent.putExtra("email", etuser); 
           	startActivity(myIntent);
          }
        });
        
        final Button recipes = (Button) findViewById(R.id.recipes);      
        recipes.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
            Intent myIntent = new Intent(v.getContext(), ViewRecipe.class);
            myIntent.putExtra("email", etuser); 
            startActivity(myIntent);
          }
        });
	}
}
