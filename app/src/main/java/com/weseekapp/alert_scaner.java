package com.weseekapp;

import android.app.Dialog;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;

import de.hdodenhof.circleimageview.CircleImageView;

public class alert_scaner {
    private Context context;
    private Drawable drawable;

    private TextView conformBtn, backBtn;
    private CircleImageView signup_img;
    private int[] imgs = {R.drawable.cir_img2, R.drawable.cir_img3,
            R.drawable.cir_img4, R.drawable.cir_img5, R.drawable.cir_img6,
            R.drawable.cir_img7, R.drawable.cir_img9};
    private int[] imgs_id = {R.id.pop_img1, R.id.pop_img2, R.id.pop_img3, R.id.pop_img4,
            R.id.pop_img5, R.id.pop_img6, R.id.pop_img7, R.id.pop_img8,
            R.id.pop_img9};
    private ImageView [] imgs_find = new ImageView [9];

    ImageView pop_img1;

    public alert_scaner(Context context, Drawable drawable){
        this.context = context;
        this.drawable = drawable;
    }

    private ModifyReturnListener modifyReturnListener;



    public interface ModifyReturnListener{
        void afterModify(Drawable context);
    }
    public void setModifyReturnListener(ModifyReturnListener modifyReturnListener){
        this.modifyReturnListener = modifyReturnListener;

    }



    public void callFun(){


        final Dialog dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dlg.setContentView(R.layout.sign_up_pop);
        dlg.show();

        conformBtn = (TextView) dlg.findViewById(R.id.conformBtn);
        backBtn = (TextView) dlg.findViewById(R.id.backBtn);
        signup_img = (CircleImageView) dlg.findViewById(R.id.signup_img);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.onBackPressed();
            }
        });


        for(int i = 0; i < imgs_id.length; i++) {
            imgs_find[i] = dlg.findViewById(imgs_id[i]);
        }

        //pop_img1 = (ImageView) dlg.findViewById(imgs_id[0]);
        imgs_find[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageInfo.loginImage = R.drawable.cir_img2;
                Log.d("프로필 1","첫번째 그림");
                view.setSelected(true);

            }
        });

        imgs_find[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageInfo.loginImage = R.drawable.cir_img11;
                Log.d("프로필 2","두번째 그림");

            }
        });
        imgs_find[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageInfo.loginImage = R.drawable.cir_img3;
                Log.d("프로필 3","세번째 그림");

            }
        });
        imgs_find[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageInfo.loginImage = R.drawable.cir_img4;
                Log.d("프로필 4","네번째 그림");
            }
        });
        imgs_find[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageInfo.loginImage = R.drawable.cir_img5;
                Log.d("프로필 5","다섯번째 그림");

            }
        });
        imgs_find[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageInfo.loginImage = R.drawable.cir_img6;
                Log.d("프로필 6","여섯번째 그림");

            }
        });
        imgs_find[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageInfo.loginImage = R.drawable.cir_img9;
                Log.d("프로필 7","일곱번째 그림");

            }
        });
        imgs_find[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageInfo.loginImage = R.drawable.cir_img7;
                Log.d("프로필 8","여덟번째 그림");

            }
        });

        imgs_find[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageInfo.loginImage = R.drawable.cir_img10;
                Log.d("프로필 9","아홉번째 그림");

            }
        });




        conformBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), Main_sign_up.class);
                context.startActivity(intent);

                for (int i = 0; i < imgs.length; i++){
                    if (view.getId() == imgs_id[i]){
                        Drawable ModifyedImg = signup_img.getDrawable();
                        modifyReturnListener.afterModify(ModifyedImg);
                        dlg.onContentChanged();
                    }
                }


//                Drawable ModifyedImg = signup_img.getDrawable();
//                modifyReturnListener.afterModify(ModifyedImg);
//                dlg.onBackPressed();

            }
        });


    }

}