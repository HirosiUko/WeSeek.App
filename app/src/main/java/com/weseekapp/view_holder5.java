package com.weseekapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
public class view_holder5 extends RecyclerView.ViewHolder {
    public CircleImageView img_profile_pt2;
    public TextView page5_tv_stid;
    public TextView page5_tv_stad;
    public TextView page5_tv_stdate;
    public TextView page5_tv_rvcount;


    public view_holder5(Context context, @NonNull View itemView) {

        super(itemView);
        img_profile_pt2 =itemView.findViewById(R.id.img_profile_pt2);
        page5_tv_stid = itemView.findViewById(R.id.page5_tv_stid);
        page5_tv_stad = itemView.findViewById(R.id.page5_tv_stad);
        page5_tv_stdate = itemView.findViewById(R.id.page5_tv_stdate);
        page5_tv_rvcount = itemView.findViewById(R.id.page5_tv_rvcount);

    }
}
