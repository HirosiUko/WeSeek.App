package com.weseekapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
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
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
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

public class Page2Activity extends Fragment implements View.OnClickListener{


    private RecyclerView page2_recyclerView;
    private Page2Adapter page2Adapter;
    private SearchView searchView_page2;
    private TextView btn_sort1, btn_sort2, btn_sort3, btn_sort4, btn_sort5, page2_tv_name;
    private ArrayList<Page2VO> arrayList;
    private View view;
    private Handler handler;

    private ImageView page2_coupon;

    private RatingBar page2_stars;

    private Boolean isCheck[] = new Boolean[50];


    private CompoundButton button_favoite_page2;
    private BounceInterpolator bounceInterpolator;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.page2, container, false);


        // ???????????? ????????? SORT ??????
        btn_sort1 = view.findViewById(R.id.btn_sort1);
        btn_sort2 = view.findViewById(R.id.btn_sort2);
        btn_sort3 = view.findViewById(R.id.btn_sort3);
        btn_sort4 = view.findViewById(R.id.btn_sort4);
        btn_sort5 = view.findViewById(R.id.btn_sort5);
        page2_tv_name = (TextView) view.findViewById(R.id.page2_tv_name);

        btn_sort1.setOnClickListener(this);
        btn_sort2.setOnClickListener(this);
        btn_sort3.setOnClickListener(this);
        btn_sort4.setOnClickListener(this);
        btn_sort5.setOnClickListener(this);



        // ?????????
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

        page2_stars = (RatingBar) view.findViewById(R.id.page2_stars);
//        page2_stars.setNumStars();



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
                            (ImageView) view.findViewById(R.id.page2_coupon),
                            (RatingBar) view.findViewById(R.id.page2_stars)
                    )
            );

        }

        for (int i = 0; i < page2Adapter.getItemCount(); i++){
            arrayList.add(page2Adapter.getItem(i));
        }

        page2_recyclerView.setAdapter(page2Adapter);
    }
    // ???????????? FliterList
    private void filterList(String text) {
        ArrayList<Page2VO> filteredList = new ArrayList<>();
        for (Page2VO vo : arrayList){
            if (vo.getVo_store_id().toLowerCase().contains(text.toLowerCase()) || vo.getVo_store_addr().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(vo);
            }
        }
        if (filteredList.isEmpty()){
//            customToast("no data");

//            Toast.makeText(getActivity().getApplicationContext(),"no data", Toast.LENGTH_SHORT).show();
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
    // ???????????? ???????????? ??????????????????
    private String gu = "";
    @Override
    public void onClick(View clicked_view) {
        clicked_view.setSelected(!clicked_view.isSelected());
        if (!clicked_view.isSelected()){
            page2_recyclerView.setAdapter(page2Adapter);
        } else if (clicked_view.isSelected()) {
            if (clicked_view.getId() == R.id.btn_sort1) {
                gu = "??????";
                (view.findViewById(R.id.btn_sort2)).setSelected(false);
                (view.findViewById(R.id.btn_sort3)).setSelected(false);
                (view.findViewById(R.id.btn_sort4)).setSelected(false);
                (view.findViewById(R.id.btn_sort5)).setSelected(false);
            } else if (clicked_view.getId() == R.id.btn_sort2) {
                gu = "??????";
                (view.findViewById(R.id.btn_sort1)).setSelected(false);
                (view.findViewById(R.id.btn_sort3)).setSelected(false);
                (view.findViewById(R.id.btn_sort4)).setSelected(false);
                (view.findViewById(R.id.btn_sort5)).setSelected(false);
            } else if (clicked_view.getId() == R.id.btn_sort3) {
                gu = "??????";
                (view.findViewById(R.id.btn_sort1)).setSelected(false);
                (view.findViewById(R.id.btn_sort2)).setSelected(false);
                (view.findViewById(R.id.btn_sort4)).setSelected(false);
                (view.findViewById(R.id.btn_sort5)).setSelected(false);
            } else if (clicked_view.getId() == R.id.btn_sort4) {
                gu = "??????";
                (view.findViewById(R.id.btn_sort1)).setSelected(false);
                (view.findViewById(R.id.btn_sort2)).setSelected(false);
                (view.findViewById(R.id.btn_sort3)).setSelected(false);
                (view.findViewById(R.id.btn_sort5)).setSelected(false);
            } else if (clicked_view.getId() == R.id.btn_sort5) {
                gu = "?????????";
                (view.findViewById(R.id.btn_sort1)).setSelected(false);
                (view.findViewById(R.id.btn_sort2)).setSelected(false);
                (view.findViewById(R.id.btn_sort3)).setSelected(false);
                (view.findViewById(R.id.btn_sort4)).setSelected(false);
            }
            ArrayList<Page2VO> filteredList = new ArrayList<>();
            for (Page2VO vo : arrayList) {
                if (vo.getVo_store_addr().contains(gu)) {
                    filteredList.add(vo);
                }
            }
            if (filteredList.isEmpty()) {
            } else {
                page2Adapter.setFilteredList(filteredList);
            }
        }
        if (view.getId()==R.id.page2_tv_name){
            if(page2_tv_name.getText().toString().equals("???????????? ?????????")){
                Intent intent = new Intent(getActivity(), PageDetail.class);
                startActivity(intent);
            }
        }

    }


    // ????????? ?????????
    public void customToast(String text){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_board, (ViewGroup) page2_recyclerView.findViewById(R.id.toast_layout_root));
        TextView textView = layout.findViewById(R.id.textboard);
        textView.setText(text);

        Toast toastView = Toast.makeText(getContext().getApplicationContext(), text, Toast.LENGTH_SHORT);
        toastView.setGravity(Gravity.CENTER,0,350);
        toastView.setView(layout);
        toastView.show();



    }
}
