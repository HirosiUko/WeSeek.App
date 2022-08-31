package com.weseekapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StoreFragment extends Fragment {
    TextView tv_storeName, tv_storeNum, tv_storeStar, tv_storeTime, tv_storeAdr, tv_storeHomepage;
    ImageView img_storeImg;

    StoreInfo storeInfo;

    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.page3_retouch2, container, false);
//        tv_storeName = view.findViewById(R.id.tv_storeName);
//        tv_storeNum = view.findViewById(R.id.tv_storeNum);
//        tv_storeStar = view.findViewById(R.id.tv_storeStar);
//        tv_storeTime = view.findViewById(R.id.tv_storeTime);
//        tv_storeAdr = view.findViewById(R.id.tv_storeAdr);
//        tv_storeHomepage = view.findViewById(R.id.tv_storeHomepage);
//        img_storeImg = view.findViewById(R.id.img_storeImg);

        tv_storeName.setText(storeInfo.storeName); // 모든 정보는 storeInfo에 저장되어있다!
        tv_storeNum.setText("가게 전화번호");
        tv_storeStar.setText(storeInfo.star_of_cleanliness);
        tv_storeTime.setText("가게 운영시간");
        tv_storeAdr.setText(storeInfo.address);
        tv_storeHomepage.setText("*");



        return view;
    }
}
