package com.example.coffeeproject2.ui.storage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class StorageTabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public StorageTabsAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                StorageAllFragment all = new StorageAllFragment();
                return all;
            case 1:
                StorageTypeFragment type = new StorageTypeFragment();
                return type;
            case 2:
                StorageDateFragment date = new StorageDateFragment();
                return date;
            default:
                return null;
        }
    }
}