<? php

	/*Login Script
	  
	  Takes email and password input and using json_encode 
	  returns 0 for a correct login, -1 for an incorrect email
	  and -2 for an incorrect password.
	  
	  WARNING: No protection against SQL injections as of yet.*/

	config.php
	
	$email    = $_POST['email_login'];	
	$password = $_POST['password_login'];
	
	#Encrypt password with PHP 5.5
	$password = password_hash($password, PASSWORD_BCRYPT);
	
	
	#Perform seperate queries to give specific error messages,
	#Prepare statements using PHP Data Objects to prevent SQL injection
  	$stm = $dbh->prepare("SELECT * 
                    	  FROM User_Login 
                    	  WHERE email    = ?;");
                    	     
    $stm->execute($email); 
    
    $sql_em = $stm->fetch();
                            
  	$stm    = $dbh->prepare("SELECT * 
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
