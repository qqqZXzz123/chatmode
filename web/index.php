<?php

$filename = "nicks.txt";


if ($_GET["hash"] == "bf1822f170dc384c42f12a98535610ca3c5ff17af9c1aeff343224cec863c382") 
{
	if ($_GET["attr"] == "add") 
	{
		if ($_GET["name"] != null) {
			$nickname = $_GET["name"];
			$str = $nickname . " ";
			$fp = fopen($filename, "a");
			fwrite($fp, $str);
			fclose($fp);
			echo $str;
		}
	}
	else if ($_GET["attr"] == "delete")
	{
		if ($_GET["name"] != null) {
			$nickname = $_GET["name"];
			$newnames = "";

			$fp2 = fopen($filename, "r");
			$contents = fread($fp2, filesize($filename));
			fclose($fp2);
			$arrayOfNames = explode(" ", $contents);
			echo count($arrayOfNames) - 2;
			echo $arrayOfNames[count($arrayOfNames)-2];
			for ($i = 0; $i < count($arrayOfNames) - 1; $i++) { 
				if ($arrayOfNames[$i] != $nickname)
				{
					$newnames =  $newnames . "" . $arrayOfNames[$i] . " ";
					echo $newnames . "\n";
				}
			}
			$fp = fopen($filename, "w");
			fwrite($fp, $newnames);
			fclose($fp);
		}
	}
} 
else if ($_GET["hash"] == "x010")
{
	if ($_GET["name"] != null) {
		$isAuth = False;
		$nickname = $_GET["name"];
		$fp = fopen($filename, "r");
		$contents = fread($fp, filesize($filename));
		$arrayOfNames = explode(" ", $contents);
		for ($i = 0; $i < count($arrayOfNames) - 1; $i++) { 
			if ($arrayOfNames[$i] == $nickname)
			{
				$isAuth = True;
			}
		}
		if ($isAuth)
		{
			echo "<p>ok</p>";
		}
		else 
		{
			echo "<p>nook</p>";
		}
		fclose($fp);
	}
}
else {
	echo "nook";
}