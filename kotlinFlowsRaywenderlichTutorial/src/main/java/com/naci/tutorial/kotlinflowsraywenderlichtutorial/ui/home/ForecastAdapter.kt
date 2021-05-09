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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naci.tutorial.kotlinflowsraywenderlichtutorial.databinding.ForecastListItemBinding
import com.naci.tutorial.kotlinflowsraywenderlichtutorial.util.image_loader.ImageLoader

class ForecastAdapter(
    private val layoutInflater: LayoutInflater,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    private val forecasts: MutableList<ForecastViewState> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding = ForecastListItemBinding.inflate(layoutInflater, parent, false)
        return ForecastViewHolder(binding, imageLoader)
    }

    override fun getItemCount() = forecasts.size

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) =
        holder.setItem(forecasts[position])

    fun setData(forecasts: List<ForecastViewState>) {
        this.forecasts.clear()
        this.forecasts.addAll(forecasts)
        notifyDataSetChanged()
    }

    class ForecastViewHolder(
        val binding: ForecastListItemBinding,
        private val imageLoader: ImageLoader
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setItem(forecast: ForecastViewState) = with(itemView) {

            imageLoader.load(forecast.iconUrl, binding.icon)

            binding.currentTemp.text = forecast.temp
            binding.shortDescription.text = forecast.state
            binding.date.text = forecast.date
            binding.windSpeed.text = forecast.windSpeed
            binding.airPressure.text = forecast.pressure
            binding.humidity.text = forecast.humidity
            binding.visibilityDistance.text = forecast.visibility
            binding.predictability.text = forecast.predictability
            binding.minMaxTemp.text = forecast.minMaxTemp
        }
    }
}
