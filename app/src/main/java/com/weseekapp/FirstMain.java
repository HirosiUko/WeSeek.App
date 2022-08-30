package com.weseekapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class FirstMain extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_logIn, tv_signUp;

    String[] permission_list = { // 필요한 권한은 여기에!
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_main);

        checkPermission();

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
                intent = new Intent(this, Main_log_in.class);
                break;
            case R.id.tv_signUp:
                intent = new Intent(this, Main_sign_up.class);
                break;

        }

        startActivity(intent);
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;

        for (String permission : permission_list) {
            // 권한 허용 여부 확인
            int chk = checkCallingOrSelfPermission(permission);

            if (chk == PackageManager.PERMISSION_DENIED) {
                // 권한 허용 여부 확인 창
                requestPermissions(permission_list, 0);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0)
        {
            for(int i=0; i<grantResults.length; i++)
            {
                // 허용시
                if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                }
                else {
                    // 하나라도 허용하지 않을 시 안내 팝업
                    Toast.makeText(getApplicationContext(),"원활한 앱 실행을 위해서는 권한 설정이 필요합니다!",Toast.LENGTH_SHORT).show();

                }
            }
        }
    }
}