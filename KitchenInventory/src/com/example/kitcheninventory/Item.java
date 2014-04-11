package com.example.kitcheninventory;

public class Item {
	
	
	private String name;
	private String upc;
	private String description;
	private String count;
	public Item(String name, String description, String upc, String count){	
    	this.name         = name;
    	this.description  = description;
    	this.upc = upc;
    	this.count = count;
    	
    } 
	public String getName(){
    	return this.name;
    }
    
    public String getDescription(){
    	if (this.description != "null") {
    	    return this.description;
    	}
    	else{
    		return "";
    	}
    }
    public String getUpc(){
    	return this.upc;
    }
    public String getCount(){
    	return this.count;
    }
    public void setName(String name){
    	this.name = name;
    }
    
    public void setDescription(String description){
    	this.description = description;
    }
    public void setUpc(String upc){
    	this.upc = upc;
    }
    public void setCount(String count){
    	this.count = count;
    }
    
    
}
