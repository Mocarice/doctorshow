package com.example.simdaebeom.docshowapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class ReservationRequest extends StringRequest {

    //서버주소...
    final static private String URL = "http://debum93.cafe24.com/Reservation.php";
    private Map<String,String> parameters;

    public ReservationRequest(String reservationNumber, String doctorID, String userID, String date, Response.Listener<String> listener) {
        super(Method.POST,URL,listener,null);
        parameters =new HashMap<>();
        parameters.put("reservationNumber",reservationNumber);
        parameters.put("doctorID",doctorID);
        parameters.put("userID",userID);
        parameters.put("date",date);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
