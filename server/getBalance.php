<?php
require "connect_db.php";

$log = date('Y-m-d H:i:s') . ' getBalance.php:';
file_put_contents(__DIR__ . '/log.txt', $log . PHP_EOL, FILE_APPEND);

$email=$_GET["email"];
$password=$_GET["password"];

$sql ="select balance from users_android where email = '$email'";

$result=mysqli_query($db,$sql);

if(mysqli_num_rows($result)>0){
	$row = mysqli_fetch_assoc($result);
	$balance=$row['balance'];
	echo json_encode(array("balance"=>$balance));
}
mysqli_close($db);

?>