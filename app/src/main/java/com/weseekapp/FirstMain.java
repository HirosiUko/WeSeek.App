package com.weseekapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class FirstMain extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_logIn, tv_signUp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_main);





        tv_logIn = findViewById(R.id.tv_logIn);
        tv_logIn.setOnClickListener(this);

        tv_signUp = findViewById(R.id.tv_signUp);
        tv_signUp.setOnClickListener(this);

    }




    @Override
    public void onClick(View view) {
        int btnId = view.getId();
        Intent intent = null;
        switch (btnId){
            case R.id.tv_logIn:
//                intent = new Intent(this, Main_log_in.class);
                break;
            case R.id.tv_signUp:
//                intent = new Intent(this, Main_sign_up.class);
                break;

        }

        startActivity(intent);
    }


}