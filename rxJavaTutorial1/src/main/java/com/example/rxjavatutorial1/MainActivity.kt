package com.example.rxjavatutorial1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.rxjavatutorial1.data.DataSource
import com.example.rxjavatutorial1.data.Task
import com.example.rxjavatutorial1.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val taskObservable = Observable
            .fromIterable(DataSource.createTaskList())
            .filter {
                Thread.sleep(3000)
                Log.d(TAG, "taskObservable: filter Thread: ${Thread.currentThread().name}")
                it.isComplete
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        taskObservable.subscribe(object: Observer<Task> {
            override fun onComplete() {
                Log.d(TAG, "onComplete: called")
            }

            override fun onSubscribe(d: Disposable?) {
                Log.d(TAG, "onSubscribe: called")
            }

            override fun onNext(task: Task?) {
                Log.d(TAG, "onNext: Thread: ${Thread.currentThread().name}")
                Log.d(TAG, "onNext: Task: ${task?.description}")
            }

            override fun onError(e: Throwable?) {
                Log.e(TAG, "onError: called", e)
            }

        })
    }
}
