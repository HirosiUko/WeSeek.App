package com.weseekapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewHolder extends RecyclerView.ViewHolder{

    public TextView page2_tv_name;
    public TextView page2_tv_addr;
    public ImageView page2_img_store;
    public CompoundButton button_favorite_page2;
    
    public ViewHolder(Context context, @NonNull View itemView) {
        super(itemView);
        

        page2_tv_name = itemView.findViewById(R.id.page2_tv_name);
        page2_tv_addr = itemView.findViewById(R.id.page2_tv_addr);
        page2_img_store = itemView.findViewById(R.id.page2_img_store);
        button_favorite_page2 = itemView.findViewById(R.id.button_favorite_page2);
        
//        page2_tv_name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Context context1 = view.getContext();
//                String strText = page2_tv_name.getText().toString();
//                Intent intent = new Intent(context1, PageDetail.class);
//                context1.startActivity(intent);
//            }
//        });
//
        
        
        
    }
}
