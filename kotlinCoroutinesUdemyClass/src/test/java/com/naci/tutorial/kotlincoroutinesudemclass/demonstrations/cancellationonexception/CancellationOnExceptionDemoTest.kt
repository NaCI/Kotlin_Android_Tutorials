package com.naci.tutorial.kotlincoroutinesudemclass.demonstrations.cancellationonexception

import kotlinx.coroutines.*
import org.junit.Test
import java.lang.RuntimeException

class CancellationOnExceptionDemoTest {

    @Test
    fun concurrentCoroutines() {
        runBlocking {
            val scopeJob = Job()
            val scope = CoroutineScope(scopeJob + Dispatchers.Default)
            val job1 = scope.launch {
                delay(100)
                println("inside coroutine")
            }
            val job2 = scope.launch {
                delay(50)
            }
            joinAll(job1, job2)
            println("scopeJob: $scopeJob")
            println("job1: $job1")
            println("job2: $job2")
        }
        Thread.sleep(100)
        println("test completed")
    }

    // Whenever an exception thrown all the jobs cancelling. So there is no "inside coroutine" text in the output
    // After job2 throws exception after 50 ms, at the time job1 couldn't finished it's job and cancels
    // in the middle middle of job
    @Test
    fun uncaughtExceptionInConcurrentCoroutines() {
        runBlocking {
            val scopeJob = Job()
            val scope = CoroutineScope(scopeJob + Dispatchers.Default)
            val job1 = scope.launch {
                delay(100)
                println("inside coroutine")
            }
            val job2 = scope.launch {
                delay(50)
                throw RuntimeException()
            }
            joinAll(job1, job2) // Wait for all jobs to be complete
            println("scopeJob: $scopeJob")
            println("job1: $job1")
            println("job2: $job2")
        }
        Thread.sleep(100)
        println("test completed")
    }


    // This test shows that job1 still cancels even we add CoroutineExceptionHandler to scope.
    // It didn't save scope from being cancel
    @Test
    fun uncaughtExceptionInConcurrentCoroutinesWithExceptionHandler() {
        runBlocking {
            val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
                println("Caught exception: $throwable")
            }

            val scopeJob = Job()
            val scope = CoroutineScope(scopeJob + Dispatchers.Default + coroutineExceptionHandler)
            val job1 = scope.launch {
                delay(100)
                println("inside coroutine")
            }
            val job2 = scope.launch {
                delay(50)
                throw RuntimeException()
            }
            joinAll(job1, job2)
            println("scopeJob: $scopeJob")
            println("job1: $job1")
            println("job2: $job2")
        }
        Thread.sleep(100)
        println("test completed")
    }

    // In order to resume scope even if any coroutines cancels inside of the scope use SupervisorJob
    // or NonCancellableJob
    @Test
    fun uncaughtExceptionInConcurrentCoroutinesWithSupervisorJob() {
        runBlocking {
            val scopeJob = SupervisorJob()
            val scope = CoroutineScope(scopeJob + Dispatchers.Default)
            val job1 = scope.launch {
                delay(100)
                println("inside coroutine")
            }
            val job2 = scope.launch {
                delay(50)
                throw RuntimeException()
            }
            joinAll(job1, job2)
            println("scopeJob: $scopeJob")
            println("job1: $job1")
            println("job2: $job2")
        }
        Thread.sleep(100)
        println("test completed")
    }

}