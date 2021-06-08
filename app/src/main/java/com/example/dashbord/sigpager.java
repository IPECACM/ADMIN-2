package com.example.dashbord;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class sigpager extends FragmentPagerAdapter {
        private int numOfTabs;

        public sigpager(FragmentManager fm, int numOfTabs) {
            super(fm);
            this.numOfTabs = numOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new RegisterSigFragment();
                case 1:
                    return new ViewSigFrag();
                default:
                    return null;


            }
        }


        @Override
        public int getCount() {
            return numOfTabs;
        }

}
