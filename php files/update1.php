 <?php
DEFINE ('DBUSER', 'yoejfbiy_user');
DEFINE ('DBPW', 'php20182019');
DEFINE ('DBHOST', 'localhost');
DEFINE ('DBNAME', 'yoejfbiy_phpTest');

echo "Hello World";

//https://www.examplewebsite.az/update.php?red=5&green=6&blue=9&yellow=8&temperature=33.2&humidity=45

// Create connection
$dbc = mysqli_connect(DBHOST, DBUSER, DBPW);
// Check connection
if (!$dbc) {
    die("Connection failed: " . mysqli_error($dbc));
    exit();
}

$dbs = mysqli_select_db($dbc, DBNAME);
if(!$dbs) {
    die("Connection failed: " . mysqli_error($dbc));
    exit();
}
//$ID = mysqli_real_escape_string($dbc, $_GET['id']);//ikinci AD balaca yaz yoxla
$RED = mysqli_real_escape_string($dbc, $_GET['red']);
$GREEN = mysqli_real_escape_string($dbc, $_GET['green']);
$BLUE = mysqli_real_escape_string($dbc, $_GET['blue']);
$YELLOW = mysqli_real_escape_string($dbc, $_GET['yellow']);
$TEMPERATURE = mysqli_real_escape_string($dbc, $_GET['temperature']);
$HUMIDITY = mysqli_real_escape_string($dbc, $_GET['humidity']);
//$query = "INSERT INTO colorSorter (id, red, green, blue, yellow, temperature, humidity)
//VALUES ('$ID', '$RED', '$GREEN', '$BLUE', '$YELLOW', '$TEMPERATURE', '$HUMIDITY')";
$query = "UPDATE colorSorter SET red = '$RED', green = '$GREEN', blue = '$BLUE', yellow = '$YELLOW', temperature = '$TEMPERATURE', humidity = '$HUMIDITY' WHERE id=1";
if(mysqli_query($dbc, $query)){
    echo "Records were updated successfully.";
} else {
    echo "ERROR: Could not able to execute $sql. " . mysqli_error($link);
}

mysqli_close($dbc);
?>