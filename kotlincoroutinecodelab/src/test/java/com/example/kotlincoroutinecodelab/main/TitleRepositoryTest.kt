/*
 * Copyright (C) 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.kotlincoroutinecodelab.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kotlincoroutinecodelab.fakes.MainNetworkCompletableFake
import com.example.kotlincoroutinecodelab.fakes.MainNetworkFake
import com.example.kotlincoroutinecodelab.fakes.TitleDaoFake
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

class TitleRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Test
    fun whenRefreshTitleSuccess_insertsRows() = runBlockingTest {
        // TODO: Write this test
        val titleDao = TitleDaoFake("title")
        val subject = TitleRepository(
            MainNetworkFake("SomeNetworkResult"),
            titleDao
        )
        subject.refreshTitle()
        Truth.assertThat(titleDao.nextInsertedOrNull()).isEqualTo("SomeNetworkResult")
    }

    @ExperimentalCoroutinesApi
    @Test(expected = TitleRefreshError::class)
    fun whenRefreshTitleTimeout_throws() = runBlockingTest {
        // TODO: Write this test
        val network = MainNetworkCompletableFake()
        val subject = TitleRepository(
            network,
            TitleDaoFake("title")
        )
        launch {
            subject.refreshTitle()
        }

        advanceTimeBy(5_000)

        //throw TitleRefreshError("Remove this – made test pass in starter code", null)
    }
}