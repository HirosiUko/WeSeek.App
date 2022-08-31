package com.weseekapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class Detail_Frag1 extends Fragment{

    private GridView frame_grid_detail_frag1;
    private Detail_Frag1_Adapter adapter = new Detail_Frag1_Adapter();
    private boolean isStart = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_page1, container, false);

        frame_grid_detail_frag1 = (GridView) view.findViewById(R.id.frame_grid_detail_frag1);
        if (isStart == true){
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.wheat_back));
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.wheat_back));
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.wheat_back));
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.wheat_back));
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.wheat_back));
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.wheat_back));
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.wheat_back));
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.wheat_back));
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.wheat_back));
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.wheat_back));
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.wheat_back));
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.wheat_back));
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.wheat_back));
            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.wheat_back));

            isStart = false;
        }




        frame_grid_detail_frag1.setAdapter(adapter);






        return view;
    }
}
