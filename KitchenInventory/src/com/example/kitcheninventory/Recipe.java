package com.example.kitcheninventory;

/**
 * Recipe Class
 * 
 * Object for storing and retrieving information about a recipe.
 * 
 * Instantiate with the three attributes name, description, and instructions.
 * Change attributes with setAttribute methods.
 * Return attributes with getAttribute methods.
 * 
 * Author: Mark Lange
*/
public class Recipe {
	
    private String name;
    private String description;
    private String instructions;
    private String id;
    
    public Recipe(String id, String name, String instructions) {
      this.id           = id;
    	this.name         = name;
    	this.instructions = instructions;
    }
    
    public Recipe(String id, String name, String description, String instructions) {	
      this.id           = id;
    	this.name         = name;
    	this.description  = description;
    	this.instructions = instructions;
    } 
    
    public String getName() {
    	return name;
    }
    
    public String getDescription() {
    	if (description != "null") {
    	    return description;
    	}
    	else{
    		return "";
    	}
    }
    
    public String getID() {
      return id;
    }
    
    public String getInstructions() {
    	return instructions;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public void setDescription(String description) {
    	this.description = description;
    }
    
    public void setInstructions(String instructions) {
    	this.instructions = instructions;
    }
    
    public void setID(String id) {
      this.id = id;
    }
    
}
