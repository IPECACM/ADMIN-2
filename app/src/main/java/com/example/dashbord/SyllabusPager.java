package com.example.dashbord;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SyllabusPager extends FragmentPagerAdapter {
        private int numOfTabs;

        public SyllabusPager(FragmentManager fm, int numOfTabs) {
            super(fm);
            this.numOfTabs = numOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new SyllabusFrag();
                case 1:
                    return new ViewSyllabus();
                default:
                    return null;


            }
        }


        @Override
        public int getCount() {
            return numOfTabs;
        }

}
