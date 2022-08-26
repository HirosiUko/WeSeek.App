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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    private TextView tv_name, tv_adr, tv_star;
    private ImageView btn_pre, btn_next;
    private int cnt = 0;

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

    private Button btn_search;

    ProgressDialog customProgressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page3, container, false);

        customProgressDialog = new ProgressDialog(view.getContext());
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(view.getContext());
        }

        tv_name = view.findViewById(R.id.tv_name);
        tv_adr = view.findViewById(R.id.tv_adr);
        tv_star = view.findViewById(R.id.tv_star);

        btn_search = view.findViewById(R.id.btn_search);
        btn_pre = view.findViewById(R.id.btn_pre);
        btn_next = view.findViewById(R.id.btn_next);

        btn_search.setOnClickListener(this);
        btn_pre.setOnClickListener(this);
        btn_next.setOnClickListener(this);

        mapView = view.findViewById(R.id.mapview);
        Log.d("에러", "onCreateView: ");
        mapView.onCreate(savedInstanceState);

        dbSet dbthread = new dbSet();
        dbthread.start();

        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        customProgressDialog.show();
        mMap = googleMap;

        currentLoc = new LatLng(curLat, curLon);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17));

        mMap.setOnMarkerClickListener(this);


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

        tv_name.setText(marker.getTitle());
        tv_adr.setText(marker.getSnippet());

        String title = marker.getTitle();
        String snippet = marker.getSnippet();
        double lat = marker.getPosition().latitude;
        double lon = marker.getPosition().longitude;
        Log.d("좌표", lat + ", " + lon);

        marker.remove();
        addMarker(title, snippet, lat, lon, true);

        return false;
    }


    private class dbSet extends Thread {
        @Override
        public void run() {
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location loc_Current = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            curLat = loc_Current.getLatitude();
            curLon = loc_Current.getLongitude();

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
            customProgressDialog.dismiss();
        }
    }

    public void infoset(int cnt){
        tv_name.setText(name[cnt]);
        tv_adr.setText(location[cnt]);
        tv_star.setText(star[cnt]);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc[cnt], 17));
    }

    private Marker addMarker(String title, String snippet, double lat, double lon, boolean isSelectedMarker){
        LatLng position = new LatLng(lat, lon);

        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.markerblue);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap blueMarker = Bitmap.createScaledBitmap(b, 80, 120, false);


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions
                .position(position)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromBitmap(blueMarker));

        return mMap.addMarker(markerOptions);

    }


    public void onClick(View view) {

        if (view.getId() == R.id.btn_search) {
            // 오류뜨면 위치코드 넣어야함

            currentLoc = new LatLng(curLat, curLon);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 15));
            Log.d("응답성공", "마커 표시 성공!");

            for (int i = 0; i < name.length; i++) {

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions
                        .position(loc[i])
                        .title(name[i])
                        .snippet(location[i]);

                mMap.addMarker(markerOptions);

            }

        }else if (view.getId() == R.id.btn_pre){
            if (cnt > 0){
                cnt--;
                Log.d("cnt", ""+cnt);
                infoset(cnt);


            }else if (cnt == 0){
                cnt = name.length -1;
                Log.d("cnt", ""+cnt);
                infoset(cnt);
            }

        }else if (view.getId() == R.id.btn_next){
            if (cnt < name.length -1){
                cnt++;
                Log.d("cnt", ""+cnt);
                infoset(cnt);

            }else if (cnt == name.length -1){
                cnt = 0;
                Log.d("cnt", ""+cnt);
                infoset(cnt);
            }

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