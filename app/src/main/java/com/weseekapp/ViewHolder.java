package com.weseekapp;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder{
    
    public TextView page2_tv_name;
    public TextView page2_tv_addr;
    public ImageView page2_img_store;
    
    public ViewHolder(Context context, @NonNull View itemView) {
        super(itemView);
        
        
        page2_tv_name = itemView.findViewById(R.id.page2_tv_name);
        page2_tv_addr = itemView.findViewById(R.id.page2_tv_addr);
        page2_img_store = itemView.findViewById(R.id.page2_img_store);
        
        page2_tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strText = page2_tv_name.getText().toString();
                Toast.makeText(itemView.getContext(), strText, Toast.LENGTH_SHORT).show();
            }
        });
        
        
        
        
    }
}
