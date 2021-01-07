package com.test.rxjavaudemyclasscasestudy2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.test.rxjavaudemyclasscasestudy2.model.Movie
import com.test.rxjavaudemyclasscasestudy2.repository.MovieRepository

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val movieRepository = MovieRepository(application)

    fun getMovies() {
        movieRepository.getMovies()
    }

    fun getMoviesLiveData(): LiveData<List<Movie>> = movieRepository.moviesLiveData

    override fun onCleared() {
        super.onCleared()
        movieRepository.clear()
    }
}