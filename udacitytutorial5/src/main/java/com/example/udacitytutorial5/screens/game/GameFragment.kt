/*
 * Copyright 2018, The Android Open Source Project
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

package com.example.udacitytutorial5.screens.game

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.udacitytutorial5.BuzzType
import com.example.udacitytutorial5.R
import com.example.udacitytutorial5.databinding.GameFragmentBinding
import timber.log.Timber

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel

    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.game_fragment,
            container,
            false
        )

        Timber.i("Called ViewModelProvider")
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding.viewModel = viewModel
        /**
        * This code allows us to use live data to automatically update layout
         */
        binding.lifecycleOwner = viewLifecycleOwner

        /**
         * We called the onClick methods directly on xml, so we don't need the methods below
         */
//        binding.correctButton.setOnClickListener {
//            viewModel.onCorrect()
//        }
//        binding.skipButton.setOnClickListener {
//            viewModel.onSkip()
//        }

        /**
        * Since we are using live data on xml we don't need to observe them here
         */
//        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
//            binding.scoreText.text = newScore.toString()
//        })
//
//        viewModel.word.observe(viewLifecycleOwner, Observer { newWord ->
//            binding.wordText.text = newWord.toString()
//        })

        /**
         * Instead of code below we are directly called live data from xml
         * We are using Tansformations.map function to convert one live data to another
         * Here we taking Long live data and convert it to String
         * More complex Transformations are switchMap and MediatorLiveData
         */
//        viewModel.curentTime.observe(viewLifecycleOwner, Observer { newTime ->
//            binding.timerText.text = DateUtils.formatElapsedTime(newTime)
//        })

        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished) {
                gameFinished()
                viewModel.onGameFinishCompleted()
            }
        })

        viewModel.buzzEvent.observe(viewLifecycleOwner, Observer { buzzType ->
            if (buzzType != BuzzType.NO_BUZZ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    buzz(buzzType.pattern)
                }
                viewModel.onBuzzCompleted()
            }
        })

        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun buzz(pattern: LongArray) {
        val buzzer = activity?.getSystemService(Vibrator::class.java)

        buzzer?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                //deprecated in API 26
                buzzer.vibrate(pattern, -1)
            }
        }
    }

    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
//        Toast.makeText(requireActivity(), "Game has finished", Toast.LENGTH_SHORT).show()
        val action = GameFragmentDirections.actionGameToScore(viewModel.score.value ?: 0)
        findNavController(this).navigate(action)
    }
}
