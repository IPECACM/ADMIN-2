package com.example.dashbord;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class gallerypager extends FragmentPagerAdapter {
        private int numOfTabs;

        public gallerypager(FragmentManager fm, int numOfTabs) {
            super(fm);
            this.numOfTabs = numOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new GalleryFrag();
                case 1:
                    return new EditGalleryFrag();
                default:
                    return null;


            }
        }


        @Override
        public int getCount() {
            return numOfTabs;
        }

}
