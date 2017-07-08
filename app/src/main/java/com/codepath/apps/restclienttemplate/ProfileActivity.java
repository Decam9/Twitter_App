package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String screenName = getIntent().getStringExtra("screen_name");
        // create the user fragment
        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);
        //display the user timeline fragment inside the container (dynamically)

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();


        //make change
        ft.replace(R.id.flContainer, userTimelineFragment);
        //commit
        ft.commit();

        client = TwitterApp.getRestClient();
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //deserialize the user object
                try {
                    User user = User.fromJSON(response);
                    //set the title of the action bar based on the user info
                    getSupportActionBar().setTitle(user.screenName);
                    //populate the user headline
                    populateUserHeadline (user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });

//        client.getOtherUserTimeline(String.valueof(screenName), screenName, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                //deserialize the user object
//                try {
//                    User user = User.fromJSON(response);
//                    //set the title of the action bar based on the user info
//                    getSupportActionBar().setTitle(user.screenName);
//                    //populate the user headline
//                    populateUserHeadline (user);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        });
    }

    public void populateUserHeadline(User user){
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);

        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvName.setText(user.name);


        tvTagline.setText(user.tagLine);
        tvFollowers.setText(user.followersCount + " followers");
        tvFollowing.setText(user.followingCount + " following");
        //load profile image with glide
        Glide.with(this).load(user.profileImageURL).bitmapTransform(new RoundedCornersTransformation(this, 200, 0)).into(ivProfileImage);
    }
}
