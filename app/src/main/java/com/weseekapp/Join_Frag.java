package com.weseekapp;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Join_Frag extends Fragment {
    @Nullable
    View view;
    private CircleImageView circleImageView;
    Drawable selected_profile = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main_sign_up, container, false);

        circleImageView = view.findViewById(R.id.signup_img);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();

            }
        });
        return view;
    }

    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()
                , R.style.AlertDialogTheme);

        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.layout_green_profile_select,
                (LinearLayout)this.view.findViewById(R.id.layoutDialog));

//        //다이얼로그 텍스트 설정
        builder.setView(view);
        ArrayList<ImageView> img_btn_list = new ArrayList<>();
        img_btn_list.add(view.findViewById(R.id.img_profile_select_1));
        img_btn_list.add(view.findViewById(R.id.img_profile_select_2));
        img_btn_list.add(view.findViewById(R.id.img_profile_select_3));
        img_btn_list.add(view.findViewById(R.id.img_profile_select_4));
        img_btn_list.add(view.findViewById(R.id.img_profile_select_5));
        img_btn_list.add(view.findViewById(R.id.img_profile_select_6));
        img_btn_list.add(view.findViewById(R.id.img_profile_select_7));
        img_btn_list.add(view.findViewById(R.id.img_profile_select_8));
        img_btn_list.add(view.findViewById(R.id.img_profile_select_9));

        for (ImageView btn_ele :
                img_btn_list) {
            btn_ele.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selected_profile = btn_ele.getDrawable();
                    for (ImageView tmp :
                            img_btn_list) {
                        tmp.setBackground(null);
                    }
                    btn_ele.setBackground(getResources().getDrawable(R.drawable.round_backgroud_border_black));
                    circleImageView.setImageDrawable(btn_ele.getDrawable());
                }
            });
        }


        AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.btn_profile_select_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        //다이얼로그 형태 지우기
        if(alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}
