<? php

	#Login script. 
	#Uses the inputs email_login and password_login to
	#search for a user. If found, they are taken to login_success.php
	#If not, they are shown an error message. 

  config.php
	
	$email = $_POST['email_login'];	
	$password = $_POST['password_login'];
	
	#TODO Protect against SQL injection and any other security risks
	
	#Encrypt password
	$password = md5($password);
	
  $sql = mysql_query("SELECT * 
                      FROM User_Login 
                      WHERE email    = '$email' and 
                            password = '$password';");

  numRows = mysql_size(sql);
	
	if(numRows>0){
		header("location:login_success.php");
	}

	else{
		echo "Login failed.";
	}

?>
