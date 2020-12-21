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

class OperatorRangeActivity : AppCompatActivity() {

    private lateinit var myObservable: Observable<Long>
    private lateinit var myObserver: DisposableObserver<Long>
    private val myCompositeDisposable = CompositeDisposable()

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.tvGreeting)

//        myObservable = Observable.range(1, 20)
        myObservable = Observable.intervalRange(1, 10, 1, 1, TimeUnit.SECONDS)

        myCompositeDisposable.add(
            myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver() : DisposableObserver<Long> {
        myObserver = object : DisposableObserver<Long>() {
            override fun onNext(t: Long?) {
                Log.i(TAG, "onNext: $t")
                textView.text = "Counting starts : $t"
            }

            override fun onError(e: Throwable?) {
                Log.i(TAG, "onError: invoked $e")
            }

            override fun onComplete() {
                Log.i(TAG, "onComplete: invoked")
                textView.text = "Well done"
            }
        }
        return myObserver
    }

    override fun onDestroy() {
        super.onDestroy()

        myCompositeDisposable.clear() // We will clear compositeDisposable instead of disposing 2 disposable observers separately
    }
}