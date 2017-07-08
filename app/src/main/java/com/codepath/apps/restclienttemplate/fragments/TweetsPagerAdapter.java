package com.codepath.apps.restclienttemplate.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by decam9 on 7/5/17.
 */

public class TweetsPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[] {"Home", "Mentions"};
    public ConcurrentHashMap<Integer, TweetsListFragment> mPageReferenceMap = new ConcurrentHashMap();

    //return the total number of fragments there are using get count method

    private Context context;

    public TweetsPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        this.context = context;
    }
    @Override
    public int getCount() {
        return 2;
    }

    // return the fragment to use depending on the position

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            HomeTimelineFragment frag = new HomeTimelineFragment();
            mPageReferenceMap.put(0, frag);
            return frag;
        } else if (position == 1){
            MentionsTimelineFragment frag = new MentionsTimelineFragment();
            mPageReferenceMap.put(1, frag);
            return frag;
        }
        else {
            return null;
        }
    }


    //return title


    @Override
    public CharSequence getPageTitle(int position) {
        //generate title based on item position;
        return tabTitles [position];
    }
}
