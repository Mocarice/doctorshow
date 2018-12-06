<?php
    $con = mysqli_connect("localhost","debum93","q1w2e3r4!!","debum93");
    $result = mysqli_query($con, "SELECT *FROM RESERVATION;");
    $response = array();
    
    while($row = mysqli_fetch_array($result)){
        array_push($response,array("reservationNumber"=>$row[0],"doctorID"=>$row[1],"userID"=>$row[2],"date"=>$row[3],"time"=>$row[4]));
    }
 
    echo json_encode(array("response"=>$response));
    mysqli_close($con);
?>
