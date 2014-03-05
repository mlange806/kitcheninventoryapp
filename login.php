<? php

	/*Login Script
	  
	  Takes email and password input and using json_encode 
	  returns 0 for a correct login, -1 for an incorrect email
	  and -2 for an incorrect password.
	  
	  WARNING: No protection against SQL injections as of yet.*/

	config.php
	
	$email    = $_POST['email_login'];	
	$password = $_POST['password_login'];
	
	#Salt is the hashed password
	$salt = mysql_query("SELECT pwd
	                     FROM User_Login
	                     WHERE email = '$email';");
	
	#Check to see if the email was found in the database.                     
	if(!$salt){
	    echo json_encode(-2);		
	}
	
	#Encrypt password with PHP 5.0. The crypt function will return the value of $salt if the password is correct.
	$password = crypt($password, $salt);
	
	/*TODO: Prevent SQL injections. Is there a way to prepare statements in PHP 5.0?*/
	
	#If the password matches the hashed password, the login was sucessful.
	if($password = $salt){
	    echo json_encode(0);	
	}
	
	#Else, the password is incorrect.
	else{
	    echo json_encode(-2);		
	}
	
	mysql_close();

?>
