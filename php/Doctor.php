<?php
    $con = mysqli_connect("localhost","debum93","q1w2e3r4!!","debum93");
    $result = mysqli_query($con, "SELECT *FROM CANDIDATE;");
    $response = array();

    while($row = mysqli_fetch_array($result)){
        array_push($response,array("candidateNumber"=>$row[0],"department"=>$row[1],"studentID"=>$row[2]));
    }
 
    echo json_encode(array("response"=>$response));
    mysqli_close($con);
?>
