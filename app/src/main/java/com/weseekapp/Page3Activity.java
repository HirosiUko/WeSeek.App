package com.weseekapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

import java.util.ArrayList;

public class Page3Activity extends Fragment implements OnMapReadyCallback, View.OnClickListener {
    @Nullable

    private GoogleMap mMap;
    private MapView mapView;
    private TextView tv_store_id, tv_store_name, tv_store_tel, tv_store_adr, tv_store_hours, tv_store_distance, tv_store_url; // 상세 페이지 뷰
    private ImageView img_store_img, img_store_thumb, img_store_star_01, img_store_star_02, img_store_star_03;
    private ImageButton btn_pre, btn_next;
    private TextView btn_search;
    private int cnt = 0;

    private CompoundButton page3_favorite_animation;
    private BounceInterpolator bounceInterpolator;
    private ScaleAnimation scaleAnimation;


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

    Double curLat, curLon;

    LatLng currentLoc;
    String hours;
    String distance;

    ProgressDialog customProgressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page3, container, false);


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


        customProgressDialog = new ProgressDialog(view.getContext());
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(view.getContext());
        }


        tv_store_name = view.findViewById(R.id.tv_store_name);
        tv_store_adr = view.findViewById(R.id.tv_store_adr);
        tv_store_distance = view.findViewById(R.id.tv_store_distance);
        tv_store_tel = view.findViewById(R.id.tv_store_tel);
        tv_store_hours = view.findViewById(R.id.tv_store_hours);
        tv_store_url = view.findViewById(R.id.tv_store_url);

        img_store_star_01 = view.findViewById(R.id.img_store_star_01);
        img_store_star_02 = view.findViewById(R.id.img_store_star_02);
        img_store_star_03 = view.findViewById(R.id.img_store_star_03);

        btn_search = view.findViewById(R.id.btn_search);
        btn_pre = view.findViewById(R.id.btn_pre);
        btn_next = view.findViewById(R.id.btn_next);

        btn_search.setOnClickListener(this);
        btn_pre.setOnClickListener(this);
        btn_next.setOnClickListener(this);

        mapView = view.findViewById(R.id.mapview);
        Log.d("에러", "onCreateView: ");
        mapView.onCreate(savedInstanceState);

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }

        try {
            Location loc_Current = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            curLat = loc_Current.getLatitude();
            curLon = loc_Current.getLongitude();
            Log.d("현재위치", "" + curLat + "," + curLon);
        }catch (Exception e){
            e.printStackTrace();
        }

        
        dbSet dbthread = new dbSet();
        dbthread.start(); // db 호출

        mapView.getMapAsync(this); // onMapReady 호출

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) { // 지도 띄우기 + 현재위치 받아와서 마커 표시

        LayoutInflater inflater2 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 = inflater2.inflate(R.layout.bottom_sheet, null, false);
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(view2.getContext());
        bottomSheetDialog.setContentView(view2);

        customProgressDialog.show();

        mMap = googleMap;

        try {
            currentLoc = new LatLng(curLat, curLon);
        }catch (Exception e){
            e.printStackTrace();
        }


        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.markerblue);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap blueMarker = Bitmap.createScaledBitmap(b, 80, 120, false);

        try {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(currentLoc)
                    .title("현재위치")
                    .icon(BitmapDescriptorFactory.fromBitmap(blueMarker));

            mMap.addMarker(markerOptions);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17));
        }catch (Exception e){
            e.printStackTrace();
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                mMap.animateCamera(CameraUpdateFactory.zoomTo(17));


                cnt = Integer.parseInt(marker.getId().replaceAll("[^\\d]", ""))-1;
                Log.d("태그", ""+cnt);

                getDistance(loc[cnt].latitude, loc[cnt].longitude);
//                infoset(cnt);
                bottomSheetDialog.show();

                return false;
            }
        });

        customProgressDialog.dismiss();



    }



    private class dbSet extends Thread { // db에서 데이터 가져오기
        @Override
        public void run() {

            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    url_id = "https://kirakirahikari.herokuapp.com/store_api/getStoreByGPS?lat=" + curLat + "&lon=" + curLon + "&dis=1500",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // 응답 성공 시
                            Log.d("응답성공", response);
                            String result = "";
                            try {
                                JSONArray jsonArray = new JSONArray(response);

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

            );

            requestQueue.add(request);
            Log.d("응답", url_id);

        }
    }

    public String getDistance(double lat, double lon){
        Location locationA = new Location("current");
        locationA.setLatitude(curLat);
        locationA.setLongitude(curLon);

        Location locationB = new Location("target");
        locationB.setLatitude(lat);
        locationB.setLongitude(lon);

        double dis = locationA.distanceTo(locationB);

        if (dis < 1000){
            distance = "" + (int)dis + " m";
        } else{
            distance = "" + ((int)dis)/10*10 / 1000.0 + " km";
        }
        Log.d("거리", distance);

        return distance;
    }

    public String getInfo(int store_id){ // 개선필요
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url_info = "https://kirakirahikari.herokuapp.com/store_api/getStoreInfoFromId?store_id=" + store_id,
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
                            }


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

        );

        requestQueue.add(request);
        Log.d("응답", url_id);

        return hours;
    }

    public void infoset(int cnt){
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc[cnt], 17));

        tv_store_name.setText(store_name[cnt]);
        tv_store_adr.setText(store_address[cnt]);
        if (store_phone.equals("")){
            tv_store_tel.setText("정보 없음");
        }else{
            tv_store_tel.setText(store_phone[cnt]);
        }
        tv_store_url.setText(store_url[cnt]);
        tv_store_hours.setText(store_hours[cnt]);

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

//        getInfo(store_id[cnt]);

        tv_store_hours.setText(hours);
        tv_store_distance.setText(distance);



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



            }else if (view.getId() == R.id.btn_pre){
                if (cnt > 0){
                    cnt--;
                    Log.d("cnt", ""+cnt);
                    getDistance(loc[cnt].latitude, loc[cnt].longitude);
                    infoset(cnt);


                }else if (cnt == 0){
                    cnt = store_id.length -1;
                    Log.d("cnt", ""+cnt);
                    getDistance(loc[cnt].latitude, loc[cnt].longitude);
                    infoset(cnt);
                }

            }else if (view.getId() == R.id.btn_next){
                if (cnt < store_id.length -1){
                    cnt++;
                    Log.d("cnt", ""+cnt);
                    getDistance(loc[cnt].latitude, loc[cnt].longitude);
                    infoset(cnt);

                }else if (cnt == store_id.length -1){
                    cnt = 0;
                    Log.d("cnt", ""+cnt);
                    getDistance(loc[cnt].latitude, loc[cnt].longitude);
                    infoset(cnt);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

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