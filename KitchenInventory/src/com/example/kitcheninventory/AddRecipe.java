package com.example.kitcheninventory;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//This page allows a user to type in new recipe information
//and add it to their recipe table.
public class AddRecipe extends Activity {
  private String userid;
  
  @Override
  //This method is called when Add Recipe is started.
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_recipe);
    
    //Get the userID from the previous activity.
    userid = getIntent().getExtras().getString("userid");
    
    //Pushing the "Add" button will add the recipe to the database and return 
    //the user to the list of recipes.
    final Button add_button = (Button) findViewById(R.id.addRecipeButton);   
    add_button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        addRecipe();
        
        //End activity and return to recipe list.
        finish();   
      }
    });
  }
  
  //Adds a recipe with the EditText views attributes
  //Preconditions: Name and Directions's EditText views are not empty.
  private void addRecipe() {
    //Get the text
    final EditText name  = (EditText) findViewById(R.id.addRecipeName);
    final EditText desc  = (EditText) findViewById(R.id.addRecipeDescription);
    final EditText dir   = (EditText) findViewById(R.id.addRecipeDirections);
    
    //Convert data to string
    String sName = name.getText().toString();
    String sDesc = desc.getText().toString();
    String sDir  = dir.getText().toString();
    
    //Store needed arguments as kvPairs
    List<NameValuePair> kvPairs = new ArrayList<NameValuePair>();
    kvPairs.add(new BasicNameValuePair("userid", "2"));
    kvPairs.add(new BasicNameValuePair("type", "recipe"));
    kvPairs.add(new BasicNameValuePair("id", "null"));
    kvPairs.add(new BasicNameValuePair("name", sName));
    kvPairs.add(new BasicNameValuePair("desc", sDesc));
    kvPairs.add(new BasicNameValuePair("dir", sDir));
    kvPairs.add(new BasicNameValuePair("fromAndroid", "1"));
    
    //Add the recipe into the database.
    String script = "http://www.treyyeager.com/php/addObject.php";             
    PhpCommunicator comms = new PhpCommunicator(script, kvPairs, this);
    comms.runScript();  
  }
}
