<?php
	$MERCHANT_ID   = 10925;                 // ID магазина
	$SECRET_WORD   = 'eComJj-8u1rvVrQogkWCO0EE1kHkLoxb';   // Секретный ключ
	$ORDER_AMOUNT  = $_POST["price"];                 // Сумма заказа
	$PAYMENT_ID    = time();             // ID заказа (мы используем time(), чтобы был всегда уникальный ID)
	
	$sign = md5($MERCHANT_ID.':'.$ORDER_AMOUNT.':'.$SECRET_WORD.':'.$PAYMENT_ID);  //Генерация ключа
	
	header('Location: https://enot.io/pay?m=10925&oa='.$ORDER_AMOUNT.'&o='.$PAYMENT_ID.'&s='.$sign.'',true, 301);
	
?>