package com.example.dashbord;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class memberPager extends FragmentPagerAdapter {
        private int numOfTabs;

        public memberPager(FragmentManager fm, int numOfTabs) {
            super(fm);
            this.numOfTabs = numOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new addMemberFrag();
                case 1:
                    return new editMemberFrag();
                default:
                    return null;


            }
        }


        @Override
        public int getCount() {
            return numOfTabs;
        }

}
