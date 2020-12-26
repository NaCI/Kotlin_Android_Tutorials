package com.test.rxjavaudemyclass.rxflowable

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.test.rxjavaudemyclass.R
import com.test.rxjavaudemyclass.data.Student
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber


private const val TAG = "myApp"

class RxFlowableActivity : AppCompatActivity() {

    private lateinit var myObservable: Observable<Student>
    private lateinit var myFlowable: Flowable<Student>
    private lateinit var mySubscriber: DisposableSubscriber<Student>
    private val myCompositeDisposable = CompositeDisposable()

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.tvGreeting)

        myObservable = Observable.create { emitter ->
            for (student in getStudents()) {
                emitter.onNext(student)
            }
            emitter.onComplete()
        }
        myFlowable = myObservable.toFlowable(BackpressureStrategy.BUFFER)

        myCompositeDisposable.add(
            myFlowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getSubscriber())
        )
    }

    private fun getSubscriber(): DisposableSubscriber<Student> {
        mySubscriber = object : DisposableSubscriber<Student>() {
            override fun onNext(t: Student?) {
                Log.i(TAG, "onNext Flowable: $t")
            }

            override fun onError(e: Throwable?) {
                Log.i(TAG, "onError Flowable: invoked $e")
            }

            override fun onComplete() {
                Log.i(TAG, "onComplete Flowable: invoked")
            }
        }
        return mySubscriber
    }

    private fun getStudents(): ArrayList<Student> {
        val students: ArrayList<Student> = ArrayList()
        val student1 = Student()
        student1.name = " student 1"
        student1.email = " student1@gmail.com "
        student1.age = 27
        students.add(student1)
        val student2 = Student()
        student2.name = " student 2"
        student2.email = " student2@gmail.com "
        student2.age = 20
        students.add(student2)
        val student3 = Student()
        student3.name = " student 3"
        student3.email = " student3@gmail.com "
        student3.age = 20
        students.add(student3)
        val student4 = Student()
        student4.name = " student 4"
        student4.email = " student4@gmail.com "
        student4.age = 20
        students.add(student4)
        val student5 = Student()
        student5.name = " student 5"
        student5.email = " student5@gmail.com "
        student5.age = 20
        students.add(student5)
        return students
    }

    override fun onDestroy() {
        super.onDestroy()

        myCompositeDisposable.clear() // We will clear compositeDisposable instead of disposing 2 disposable observers separately
    }
}