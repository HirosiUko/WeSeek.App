package com.weseekapp;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Page3Activity extends Fragment implements OnMapReadyCallback, View.OnClickListener {
    @Nullable

    private GoogleMap mMap; // 지도
    private MapView mapView; // 맵뷰
    private TextView tv_name, tv_star, tv_distance, tv_adr; // 심플 페이지 뷰 (이름, 별점, 거리, 주소)
    private ImageView img_detail; // 디테일 이미지 이미집퓨

    private TextView tv_store_id, tv_store_name, tv_store_tel, tv_store_adr, tv_store_hours, tv_store_distance, tv_store_url; // 상세 페이지 뷰

    // 상세 페이지 버튼
    private TextView btn_link, btn_cancel, btn_detailPage; // 링크, 뒤로가기, 가게별 세부페이지 이동 버튼
    private ImageView btn_call; // 전화 버튼

    private ImageView img_store_img, img_store_thumb, img_store_star_01, img_store_star_02, img_store_star_03;
    private ImageButton btn_pre, btn_next; // 좌우이동 버튼
    private TextView btn_search; // 주변탐색 버튼
    private int cnt = 0;

    private CompoundButton page3_favorite_animation;
    private BounceInterpolator bounceInterpolator;
    private ScaleAnimation scaleAnimation;

    private ArrayList<String> arrayList;
    private SearchView page3_searchView;

    String url_id;
    String url_info;
    RequestQueue requestQueue;

    int[] store_id; // 가게 ID
    String[] store_name; // 업소명
    String[] store_address; // 소재지
    String[] store_phone; // 전화번호
    String[] store_hours; // 운영시간
    String[] store_url; // 가게 인터넷 주소
    String[] store_grade; // 별 갯수

    LatLng[] loc; // 위치정보

    Double curLat, curLon; // 현재 위,경도

    LatLng currentLoc; // 현재위치
    String hours; // 운영시간
    String distance; // 거리
    String imgUrl; // 이미지 URL

    ProgressDialog customProgressDialog; // 로딩창 선언


    LinearLayout sliding; // 애니메이션을 동작시킬 객체
    Animation slidingUp; // 슬라이딩이 왼쪽으로 펼쳐지는 애니메이션 객체
    Animation slidingDown; // 슬라이딩이 오른쪽으로 접어지는 애니메이션 객체
    boolean isOpen = false; // 페이지가 처음에는 오픈이 안 된 상태

    public static Context context_page3;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page3, container, false);

        isOpen = false; // 닫힌 상태

        sliding = (LinearLayout) view.findViewById(R.id.sliding); // 페이지 슬라이딩할 객체 지정
        slidingUp= AnimationUtils.loadAnimation(view.getContext(), R.anim.sliding_up); // 페이지 위로 올리기
        slidingDown= AnimationUtils.loadAnimation(view.getContext(), R.anim.sliding_down); // 페이지 닫기

        SlidingAnimationListener listener = new SlidingAnimationListener();
        slidingUp.setAnimationListener(listener);
        slidingDown.setAnimationListener(listener);


        scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF,
                0.7f, Animation.RELATIVE_TO_SELF, 0.7f);

        scaleAnimation.setDuration(500);
        bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);
        page3_favorite_animation = (CompoundButton) view.findViewById(R.id.page3_favorite_animation);
        page3_favorite_animation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                compoundButton.startAnimation(scaleAnimation);
            }
        });


        customProgressDialog = new ProgressDialog(view.getContext()); // 로딩창
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(view.getContext());
        }

        // 선언

        tv_name = view.findViewById(R.id.tv_name); // 심플 페이지 뷰
        tv_adr = view.findViewById(R.id.tv_adr);
        tv_distance = view.findViewById(R.id.tv_distance);
        tv_star = view.findViewById(R.id.tv_star);
        img_detail = view.findViewById(R.id.img_detail);

        tv_store_name = view.findViewById(R.id.tv_store_name); // 상세 페이지 뷰
        tv_store_adr = view.findViewById(R.id.tv_store_adr);
        tv_store_distance = view.findViewById(R.id.tv_store_distance);
        tv_store_tel = view.findViewById(R.id.tv_store_tel);
        tv_store_hours = view.findViewById(R.id.tv_store_hours);
        tv_store_url = view.findViewById(R.id.tv_store_url);

        btn_link = view.findViewById(R.id.btn_link);
        btn_call = view.findViewById(R.id.btn_call);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_detailPage = view.findViewById(R.id.btn_detailPage);

        img_store_thumb = view.findViewById(R.id.img_store_thumb);

        img_store_star_01 = view.findViewById(R.id.img_store_star_01);
        img_store_star_02 = view.findViewById(R.id.img_store_star_02);
        img_store_star_03 = view.findViewById(R.id.img_store_star_03);

        btn_search = view.findViewById(R.id.btn_search);
        btn_pre = view.findViewById(R.id.btn_pre);
        btn_next = view.findViewById(R.id.btn_next);

        btn_search.setOnClickListener(this);
        btn_pre.setOnClickListener(this);
        btn_next.setOnClickListener(this);

        btn_cancel.setOnClickListener(this);
        btn_call.setOnClickListener(this);
        btn_link.setOnClickListener(this);
        btn_detailPage.setOnClickListener(this);

        img_detail.setOnClickListener(this);

        mapView = view.findViewById(R.id.mapview);
        Log.d("에러", "onCreateView: ");
        mapView.onCreate(savedInstanceState);

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 권한체크
        }

        try {
            Location loc_Current = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); // GPS 현재위치 가져오기
            curLat = loc_Current.getLatitude(); // 현재위도
            curLon = loc_Current.getLongitude(); // 현재경도
            Log.d("현재위치", "" + curLat + "," + curLon);
        }catch (Exception e){
            e.printStackTrace();
        }


        dbSet dbthread = new dbSet(); // 스레드 선언
        dbthread.start(); // 스레드 호출

        mapView.getMapAsync(this); // onMapReady 호출

        // 서치뷰
        page3_searchView = view.findViewById(R.id.page3_searchView);
        page3_searchView.clearFocus();

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapview);
        page3_searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = page3_searchView.getQuery().toString();
                List<Address> addressList = null;
                if (location != null || location.equals("")){
//                    Geocoder geocoder = new Geocoder(getContext().getApplicationContext());
                    try {
                        Log.d("서치뷰", "" + Arrays.asList(store_name).indexOf(location));
                        cnt = Arrays.asList(store_name).indexOf(location);
                        getDistance(loc[cnt].latitude, loc[cnt].longitude);
                        infoset(cnt); // 정보 출력 메소드 1
                        infoset_simple(cnt); // 정보 출력 메소드 2
                        getInfo(store_id[cnt]); // 가게별 세부정보 출력 메소드
                        LatLng latLng = new LatLng(loc[cnt].latitude, loc[cnt].longitude); // 마커찍기용 위치정보 선언
                        mMap.addMarker(new MarkerOptions().position(latLng).title(location)); // 마커 추가
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17)); // 카메라이동

//                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (Exception e){
                        e.printStackTrace();
                    }

//                    Address address = addressList.get(0);
//                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
//        mapFragment.getMapAsync(this);





        return view;
    }

    class  SlidingAnimationListener implements Animation.AnimationListener { // 페이지 슬라이딩
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) { // 애니메이션이 끝날 때 자동 호출됨
            if(isOpen) { // 슬라이딩 레이아웃의 열린 상태가 끝나면
                sliding.setVisibility(View.INVISIBLE); // 슬라이딩 레이아웃 안보이게 하고
                isOpen = false; // 닫기 상태가 됨
            } else { // 슬라이딩 레이아웃의 닫힌 상태가 끝나면
                isOpen = true; // 열기 상태가됨
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) { // 지도 띄우기 + 현재위치 받아와서 마커 표시

        customProgressDialog.show();

        mMap = googleMap;

        try {
            currentLoc = new LatLng(curLat, curLon);
        }catch (Exception e){
            e.printStackTrace();
        }


        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.markerblue); // 현재위치 표시용 블루마커 이미지 등록
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap blueMarker = Bitmap.createScaledBitmap(b, 80, 120, false);

        try {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(currentLoc) // 마커선언
                    .title("현재위치")
                    .icon(BitmapDescriptorFactory.fromBitmap(blueMarker));

            mMap.addMarker(markerOptions); // 마커추가

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17)); // 카메라 이동
        }catch (Exception e){
            e.printStackTrace();
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() { // 마커 클릭 리스너
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) { // 마커를 클릭했을 때의 동작
                try {
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(17)); // 카메라 이동

                    if (cnt != 0){ // 가게 배열 인덱싱용 변수 cnt 초기화

                    }else{
                        cnt = Integer.parseInt(marker.getId().replaceAll("[^\\d]", ""))-1; // 마커 id 받아와서 cnt로 재선언
                    }

                    Log.d("태그", ""+cnt);

                    getDistance(loc[cnt].latitude, loc[cnt].longitude); //거리
                    infoset(cnt); // 정보
                    infoset_simple(cnt);
                    getInfo(store_id[cnt]);
                    LatLng latLng = new LatLng(loc[cnt].latitude, loc[cnt].longitude); // 위치
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17)); // 카메라 이동

                }catch (Exception e){
                    e.printStackTrace();
                }

                if (isOpen){ // 슬라이딩 레이아웃이 열려져 있으면
                    
                } else if (marker.getTitle().equals("현재위치")){ // 클릭한 마커의 타이틀이 '현재위치' 일 경우
                    if (isOpen){ // 슬라이딩 레이아웃이 열려져 있으면
                        sliding.startAnimation(slidingDown); // 슬라이딩 레이아웃 닫기
                    }

                } else { // 슬라이딩 레이아웃이 닫혀져 있으면
                    sliding.setVisibility(View.VISIBLE); // 슬라이딩 레이아웃을 보이게하기
                    sliding.startAnimation(slidingUp); // 슬라이딩 레이아웃 열기
                }


                return false;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() { // 맵을 클릭했을 때의 이밴트
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                if (isOpen){ // 슬라이딩 레이아웃이 열려져 있으면
                    sliding.startAnimation(slidingDown); // 슬라이딩 레이아웃 닫기
                }
            }
        });

        customProgressDialog.dismiss();

    }



    private class dbSet extends Thread { // db에서 데이터 가져오는 스레드
        @Override
        public void run() {

            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    url_id = "https://kirakirahikari.herokuapp.com/store_api/getStoreByGPS?lat=" + curLat + "&lon=" + curLon + "&dis=1500",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // 응답 성공 시
//깨짐                            URLEncoder.encode(response, "UTF-8");
                            Log.d("응답성공", response);
//                            response.setCharacterEncoding("UTF-8");
                            String result = "";
                            try {
                                JSONArray jsonArray = new JSONArray(response); // jsonArray방식으로 db 가져오기

                                // 각 항목 배열 초기화
                                store_id = new int[jsonArray.length()];
                                store_name = new String[jsonArray.length()];
                                store_address = new String[jsonArray.length()];
                                store_phone = new String[jsonArray.length()];
                                store_hours = new String[jsonArray.length()];
                                store_url = new String[jsonArray.length()];


                                store_grade = new String[jsonArray.length()];
                                loc = new LatLng[jsonArray.length()];

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                                    // 각 배열에 DB에서 가져온 정보 등록
                                    store_id[i] = jsonObject.getInt("id");
                                    store_name[i] = jsonObject.getString("place_name");
                                    store_address[i] = jsonObject.getString("address_name");
                                    store_phone[i] = jsonObject.getString("phone");
                                    store_url[i] = jsonObject.getString("place_url");

                                    store_grade[i] = jsonObject.getString("evl_grade");

                                    Double lat = jsonObject.getDouble("y");
                                    Double lon = jsonObject.getDouble("x");
                                    
                                    loc[i] = new LatLng(lat, lon);

                                }

                                Log.d("응답", "storeid : " + store_id.length);
//                                for (int i = 0; i < store_id.length; i++){
//                                    getInfo(store_id[i]);
//                                    store_hours[i] = hours;
//                                }

                                Log.d("응답성공", "ID 로드 성공!");
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

            requestQueue.add(request);
            Log.d("응답", url_id);

        }
    }

    public String getDistance(double lat, double lon){ // 현재 위치에서 목표 지점까지의 거리를 반환하는 메소드
        Location locationA = new Location("current");
        locationA.setLatitude(curLat);
        locationA.setLongitude(curLon);

        Location locationB = new Location("target");
        locationB.setLatitude(lat);
        locationB.setLongitude(lon);

        double dis = locationA.distanceTo(locationB);

        if (dis < 1000){ // 거리가 1000m 미만일 때
            distance = "" + (int)dis + " m"; // m 로 표시
        } else{ // 1000m 이상일 떄
            distance = "" + ((int)dis)/10*10 / 1000.0 + " km"; // km로 표시
        }
        Log.d("거리", distance);

        return distance; // 거리 반환
    }

    public String getInfo(int store_id){ // 개선필요
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url_info = "https://kirakirahikari.herokuapp.com/store_api/getStoreInfoFromId?store_id=" + store_id, // store id로 세부정보 가져오기
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
                                hours = jsonObject.getString("store_hours");
                                imgUrl = jsonObject.getString("store_img");
                                Log.d("응답, imgUrls", imgUrl);
                            }

                            if (hours.equals("N\\/A")){ // 영업시간 값이 공란일 때
                                tv_store_hours.setText("정보 없음");
                            }else{
                                tv_store_hours.setText(hours);
                            }
                            Log.d("응답", hours);

                            String[] imgUrls = imgUrl.split(","); // 이미지 url들 가져오기
                            String urlt = "";

                            for (int i = 0; i < imgUrls.length; i++){ // 빈 url 주소가 있어서 그것을 걸러내는 과정 필요
                                if (imgUrls[i].length() != 0){ // url이 빈 값이 아닐 때
                                    Log.d("응답, 길이", "" + imgUrls[i].length());
                                    urlt = imgUrls[i]; // 변수 urlt에 이미지 url 추가
                                    break; 
                                }
                            }


                            Log.d("응답, 길이", urlt);

                            Log.d("응답, imgUrls", urlt);
                            Glide.with(getContext()).load(urlt).into(img_store_thumb); // glide 라이브러리를 사용하여 이미지 출력


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

        );

        requestQueue.add(request);
        Log.d("응답", url_id);

        return hours;
    }

    public void infoset_simple(int cnt){ // 간단 정보창 출력 메소드

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc[cnt], 17));

        tv_name.setText(store_name[cnt]);
        tv_adr.setText(store_address[cnt]);
        tv_distance.setText(distance);
        tv_star.setText(store_grade[cnt]);

    }


    public void infoset(int cnt){ // 세부정보창 출력 메소드

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc[cnt], 17));

        tv_store_name.setText(store_name[cnt]);
        tv_store_adr.setText(store_address[cnt]);
        tv_store_distance.setText(distance);

        if (store_grade[cnt].equals("★")) {
            img_store_star_01.setImageResource(R.drawable.ic_action_star1);
            img_store_star_02.setImageResource(R.drawable.ic_action_star0);
            img_store_star_03.setImageResource(R.drawable.ic_action_star0);
        } else if (store_grade[cnt].equals("★★")){
            img_store_star_01.setImageResource(R.drawable.ic_action_star1);
            img_store_star_02.setImageResource(R.drawable.ic_action_star1);
            img_store_star_03.setImageResource(R.drawable.ic_action_star0);
        } else if (store_grade[cnt].equals("★★★")){
            img_store_star_01.setImageResource(R.drawable.ic_action_star1);
            img_store_star_02.setImageResource(R.drawable.ic_action_star1);
            img_store_star_03.setImageResource(R.drawable.ic_action_star1);
        }

        tv_store_tel.setText(store_phone[cnt]);
        tv_store_url.setText(store_url[cnt]);


    }


    public void onClick(View view) { // 버튼 클릭 이벤트

        try {
            if (view.getId() == R.id.btn_search) { // 주변탐색 버튼 클릭시 이벤트
                // 오류뜨면 위치코드 넣어야함

                Log.d("응답성공", "마커 표시 성공!");

                for (int i = 0; i < store_id.length; i++) { // db에서 받아온 정보로 마커 표시

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions
                            .position(loc[i])
                            .title(store_name[i])
                            .snippet(store_address[i]);

                    mMap.addMarker(markerOptions);

                }
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 15));
                cnt = 0;



            }else if (view.getId() == R.id.btn_pre){ // 이전 버튼
                if (cnt > 0){
                    cnt--;
                    Log.d("cnt", ""+cnt);
                    getDistance(loc[cnt].latitude, loc[cnt].longitude);
                    infoset_simple(cnt);


                }else if (cnt == 0){
                    cnt = store_id.length -1;
                    Log.d("cnt", ""+cnt);
                    getDistance(loc[cnt].latitude, loc[cnt].longitude);
                    infoset_simple(cnt);
                }

            }else if (view.getId() == R.id.btn_next){ // 다음 버튼
                if (cnt < store_id.length -1){
                    cnt++;
                    Log.d("cnt", ""+cnt);
                    getDistance(loc[cnt].latitude, loc[cnt].longitude);
                    infoset_simple(cnt);

                }else if (cnt == store_id.length -1){
                    cnt = 0;
                    Log.d("cnt", ""+cnt);
                    getDistance(loc[cnt].latitude, loc[cnt].longitude);
                    infoset_simple(cnt);
                }

            }else if (view.getId() == R.id.btn_cancel){ // 취소 버튼
                if (isOpen){
                    sliding.startAnimation(slidingDown);
                }

            }else if (view.getId() == R.id.btn_call){ // 전화 버튼
                String num = tv_store_tel.getText().toString().replace("-", ""); // 불러온 db에서 숫자만을 가져오는 과정
                Log.d("응답", num);
                Uri uri = Uri.parse("tel:"+num);
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent); // 전화 액티비티로 이동
            }else if (view.getId() == R.id.btn_detailPage){ // 디테일 페이지 버튼
                Intent intent = new Intent(getContext(), PageDetail.class);
                int storeIdtoDetail = store_id[cnt];
                Log.d("응답", "디테일페이지 : " + storeIdtoDetail);
                intent.putExtra("storeId", storeIdtoDetail);
                startActivity(intent);



            }else if (view.getId() == R.id.btn_link){ // 링크 버튼
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", tv_store_url.getText().toString());
                Toast.makeText(getActivity().getApplicationContext(),"클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show();
                clipboard.setPrimaryClip(clip);
            }

            else if (view.getId() == R.id.img_detail){
                if (isOpen){ // 슬라이딩 레이아웃이 열려져 있으면
                    // 보류
                } else { // 슬라이딩 레이아웃이 닫혀져 있으면
                    sliding.setVisibility(View.VISIBLE); // 슬라이딩 레이아웃을 보이게하기
                    sliding.startAnimation(slidingUp); // 슬라이딩 레이아웃 열기
                    Log.d("응답", ""+cnt);
                    getDistance(loc[cnt].latitude, loc[cnt].longitude);
                    infoset(cnt);
                    getInfo(store_id[cnt]);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // 지도 최적화 코드

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}