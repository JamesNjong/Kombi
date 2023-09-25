package xyz.thisisjames.boulevard.android.kombi.home.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    private int Pages =1;

    private Fragment _fragment;

    public SectionsPagerAdapter(Context context, int pages,Fragment fragment, FragmentManager fm) {
        super(fm);
        mContext = context;
        Pages = pages ;
        this._fragment = fragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {


        return new Fragment() ;
    }

    @Override
    public int getCount() {
        return Pages;
    }
}
