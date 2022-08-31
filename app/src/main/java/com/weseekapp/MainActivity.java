package com.weseekapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private Page1Activity page1Activity;
    private Page2Activity page2Activity;
    private Page3Activity page3Activity;
    private Page4Activity page4Activity;
    private Page5Activity page5Activity;

    private FragmentManager fragmentManager;
    private BottomNavigationView navi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Frame 설정하는 부분
        page1Activity = new Page1Activity();
        page2Activity = new Page2Activity();
        page3Activity = new Page3Activity();
        page4Activity = new Page4Activity();
        page5Activity = new Page5Activity();


        fragmentManager = getSupportFragmentManager();
        // 처음시작 Page는 Page1로 한다.
        fragmentManager.beginTransaction().replace(R.id.frame, page1Activity).commit();

        // Navigation 연결하는 부분
        navi = findViewById(R.id.nav_View);
        // 클릭시 Event Listener 생성.
        navi.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.page1:
                        Log.d("WeSeek", "onNavigationItemSelected: move to page1Activity");
                        fragmentManager.beginTransaction().replace(R.id.frame, page1Activity).commit();
                        break;
                    case R.id.page2:
                        Log.d("WeSeek", "onNavigationItemSelected: move to page2Activity");
//                        fragmentManager.beginTransaction().replace(R.id.frame, page2Activity).commit();
                        // Test Code from : https://kumgo1d.tistory.com/29
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.replace(R.id.frame, page2Activity);
                        ft.commit();
                        break;
                    case R.id.page3:
                        Log.d("WeSeek", "onNavigationItemSelected: move to page3Activity");
                        fragmentManager.beginTransaction().replace(R.id.frame, page3Activity).commit();
                        break;
                    case R.id.page4:
                        Log.d("WeSeek", "onNavigationItemSelected: move to page4Activity");
                        fragmentManager.beginTransaction().replace(R.id.frame, page4Activity).commit();
                        break;
                    case R.id.page5:
                        Log.d("WeSeek", "onNavigationItemSelected: move to page5Activity");
                        fragmentManager.beginTransaction().replace(R.id.frame, page5Activity).commit();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }
    public void onFragmentChange(int index){
        if (index == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, page2Activity).commit();
        }else if (index == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, page4Activity).commit();
        }else if (index == 2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, page3Activity).commit();
        }else if (index == 3) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, page5Activity).commit();
        }
    }


}