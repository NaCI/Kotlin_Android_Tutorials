package com.test.rxjavaudemyclass.operators

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.test.rxjavaudemyclass.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


private const val TAG = "myApp"

class OperatorJustActivity : AppCompatActivity() {

    private lateinit var myObservable: Observable<String>
    private lateinit var myObserver: DisposableObserver<String>
    private val myCompositeDisposable = CompositeDisposable()

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.tvGreeting)

        myObservable = Observable.just("Hello 1", "Hello 2", "Hello 3", "Hello 4")

        myCompositeDisposable.add(
            myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver() : DisposableObserver<String> {
        myObserver = object : DisposableObserver<String>() {
            override fun onNext(t: String?) {
                Log.i(TAG, "onNext: $t")
            }

            override fun onError(e: Throwable?) {
                Log.i(TAG, "onError: invoked $e")
            }

            override fun onComplete() {
                Log.i(TAG, "onComplete: invoked")
            }
        }
        return myObserver
    }

    override fun onDestroy() {
        super.onDestroy()

        myCompositeDisposable.clear() // We will clear compositeDisposable instead of disposing 2 disposable observers separately
    }
}