<?php

define ("HOST", "localhost");
define ("USER", "a0501369_admin");
define ("PASS", "admin");
define ("DB", "a0501369_admin");
$db = @mysqli_connect(HOST, USER, PASS, DB) or die ('Не получилось из-за @mysqli_connect :(');
mysqli_set_charset ($db, 'utf8') or die ('Не получилось из-за mysql_set_charset :(');
if($db)
echo"Connecttion success...";
else
echo"Connecttion Failed...";

?>
