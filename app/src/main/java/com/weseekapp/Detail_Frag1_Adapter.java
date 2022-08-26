package com.weseekapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class Detail_Frag1_Adapter extends BaseAdapter {

    private ArrayList<Detail_Frag1_VO> items = new ArrayList<>();

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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.detail_page1_pics, viewGroup, false);
        }

        Detail_Frag1_VO vo = items.get(i);
        ImageView detail_frag1_tv_img = view.findViewById(R.id.detail_page1_tv_img);

        detail_frag1_tv_img.setImageDrawable(vo.getDetail_frag1_img());


        return view;
    }
    public void addItem(Drawable detail_frag1_img){
        Detail_Frag1_VO vo = new Detail_Frag1_VO(detail_frag1_img);
        items.add(vo);
    }
}
