<?php
    $con = mysqli_connect("localhost","debum93","q1w2e3r4!!","debum93");
    $result = mysqli_query($con, "SELECT *FROM USER;");
    $response = array();

    while($row = mysqli_fetch_array($result)){
        array_push($response,array("userID"=>$row[0],"userPassword"=>$row[1],"userName"=>$row[2],"userBirth"=>$row[3]));
    }
 
    echo json_encode(array("response"=>$response));
    mysqli_close($con);
?>
