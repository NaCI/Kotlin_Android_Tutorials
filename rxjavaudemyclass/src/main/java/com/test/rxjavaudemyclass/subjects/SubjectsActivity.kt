package com.test.rxjavaudemyclass.subjects

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.test.rxjavaudemyclass.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.AsyncSubject
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject


private const val TAG = "myApp"

class SubjectsActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.tvGreeting)

//        asyncSubjectDemo()
        asyncSubjectDemo2()
//        behaviourSubjectDemo()
        behaviourSubjectDemo2()
//        publishSubjectDemo()
        publishSubjectDemo2()
//        replaySubjectDemo()
        replaySubjectDemo2()
    }

    private fun asyncSubjectDemo() {
        val myObservable: Observable<String> = Observable.just("TAŞ", "KAĞIT", "MAKAS", "TOPRAK")
        myObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        val asyncSubject: AsyncSubject<String> = AsyncSubject.create()
        myObservable.subscribe(asyncSubject)

        asyncSubject.subscribe(getFirstObserver())
        asyncSubject.subscribe(getSecondObserver())
        asyncSubject.subscribe(getThirdObserver())
    }

    // Async subject always emits the last item no matter if onNext or complete called before the subscription
    private fun asyncSubjectDemo2() {
        Log.i(TAG, "asyncSubjectDemo2: -------------------------------------")
        val asyncSubject: AsyncSubject<String> = AsyncSubject.create()
        asyncSubject.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        asyncSubject.subscribe(getFirstObserver())
        asyncSubject.onNext("TAŞ")
        asyncSubject.onNext("KAĞIT")
        asyncSubject.onNext("MAKAS")

        asyncSubject.subscribe(getSecondObserver())
        asyncSubject.onNext("TOPRAK")
        asyncSubject.onComplete()

        asyncSubject.subscribe(getThirdObserver())

        /* OUTPUT :
        I/myApp: First observer onSubscribe:
        I/myApp: Second observer onSubscribe:
        I/myApp: First observer onNext: TOPRAK
        I/myApp: First observer onComplete: invoked
        I/myApp: Second observer onNext: TOPRAK
        I/myApp: Second observer onComplete: invoked
        I/myApp: Third observer onSubscribe: invoked
        I/myApp: Third observer onNext: TOPRAK
        I/myApp: Third observer onComplete: invoked
        */
        Log.i(TAG, "-------------------------------------------------------")
    }

    private fun behaviourSubjectDemo() {
        val myObservable: Observable<String> = Observable.just("TAŞ", "KAĞIT", "MAKAS", "TOPRAK")
        myObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        val behaviourSubject: BehaviorSubject<String> = BehaviorSubject.create()

        /* If we call myObservable.subscribe before subscription to observer like below; then no value will be emitted for observer's on next method
        myObservable.subscribe(behaviourSubject)
        */
        behaviourSubject.subscribe(getFirstObserver())
        behaviourSubject.subscribe(getSecondObserver())
        behaviourSubject.subscribe(getThirdObserver())

        myObservable.subscribe(behaviourSubject)
    }

    // Behaviour subject emits last emitted item and all subsequent items
    private fun behaviourSubjectDemo2() {
        Log.i(TAG, "behaviourSubjectDemo2: -------------------------------------")
        val behaviourSubject: BehaviorSubject<String> = BehaviorSubject.create()
        behaviourSubject.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        behaviourSubject.subscribe(getFirstObserver())
        behaviourSubject.onNext("TAŞ")
        behaviourSubject.onNext("KAĞIT")
        behaviourSubject.onNext("MAKAS")

        behaviourSubject.subscribe(getSecondObserver())
        behaviourSubject.onNext("TOPRAK")
        behaviourSubject.onComplete()

        behaviourSubject.subscribe(getThirdObserver())

        /* OUTPUT :
        I/myApp: First observer onSubscribe:
        I/myApp: First observer onNext: TAŞ
        I/myApp: First observer onNext: KAĞIT
        I/myApp: First observer onNext: MAKAS
        I/myApp: Second observer onSubscribe:
        I/myApp: Second observer onNext: MAKAS
        I/myApp: First observer onNext: TOPRAK
        I/myApp: Second observer onNext: TOPRAK
        I/myApp: First observer onComplete: invoked
        I/myApp: Second observer onComplete: invoked
        I/myApp: Third observer onSubscribe: invoked
        I/myApp: Third observer onComplete: invoked
        */
        Log.i(TAG, "-------------------------------------------------------")
    }

    private fun publishSubjectDemo() {
        val myObservable: Observable<String> = Observable.just("TAŞ", "KAĞIT", "MAKAS", "TOPRAK")
        myObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        val publishSubject: PublishSubject<String> = PublishSubject.create()

        publishSubject.subscribe(getFirstObserver())
        publishSubject.subscribe(getSecondObserver())
        publishSubject.subscribe(getThirdObserver())

        myObservable.subscribe(publishSubject)
    }

    // Emits all the subsequent items of the source Observable at the time of subscription
    private fun publishSubjectDemo2() {
        Log.i(TAG, "publishSubjectDemo2: -------------------------------------")
        val publishSubject: PublishSubject<String> = PublishSubject.create()
        publishSubject.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        publishSubject.subscribe(getFirstObserver())
        publishSubject.onNext("TAŞ")
        publishSubject.onNext("KAĞIT")
        publishSubject.onNext("MAKAS")

        publishSubject.subscribe(getSecondObserver())
        publishSubject.onNext("TOPRAK")
        publishSubject.onComplete()

        publishSubject.subscribe(getThirdObserver())

        /* OUTPUT :
        I/myApp: First observer onSubscribe:
        I/myApp: First observer onNext: TAŞ
        I/myApp: First observer onNext: KAĞIT
        I/myApp: First observer onNext: MAKAS
        I/myApp: Second observer onSubscribe:
        I/myApp: First observer onNext: TOPRAK
        I/myApp: Second observer onNext: TOPRAK
        I/myApp: First observer onComplete: invoked
        I/myApp: Second observer onComplete: invoked
        I/myApp: Third observer onSubscribe: invoked
        I/myApp: Third observer onComplete: invoked
        */
        Log.i(TAG, "-------------------------------------------------------")
    }

    private fun replaySubjectDemo() {
        val myObservable: Observable<String> = Observable.just("TAŞ", "KAĞIT", "MAKAS", "TOPRAK")
        myObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        val replaySubject: ReplaySubject<String> = ReplaySubject.create()

        replaySubject.subscribe(getFirstObserver())
        replaySubject.subscribe(getSecondObserver())
        replaySubject.subscribe(getThirdObserver())

        myObservable.subscribe(replaySubject)
    }

    // Emits all the items of the Observable without considering when the subscriber subscribed
    private fun replaySubjectDemo2() {
        Log.i(TAG, "replaySubjectDemo2: -------------------------------------")
        val replaySubject: ReplaySubject<String> = ReplaySubject.create()
        replaySubject.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        replaySubject.subscribe(getFirstObserver())
        replaySubject.onNext("TAŞ")
        replaySubject.onNext("KAĞIT")
        replaySubject.onNext("MAKAS")

        replaySubject.subscribe(getSecondObserver())
        replaySubject.onNext("TOPRAK")
        replaySubject.onComplete()

        replaySubject.subscribe(getThirdObserver())

        /* OUTPUT :
        I/myApp: First observer onSubscribe:
        I/myApp: First observer onNext: TAŞ
        I/myApp: First observer onNext: KAĞIT
        I/myApp: First observer onNext: MAKAS
        I/myApp: Second observer onSubscribe:
        I/myApp: Second observer onNext: TAŞ
        I/myApp: Second observer onNext: KAĞIT
        I/myApp: Second observer onNext: MAKAS
        I/myApp: First observer onNext: TOPRAK
        I/myApp: Second observer onNext: TOPRAK
        I/myApp: First observer onComplete: invoked
        I/myApp: Second observer onComplete: invoked
        I/myApp: Third observer onSubscribe: invoked
        I/myApp: Third observer onNext: TAŞ
        I/myApp: Third observer onNext: KAĞIT
        I/myApp: Third observer onNext: MAKAS
        I/myApp: Third observer onNext: TOPRAK
        I/myApp: Third observer onComplete: invoked
        */
        Log.i(TAG, "-------------------------------------------------------")
    }

    private fun getFirstObserver(): Observer<String> {
        return object : Observer<String> {
            override fun onSubscribe(d: Disposable?) {
                Log.i(TAG, "First observer onSubscribe: ")
            }

            override fun onNext(t: String?) {
                Log.i(TAG, "First observer onNext: $t")
            }

            override fun onError(e: Throwable?) {
                Log.i(TAG, "First observer onError: invoked $e")
            }

            override fun onComplete() {
                Log.i(TAG, "First observer onComplete: invoked")
            }
        }
    }

    private fun getSecondObserver(): Observer<String> {
        return object : Observer<String> {
            override fun onSubscribe(d: Disposable?) {
                Log.i(TAG, "Second observer onSubscribe: ")
            }

            override fun onNext(t: String?) {
                Log.i(TAG, "Second observer onNext: $t")
            }

            override fun onError(e: Throwable?) {
                Log.i(TAG, "Second observer onError: invoked $e")
            }

            override fun onComplete() {
                Log.i(TAG, "Second observer onComplete: invoked")
            }
        }
    }

    private fun getThirdObserver(): Observer<String> {
        return object : Observer<String> {
            override fun onSubscribe(d: Disposable?) {
                Log.i(TAG, "Third observer onSubscribe: invoked")
            }

            override fun onNext(t: String?) {
                Log.i(TAG, "Third observer onNext: $t")
            }

            override fun onError(e: Throwable?) {
                Log.i(TAG, "Third observer onError: invoked $e")
            }

            override fun onComplete() {
                Log.i(TAG, "Third observer onComplete: invoked")
            }
        }
    }
}