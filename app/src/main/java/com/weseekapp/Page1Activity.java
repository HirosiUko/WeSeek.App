package com.weseekapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Page1Activity extends Fragment implements View.OnClickListener{

    MainActivity mainActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    private TextView btn_page1_menu, btn_page1_info, btn_page1_location, btn_page1_jjim;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page1, container, false);

        btn_page1_menu = (TextView) view.findViewById(R.id.btn_page1_menu);
        btn_page1_info = (TextView) view.findViewById(R.id.btn_page1_info);
        btn_page1_location = (TextView) view.findViewById(R.id.btn_page1_location);
        btn_page1_jjim = (TextView) view.findViewById(R.id.btn_page1_jjim);

        btn_page1_menu.setOnClickListener(this);
        btn_page1_info.setOnClickListener(this);
        btn_page1_location.setOnClickListener(this);
        btn_page1_jjim.setOnClickListener(this);

        PersonInfo personInfo = PersonInfo.getInstance();
        if(personInfo.isLogin)
        {
            ((TextView)view.findViewById(R.id.tv_page1_title)).setText("Hello "+personInfo.getName());
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_page1_menu){
            mainActivity.onFragmentChange(0);
        }else if (view.getId() == R.id.btn_page1_info) {
            mainActivity.onFragmentChange(1);
        }else if (view.getId() == R.id.btn_page1_location){
            mainActivity.onFragmentChange(2);
        }else if (view.getId() == R.id.btn_page1_jjim){
            mainActivity.onFragmentChange(3);
        }
    }
}
