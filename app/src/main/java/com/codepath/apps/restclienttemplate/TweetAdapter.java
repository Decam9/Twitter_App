package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by decam9 on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private List<Tweet> mTweets;
    Context context;
    private TweetAdapterListener mListener;

    // define an interface required by the ViewHolder
    public interface TweetAdapterListener {
         void onItemSelected (View view, int Position);
    }
    //pass in the tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets, TweetAdapterListener listener) {
        mTweets = tweets;
        mListener = listener;
    }

    // for each row, inflate the layout and cache references into the viewholder

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    // bind the values based on the position of the element

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //get the data according to position
        final Tweet tweet = mTweets.get(position);

        // populate the views according to this data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvHandle.setText("@"+tweet.user.screenName);
        holder.tvTimeStamp.setText(getRelativeTimeAgo(tweet.createdAt));



        Glide.with(context).load(tweet.user.profileImageURL).bitmapTransform(new RoundedCornersTransformation(context, 200, 0)).into(holder.ivProfileImage);

        holder.ivProfileImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (tweet.getUser().getScreenName() != null)
                {
                    Intent i = new Intent(context, ProfileActivity.class);
                    i.putExtra("userID", tweet.getUser().getUid());
                    i.putExtra("screen_name", tweet.getUser().getScreenName());
                    context.startActivity(i);
                }
            }

        });
    }



    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    // create viewholder class

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvHandle;
        public TextView tvTimeStamp;

        public ViewHolder(View itemView) {
            super(itemView);

            //perform the findViewById lookups

            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvHandle = (TextView) itemView.findViewById(R.id.tvHandle);
            tvTimeStamp = (TextView) itemView.findViewById(R.id.tvTimeStamp);

            //handle row click event
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        //get the position of row element
                        int position = getAdapterPosition();
                        // fire the listener callback
                        mListener.onItemSelected(view, position);
                    }
                }
            });
        }

    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {


        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_ALL).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public void clear() {
        mTweets.clear();
    }

    public void addItem(Tweet tweet) {
        mTweets.add(tweet);
    }
}
