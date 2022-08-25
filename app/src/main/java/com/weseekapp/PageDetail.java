package com.weseekapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PageDetail extends AppCompatActivity {

    private Detail_Frag1 detail_frag1;
    private Detail_Frag2 detail_frag2;
    private Detail_Frag3 detail_frag3;

    private FragmentManager fragmng;
    private BottomNavigationView navi2;

    private ScaleAnimation scaleAnimation;
    private BounceInterpolator bounceInterpolator;
    private CompoundButton button_favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_detail);

        scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF,
                0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);

        button_favorite = findViewById(R.id.button_favorite_page2);

        button_favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                compoundButton.startAnimation(scaleAnimation);
            }
        });


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