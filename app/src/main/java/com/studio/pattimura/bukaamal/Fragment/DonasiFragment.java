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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.LandingPage;
import com.studio.pattimura.bukaamal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonasiFragment extends Fragment {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2;

    public DonasiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_donasi, container, false);
        tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        Bundle b = getArguments();
        if (b != null) {
            viewPager.setCurrentItem(1);
        }

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
                    return new DaftarUKMFragment();
                case 1:
                    return new BantuanLainFragment();

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
                    return "Modal UKM";

                case 1:
                    return "Bencana Alam, Penyakit, dan Yatim Piatu";
            }
            return null;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    Log.e("gif--","fragment back key is clicked");
                    getActivity().getSupportFragmentManager().popBackStack("BerandaFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    LandingPage l = (LandingPage) getActivity();
                    Picasso.with(getActivity().getApplicationContext()).load(R.drawable.logoberanda).into(l.getLogo());
                    l.getTxtJudul().setText("");
                    return true;
                }
                return false;
            }
        });
    }
}
