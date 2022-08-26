package com.weseekapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Page2Activity extends Fragment {

    private ListView page_listView;
    private page2Adapter adapter = new page2Adapter();

    private ScaleAnimation scaleAnimation;
    private BounceInterpolator bounceInterpolator;
    private CompoundButton button_favoite_page2;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page2, container, false);

        page_listView = (ListView) view.findViewById(R.id.page2_listView);
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_action_camera), "세계제일감자탕집",
                "광주 광산구 어쩌고동");
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_action_food),"존맛탱구리","광주 동구 어쩌고동");
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_action_joinin), "국밥한사발주이소","광주 북구 어쩌고");
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_action_home), "광주하면상추튀김이지","광주 맛있동");
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.bird001),"새도들르는방앗간","광주임당");
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_action_detail), "아조맛있어요","광주어쩌고저쩌고힘들다야");
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_action_camera), "힝구","광주 북구 맛있다리");







        adapter.notifyDataSetChanged();
        page_listView.setAdapter(adapter);


//
//        scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF,
//                0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
//        scaleAnimation.setDuration(500);
//        bounceInterpolator = new BounceInterpolator();
//        scaleAnimation.setInterpolator(bounceInterpolator);
//        button_favoite_page2 = (CompoundButton) view.findViewById(R.id.button_favorite_page2);
//
//        button_favoite_page2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                compoundButton.startAnimation(scaleAnimation);
//            }
//        });


        return view;

    }
}
