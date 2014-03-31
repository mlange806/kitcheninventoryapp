package com.example.kitcheninventory;

/**
 * Recipe Class
 * 
 * Objects for storing and retrieving information about a recipe.
 * 
 * Instantiate with the three attributes name, description, and instructions.
 * Change attributes with setAttribute methods.
 * Return attributes with getAttribute methods.
 * 
 * Author: Mark Lange
 * 
*/
public class Recipe {
	
    private String name;
    private String description;
    private String instructions;
    
    public Recipe(String name, String instructions){
    	this.name         = name;
    	this.instructions = instructions;
    }
    
    public Recipe(String name, String description, String instructions){	
    	this.name         = name;
    	this.description  = description;
    	this.instructions = instructions;
    } 
    
    public String getName(){
    	return this.name;
    }
    
    public String getDescription(){
    	return this.description;
    }
    
    public String getInstructions(){
    	return this.instructions;
    }
    
    public void setName(String name){
    	this.name = name;
    }
    
    public void setDescription(String description){
    	this.description = description;
    }
    
    public void setInstructions(String instructions){
    	this.instructions = instructions;
    }
    
}
