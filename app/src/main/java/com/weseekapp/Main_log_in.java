package com.weseekapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class
Main_log_in extends AppCompatActivity {

    private TextView edt_login_id, edt_login_pw;
    private ImageView btn_login_check;

    private RequestQueue queue;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_log_in);

        edt_login_id = findViewById(R.id.edt_login_id);
        edt_login_pw = findViewById(R.id.edt_login_pw);

        btn_login_check = findViewById(R.id.btn_login_check);
        btn_login_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class); // 프로그램 실행 후 초기화면으로 이동, 회원가입 성공 여부 체크할 방법 필요
                startActivity(intent);
            }
        });

    }

    public void sendRequest(){
        // Volley Lib 새로운 요청객체 생성
        queue = Volley.newRequestQueue(this);
        // 서버에 요청할 주소
        String url = "http://192.168.32.1:5001/login";
        // 요청 문자열 저장
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("resultValue",response);
                String[] info = response.split(",");
                Log.v("resultValue","id : " + info[0]);
                Log.v("resultValue","pw : " + info[1]);
                Log.v("resultValue","nick : " + info[2]);
            }
        }, new Response.ErrorListener() {
            // 서버와의 연동 에러시 출력
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override //response를 UTF8로 변경해주는 소스코드
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    // log error
                    return Response.error(new ParseError(e));
                } catch (Exception e) {
                    // log error
                    return Response.error(new ParseError(e));
                }
            }
            // 보낼 데이터를 저장하는 곳
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String id = edt_login_id.getText().toString();
                String pw = edt_login_pw.getText().toString();

                params.put("id", id);
                params.put("pw", pw);

                return params;
            }
        };

        String TAG = "로그인 TEST";
        stringRequest.setTag(TAG);
        queue.add(stringRequest);


    }
}