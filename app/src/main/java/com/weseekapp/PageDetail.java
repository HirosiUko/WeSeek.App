package com.weseekapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PageDetail extends AppCompatActivity {

    private Detail_Frag1 detail_frag1;
    private Detail_Frag2 detail_frag2;
    private Detail_Frag3 detail_frag3;

    private FragmentManager fragmng;
    private BottomNavigationView navi2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_detail);

        detail_frag1 = new Detail_Frag1();
        detail_frag2 = new Detail_Frag2();
        detail_frag3 = new Detail_Frag3();

        fragmng = getSupportFragmentManager();
        fragmng.beginTransaction().replace(R.id.frame2, detail_frag1).commit();

        navi2 = findViewById(R.id.navi2);
        navi2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int selet_id = item.getItemId();
                if (selet_id == R.id.detail_menu_id1){
                    fragmng.beginTransaction().replace(R.id.frame2, detail_frag1).commit();
                }else if (selet_id == R.id.detail_menu_id2){
                    fragmng.beginTransaction().replace(R.id.frame2, detail_frag2).commit();
                }else if (selet_id == R.id.detail_menu_id3){
                    fragmng.beginTransaction().replace(R.id.frame2, detail_frag3).commit();
                }



                return true;
            }
        });


    }
}