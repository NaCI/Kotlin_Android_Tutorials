/*
 * Copyright (c) 2020 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.naci.tutorial.kotlinflowsraywenderlichtutorial.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.LinearSnapHelper
import com.naci.tutorial.kotlinflowsraywenderlichtutorial.databinding.ActivityHomeBinding
import com.naci.tutorial.kotlinflowsraywenderlichtutorial.util.image_loader.ImageLoader
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val imageLoader: ImageLoader by inject()
    private val homeViewModel by viewModel<HomeViewModel>()

    private val forecastAdapter by lazy { ForecastAdapter(layoutInflater, imageLoader) }
    private val locationAdapter by lazy { LocationAdapter(layoutInflater, ::onLocationClick) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
    }

    private fun initUi() {
        initSearchBar()
        initRecyclerView()
        initObservers()
        fetchLocationDetails(851128)
    }

    private fun fetchLocationDetails(cityId: Int) {
        homeViewModel.fetchLocationDetails(cityId)
    }

    private fun initSearchBar() {
        binding.locationsList.adapter = locationAdapter

        binding.search.isActivated = true
        binding.search.onActionViewExpanded()
        binding.search.isIconified = true
        binding.search.clearFocus()

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                homeViewModel.queryChannel.offer(newText)
                return false
            }
        })
    }

    private fun initRecyclerView() {
        binding.forecastList.layoutManager = LinearLayoutManager(this, HORIZONTAL, false)
        binding.forecastList.adapter = forecastAdapter

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.forecastList)
    }

    private fun initObservers() {
        homeViewModel.forecasts.observe(this, {
            forecastAdapter.setData(it)
        })
        homeViewModel.locations.observe(this, {
            locationAdapter.setData(it)
        })
    }

    private fun onLocationClick(locationViewState: LocationViewState) {
        homeViewModel.queryChannel.offer("")
        homeViewModel.fetchLocationDetails(locationViewState.id)
        binding.locationTitle.text = locationViewState.location
    }
}
