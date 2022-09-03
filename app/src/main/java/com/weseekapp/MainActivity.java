package com.weseekapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Page1Activity page1Activity;
    private Page2Activity page2Activity;
    private Page3Activity page3Activity;
    private Page4Activity page4Activity;
    private Page5Activity page5Activity;
    private Handler handler;
    private FragmentManager fragmentManager;
    private BottomNavigationView navi;

    private View firstLayout;

    static final int PERMISSIONS_REQUEST = 0x0000001;

    String[] permission_list = { // 필요한 권한은 여기에
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OnCheckPermission();

        handler = new Handler();

        // Frame 설정하는 부분
        page1Activity = new Page1Activity();
        page2Activity = new Page2Activity();
        page3Activity = new Page3Activity();
        page4Activity = new Page4Activity();
        page5Activity = new Page5Activity();

        firstLayout = findViewById(R.id.firstLayout);

        PersonInfo personInfo = PersonInfo.getInstance();
        // get data
        if (PersonInfo.requestQueue == null) {
            // requestQueue 생성
            PersonInfo.requestQueue = Volley.newRequestQueue(this);
        }

        fragmentManager = getSupportFragmentManager();
        // 처음시작 Page는 Page1로 한다.
        fragmentManager.beginTransaction().replace(R.id.frame, page1Activity).commit();

        // Navigation 연결하는 부분
        navi = findViewById(R.id.nav_View);
        // 클릭시 Event Listener 생성.
        navi.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.page1:
                        Log.d("WeSeek", "onNavigationItemSelected: move to page1Activity");
                        fragmentManager.beginTransaction().replace(R.id.frame, page1Activity).commit();
                        break;
                    case R.id.page2:
                        Log.d("WeSeek", "onNavigationItemSelected: move to page2Activity");
//                        fragmentManager.beginTransaction().replace(R.id.frame, page2Activity).commit();
                        // Test Code from : https://kumgo1d.tistory.com/29
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.replace(R.id.frame, page2Activity);
                        ft.commit();
                        break;
                    case R.id.page3:
                        Log.d("WeSeek", "onNavigationItemSelected: move to page3Activity");
                        fragmentManager.beginTransaction().replace(R.id.frame, page3Activity).commit();
                        break;
                    case R.id.page4:
                        Log.d("WeSeek", "onNavigationItemSelected: move to page4Activity");
                        fragmentManager.beginTransaction().replace(R.id.frame, page4Activity).commit();
                        break;
                    case R.id.page5:
                        Log.d("WeSeek", "onNavigationItemSelected: move to page5Activity");
                        fragmentManager.beginTransaction().replace(R.id.frame, page5Activity).commit();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        // init request Queue
        if (StoreInfoHandler.requestQueue == null) {
            // requestQueue 생성
            StoreInfoHandler.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        // get Data.
        sendRequest();
        // check Data.
        handler.post(runable);

    }
    public void onFragmentChange(int index){
        if (index == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, page2Activity).commit();
        }else if (index == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, page4Activity).commit();
        }else if (index == 2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, page3Activity).commit();
        }else if (index == 3) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, page5Activity).commit();
        }
    }

    private void OnCheckPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, permission_list, PERMISSIONS_REQUEST);
            }else{
                ActivityCompat.requestPermissions(this, permission_list, PERMISSIONS_REQUEST);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(firstLayout, "권한이 설정되었습니다", Snackbar.LENGTH_LONG).show();
                }else {
//
                    Snackbar.make(firstLayout, "앱 사용을 위해서는 권한 설정이 필요합니다.\n설정으로 이동하시겠습니까?", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // 스낵바 OK 클릭시 실행작업
                            startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                        }
                    }).show();
                }
                break;
        }
    }

    private void sendRequest() {
        // 서버에 요청할 주소
        String url = "https://kirakirahikari.herokuapp.com/store_api/getStoreAll";

        // 요청 문자열 저장
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("WeSeek", "Res"+response);
                parsingResp(response);
            }
        }, new Response.ErrorListener() {
            // 서버와의 연동 에러시 출력
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })
        {
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
////                BreakIterator edt_id;
//                params.put("id",edt_join_id.getText().toString());
//                params.put("pw",edt_join_pw.getText().toString());
//                params.put("name", edt_join_name.getText().toString());
                return params;
            }
        };
        stringRequest.setTag("ai");
        StoreInfoHandler.requestQueue.add(stringRequest);
    }

    private void parsingResp(String response)
    {
        Log.d("result", response);
        try {
            JSONArray jsonArray = new JSONArray (response);
            JSONObject jsonObject1 = (JSONObject) jsonArray.opt(0);
            JSONObject jsonObject2 = (JSONObject) jsonArray.opt(1);
            if(jsonObject1.optString("result").compareTo("success")==0)
            {
//                PersonInfo personInfo = PersonInfo.getInstance();
//                personInfo.setEveryThing(
//                        jsonObject2.optString("user"),
//                        jsonObject2.optString("nick"),
//                        jsonObject2.optString("email"),
//                        jsonObject2.optString("create_date"),
//                        jsonObject2.optString("userprofile"),
//                        jsonObject2.optString("pic"));

            } else{
                // todo
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Runnable runable = new Runnable() {
        @Override
        public void run() {
            StoreInfoHandler storeInfoHandler = StoreInfoHandler.getInstance();
            if(storeInfoHandler.getCurrent_state() == StoreInfoHandler.State.NORMAL){
                Log.d("WeSeek", "Loading Store info from the https://kirakirahikari.herokuapp.com");
            }else{
                handler.postDelayed(this, 500);
            }
        }
    };
}