<?php
	$log = date('Y-m-d H:i:s') . ' notify.php:';
	file_put_contents(__DIR__ . '/log.txt', $log . PHP_EOL, FILE_APPEND);
	
	$fk_merchant_id = '206154';
	$fk_merchant_key = 'vt4mxlsd';
	
	$id = $_REQUEST['MERCHANT_ORDER_ID'];
	
	$id = $_REQUEST['MERCHANT_ORDER_ID'];
	
	$log = date('Y-m-d H:i:s') . $merchant_id;
	file_put_contents(__DIR__ . '/log.txt', $log . PHP_EOL, FILE_APPEND);
	
	$sign = md5($fk_merchant_id.':'.$_REQUEST['AMOUNT'].':'.$fk_merchant_key.':'.$_REQUEST['MERCHANT_ORDER_ID']);
	
    if ($sign != $_REQUEST['SIGN']) {
		
        $log = date('Y-m-d H:i:s') . ' wrong sign';
        file_put_contents(__DIR__ . '/log.txt', $log . PHP_EOL, FILE_APPEND);
		
		}
	else{
        $log = date('Y-m-d H:i:s') . ' true sign';
        file_put_contents(__DIR__ . '/log.txt', $log . PHP_EOL, FILE_APPEND);
        
        $sign=md5('206154'.'vt4mxlsd');
        
        $res = file_get_contents('https://www.free-kassa.ru/api.php?merchant_id=206154&s='.$sign.'&action=check_order_status&order_id='.$id.'&type=json');
		
        
        $json2 = json_decode($res,TRUE);      
        $email=$json2["data"]["email"];
        $amount=$json2["data"]["amount"];
		$log = date('Y-m-d H:i:s') . $email;
		file_put_contents(__DIR__ . '/log.txt', $log . PHP_EOL, FILE_APPEND);
		
        require "connect_db.php";
        
        $sql ="select balance from users_android where email = '$email'";
        
        $result=mysqli_query($db,$sql);
		
		if(mysqli_num_rows($result)>0){
			$row = mysqli_fetch_assoc($result);
			$balance=$row['balance'];
			$balance=$balance+$amount;
			$log = date('Y-m-d H:i:s') . $balance;
			file_put_contents(__DIR__ . '/log.txt', $log . PHP_EOL, FILE_APPEND);
			$sql = "UPDATE users_android SET balance ='$balance' where email = '$email';";
			// $result=mysqli_query($db,$sql);
			if(mysqli_query($db,$sql)){
				$log = date('Y-m-d H:i:s') . "addBalance";
				file_put_contents(__DIR__ . '/log.txt', $log . PHP_EOL, FILE_APPEND);
				}else{
				$log = date('Y-m-d H:i:s') . "not_Add_Balance";
				file_put_contents(__DIR__ . '/log.txt', $log . PHP_EOL, FILE_APPEND);
			}
			}else{
			$log = date('Y-m-d H:i:s') . " 0";
			file_put_contents(__DIR__ . '/log.txt', $log . PHP_EOL, FILE_APPEND);
		}
		mysqli_close($db);
		
		// $sql = "insert into users_android (email,password,balance) values('$email','$password','20');";
		
		
		
	}
    
    
?>	