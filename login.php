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
	$salt = mysql_query("SELECT password
	                     FROM User_Login
	                     WHERE email = '$email';");
	
	#Encrypt password with PHP 5.0. The crypt function will return the value of $salt if the password is correct.
	$password = crypt($password, $salt);
	
	/*TODO: Prevent SQL injections. Is there a way to prepare statements in PHP 5.0?*/
	
	#Query the database with the input for a match.
  	$sql_em = mysql_query("SELECT * 
	                       FROM User_Login 
	                       WHERE email    = ?;");
                            
  	$sql    = mysql_query("SELECT * 
  	                       FROM User_Login 
	                       WHERE email    = ? and
	                       password = ?;");
    
	$stm->execute(array($email, $password));
                                   
	$sql = stm->fetch();
	
	$numRows_em = mysql_size($sql_em);
  	$numRows    = mysql_size($sql);
	
	#Return 0 for correct email and password, -1 for incorrect email, 
	#and -2 for incorrect password.
	if($numRows>0){
		echo json_encode(0);
	}
	
	else if($numRows_em == 0){
		echo json_encode(-1);
	}

	else{
		echo json_encode(-2);
	}
	
	mysql_close();

?>
