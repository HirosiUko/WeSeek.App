package com.weseekapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Page2Activity extends Fragment {


    private RecyclerView page2_recyclerView;
    private Page2Adapter page2Adapter;
    private SearchView searchView_page2;
    private ArrayList<Page2VO> arrayList;
    private View view;
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.page2, container, false);

        searchView_page2 = (SearchView) view.findViewById(R.id.searchView_page2);
        searchView_page2.clearFocus();

        searchView_page2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        loadingView();
//        handler.post(runable);
        return view;
    }

    public Runnable runable = new Runnable() {
        @Override
        public void run() {
            StoreInfoHandler storeInfoHandler = StoreInfoHandler.getInstance();
            if(storeInfoHandler.getCurrent_state() != StoreInfoHandler.State.NORMAL) {
                Log.d("WeSeek", "run: Loading from Server");
                handler.postDelayed(this, 100);
            }else{
                loadingView();
            }
        }
    };

    private void loadingView()
    {
        page2_recyclerView = (RecyclerView) view.findViewById(R.id.page2_recyclerView);
        page2_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),
                RecyclerView.VERTICAL, false));


        arrayList = new ArrayList<>();
        page2Adapter = new Page2Adapter();

        StoreInfoHandler storeInfoHandler = StoreInfoHandler.getInstance();
        for(StoreInfo storeInfo : storeInfoHandler.getStore_list())
        {
            RequestBuilder image_request;
            if(storeInfo.img_list.isEmpty())
            {
                image_request = Glide.with(this).load(getContext().getDrawable(R.drawable.ic_action_camera));
            }else {
                String load_image_url = storeInfo.img_list.get(0);
                image_request = Glide.with(this).load(load_image_url).error(R.drawable.ic_action_camera);
                Log.d("WeSeek", "Image:"+storeInfo.img_list.get(0));
            }
            page2Adapter.addItem(
                    new Page2VO(
//                            R.drawable.ic_action_camera,
//                                drawableFromUrl(storeInfo.img_list.get(0)),
                            image_request,
                            storeInfo.storeName,
                            storeInfo.address,
                            (CompoundButton) view.findViewById(R.id.button_favorite_page2),
                            (ImageView) view.findViewById(R.id.page2_coupon)
                    )
            );

        }

        for (int i = 0; i < page2Adapter.getItemCount(); i++){
            arrayList.add(page2Adapter.getItem(i));
        }

        page2_recyclerView.setAdapter(page2Adapter);
    }

    private void filterList(String text) {
        ArrayList<Page2VO> filteredList = new ArrayList<>();
        for (Page2VO vo : arrayList){
            if (vo.getVo_store_id().toLowerCase().contains(text.toLowerCase()) || vo.getVo_store_addr().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(vo);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(getActivity().getApplicationContext(),"no data", Toast.LENGTH_SHORT).show();
        } else {
            page2Adapter.setFilteredList(filteredList);
        }
    }
    private Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection =
                (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(getResources(),x);
    }
}
