package com.naci.tutorial.kotlincoroutinesudemclass.demonstrations.stucturedconcurrency

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.math.BigInteger

@ExperimentalCoroutinesApi
class FibonacciUseCaseUiCoroutinesTest {

    private lateinit var SUT: FibonacciUseCaseUiCoroutines

    @Before
    @Throws(Exception::class)
    fun setup() {
        SUT = FibonacciUseCaseUiCoroutines()
    }

    @Test
    @Throws(Exception::class)
    fun computeFibonacci_0_returns0() {
        runBlocking {
            // Arrange
            // Act
            val result = SUT.computeFibonacci(0)
            // Assert
            assertThat(result, CoreMatchers.`is`(BigInteger("0")))
        }
    }

    @Test
    @Throws(Exception::class)
    fun computeFibonacci_1_returns1() {
        runBlocking {
            // Arrange
            // Act
            val result = SUT.computeFibonacci(1)
            // Assert
            assertThat(result, CoreMatchers.`is`(BigInteger("1")))
        }
    }

    @Test
    @Throws(Exception::class)
    fun computeFibonacci_10_returnsCorrectAnswer() {
        runBlocking {
            // Arrange
            // Act
            val result = SUT.computeFibonacci(10)
            // Assert
            assertThat(result, CoreMatchers.`is`(BigInteger("55")))
        }
    }

    @Test
    @Throws(Exception::class)
    fun computeFibonacci_30_returnsCorrectAnswer() {
        runBlocking {
            // Arrange
            // Act
            val result = SUT.computeFibonacci(30)
            // Assert
            assertThat(result, CoreMatchers.`is`(BigInteger("832040")))
        }
    }
}