package com.test.rxjavaudemyclasscasestudy2.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.test.rxjavaudemyclasscasestudy2.R
import com.test.rxjavaudemyclasscasestudy2.model.Movie
import com.test.rxjavaudemyclasscasestudy2.service.RetrofitInstance
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import java.util.*

class MovieRepository(private val application: Application) {

    private val compositeDisposable = CompositeDisposable()
    private val movies = ArrayList<Movie>()
    val moviesLiveData: MutableLiveData<List<Movie>> = MutableLiveData()

    fun getMovies() {
        val getMoviesDataService = RetrofitInstance.getService()
        val moviesFlowable =
            getMoviesDataService.getPopularMoviesWithRx(application.getString(R.string.api_key))

        compositeDisposable.add(
            moviesFlowable.subscribeOn(Schedulers.io())
                .flatMap { movieDBResponse ->
                    Flowable.fromIterable(movieDBResponse.movies)
                }
                .filter { movie ->
                    movie.voteAverage > 7.0
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSubscriber<Movie?>() {
                    override fun onNext(movie: Movie?) {
                        Log.d("TAG", "onNext: movie: $movie")
                        movie?.let {
                            movies.add(movie)
                        }
                    }

                    override fun onError(t: Throwable) {
                        Log.d("TAG", "onError: ${t.message}")
                    }

                    override fun onComplete() {
                        moviesLiveData.postValue(movies)
                    }
                })
        )
    }

    fun clear() {
        compositeDisposable.clear()
    }
}