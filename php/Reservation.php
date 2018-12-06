 <?php
    $con = mysqli_connect("localhost","debum93","q1w2e3r4!!","debum93");

    $reservationNumber = $_POST["reservationNumber"];
    $doctorID = $_POST["doctorID"];
    $userID = $_POST["userID"];
    $date = $_POST["date"];


    $statement = mysqli_prepare($con,"INSERT INTO RESERVATION VALUES (?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement,"ssss",$reservationNumber,$doctorID,$userID,$date);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>