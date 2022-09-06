package com.weseekapp;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import de.hdodenhof.circleimageview.CircleImageView;

public class Page4Activity extends Fragment {
    @Nullable
    private CircleImageView circle_iv;

    Dialog dialog;
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.page4, container, false);

        circle_iv=(CircleImageView) view.findViewById(R.id.circle_iv);
        circle_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "너는 사진을 바꿀 수 없다. 불가능 지금은", Toast.LENGTH_SHORT).show();
            }
        });

        PersonInfo personInfo = PersonInfo.getInstance();
        if(personInfo.isLogin == false)
        {
            // 시작
            dialog = new Dialog(view.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_green_dialog);
            showCustomDialog();
            // 끝
        }else{
            updateProfile();
        }

        return view;
    }
    private void updateProfile(){
        PersonInfo personInfo = PersonInfo.getInstance();
        ((TextView)view.findViewById(R.id.tv_page4_name)).setText(personInfo.getName());
        ((TextView)view.findViewById(R.id.tv_page4_nickname)).setText(personInfo.getNickName());
        ((TextView)view.findViewById(R.id.tv_page4_email)).setText(personInfo.getEmail());
        ((TextView)view.findViewById(R.id.tv_page4_createDate)).setText(personInfo.getCreateDate());
        ((TextView)view.findViewById(R.id.tv_page4_profile)).setText(personInfo.getUserProfile());
    }
    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()
                , R.style.AlertDialogTheme);

        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.layout_green_dialog,
                (LinearLayout)this.view.findViewById(R.id.layoutDialog));

        //다이얼로그 텍스트 설정
        builder.setView(view);
        ((TextView)view.findViewById(R.id.textTitle)).setText("WeSeek.App");
        ((TextView)view.findViewById(R.id.textMessage)).setText("아앗! 아직 로그인을 안하셨군요!\n 로그인후 저희와 함께 여행을 시작해요!❤️");
        ((Button)view.findViewById(R.id.btn_join)).setText("Join");
        ((Button)view.findViewById(R.id.btn_profile_select_ok)).setText("Login");

        AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.btn_profile_select_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "너는 이미 눌렀다.", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new Login_Frag();
//                fragmentTransaction.add(R.id.frame, fragment);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commit();
                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.btn_join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "오마에와 모 신지떼르!", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new Join_Frag();
//                fragmentTransaction.add(R.id.frame, fragment);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commit();
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

