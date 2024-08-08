<?php
$db = new SQLite3('bidhubdb1.db');

// Check if the connection is successful
if (! $db) {
    die("Connection failed: " . $db->lastErrorMsg());
}

// Assuming form data is posted
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $firstname = $_POST['firstname'];
    $lastname = $_POST['lastname'];
    $streetaddress = $_POST['streetaddress'];
    $streetnumber = $_POST['streetnumber'];
    $postalcode = $_POST['postalcode'];
    $city = $_POST['city'];
    $country = $_POST['country'];
    $username = $_POST['username'];
    $password = $_POST['password'];
    // You should hash the password before storing it in the database for security reasons

    $sql = "INSERT INTO users (streetaddress, streetnumber, postalcode, city, country, username, password)
     VALUES (:streetaddress, :streetnumber, :postalcode, :city, :country, :username, :password)";
    $stmt = $db->prepare($sql);
    $stmt->bindParam(':streetaddress', $streetaddress);
    $stmt->bindParam(':streetnumber', $streetnumber);
    $stmt->bindParam(':postalcode', $postalcode);
    $stmt->bindParam(':city', $city);
    $stmt->bindParam(':country', $country);
    $stmt->bindParam(':password', $password);
    $stmt->bindParam(':username', $username);
    $stmt->bindParam(':password', $password);

    $result = $stmt->execute();

    if ($result) {
        echo "New record inserted successfully";
    } else {
        echo "Error: " . $sql . "<br>" . $db->lastErrorMsg();
    }
}

// Close the database connection
$db->close();
?>
