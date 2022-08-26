package com.weseekapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class page2Adapter extends BaseAdapter{

    private ArrayList<page2VO> items = new ArrayList<>();

    @Override
    public int getCount() {
        return items.size();

    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Context context = viewGroup.getContext();
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.page2_second, viewGroup, false);
        }

        page2VO vo = items.get(i);
        ImageView page2_img_store = view.findViewById(R.id.page2_img_store);
        TextView page2_tv_name = view.findViewById(R.id.page2_tv_name);
        TextView page2_tv_addr = view.findViewById(R.id.page2_tv_addr);

        page2_img_store.setImageDrawable(vo.getPage2_store_pic());
        page2_tv_name.setText(vo.getPage2_store_name());
        page2_tv_addr.setText(vo.getPage2_store_addr());

        return view;

    }
    public void addItem(Drawable page2_store_pic, String page2_store_name, String page2_store_addr){
        page2VO vo = new page2VO(page2_store_pic, page2_store_name, page2_store_addr);
        items.add(vo);
    }


}