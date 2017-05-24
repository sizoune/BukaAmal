package com.studio.pattimura.bukaamal.Fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.Model.userAuth;
import com.studio.pattimura.bukaamal.Model.userProfile;
import com.studio.pattimura.bukaamal.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailProfileFragment extends Fragment {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private userProfile profileData;

    public DetailProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_profile, container, false);

        preferences = this.getActivity().getSharedPreferences("prefProfile", MODE_PRIVATE);
        editor = preferences.edit();
        Gson gson = new Gson();
        String json = preferences.getString("prefProfile", "");
        profileData = gson.fromJson(json, userProfile.class);

        tabLayout = (TabLayout) v.findViewById(R.id.tabsDetailprofile);
        viewPager = (ViewPager) v.findViewById(R.id.viewpagerdetailprofile);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        ImageView gambar = (ImageView) v.findViewById(R.id.imageViewProfile);
        TextView nama = (TextView) v.findViewById(R.id.txtNameDetailProfile);
        TextView asal = (TextView) v.findViewById(R.id.txtAsalDetailProfile);

        Picasso.with(DetailProfileFragment.this.getContext()).load(profileData.getAvatar()).fit().into(gambar);
        nama.setText(profileData.getNama());
        asal.setText(profileData.getAlamat());
        return v;
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
                    return new GalangDanaSaya();
                case 1:
                    return new DonasiSaya();

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
                    return "Galang Dana Saya";
                case 1:
                    return "Donasi Saya";
            }
            return null;
        }
    }

}
