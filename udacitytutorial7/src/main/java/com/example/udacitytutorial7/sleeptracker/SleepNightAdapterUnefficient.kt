/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.udacitytutorial7.sleeptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.udacitytutorial7.R
import com.example.udacitytutorial7.convertDurationToFormatted
import com.example.udacitytutorial7.convertNumericQualityToString
import com.example.udacitytutorial7.database.SleepNight

class SleepNightAdapterUnefficient: RecyclerView.Adapter<SleepNightAdapterUnefficient.ViewHolder>() {
    var data = listOf<SleepNight>()
        set(value) {
            field = value
            /*
            Using notifyDataSetChanged method is not efficient
            It's because notifyDataSetChanged redraw all the items in the list
            Instead we can use DiffUtil to detect exact changes on the list and update just those items
             */
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    /**
     * It's important to know that viewholder responsible to know
     *  - what layout it is going to be use to inflate the view
     *  - update it's view according to data
     * So we create "bind" and "from" methods inside of ViewHolder
     * It is called as encapsulation of viewholder's logic, which helps us seperate the concerns
     */
    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val sleepLength: TextView = itemView.findViewById(R.id.sleep_length)
        val quality: TextView = itemView.findViewById(R.id.quality_string)
        val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)

        fun bind(item: SleepNight) {
            val res = itemView.context.resources
            //sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            quality.text = convertNumericQualityToString(item.sleepQuality, res)

            qualityImage.setImageResource(
                when (item.sleepQuality) {
                    0 -> R.drawable.ic_sleep_0
                    1 -> R.drawable.ic_sleep_1
                    2 -> R.drawable.ic_sleep_2
                    3 -> R.drawable.ic_sleep_3
                    4 -> R.drawable.ic_sleep_4
                    5 -> R.drawable.ic_sleep_5
                    else -> R.drawable.ic_sleep_active
                }
            )
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_sleep_night, parent, false)
                return ViewHolder(view)
            }
        }
    }
}