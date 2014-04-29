package com.example.kitcheninventory;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/* Displays the contents of a recipe entry.
 * Precondition: Recipe list has been populated. */
public class ViewRecipeEntry extends Activity {
  String id;
  String name;
  String description;
  String directions;
  String userid;
  
  @Override
  //This method is called when View Recipe Entry is started.
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_recipe_entry);
    
    //Recipe attributes to display and email sent from ViewRecipe
    id          = getIntent().getExtras().getString("id");
    name        = getIntent().getExtras().getString("name");
    description = getIntent().getExtras().getString("description");
    directions  = getIntent().getExtras().getString("directions");
    userid      = getIntent().getExtras().getString("userid");
    
    //Sets the view.
    setView(name, description, directions);
    
    //Pushing the update button updates the user's recipe table
    //And returns them to the ViewRecipe Activity
    final Button update_button = (Button) findViewById(R.id.recipeUpdateButton);   
    update_button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        updateRecipe();
        
        //Inform the user that the recipe has been updated.
        Toast.makeText(v.getContext(), "Recipe updated.", Toast.LENGTH_LONG).show();
        
        //End activity and return to recipe list.
        finish();   
      }
    });
    
    //Pushing the delete button deletes the recipe from the table
    //And returns the user to the ViewRecipe Activity
    final Button delete_button = (Button) findViewById(R.id.recipeDeleteButton);   
    delete_button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        deleteRecipe();
        
        //Inform the user that the recipe has been deleted.
        Toast.makeText(v.getContext(), "Recipe deleted.", Toast.LENGTH_LONG).show();
        
        //End activity and return to recipe list.
        finish();   
      }
    });
  }
  
  //Updates the user's recipe table with the text in the EditViews
  private void updateRecipe() {
    //Get the new text
    final EditText newName  = (EditText) findViewById(R.id.recipeName);
    final EditText newDescr = (EditText) findViewById(R.id.recipeDescription);
    final EditText newDir   = (EditText) findViewById(R.id.recipeDirections);
    
    //Convert data to string
    String nName = newName.getText().toString();
    String nDesc = newDescr.getText().toString();
    String nDir  = newDir.getText().toString();
    
    //Store needed arguments as kvPairs
    List<NameValuePair> kvPairs = new ArrayList<NameValuePair>();
    kvPairs.add(new BasicNameValuePair("userid", userid));
    kvPairs.add(new BasicNameValuePair("type", "recipe"));
    kvPairs.add(new BasicNameValuePair("id", id));
    kvPairs.add(new BasicNameValuePair("name", nName));
    kvPairs.add(new BasicNameValuePair("desc", nDesc));
    kvPairs.add(new BasicNameValuePair("dir", nDir));
    kvPairs.add(new BasicNameValuePair("fromAndroid", "1"));
    
    //Update the items with the PHP script
    String script = "http://www.treyyeager.com/php/addObject.php";             
    PhpCommunicator comms = new PhpCommunicator(script, kvPairs, this);
    comms.runScript();  
    Log.w("AddObject", comms.getResponse());
    
  }
  
  private void deleteRecipe(){
    //Store needed arguments as kvPairs
    List<NameValuePair> kvPairs = new ArrayList<NameValuePair>();
    kvPairs.add(new BasicNameValuePair("user_id", userid));
    kvPairs.add(new BasicNameValuePair("id", id));
    kvPairs.add(new BasicNameValuePair("type", "recipe"));
    kvPairs.add(new BasicNameValuePair("fromAndroid", "1"));    
    
    //Update the items with the PHP script
    String script = "http://www.treyyeager.com/php/delete.php";             
    PhpCommunicator comms = new PhpCommunicator(script, kvPairs, this);
    comms.runScript();  
  }
  
  //Changes the TextViews to display recipe attributes
  private void setView(String name, String description, String directions) {	
    TextView n = (TextView) findViewById(R.id.recipeName);		
    n.setText(this.name);
    TextView de = (TextView) findViewById(R.id.recipeDescription);
    de.setText(this.description);
    TextView di = (TextView) findViewById(R.id.recipeDirections);
    di.setText(this.directions);
  }  
}
