package com.example.simdaebeom.docshowapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest {

    //서버주소...
    final static private String URL = "http://debum93.cafe24.com/UserValidate.php";
    private Map<String,String> parameters;

    public ValidateRequest(String userID, Response.Listener<String> listener) {
        super(Method.POST,URL,listener,null);
        parameters =new HashMap<>();
        parameters.put("userID",userID);

    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
