package com.weseekapp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class StoreViewPaperAdapter extends FragmentStateAdapter {
    public int mCount;
    public StoreViewPaperAdapter(@NonNull Page3Activity fragmentActivity, int count) {
        super(fragmentActivity);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d("호준", String.format("createFragment: %d", position));
        StoreInfoHandler storeInfoHandler = StoreInfoHandler.getInstance();
        StoreInfo store = storeInfoHandler.getStore_list().get(position);
        StoreFragment storeFragment = new StoreFragment();
        storeFragment.setContent(store.toString());
        return storeFragment;
    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
