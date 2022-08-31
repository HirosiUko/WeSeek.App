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

    private View firstLayout;

    static final int PERMISSIONS_REQUEST = 0x0000001;

    String[] permission_list = { // 필요한 권한은 여기에 넣으면 됨
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_main);


        firstLayout = findViewById(R.id.firstLayout);

        OnCheckPermission();


        tv_logIn = findViewById(R.id.tv_logIn);
        tv_logIn.setOnClickListener(this);

        tv_signUp = findViewById(R.id.tv_signUp);
        tv_signUp.setOnClickListener(this);

    }

    private void OnCheckPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, permission_list, PERMISSIONS_REQUEST);
            }else{
                ActivityCompat.requestPermissions(this, permission_list, PERMISSIONS_REQUEST);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(firstLayout, "권한이 설정되었습니다", Snackbar.LENGTH_LONG).show();
                }else {
//
                    Snackbar.make(firstLayout, "앱 사용을 위해서는 권한 설정이 필요합니다.\n설정으로 이동하시겠습니까?", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // 스낵바 OK 클릭시 실행작업
                            startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                        }
                    }).show();
                }

                break;
        }

    }


    @Override
    public void onClick(View view) {
        int btnId = view.getId();
        Intent intent = null;
        switch (btnId){
            case R.id.tv_logIn:
                intent = new Intent(this, Main_log_in.class);
                break;
            case R.id.tv_signUp:
                intent = new Intent(this, Main_sign_up.class);
                break;

        }

        startActivity(intent);
    }


}