package com.weseekapp;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Page2Activity extends Fragment {


    private RecyclerView page2_recyclerView;
    private Page2Adapter page2Adapter;
    private SearchView searchView_page2;
    private ArrayList<Page2VO> arrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page2, container, false);

        searchView_page2 = (SearchView) view.findViewById(R.id.searchView_page2);
        searchView_page2.clearFocus();

        searchView_page2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });


        page2_recyclerView = (RecyclerView) view.findViewById(R.id.page2_recyclerView);
        page2_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),
                RecyclerView.VERTICAL, false));


        arrayList = new ArrayList<>();
        page2Adapter = new Page2Adapter();

        page2Adapter.addItem(new Page2VO(R.drawable.ic_action_camera,"aaa","광주광역시 북구 어쩌고동 어쩌고길 11-1", (CompoundButton) view.findViewById(R.id.button_favorite_page2), (ImageView) view.findViewById(R.id.page2_coupon)));
        page2Adapter.addItem(new Page2VO(R.drawable.ic_action_detail,"bbb","adfasdfasf",(CompoundButton) view.findViewById(R.id.button_favorite_page2), (ImageView) view.findViewById(R.id.page2_coupon)));
        page2Adapter.addItem(new Page2VO(R.drawable.ic_action_camera,"sdfsdf","sdfsdf",(CompoundButton) view.findViewById(R.id.button_favorite_page2), (ImageView) view.findViewById(R.id.page2_coupon)));
        page2Adapter.addItem(new Page2VO(R.drawable.ic_action_camera,"fgf","sdfsdf",(CompoundButton) view.findViewById(R.id.button_favorite_page2), (ImageView) view.findViewById(R.id.page2_coupon)));
        page2Adapter.addItem(new Page2VO(R.drawable.ic_action_camera,"eeee","sdfsdf",(CompoundButton) view.findViewById(R.id.button_favorite_page2), (ImageView) view.findViewById(R.id.page2_coupon)));
        page2Adapter.addItem(new Page2VO(R.drawable.ic_action_camera,"assss","sdfsdf",(CompoundButton) view.findViewById(R.id.button_favorite_page2),(ImageView) view.findViewById(R.id.page2_coupon)));
        page2Adapter.addItem(new Page2VO(R.drawable.ic_action_camera,"ww","sdfsdf",(CompoundButton) view.findViewById(R.id.button_favorite_page2),(ImageView) view.findViewById(R.id.page2_coupon)));
        page2Adapter.addItem(new Page2VO(R.drawable.ic_action_camera,"yyyyy","sdfsdf",(CompoundButton) view.findViewById(R.id.button_favorite_page2),(ImageView) view.findViewById(R.id.page2_coupon)));
        page2Adapter.addItem(new Page2VO(R.drawable.ic_action_camera,"nnnnn","sdfsdf",(CompoundButton) view.findViewById(R.id.button_favorite_page2), (ImageView) view.findViewById(R.id.page2_coupon)));
        page2Adapter.addItem(new Page2VO(R.drawable.ic_action_camera,"111","sdfsdf",(CompoundButton) view.findViewById(R.id.button_favorite_page2), (ImageView) view.findViewById(R.id.page2_coupon)));
        page2Adapter.addItem(new Page2VO(R.drawable.ic_action_camera,"aaaaaa","sdfsdf",(CompoundButton) view.findViewById(R.id.button_favorite_page2), (ImageView) view.findViewById(R.id.page2_coupon)));
        page2Adapter.addItem(new Page2VO(R.drawable.ic_action_camera,"pppppp","sdfsdf",(CompoundButton) view.findViewById(R.id.button_favorite_page2),(ImageView) view.findViewById(R.id.page2_coupon)));
        page2Adapter.addItem(new Page2VO(R.drawable.ic_action_camera,"jjjjj","sdfsdf",(CompoundButton) view.findViewById(R.id.button_favorite_page2),(ImageView) view.findViewById(R.id.page2_coupon)));

        for (int i = 0; i < page2Adapter.getItemCount(); i++){
            arrayList.add(page2Adapter.getItem(i));
        }

        page2_recyclerView.setAdapter(page2Adapter);





        return view;
    }

    private void filterList(String text) {
        ArrayList<Page2VO> filteredList = new ArrayList<>();
        for (Page2VO vo : arrayList){
            if (vo.getVo_store_id().toLowerCase().contains(text.toLowerCase()) || vo.getVo_store_addr().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(vo);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(getActivity().getApplicationContext(),"no data", Toast.LENGTH_SHORT).show();
        } else {
            page2Adapter.setFilteredList(filteredList);
        }
    }


}
