<?
	$log = date('Y-m-d H:i:s') . ' enotCallBack.php:';
	file_put_contents(__DIR__ . '/log.txt', $log . PHP_EOL, FILE_APPEND);
	
	$merchant = $_REQUEST['merchant']; // id вашего магазина
	$secret_word2 = 'JVU996X-vDvsRaYdwX4Gd_yqA1i5MjR3'; // секретный ключ 2
	
	$email =$_REQUEST['custom_field']; 
	$amount =$_REQUEST['amount']; 
	$credited =$_REQUEST['credited']; 
	$merchant_id =$_REQUEST['merchant_id']; 
	$date = date('Y-m-d H:i:s');
	
	$sign = md5($merchant.':'.$_REQUEST['amount'].':'.$secret_word2.':'.$_REQUEST['merchant_id']);
	
	if ($sign != $_REQUEST['sign_2']) {
		
		$log = date('Y-m-d H:i:s') . ' bad sign! '.$email.'';
		file_put_contents(__DIR__ . '/log.txt', $log . PHP_EOL, FILE_APPEND);
		
	}
	else{
	    require "connect_db.php";
		$log = date('Y-m-d H:i:s') . ' good sign! '.$email.'';
		file_put_contents(__DIR__ . '/log.txt', $log . PHP_EOL, FILE_APPEND);
		
		$sql ="select balance from users_android where email = '$email' ";
        
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
			
            $sql = "insert into users_android_purchases (merchant_id,email,date,count,price,type,link) 
			values('$merchant_id','$email','$date','$credited','$amount','Balance','$email');";
			if(mysqli_query($db,$sql)){
                $log = date('Y-m-d H:i:s') . "add_Order_Balance";
                file_put_contents(__DIR__ . '/log.txt', $log . PHP_EOL, FILE_APPEND);
				}else{
                $log = date('Y-m-d H:i:s') . "not_Add_Order_Balance";
                file_put_contents(__DIR__ . '/log.txt', $log . PHP_EOL, FILE_APPEND);
			}
			
			}else{
            $log = date('Y-m-d H:i:s') . " User 404";
            file_put_contents(__DIR__ . '/log.txt', $log . PHP_EOL, FILE_APPEND);
		}
        mysqli_close($db);
	}
	
	
	// далее ваш код
	
	
	
?>