package com.studio.pattimura.bukaamal.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.studio.pattimura.bukaamal.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DonasiSekarangFragment extends Fragment {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3;
    public boolean donasiTrue=false;
    public boolean pembayaranTrue=false;

    public DonasiSekarangFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donasi_sekarang, container, false);
        tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));


        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    Toast.makeText(DonasiSekarangFragment.this.getContext(), "0", Toast.LENGTH_SHORT).show();
                }
                else if (position==1){
                    if(!isDonasiTrue()){
                        Toast.makeText(DonasiSekarangFragment.this.getContext(), "Lengkapi Data Terlebih Dahulu", Toast.LENGTH_SHORT).show();
//                        viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
//                        Toast.makeText(DonasiSekarangFragment.this.getContext(), viewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();

//                        TabLayout.Tab tab = tabLayout.getTabAt(0);
//                        tab.select();
                    }
//                    Toast.makeText(DonasiSekarangFragment.this.getContext(), "abc", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }
    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new JumlahDonasiFragment();
                case 1:
                    if(!isDonasiTrue()) {
                        Toast.makeText(DonasiSekarangFragment.this.getContext(), "Lengkapi Data Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                        viewPager.setCurrentItem(0);
                        return new JumlahDonasiFragment();
                    }else
                    return new MetodePembayaranFragment();
                case 2:
                    return new PembayaranFragment();

            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Donasi";
                case 1:
                    return "Metode Pembayaran";
                case 2:
                    return "Pembayaran";
            }
            return null;
        }
    }



    public boolean isDonasiTrue() {
        return donasiTrue;
    }

    public void setDonasiTrue(boolean donasiTrue) {
        this.donasiTrue = donasiTrue;
    }
}
