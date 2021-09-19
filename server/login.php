<?php
require "connect_db";

$email=$_GET["email"];
$password=$_GET["password"];

$sql ="select balance from users_android where email = '$email' and password='$password'";

$result=mysqli_query($db,$sql);

if(mysqli_num_rows($result)>0){
	$status = "failed";
	echo json_encode(array("response"=>$status));
}else{
	$row = mysqli_fetch_assoc($result);
	$balance=$row['balance'];
	$status = "ok";
		echo json_encode(array("response"=>$status,"balance"=>$balance));
}
mysqli_close($db);

?>