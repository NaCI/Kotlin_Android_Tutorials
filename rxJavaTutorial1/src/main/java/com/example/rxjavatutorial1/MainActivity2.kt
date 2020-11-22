package com.example.rxjavatutorial1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity2 : AppCompatActivity() {

    val TAG: String = "Wulululu"

    // Prevents memory leaks. On activity destroys but thread still runs. Ends thread onDestroy
//    lateinit var disposable: Disposable

    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

//        getObservable()
//            .subscribeOn(Schedulers.io())
//            .delay(1600, TimeUnit.MILLISECONDS)
//            .filter { animalName ->
//                animalName.toLowerCase().startsWith("b")
//            }
////            .debounce(600, TimeUnit.MILLISECONDS)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeWith(getObserver())

        compositeDisposable.add(
            getAnimalObservable()
                .subscribeOn(Schedulers.io())
                .filter { animalName ->
                    animalName.toLowerCase().startsWith("b")
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getAnimalObserver())
        )

        compositeDisposable.add(
            getAnimalObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter { animalName ->
                    animalName.toLowerCase().startsWith("c")
                }
                .map { animalName ->
                    "${animalName.toUpperCase()} - wululu"
                }
                .subscribeWith(getAllCapsAnimalObserver())
        )


        Log.d(TAG, "onCreate: just another command")
    }

    private fun getObservable() = Observable.just("Ant", "Bee", "bird", "Cat", "Dog", "Fox")

    private fun getAnimalObservable() = Observable.fromArray(
        "Ant", "Ape",
        "bat", "Bee", "Bear", "Butterfly",
        "cat", "crab", "Cod",
        "Dog", "dove",
        "fox", "Frog")

//    private fun getObserver() = object: Observer<String> {
//        override fun onSubscribe(d: Disposable?) {
//            Log.d(TAG, "getObserver onSubscribe: $d")
//            d?.let { disposable = d }
//        }
//
//        override fun onNext(t: String?) {
//            Log.d(TAG, "getObserver onNext: $t")
//        }
//
//        override fun onError(e: Throwable?) {
//            Log.d(TAG, "getObserver onError: ${e.toString()}")
//        }
//
//        override fun onComplete() {
//            Log.d(TAG, "getObserver onComplete")
//        }
//    }

    private fun getAnimalObserver(): DisposableObserver<String> = object: DisposableObserver<String>() {
        override fun onNext(t: String?) {
            Log.d(TAG, "getObserver onNext: $t")
        }

        override fun onError(e: Throwable?) {
            Log.d(TAG, "getObserver onError: ${e.toString()}")
        }

        override fun onComplete() {
            Log.d(TAG, "getObserver onComplete")
        }
    }

    private fun getAllCapsAnimalObserver() = object: DisposableObserver<String>() {
        override fun onNext(t: String?) {
            Log.d(TAG, "getAllCapsObserver onNext: $t")
        }

        override fun onError(e: Throwable?) {
            Log.d(TAG, "getAllCapsObserver onError: ${e.toString()}")
        }

        override fun onComplete() {
            Log.d(TAG, "getAllCapsObserver onComplete")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        disposable.dispose()
        compositeDisposable.clear()
    }
}