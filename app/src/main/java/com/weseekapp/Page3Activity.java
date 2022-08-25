package com.weseekapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
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

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator3;

public class Page3Activity extends Fragment implements OnMapReadyCallback {
    @Nullable

    private static GoogleMap mMap;
    private MapView mapView;

    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 10;
    private CircleIndicator3 mIndicator;
    private Handler handler;
    private Boolean isInitial;

    private int cnt = 0;


//    String[] name; // 업소명
//    String[] location; // 소재지
//    String[] gps; // GPS
//    String[] star; // 별 갯수
//    LatLng[] loc; // 위치정보

    View view;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.page3, container, false);

        isInitial = true;
        handler = new Handler();

        if (StoreInfoHandler.requestQueue == null) {
            StoreInfoHandler.requestQueue = Volley.newRequestQueue(getContext());
        }
        sendRequest();


        mapView = view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        handler.post(runable);


        return view;
    }

    private void initMap() {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMap = googleMap;
                // Set the map coordinates
                StoreInfoHandler storeInfoHandler = StoreInfoHandler.getInstance();
                StoreInfo storeInfo = storeInfoHandler.getStore_list().get(0);
                moveMap(storeInfo);
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.setTrafficEnabled(false);
                mMap.setBuildingsEnabled(true);
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        Log.d("마커클릭", "onMarkerClick: ");
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(19)); // 도움필요
                        return false;
                    }
                });
            }
        });
    }

    private void initContents() {
        //ViewPager2
        mPager = view.findViewById(R.id.mPager);
        //Adapter
        pagerAdapter = new StoreViewPaperAdapter(this, num_page);
        mPager.setAdapter(pagerAdapter);
        //Indicator
        mIndicator = view.findViewById(R.id.mIndicator);
        mIndicator.setViewPager(mPager);
        mIndicator.createIndicators(num_page, 0);
        //ViewPager Setting
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        mPager.setCurrentItem(1); //시작 지점
        mPager.setOffscreenPageLimit(StoreInfoHandler.getInstance().getStore_list().size()); //최대 이미지 수

        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mIndicator.animatePageSelected(position % num_page);
                Log.d("호준", String.format("onPageSelected: %d", position));
                if (isInitial != true) {
                    moveMap(StoreInfoHandler.getInstance().getStore_list().get(position));
                }
                isInitial = false;
            }
        });
    }

    private static void moveMap(StoreInfo storeInfo) // 화면 이동 + 마커 세트
    {
        Log.d("호준", "moveMap: " + storeInfo.toString());
        LatLng loc = new LatLng(storeInfo.latitude, storeInfo.longitude);
        //  Add a marker on the map coordinates.
        mMap.addMarker(new MarkerOptions()
                .position(loc)
                .title(storeInfo.storeName)
                .snippet(storeInfo.address + " : " + storeInfo.star_of_cleanliness)).showInfoWindow();
        // Move the camera to the map coordinates and zoom in closer.
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 19));
    }

    private void sendRequest() {
        // 서버에 요청할 주소

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("gps", "onCreate: 퍼미션에러");
            return;
        }
        Location loc_Current = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        double curLat = loc_Current.getLatitude();
        double curLon = loc_Current.getLongitude();
        Log.d("gps", ""+curLat + "," + curLon);
        String url = "https://dokkydokky.herokuapp.com/getStoreByGPS?lat=" + curLat + "&lon=" + curLon + "&dis=1500";

        // 요청 문자열 저장
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("resultValue",response);
                try {
                    JSONArray jsonArray = new JSONArray (response);
                    for(int i=0; i< jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = (JSONObject) jsonArray.opt(i);

                        String[] strGps = jsonObject.optString("GPS").split(",");
                        StoreInfo store = new StoreInfo(
                                jsonObject.optString("업소명"),
                                Float.parseFloat(strGps[0]),
                                Float.parseFloat(strGps[1]),
                                jsonObject.optString("소재지"),
                                jsonObject.optString("별점")
                        );
                        StoreInfoHandler storeInfoHandler = StoreInfoHandler.getInstance();
                        storeInfoHandler.addStore(store);
                        Log.d("호준", "onResponse: "+ store.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
    }

    public Runnable runable = new Runnable() {
        @Override
        public void run() {
            StoreInfoHandler storeInfoHandler = StoreInfoHandler.getInstance();
            if(storeInfoHandler.getCurrent_state() == StoreInfoHandler.State.NORMAL){
                initMap();
                initContents();
            }else{
                handler.postDelayed(this, 500);
            }
        }
    };



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