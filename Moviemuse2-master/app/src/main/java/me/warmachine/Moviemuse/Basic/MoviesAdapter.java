package me.warmachine.Moviemuse.Basic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.warmachine.Moviemuse.BuildConfig;
import me.warmachine.Moviemuse.R;
import me.warmachine.Moviemuse.entity.Movie;
import me.warmachine.Moviemuse.Effects.TMDbUtil;

final class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {


    private static final double TMDB_POSTER_SIZE_RATIO = 185.0 / 277.0;

    private final List<Movie> objMovies;
    private final LayoutInflater mInflater;
    private final Picasso mPicasso;
    private final int mPosterWidth;
    private final int mPosterHeight;
    private final ClickListener mItemClickListener;

    public MoviesAdapter(Context context, List<Movie> movies, int posterWidth,
                         ClickListener itemClickListener) {
        objMovies = movies;
        mInflater = LayoutInflater.from(context);
        mPicasso = Picasso.with(context);
        mPosterWidth = posterWidth;
        mPosterHeight = (int) (posterWidth / TMDB_POSTER_SIZE_RATIO);
        mItemClickListener = itemClickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.movie_list_item, parent, false);
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) view.getLayoutParams();
        lp.width = mPosterWidth;
        lp.height = mPosterHeight;
        view.setLayoutParams(lp);
        return new MovieViewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = getItem(position);
        holder.setMovie(movie);
        mPicasso
                .load(TMDbUtil.buildPosterUrl(movie.getPosterPath(), mPosterWidth))
                .resize(mPosterWidth, mPosterHeight)
                .centerCrop()
                .into(holder.mPoster);
        if (BuildConfig.DEBUG) {
            Log.v("Picasso", "Will resize image to " + mPosterWidth + "x" + mPosterHeight);
        }
    }

    public Movie getItem(int position) {
        return objMovies.get(position);
    }

    @Override
    public int getItemCount() {
        return objMovies.size();
    }



    static final class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @Bind(R.id.movie_poster)    ImageView mPoster;

        private Movie objMovie = null;
        private final ClickListener mClickListener;

        public MovieViewHolder(View itemView, ClickListener clickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            mClickListener = clickListener;
        }

        public void setMovie(Movie movie) {
            objMovie = movie;
        }

        @Override
        public void onClick(View v) {
            mClickListener.onMovieClick(itemView, objMovie);
        }

    }

    public interface ClickListener {
        void onMovieClick(View movieView, Movie movie);
    }

}
