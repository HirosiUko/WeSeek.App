package com.weseekapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Page5_adapter extends RecyclerView.Adapter<view_holder5> {
    private ImageView img_profile_pt2;

    private ArrayList<Page5VO> arrayList;
    private Page5VO vo;
    private Object view_holder5;

    public Page5_adapter() {
        arrayList = new ArrayList<>();
    }


    @NonNull
    @Override
    public view_holder5 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.page5_reviewlist, parent, false);
        view_holder5 ViewHolder5 = new view_holder5(context, view);
        return ViewHolder5;
    }


    @Override
    public void onBindViewHolder(@NonNull view_holder5 holder, int position) {
    Page5VO vo = arrayList.get(position);

    holder.img_profile_pt2.setImageResource(vo.vo_profile);
    holder.page5_tv_stid.setText(vo.vo_st_id);
    holder.page5_tv_stad.setText(vo.vo_st_ad);
    holder.page5_tv_stdate.setText(vo.vo_st_date);
    holder.page5_tv_rvcount.setText(vo.vo_rv_count);
    }

    @Override
    public int getItemCount() { return arrayList.size();}

    public void addItem(Page5VO vo) { arrayList.add(vo);}
    public void setItem(ArrayList<Page5VO>arrayList) {this.arrayList = arrayList;}
    public Page5VO getItem(int position) {return  arrayList.set(position, vo);}

    }