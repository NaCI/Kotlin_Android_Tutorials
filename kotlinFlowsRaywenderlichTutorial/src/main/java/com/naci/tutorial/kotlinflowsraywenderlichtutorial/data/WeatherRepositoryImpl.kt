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

package com.naci.tutorial.kotlinflowsraywenderlichtutorial.data

import com.naci.tutorial.kotlinflowsraywenderlichtutorial.data.db.dao.ForecastDao
import com.naci.tutorial.kotlinflowsraywenderlichtutorial.data.db.mapper.DbMapper
import com.naci.tutorial.kotlinflowsraywenderlichtutorial.data.network.client.WeatherApiClient
import com.naci.tutorial.kotlinflowsraywenderlichtutorial.data.network.mapper.ApiMapper
import com.naci.tutorial.kotlinflowsraywenderlichtutorial.domain.model.Location
import com.naci.tutorial.kotlinflowsraywenderlichtutorial.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val weatherApiClient: WeatherApiClient,
    private val apiMapper: ApiMapper,
    private val backgroundDispatcher: CoroutineDispatcher,
    private val forecastDao: ForecastDao,
    private val dbMapper: DbMapper
) : WeatherRepository {

    override fun getForecasts() =
        forecastDao
            .getForecasts()
            .map {
                dbMapper.mapDbForecastsToDomain(it)
//                throw IllegalStateException("nope")
            }

    override suspend fun findLocation(location: String) = withContext(backgroundDispatcher) {
        try {
            weatherApiClient.findLocation(location)
                .map(apiMapper::mapApiLocationToDomain)
        } catch (error: Throwable) {
            emptyList<Location>()
        }
    }

    override suspend fun fetchLocationDetails(id: Int) = withContext(backgroundDispatcher) {
        val locationDetails = try {
            apiMapper.mapApiLocationDetailsToDomain(
                weatherApiClient.getLocationDetails(id)
            )
        } catch (error: Throwable) {
            null
        }

        if (locationDetails != null) {
            forecastDao.updateLocationDetails(dbMapper.mapDomainLocationDetailsToDb(locationDetails))
            forecastDao.updateForecasts(dbMapper.mapDomainForecastsToDb(locationDetails.forecasts))
        }
    }
}