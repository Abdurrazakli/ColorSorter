<?php
  $servername = "localhost";
  $username = "yoejfbiy_user";
  $password = "php20182019";
  $dbname = "yoejfbiy_phpTest";
  $tablename = "colorSorter";
  $connect = mysqli_connect($servername,$username,$password,$dbname);
  if (!$connect) {
    die("Connection failed: " . mysqli_error($connect));
    exit();
}
  $sql = "show columns from $tablename";  // data table-in adidi,
  
  $query = mysqli_query($connect,$sql);
  $csv_output = "";
  
  $numberOfRows = mysqli_num_rows($query);
  if ($numberOfRows > 0) {
	  $values = mysqli_query($connect,"select * from $tablename where id=1"); // table-in adini yaz
	  while ($rows = mysqli_fetch_row($values)) {
		  for ($j=0;$j<$numberOfRows;$j++) {
			  $csv_output .= $rows[$j].", ";
		  }
		  $csv_output .= "\n";
	  }
  }
  
  print $csv_output;
  
?>