package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.fragments.TweetsListFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsPagerAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class TimelineActivity extends AppCompatActivity implements TweetsListFragment.TweetSelectedListener {

    ViewPager vpPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //get the view pager
        vpPager = (ViewPager) findViewById(R.id.viewpager);
        //set the adapter for the pager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager(), this));
        //set the TabLayout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compose, menu);
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miCompose:
                composeMessage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private final int REQUEST_CODE = 20;

    private void composeMessage() {
        Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // check request code and result code first
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

            // Use data parameter
            Tweet tweet = (Tweet) Parcels.unwrap(data.getParcelableExtra("compose_tweet"));
            ((TweetsPagerAdapter) vpPager.getAdapter()).mPageReferenceMap.get(0).onCreatedNewTweet(tweet);
        }
    }

    public void onSubmit() {
        // closes the activity and returns to first screen
        this.finish();
    }

    public void onProfileView(MenuItem item) {
        //launch the profile view
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    @Override
    public void onTweetSelected(Tweet tweet) {
        Toast.makeText(this, tweet.body, Toast.LENGTH_SHORT).show();
    }
}
