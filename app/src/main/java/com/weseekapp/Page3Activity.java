package com.weseekapp;

import android.Manifest;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Page3Activity extends Fragment implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMarkerClickListener {
    @Nullable

    private GoogleMap mMap;
    private MapView mapView;
    private TextView tv_name, tv_adr, tv_star, tv_distance; // 구 레이아웃용, 나중에 삭제
    private TextView tv_store_id, tv_store_name, tv_store_tel, tv_store_adr, tv_store_hours, img_store_img, img_store_thumb, tv_store_star, tv_store_distance; // 임시, 새 레이아웃용
    private ImageButton btn_pre, btn_next;
    private TextView btn_search;
    private int cnt = 0;

    private CompoundButton page3_favorite_animation;
    private BounceInterpolator bounceInterpolator;
    private ScaleAnimation scaleAnimation;


    String url;
    RequestQueue requestQueue;

    String[] name; // 업소명
    String[] location; // 소재지
    String[] gps; // GPS
    String[] gpsS;
    String[] star; // 별 갯수
    LatLng[] loc; // 위치정보

    Double curLat, curLon;

    LatLng currentLoc;
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

        tv_name = view.findViewById(R.id.tv_name);
        tv_adr = view.findViewById(R.id.tv_adr);
        tv_star = view.findViewById(R.id.tv_star);
        tv_distance = view.findViewById(R.id.tv_distance);

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

//        customProgressDialog.show();

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

        mMap.setOnMarkerClickListener(this);

//        customProgressDialog.dismiss();



    }

    private void getMarkerItem(){
        ArrayList<MarkerItem> markerItemArrayList = new ArrayList<>();

//        for (MarkerItem markerItem : markerItemArrayList){
//            addMarker(markerItem, false);
//        }
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));

//        String title = marker.getTitle();
//        String snippet = marker.getSnippet();
//        double lat = marker.getPosition().latitude;
//        double lon = marker.getPosition().longitude;
//        Log.d("좌표", lat + ", " + lon);
//        marker.remove();
//        addMarker(title, snippet, lat, lon, true);

        cnt = Integer.parseInt(marker.getId().replaceAll("[^\\d]", ""))-1;
        Log.d("태그", ""+cnt);

        getDistance(loc[cnt].latitude, loc[cnt].longitude);
        infoset(cnt);

        tv_name.setText(marker.getTitle());
        tv_adr.setText(marker.getSnippet());
        tv_distance.setText(distance);

        return false;
    }


    private class dbSet extends Thread { // db에서 데이터 가져오기
        @Override
        public void run() {

            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    url = "https://dokkydokky.herokuapp.com/getStoreByGPS?lat=" + curLat + "&lon=" + curLon + "&dis=1500",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // 응답 성공 시
                            Log.d("응답성공", response);
                            String result = "";
                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                name = new String[jsonArray.length()];
                                location = new String[jsonArray.length()];
                                gps = new String[jsonArray.length()];
                                star = new String[jsonArray.length()];
                                loc = new LatLng[jsonArray.length()];

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                                    name[i] = jsonObject.getString("업소명");
                                    location[i] = jsonObject.getString("소재지");
                                    gps[i] = jsonObject.getString("GPS");
                                    star[i] = jsonObject.getString("별점");
                                    gpsS = gps[i].split(",", 2);
                                    float a1 = Float.parseFloat(gpsS[0]);
                                    float a2 = Float.parseFloat(gpsS[1]);
                                    loc[i] = new LatLng(a1, a2);

                                }

                                Log.d("응답성공", "DB 로드 성공!");
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

            Log.d("에러", url);
            requestQueue.add(request);
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

    public void infoset(int cnt){
        tv_name.setText(name[cnt]);
        tv_adr.setText(location[cnt]);
        tv_star.setText(star[cnt]);
        tv_distance.setText(distance);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc[cnt], 17));
    }

//    private Marker addMarker(String title, String snippet, double lat, double lon, boolean isSelectedMarker){
//        LatLng position = new LatLng(lat, lon);
//
//        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.markerblue);
//        Bitmap b = bitmapdraw.getBitmap();
//        Bitmap blueMarker = Bitmap.createScaledBitmap(b, 80, 120, false);
//
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions
//                .position(position)
//                .title(title)
//                .snippet(snippet)
//                .icon(BitmapDescriptorFactory.fromBitmap(blueMarker));
//
//        return mMap.addMarker(markerOptions);
//
//    }


    public void onClick(View view) { // 버튼 클릭 이벤트

        try {
            if (view.getId() == R.id.btn_search) { // 주변탐색 버튼 클릭시 이벤트
                // 오류뜨면 위치코드 넣어야함

                Log.d("응답성공", "마커 표시 성공!");

                for (int i = 0; i < name.length; i++) { // db에서 받아온 정보로 마커 표시

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions
                            .position(loc[i])
                            .title(name[i])
                            .snippet(location[i]);

                    mMap.addMarker(markerOptions);

                }
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17));
                cnt = 0;

            }else if (view.getId() == R.id.btn_pre){
                if (cnt > 0){
                    cnt--;
                    Log.d("cnt", ""+cnt);
                    getDistance(loc[cnt].latitude, loc[cnt].longitude);
                    infoset(cnt);


                }else if (cnt == 0){
                    cnt = name.length -1;
                    Log.d("cnt", ""+cnt);
                    getDistance(loc[cnt].latitude, loc[cnt].longitude);
                    infoset(cnt);
                }

            }else if (view.getId() == R.id.btn_next){
                if (cnt < name.length -1){
                    cnt++;
                    Log.d("cnt", ""+cnt);
                    getDistance(loc[cnt].latitude, loc[cnt].longitude);
                    infoset(cnt);

                }else if (cnt == name.length -1){
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