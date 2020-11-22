package com.example.rxjavatutorial1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.rxjavatutorial1.data.DataSource
import com.example.rxjavatutorial1.data.Task
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity3 : AppCompatActivity() {

    val TAG = "Wulululu"

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        compositeDisposable.add(
            getTaskObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .sorted { o1, o2 -> o1.priority - o2.priority } // Sort results by priority
                .map { task ->
                    Task(task.description.toUpperCase(), task.isComplete, task.priority)
                }
                .subscribeWith(getTaskObserver())
        )
    }

    private fun getTaskObserver() = object: DisposableObserver<Task>() {
        override fun onNext(t: Task?) {
            Log.d(TAG, "getTasksObserver onNext: $t")
        }

        override fun onError(e: Throwable?) {
            Log.d(TAG, "getTasksObserver onError: ${e.toString()}")
        }

        override fun onComplete() {
            Log.d(TAG, "getTasksObserver onComplete")
        }
    }

    private fun getTaskObservable() : Observable<Task> {
        val tasks = DataSource.createTaskList()

        return Observable.create(ObservableOnSubscribe<Task> { emitter ->
            for (singleTask in tasks) {
                if(!emitter.isDisposed) {
                    emitter.onNext(singleTask)
                }
            }

            if(!emitter.isDisposed) {
                emitter.onComplete()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}