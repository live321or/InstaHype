<?php
	$log = date('Y-m-d H:i:s') . ' setOrder.php:';
	file_put_contents(__DIR__ . '/log.txt', $log . PHP_EOL, FILE_APPEND);
	
	require "connect_db.php";
	
	$email=$_GET["email"];
	$count=$_GET["count"];
	$price=$_GET["price"];
	$type=$_GET["type"];
	$link=$_GET["link"];
	$balance=0;
	$log = date('Y-m-d H:i:s') . $email;
	file_put_contents(__DIR__ . '/log.txt', $log . PHP_EOL, FILE_APPEND);
	
	$date = date('Y-m-d H:i:s');
	
	$sql ="select balance from users_android where email = '$email' ";
	
	$result=mysqli_query($db,$sql);
	
	if(mysqli_num_rows($result)>0){
		$row = mysqli_fetch_assoc($result);
		$balance=$row['balance'];
		
		if($balance>= $price){
			$sql = "insert into users_android_purchases (email,date,count,price,type,link) 
			values('$email','$date','$count','$price','$type','$link');";
			
			
			
			if(mysqli_query($db,$sql)){
				$status = "200";
				$balance=$balance-$price;
				$sql = "UPDATE users_android SET balance ='$balance' where email = '$email';";
				
				if(mysqli_query($db,$sql)){
					// сюда нужно вписать токен вашего бота
					define('TELEGRAM_TOKEN', '1547164817:AAHDrJ3kCvS-pu5_QGTUijMaOFq-Nt_p9Ss');
					
					// сюда нужно вписать ваш внутренний айдишник
					define('TELEGRAM_CHATID1', '746984250');
					define('TELEGRAM_CHATID', '1114910983');
					
					
					function message_to_telegram($text)
					{
						$ch = curl_init();
						curl_setopt_array(
						$ch,
						array(
						CURLOPT_URL => 'https://api.telegram.org/bot' . TELEGRAM_TOKEN . '/sendMessage',
						CURLOPT_POST => TRUE,
						CURLOPT_RETURNTRANSFER => TRUE,
						CURLOPT_TIMEOUT => 10,
						CURLOPT_POSTFIELDS => array(
						'chat_id' => TELEGRAM_CHATID,
						'text' => $text,
						),
						)
						);
						curl_exec($ch);
					}
					function message_to_telegram1($text)
					{
						$ch = curl_init();
						curl_setopt_array(
						$ch,
						array(
						CURLOPT_URL => 'https://api.telegram.org/bot' . TELEGRAM_TOKEN . '/sendMessage',
						CURLOPT_POST => TRUE,
						CURLOPT_RETURNTRANSFER => TRUE,
						CURLOPT_TIMEOUT => 10,
						CURLOPT_POSTFIELDS => array(
						'chat_id' => TELEGRAM_CHATID1,
						'text' => $text,
						),
						)
						);
						curl_exec($ch);
					}
					
					message_to_telegram('У тебя новый заказ от: '.$email.
					' Вид: '.$type.
					' Кол-во: '.$count.
					' Цена: '.$price.
					' Ссылка: '.$link.
					'');
					message_to_telegram1('У тебя новый заказ от: '.$email.
					' Вид: '.$type.
					' Кол-во: '.$count.
					' Цена: '.$price.
					' Ссылка: '.$link.
					'');
				}
				}else{
				$status = "406";
			}
			
			
			$a=array("response"=>$status);
			echo json_encode($a);
			
			$log = date('Y-m-d H:i:s ') . json_encode($a);
			file_put_contents(__DIR__ . '/log.txt', $log . PHP_EOL, FILE_APPEND);
			
			mysqli_close($db);
		}
		else{
			$status="Balance < price";
			$a=array("response"=>$status);
			echo json_encode($a);
			
			mysqli_close($db);
		}
	}
	else{
		$status="Balance 404";
		$a=array("response"=>$status);
		echo json_encode($a);
		
		mysqli_close($db);		
	}
?>