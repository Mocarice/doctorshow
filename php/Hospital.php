<?php
    $con = mysqli_connect("localhost","debum93","q1w2e3r4!!","debum93");
    $result = mysqli_query($con, "SELECT *FROM HOSPITAL;");
    $response = array();

    while($row = mysqli_fetch_array($result)){
        array_push($response,array("hospitalID"=>$row[0],"hospitalName"=>$row[1],"hospitalAddress"=>$row[2],"Telephone"=>$row[3]));
    }
 
    echo json_encode(array("response"=>$response));
    mysqli_close($con);
?>