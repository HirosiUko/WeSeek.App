package com.weseekapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Detail_Frag2 extends Fragment implements OnMapReadyCallback{

    private GoogleMap mMap;
    private MapView mapView_detail;

    private RadarChart radarChart;
    private TextView tv_detail_adr, tv_detail_tel, tv_detail_hours;
    private LatLng loc ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_page2, container, false);

        // 레이더차트
        radarChart = (RadarChart) view.findViewById(R.id.radarChart);

        Detail_INFO_VO detail_info_vo = null;

        Bundle bundle = getArguments();

        if (bundle != null){
            detail_info_vo = (Detail_INFO_VO) bundle.getSerializable("vo");
        }

        Log.d("응답, 확인용", detail_info_vo.adr);
        Log.d("응답, 확인용", detail_info_vo.hours);
        Log.d("응답, 확인용", detail_info_vo.tel);
        Log.d("응답, 확인용", ""+detail_info_vo.lat);
        Log.d("응답, 확인용", ""+detail_info_vo.lon);

        tv_detail_adr = view.findViewById(R.id.tv_detail_adr);
        tv_detail_tel = view.findViewById(R.id.tv_detail_tel);
        tv_detail_hours = view.findViewById(R.id.tv_detail_hours);

        tv_detail_adr.setText(detail_info_vo.adr);
        tv_detail_tel.setText(detail_info_vo.tel);
        tv_detail_hours.setText(detail_info_vo.hours);

        loc = new LatLng(detail_info_vo.lat, detail_info_vo.lon);

        mapView_detail = view.findViewById(R.id.mapView_detail);
        Log.d("에러", "onCreateView: ");
        mapView_detail.onCreate(savedInstanceState);

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }

        // 레이더차트안에 들어갈 값
        ArrayList<RadarEntry> visitors = new ArrayList<>();
        visitors.add(new RadarEntry(5));
        visitors.add(new RadarEntry(7));
        visitors.add(new RadarEntry(2));
        visitors.add(new RadarEntry(8));
        visitors.add(new RadarEntry(6));

        // 레이더차트 설정
        RadarDataSet radarDataSet = new RadarDataSet(visitors, " ");
        radarDataSet.setColor(Color.RED);
        radarDataSet.setLineWidth(2f);
        radarDataSet.setValueTextColor(Color.RED);
        radarDataSet.setValueTextSize(11f);

        radarChart.getLegend().setEnabled(false);
        radarChart.getDescription().setEnabled(false);
        radarChart.setRotationEnabled(false);

        RadarData radarData = new RadarData();
        radarData.addDataSet(radarDataSet);

        String[] labels = {"위생", "맛", "위치", "교통", "가격"};

        XAxis xAxis = radarChart.getXAxis();

        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        radarChart.setData(radarData);



        mapView_detail.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) { // 지도 띄우기 + 현재위치 받아와서 마커 표시

        mMap = googleMap;

        Detail_INFO_VO detail_info_vo = null;

        Bundle bundle = getArguments();

        if (bundle != null){
            detail_info_vo = (Detail_INFO_VO) bundle.getSerializable("vo");
        }

        try {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(loc)
                    .title(detail_info_vo.storeName)
                    .snippet(detail_info_vo.adr);

            mMap.addMarker(markerOptions);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 17));
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    @Override
    public void onResume() {
        mapView_detail.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView_detail.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView_detail.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView_detail.onLowMemory();
    }

}
