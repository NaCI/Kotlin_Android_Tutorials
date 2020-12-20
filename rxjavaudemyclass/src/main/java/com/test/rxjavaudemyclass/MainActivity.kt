package com.test.rxjavaudemyclass

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


private const val TAG = "myApp"

class MainActivity : AppCompatActivity() {

    private lateinit var myObservable: Observable<String>
    private lateinit var myObserver: Observer<String>
    private lateinit var myDisposable: Disposable

    private val greeting = "Hello From RxJava"

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.tvGreeting)

        myObservable = Observable.just(greeting)

        myObserver = object : Observer<String> {
            override fun onSubscribe(d: Disposable?) {
                Log.i(TAG, "onSubscribe: invoked")
                d?.let { myDisposable = d }
            }

            override fun onNext(t: String?) {
                Log.i(TAG, "onNext: $t")
                textView.text = t
            }

            override fun onError(e: Throwable?) {
                Log.i(TAG, "onError: invoked $e")
            }

            override fun onComplete() {
                Log.i(TAG, "onComplete: invoked")
            }
        }

        // The order of operators are important
        myObservable
            .subscribeOn(Schedulers.io())
            .delay(2, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(myObserver)

        //This wont work cause it will change the thread to main before delay operation. Delay changes thread to io and ui operation gives error
        /*myObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .delay(2, TimeUnit.SECONDS)
            .subscribe(myObserver)*/
    }

    override fun onDestroy() {
        super.onDestroy()

        if (this::myDisposable.isInitialized) {
            myDisposable.dispose()
        }
    }
}