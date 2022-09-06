package com.weseekapp;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Page2Adapter extends RecyclerView.Adapter<ViewHolder>{

    private CompoundButton button_favoite_page2;
    private BounceInterpolator bounceInterpolator;
    private ImageView page2_coupon;
    private View view;
    public ArrayList<String> JjimList;
    public Boolean isCheck[] = new Boolean[50];
    private TextView page2_tv_name;

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private OnItemClickListener onItemClickListener;

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

        view = inflater.inflate(R.layout.page2_second, parent, false);

        ViewHolder viewHolder = new ViewHolder(context, view);

        // 하트 애니메이션
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF,
                0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);
        button_favoite_page2 = (CompoundButton) view.findViewById(R.id.button_favorite_page2);

        button_favoite_page2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                compoundButton.startAnimation(scaleAnimation);
                if (compoundButton.isChecked()){
                    customToast(getItem(viewHolder.getAdapterPosition()).vo_store_id+" 찜!");
//                    JjimList.add(getItem(viewHolder.getAdapterPosition()).vo_store_id);
                }else if (!compoundButton.isChecked()){
                    customToast("찜이 해제되었습니다");
//                    JjimList.remove(getItem(viewHolder.getAdapterPosition()).vo_store_id);
                }


//                if (b == true){
//                    customToast("찜");
//
////                    Toast toast = Toast.makeText(context,"찜!", Toast.LENGTH_SHORT);
////                    toast.show();
//                }else{
//                    customToast("찜이 취소되었습니다");
////                    Toast toast = Toast.makeText(context, "찜이 취소되었습니다", Toast.LENGTH_SHORT);
////                    toast.show();
//                }
            }
        });


      if (button_favoite_page2.isChecked()){
             JjimList.set(viewHolder.getAdapterPosition(), getItem(viewHolder.getPosition()).vo_store_id);

      }
        page2_coupon = (ImageView) view.findViewById(R.id.page2_coupon);





        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Page2VO vo = arrayList.get(position);

        holder.page2_tv_name.setText(vo.vo_store_id);
        holder.page2_tv_addr.setText(vo.vo_store_addr);
        vo.vo_store_pic.into(holder.page2_img_store);
        //page2_stars
//        holder.page
//        vo.vo_stars.setNumStars();
        holder.button_favorite_page2.setOnClickListener((View.OnClickListener) vo.vo_btn_favorite);
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
    public void customToast(String text){
        LayoutInflater inflater = LayoutInflater.from(button_favoite_page2.getContext());
        View layout = inflater.inflate(R.layout.toast_board, (ViewGroup) view.findViewById(R.id.toast_layout_root));

        TextView textView = layout.findViewById(R.id.textboard);
        textView.setText(text);

        Toast toastView = Toast.makeText(layout.getContext(), text, Toast.LENGTH_SHORT);
        toastView.setGravity(Gravity.CENTER,0,600);
        toastView.setView(layout);
        toastView.show();



    }


}