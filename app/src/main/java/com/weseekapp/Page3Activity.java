package com.weseekapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Page3Activity extends Fragment implements OnMapReadyCallback, View.OnClickListener {
    @Nullable

    private GoogleMap mMap;
    private MapView mapView;
    final String URL = "https://dokkydokky.herokuapp.com/getAllData";
    RequestQueue requestQueue;

    String[] name; // 업소명
    String[] location; // 소재지
    String[] gps; // GPS
    String[] gpsS;

    LatLng korea = new LatLng(36.4894573, 127.7294827);
    LatLng gwangju = new LatLng(35.1398252, 126.8109661);

    private Button btn_array, btn_marker, btn_location;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page3, container, false);

//        if (requestQueue == null) {
//            requestQueue = Volley.newRequestQueue(view.getContext());
//        }
//
//        btn_array = view.findViewById(R.id.btn_array);
//        btn_marker = view.findViewById(R.id.btn_marker);
//
//        //btn_location = view.findViewById(R.id.btn_location);
//
//        btn_array.setOnClickListener(this);
//        btn_marker.setOnClickListener(this);
//        //btn_location.setOnClickListener(this);
//
//
//        mapView = view.findViewById(R.id.map);
//        mapView.onCreate(savedInstanceState);
//        mapView.getMapAsync(this);


        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;


        Log.d("응답", "" + korea);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(korea, 7));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Log.d("이벤트확인", marker.getTitle());
                mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                return false;
            }
        });

    }

    @Override
    public void onClick(View view) {
//        if (view.getId() == R.id.btn_array) {
//            StringRequest request = new StringRequest(
//                    Request.Method.GET,
//                    URL,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            // 응답 성공 시
//                            Log.d("응답성공", response);
//                            String result = "";
//                            try {
//                                JSONArray jsonArray = new JSONArray(response);
//
//                                name = new String[jsonArray.length()];
//                                location = new String[jsonArray.length()];
//                                gps = new String[jsonArray.length()];
//
//                                for (int i = 0; i < jsonArray.length(); i++) {
//
//                                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//
//                                    name[i] = jsonObject.getString("업소명");
//                                    location[i] = jsonObject.getString("소재지");
//                                    gps[i] = jsonObject.getString("GPS");
//
//
//
//
//
//                                }
//
//
//                                Log.d("응답성공", "DB 로드 성공!");
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            // 응답 실패 시
//                            Log.d("응답", "응답 실패");
//                        }
//                    }
//            );
//
//
//            requestQueue.add(request);
//
//        } else if (view.getId() == R.id.btn_marker) {
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gwangju, 15));
//            Log.d("응답성공", "마커 표시 성공!");
//
//            for (int i = 0; i < name.length; i++) {
//                gpsS = gps[i].split(",", 2);
//                float a1 = Float.parseFloat(gpsS[0]);
//                float a2 = Float.parseFloat(gpsS[1]);
//                MarkerOptions markerOptions = new MarkerOptions();
//
//                markerOptions
//                        .position(new LatLng(a1, a2))
//                        .title(name[i])
//                        .snippet(location[i]);
//
//                mMap.addMarker(markerOptions);
//            }
//        }
////        } else if (view.getId() == R.id.btn_location) {
////            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
////            if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////                // TODO: Consider calling
////                //    ActivityCompat#requestPermissions
////                // here to request the missing permissions, and then overriding
////                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
////                //                                          int[] grantResults)
////                // to handle the case where the user grants the permission. See the documentation
////                // for ActivityCompat#requestPermissions for more details.
////                return;
////            }
////            Location loc_Current = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
////            double cur_lat = loc_Current.getLatitude();
////            double cur_lon = loc_Current.getLongitude();
////            LatLng current = new LatLng(cur_lat, cur_lon);
////            Log.d("응답성공", " " + cur_lat + " " + cur_lon);
////            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 10));
////
////        }

    }

//    @Override
//    public void onResume() {
//        mapView.onResume();
//        super.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mapView.onLowMemory();
//    }
}