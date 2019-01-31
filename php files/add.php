 <?php
DEFINE ('DBUSER', 'yoejfbiy_user');
DEFINE ('DBPW', 'php20182019');
DEFINE ('DBHOST', 'localhost:3306');
DEFINE ('DBNAME', 'yoejfbiy_phpTest');

echo "Hello World";

// Create connection
$dbc = mysqli_connect(DBHOST, DBUSER, DBPW);
// Check connection
if (!$dbc) {
    die("Connection failed: " . mysqli_error($dbc));
    exit();
}

$dbs = mysqli_select_db($dbc, DBNAME);
if(!dbs) {
    die("Connection failed: " . mysqli_error($dbc));
    exit();
}

$AD = mysqli_real_escape_string($dbc, $_GET['AD']);//ikinci AD balaca yaz yoxla
$SOYAD = mysqli_real_escape_string($dbc, $_GET['SOYAD']);
$query = "INSERT INTO ilkTest (AD, SOYAD)
VALUES ('$AD', '$SOYAD')";

$result = mysqli_query($dbc, $query) or trigger_error("Query MySQL Error: " . mysqli_error($dbc));

mysqli_close($dbc);
?>
