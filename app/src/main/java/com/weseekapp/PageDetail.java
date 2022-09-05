package com.weseekapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class PageDetail extends AppCompatActivity {

    private Detail_Frag1 detail_frag1;
    private Detail_Frag2 detail_frag2;
    private Detail_Frag3 detail_frag3;

    private FragmentManager fragmng;
    private BottomNavigationView navi2;

    private ScaleAnimation scaleAnimation;
    private BounceInterpolator bounceInterpolator;
    private CompoundButton button_favorite;


    // 데이터 받아오기 관련 변수
    private String url = "";
    private TextView store_detail_name;
    private ImageView circle_iv;
    private RatingBar ratingBar_star;


    String storeName, imgUrl, urlt, stars;

    String adr, hours, tel;
    Double lat, lon;

    RequestQueue requestQueue;



    Page2VO selected_store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_detail);

        // request null check
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this);
        }

        // page3로부터 storeid값 넘겨받음
        Intent intent = getIntent();
        int store_id = intent.getIntExtra("storeId", 0);
        Log.d("응답", "아이디넘겨받음: " + store_id);


        scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF,
                0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);

        button_favorite = findViewById(R.id.button_favorite_page2);

        store_detail_name = findViewById(R.id.store_detail_name);
        
        circle_iv = findViewById(R.id.circle_iv); // 기본 id값, 변경해도 상관없음
        ratingBar_star = findViewById(R.id.ratingBar_star);

        getInfo(store_id); // json에서 데이터를 가져오는 메서드


        button_favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                compoundButton.startAnimation(scaleAnimation);
            }
        });


        detail_frag1 = new Detail_Frag1();
        detail_frag2 = new Detail_Frag2();
        detail_frag3 = new Detail_Frag3();

        fragmng = getSupportFragmentManager();
        fragmng.beginTransaction().replace(R.id.frame2, detail_frag1).commit();

        navi2 = findViewById(R.id.navi2);
        navi2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int selet_id = item.getItemId();
                if (selet_id == R.id.detail_menu_id1){
                    fragmng.beginTransaction().replace(R.id.frame2, detail_frag1).commit();
                }else if (selet_id == R.id.detail_menu_id2){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("vo", getInfo(store_id));
                    detail_frag2.setArguments(bundle);

                    fragmng.beginTransaction().replace(R.id.frame2, detail_frag2).commit();
                }else if (selet_id == R.id.detail_menu_id3){
                    fragmng.beginTransaction().replace(R.id.frame2, detail_frag3).commit();
                }

                return true;
            }
        });


    }

    public Detail_INFO_VO getInfo(int store_id){
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url = "https://kirakirahikari.herokuapp.com/store_api/getStoreInfoFromId?store_id=" + store_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 응답 성공 시
                        Log.d("응답성공", response);
                        String result = "";
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                                storeName =jsonObject.getString("store_name"); // 가게이름
                                imgUrl = jsonObject.getString("store_img"); // 이미지(썸네일)
                                stars = jsonObject.getString("evl_grade"); // 별 갯수
                                adr = jsonObject.getString("store_addr"); // 주소
                                hours = jsonObject.getString("store_hours"); // 영업시간
                                tel = jsonObject.getString("store_tel"); // 전화번호
                                lat = jsonObject.getDouble("store_long");
                                lon = jsonObject.getDouble("store_lat");


                            }


                            String[] imgUrls = imgUrl.split(",");

                            for (int i = 0; i < imgUrls.length; i++){
                                if (imgUrls[i].length() != 0){
                                    Log.d("응답, 길이", "" + imgUrls[i].length());
                                    urlt = imgUrls[i];
                                    break;
                                }
                            }

                            Log.d("응답확인", storeName + ", " + urlt + ", " + stars);

                            store_detail_name.setText(storeName); // 가게이름
                            Glide.with(getApplicationContext()).load(urlt).into(circle_iv); // 가게이미지

                            // 별 갯수별 이미지 체크
                            if (stars.equals("★")) {
                                ratingBar_star.setRating(1);
                            } else if (stars.equals("★★")){
                                ratingBar_star.setRating(2);
                            } else if (stars.equals("★★★")){
                                ratingBar_star.setRating(3);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 응답 실패 시
                        Log.d("응답", "응답 실패");
                    }
                }

        ){
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
        };

        Log.d("응답", url);

        requestQueue.add(request);

        Detail_INFO_VO detail_infoVO = new Detail_INFO_VO(storeName, adr, hours, tel, lat, lon);

        return detail_infoVO;
    }

}