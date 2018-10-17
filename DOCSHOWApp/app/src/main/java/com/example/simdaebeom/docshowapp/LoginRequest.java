package com.example.simdaebeom.docshowapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    //서버주소...
    final static private String URL = "http://debum93.cafe24.com/Login.php";
    private final String userID;
    private final String userPassword;
    private Map<String,String> parameters;

    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener) {
        super(Method.POST,URL,listener,null);
        this.userID = userID;
        this.userPassword = userPassword;
        parameters =new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userPassword",userPassword);

    }
    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
