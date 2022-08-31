package com.weseekapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Detail_Frag3_Adapter extends BaseAdapter {

    private ArrayList<Detail_Frag3_VO> items = new ArrayList<>();


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
            view = inflater.inflate(R.layout.detail_page3_review, viewGroup, false);
        }

        Detail_Frag3_VO vo = items.get(i);
        CircleImageView img_profile = view.findViewById(R.id.img_profile_pt);
        TextView tv_id_review = view.findViewById(R.id.tv_id_review);
        TextView tv_review = view.findViewById(R.id.edt_review);
        RatingBar ratingcount = view.findViewById(R.id.ratingcount); // 수정필요

        img_profile.setImageDrawable(vo.getProfile());
        tv_id_review.setText(vo.getTag());
        tv_review.setText(vo.getReview());
        ratingcount.setRating(vo.getRate());

        return view;
    }


    public void addItem(Drawable profile, String tag, String review, Float rate){
        Detail_Frag3_VO vo = new Detail_Frag3_VO(profile, tag, review, rate);
        items.add(vo);
    }
}
