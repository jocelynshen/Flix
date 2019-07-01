package com.example.flixter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.flixter.models.Movie;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieDetailsActivity extends AppCompatActivity {
    Movie movie;
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName())); // grab movie object parcel
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // set all text
        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(movie.getTitle());
        tvOverview = findViewById(R.id.tvOverview);
        tvOverview.setText(movie.getOverview());

        // set ratings bar
        rbVoteAverage = findViewById(R.id.rbVoteAverage);
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);

        // set movie poster image
        String imageUrl = getIntent().getExtras().getString("imageUrl");
        ImageView iv = findViewById(R.id.ivBackdropImage);
        Context context = MovieAdapter.context;
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.flicks_backdrop_placeholder)
                .bitmapTransform(new RoundedCornersTransformation(context, 15, 0))
                .into(iv);
    }

    public void getTrailer(View v) {
        /*
        Grabs movie trailer
         */
        Intent i = new Intent(this.getApplicationContext(), MovieTrailerActivity.class);
        int id = getIntent().getExtras().getInt("id");
        i.putExtra("id", id);
        startActivityForResult(i, 20);
    }
}
