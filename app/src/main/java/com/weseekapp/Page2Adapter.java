package com.weseekapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Page2Adapter extends RecyclerView.Adapter<ViewHolder>{



    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public interface OnLongItemClickListener {
        void onLongItemClick(int pos);
    }

    private OnLongItemClickListener onLongItemClickListener = null;

    public void setOnLongItemClickListener(OnLongItemClickListener listener){
        this.onLongItemClickListener = listener;
    }


    private ArrayList<Page2VO> arrayList;
    public Page2Adapter(){
        arrayList = new ArrayList<>();
    }

    public void setFilteredList(ArrayList<Page2VO> filteredList){
        this.arrayList = filteredList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.page2_second, parent, false);

        ViewHolder viewHolder = new ViewHolder(context, view);




        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Page2VO vo = arrayList.get(position);

        holder.page2_tv_name.setText(vo.vo_store_id);
        holder.page2_tv_addr.setText(vo.vo_store_addr);
        holder.page2_img_store.setImageResource(vo.vo_store_pic);



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void addItem(Page2VO vo){
        arrayList.add(vo);
    }

    public void setItem(ArrayList<Page2VO> arrayList){
        this.arrayList = arrayList;
    }

    public Page2VO getItem(int position){
        return arrayList.get(position);
    }

    public void setItem(int position, Page2VO vo){
        arrayList.set(position, vo);
    }


//    public void filter(String text){
//        if (text.isEmpty()){
//            arrayList.clear();
//            arrayList.addAll(arrayList);
//        } else {
//            ArrayList<Page2VO> result = new ArrayList<>();
//            text = text.toLowerCase();
//            for (Page2VO vo: arrayList){
//                if (vo.vo_store_id.toLowerCase().contains(text) || vo.vo_store_addr.toLowerCase().contains(text)){
//                    arrayList.add(vo);
//                }
//            }
//            arrayList.clear();
//            arrayList.addAll(result);
//        }
//        notifyDataSetChanged();
//    }

    public void filterList(ArrayList<Page2VO> filteredList){
        arrayList = filteredList;
        notifyDataSetChanged();

    }


}