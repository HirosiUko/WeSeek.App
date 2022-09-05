package com.weseekapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class Detail_Frag2 extends Fragment{

    private RadarChart radarChart;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_page2, container, false);

        radarChart = (RadarChart) view.findViewById(R.id.radarChart);


        ArrayList<RadarEntry> visitors = new ArrayList<>();
        visitors.add(new RadarEntry(5));
        visitors.add(new RadarEntry(7));
        visitors.add(new RadarEntry(2));
        visitors.add(new RadarEntry(8));
        visitors.add(new RadarEntry(6));

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


        return view;
    }
}
