package com.weseekapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class Main_sign_up extends AppCompatActivity {

    private EditText edt_signUp_nick, edt_signUp_id, edt_signUp_pw;
    private ImageView btn_signUp_check;
    private CircleImageView signup_img;

    // 실제로 해당 Server에 요청하는 객체
    private RequestQueue queue;
    // 요청할 떄 필요한 요청사항을 기록하는 객체
    private StringRequest stringRequest;

    private String check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sign_up);

        signup_img = findViewById(R.id.signup_img);
        signup_img.setImageResource(ImageInfo.loginImage);
        signup_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawable = signup_img.getDrawable();
                alert_scaner alert = new alert_scaner(Main_sign_up.this, drawable);
                alert.callFun();
                alert.setModifyReturnListener(new alert_scaner.ModifyReturnListener() {
                    @Override
                    public void afterModify(Drawable context) {
                        signup_img.setImageDrawable(drawable);
                    }
                });
            }
        });





        edt_signUp_nick = findViewById(R.id.edt_signUp_id);
        edt_signUp_id = findViewById(R.id.edt_signUp_id);
        edt_signUp_pw = findViewById(R.id.edt_signUp_pw);

        btn_signUp_check = findViewById(R.id.btn_signUp_check);
        btn_signUp_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();

//                if (check == null){
//                    Toast.makeText(getApplicationContext(),"아이디가 중복되었거나 빈 칸이 있습니다. 다시 시도해 주세요!",Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(getApplicationContext(),check + "님 가입을 환영합니다!",Toast.LENGTH_SHORT).show();
//                }
                Intent intent = new Intent(getApplicationContext(), FirstMain.class); // 프로그램 실행 후 초기화면으로 이동, 회원가입 성공 여부 체크할 방법 필요
                startActivity(intent);

            }
        });


    }

    public void sendRequest(){
        // Volley Lib 새로운 요청객체 생성
        queue = Volley.newRequestQueue(this);
        // 서버에 요청할 주소
        String url = "http://192.168.32.1:5001/join";
        // 요청 문자열 저장
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("resultValue",response);
                check = response;
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
                String nick = edt_signUp_nick.getText().toString();
                String id = edt_signUp_id.getText().toString();
                String pw = edt_signUp_pw.getText().toString();

                params.put("nick", nick);
                params.put("id", id);
                params.put("pw", pw);

                return params;
            }
        };


        String TAG = "회원가입 TEST";
        stringRequest.setTag(TAG);
        queue.add(stringRequest);
    }
}