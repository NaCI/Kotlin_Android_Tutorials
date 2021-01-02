package com.test.rxjavaudemyclasscasestudy2.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.test.rxjavaudemyclasscasestudy2.R;
import com.test.rxjavaudemyclasscasestudy2.adapter.MovieAdapter;
import com.test.rxjavaudemyclasscasestudy2.model.Movie;
import com.test.rxjavaudemyclasscasestudy2.model.MovieDBResponse;
import com.test.rxjavaudemyclasscasestudy2.service.MoviesDataService;
import com.test.rxjavaudemyclasscasestudy2.service.RetrofitInstance;

import org.reactivestreams.Publisher;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Movie> movies;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private SwipeRefreshLayout swipeContainer;
    private Flowable<MovieDBResponse> flowable;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setTitle(" TMDb Popular Movies Today");


        getPopularMoviesWithRx();


        swipeContainer = findViewById(R.id.swipe_layout);
        swipeContainer.setColorSchemeResources(R.color.colorPrimary);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPopularMoviesWithRx();
            }
        });


    }

    public void getPopularMoviesWithRx() {

        movies = new ArrayList<>();
        MoviesDataService getMoviesDataService = RetrofitInstance.getService();
        flowable = getMoviesDataService.getPopularMoviesWithRx(this.getString(R.string.api_key));

        compositeDisposable.add(
                flowable.subscribeOn(Schedulers.io())
                        .flatMap(new Function<MovieDBResponse, Publisher<Movie>>() {
                            @Override
                            public Publisher<Movie> apply(MovieDBResponse movieDBResponse) throws Throwable {
                                return Flowable.fromArray(movieDBResponse.getMovies().toArray(new Movie[0]));
                            }
                        })
                        .filter(new Predicate<Movie>() {
                            @Override
                            public boolean test(Movie movie) throws Throwable {
                                return movie.getVoteAverage() > 7.0;
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSubscriber<Movie>() {
                            @Override
                            public void onNext(Movie movie) {
                                movies.add(movie);
                            }

                            @Override
                            public void onError(Throwable t) {
                                if (t instanceof SocketTimeoutException) {
                                    Toast.makeText(getApplicationContext(), "Socket Time out.", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onComplete() {
                                init();
                                swipeContainer.setRefreshing(false);
                            }
                        })
        );
    }


    public void init() {


        recyclerView = findViewById(R.id.rvMovies);
        movieAdapter = new MovieAdapter(this, movies);


        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}


