<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $firstName = $_POST["firstname"];
    $lastName = $_POST["lastname"];
    $streetAddress = $_POST["streetaddress"];
    $streetNumber = $_POST["streetnumber"];
    $postalCode = $_POST["postalcode"];
    $city = $_POST["city"];
    $country = $_POST["country"];
    $username = $_POST["username"];
    $password = $_POST["password"];

    try {
        require_once "connect.php";

        $query = "INSERT INTO users (firstname, lastname, streetaddress,
    streetnumber, postalcode, city, country, username, password) 
VALUES($firstName, $lastName, $streetAddress, $streetNumber, $postalCode, $city,
 $country, $username, $password );";

        $stmt = $pdo->prepare($query);

        $stmt->execute([
            $firstName,
            $lastName,
            $streetAddress,
            $streetNumber,
            $postalCode,
            $city,
            $country,
            $username,
            $password
        ]);

        $pdo = null;
        $stmt = null;

        header("Location: ../index.php");
        die();
    } catch (PDOException $e) {
        die("Query failed: " . $e->getMessage());
    }
} else {
    header("Location: ../index.php");
}