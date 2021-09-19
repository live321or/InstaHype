<?php

require "connect_db.php";
$email=$_GET["email"];
$password=$_GET["password"];

$sql ="select * from users_android where email = '$email'";

$result=mysqli_query($db,$sql);

if(mysqli_num_rows($result)>0){
	$status = "exist";
}else{
	$sql = "insert into users_android(email,password) values('$email','$password');";
	
	if(mysqli_query($db,$sql)){
		$status = "ok";
	}else{
		$status = "error";
	}
}

echo json_encode(array("response"=>$status));

mysqli_close($db);

?>