package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweetAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by decam9 on 7/3/17.
 */

public class TweetsListFragment extends Fragment {

    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;

    //inflation happens inside onCreateView


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the layout
        View v = inflater.inflate(R.layout.fragments_tweets_list, container, false);

        //find the Recyclerview
        rvTweets = (RecyclerView) v.findViewById(R.id.rvTweets);
        //initiate the arraylist (data source)
        tweets = new ArrayList<>();
        //construct the adapter from this data source
        tweetAdapter = new TweetAdapter(tweets);
        //RecyclerView setup(layout manager, use adapter)
        rvTweets.setLayoutManager(new LinearLayoutManager(getContext()));
        //set the adapter
        rvTweets.setAdapter(tweetAdapter);
        return v;
    }

    public void addItems (JSONArray response){
        tweetAdapter.clear();
        for (int i = 0; i < response.length(); i++) {
            try {
                Tweet newTweet;
                newTweet = Tweet.fromJSon((JSONObject) response.get(i));
                tweetAdapter.addItem(newTweet);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        tweetAdapter.notifyDataSetChanged();
    }
}
