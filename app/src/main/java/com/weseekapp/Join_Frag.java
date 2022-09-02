package com.weseekapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class Join_Frag extends Fragment {
    @Nullable
    View view;
    private CircleImageView circleImageView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main_sign_up, container, false);

        circleImageView = view.findViewById(R.id.signup_img);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = circleImageView.getDrawable();
                alert_scaner alert = new alert_scaner(getContext(), drawable);
                alert.callFun();
                alert.setModifyReturnListener(new alert_scaner.ModifyReturnListener() {
                    @Override
                    public void afterModify(Drawable context) {
                        circleImageView.setImageDrawable(drawable);
                    }
                });
            }
        });

        return view;
    }
}
