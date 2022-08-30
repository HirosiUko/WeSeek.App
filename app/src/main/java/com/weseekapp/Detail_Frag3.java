package com.weseekapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class Detail_Frag3 extends Fragment{

    private ListView listView;
    private Detail_Frag3_Adapter adapter = new Detail_Frag3_Adapter();
    boolean check = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_page3, container,false);

        listView = view.findViewById(R.id.listview_review);


        if (check){
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.anya0001), "# 존맛", "맛있다!");
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.anya0001), "# 꿀맛", "맛있다!");
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.anya0001), "# 꿀맛", "맛있다!");
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.anya0001), "# 맛집추천", "맛있다!");
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.anya0001), "# 가성비킹", "맛있다!");
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.anya0001), "# 낫배드", "맛있다!");
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.anya0001), "# 태그1", "맛있다!");
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.anya0001), "# 태그2", "맛있다!");
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.anya0001), "# 태그2", "맛있다!");
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.anya0001), "# 태그2", "맛있다!");
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.anya0001), "# 태그2", "맛있다!");

            check = !check;
        }


        listView.setAdapter(adapter);


        return view;
    }
}
