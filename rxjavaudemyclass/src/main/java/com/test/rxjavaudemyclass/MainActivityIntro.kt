package com.test.rxjavaudemyclass

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


private const val TAG = "myApp"

class MainActivityIntro : AppCompatActivity() {

    private lateinit var myObservable: Observable<String>
    private lateinit var myObserver: DisposableObserver<String>
    private lateinit var myObserver2: DisposableObserver<String>
    private val myCompositeDisposable = CompositeDisposable()

    private val greeting = "Hello From RxJava"

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.tvGreeting)

        myObservable = Observable.just(greeting)

        myObserver = object : DisposableObserver<String>() {
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

        myCompositeDisposable.add(
            myObservable
                .subscribeOn(Schedulers.io())
                .delay(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(myObserver)
        )

        //This wont work cause it will change the thread to main before delay operation. Delay changes thread to io and ui operation gives error
        /*myObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .delay(2, TimeUnit.SECONDS)
            .subscribe(myObserver)*/

        myObserver2 = object : DisposableObserver<String>() {
            override fun onNext(t: String?) {
                Log.i(TAG, "myObserver2 onNext: $t")
                Snackbar.make(textView, greeting, Snackbar.LENGTH_LONG).show()
            }

            override fun onError(e: Throwable?) {
                Log.i(TAG, "myObserver2 onError: invoked $e")
            }

            override fun onComplete() {
                Log.i(TAG, "myObserver2 onComplete: invoked")
            }
        }

        myCompositeDisposable.add(
            myObservable
                .subscribeOn(Schedulers.io())
                .delay(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(myObserver2) // "subscribeWith" returns observer, thats why we use it here instead of "subscribe" method which returns unit
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        myCompositeDisposable.clear() // We will clear compositeDisposable instead of disposing 2 disposable observers separately
    }
}