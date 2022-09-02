package com.weseekapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class Login_Frag extends Fragment {
    @Nullable
    TextView tv_id, tv_pw;
    View view;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main_log_in, container, false);

        view.findViewById(R.id.btn_login_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = ((TextView)view.findViewById(R.id.edt_login_id)).getText().toString();
                String pw = ((TextView)view.findViewById(R.id.edt_login_pw)).getText().toString();
                sendRequest(id, pw);
            }
        });

        return view;
    }

    public static Map<String, Object> getMapFromJsonObject(JSONObject jsonObj){
        Map<String, Object> map = null;

        try {
            map = new ObjectMapper().readValue(jsonObj.toString(), Map.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return map;
    }

    private void parsingResp(String response)
    {
        try {
            JSONArray jsonArray = new JSONArray (response);
            JSONObject jsonObject1 = (JSONObject) jsonArray.opt(0);
            JSONObject jsonObject2 = (JSONObject) jsonArray.opt(1);
            if(jsonObject1.optString("result").compareTo("success")==0)
            {
                PersonInfo personInfo = PersonInfo.getInstance();
                personInfo.setEveryThing(
                        jsonObject2.optString("user"),
                        jsonObject2.optString("nick"),
                        jsonObject2.optString("email"),
                        jsonObject2.optString("create_date"),
                        jsonObject2.optString("userprofile"),
                        jsonObject2.optString("pic"));

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.remove(this);
                fragmentTransaction.replace(R.id.frame, new Page1Activity());
                fragmentTransaction.commit();
            } else{
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Login Failure"); //AlertDialog의 제목 부분
                builder.setMessage(jsonObject2.optString("reson")); //AlertDialog의 내용 부분
                builder.setPositiveButton("확인",null);
                builder.create().show(); //보이기
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest(String id, String pw) {
        // 서버에 요청할 주소
        String url = "https://kirakirahikari.herokuapp.com/auth/api_login/";
//        String url = "https://dokkydokky.herokuapp.com/auth/api_login";
//        String url = "http://192.168.21.231:5000/auth/api_login";

        Response.Listener<String> stringListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("resultValue",response);
                parsingResp(response);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                final int status = error.networkResponse.statusCode;
                Log.v("resultValue", String.format("%d:%s", status, error.networkResponse.headers.get("Location")));;
                // Handle 30x
                if(HttpURLConnection.HTTP_MOVED_PERM == status ||
                        status == HttpURLConnection.HTTP_MOVED_TEMP ||
                        status == HttpURLConnection.HTTP_SEE_OTHER ||
                        status == 308) {
                    final String location = error.networkResponse.headers.get("Location");
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, location, stringListener, this){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("username", id);
                            params.put("password", pw);
                            return params;
                        }
                    };
                    PersonInfo.requestQueue.add(stringRequest);
                }
            }
        };

        // 요청 문자열 저장
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, stringListener, errorListener)
        {
            // 보낼 데이터를 저장하는 곳
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", id);
                params.put("password", pw);
                return params;
            }
        };
//        stringRequest.setTag("ai");
        PersonInfo.requestQueue.add(stringRequest);
    }
}
