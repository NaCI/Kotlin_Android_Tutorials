package com.naci.tutorial.kotlincoroutinesudemclass.demonstrations.stucturedconcurrency

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.math.BigInteger

@ExperimentalCoroutinesApi
class FibonacciUseCaseAsyncUiCoroutinesTest {

    private lateinit var mCallback: FibonacciUseCaseAsyncUiCoroutines.Callback
    private lateinit var SUT: FibonacciUseCaseAsyncUiCoroutines
    private lateinit var testCoroutineDispatcher: TestCoroutineDispatcher

    var lastResult: BigInteger? = null


    @Before
    @Throws(Exception::class)
    fun setup() {
        testCoroutineDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(testCoroutineDispatcher)
        mCallback = object : FibonacciUseCaseAsyncUiCoroutines.Callback {
            override fun onFibonacciComputed(result: BigInteger?) {
                lastResult = result
            }
        }
        SUT = FibonacciUseCaseAsyncUiCoroutines(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    @Throws(Exception::class)
    fun computeFibonacci_0_returns0() {
        testCoroutineDispatcher.runBlockingTest {
            // Arrange
            // Act
            SUT.computeFibonacci(0, mCallback)
            // Assert
            assertThat(lastResult, CoreMatchers.`is`(BigInteger("0")))
        }
    }

    @Test
    @Throws(Exception::class)
    fun computeFibonacci_1_returns1() {
        testCoroutineDispatcher.runBlockingTest {
            // Arrange
            // Act
            SUT.computeFibonacci(1, mCallback)
            // Assert
            assertThat(lastResult, CoreMatchers.`is`(BigInteger("1")))
        }
    }

    @Test
    @Throws(Exception::class)
    fun computeFibonacci_10_returnsCorrectAnswer() {
        testCoroutineDispatcher.runBlockingTest {
            // Arrange
            // Act
            SUT.computeFibonacci(10, mCallback)
            // Assert
            assertThat(lastResult, CoreMatchers.`is`(BigInteger("55")))
        }
    }

    @Test
    @Throws(Exception::class)
    fun computeFibonacci_30_returnsCorrectAnswer() {
        testCoroutineDispatcher.runBlockingTest {
            // Arrange
            // Act
            SUT.computeFibonacci(30, mCallback)
            // Assert
            assertThat(lastResult, CoreMatchers.`is`(BigInteger("832040")))
        }
    }

}