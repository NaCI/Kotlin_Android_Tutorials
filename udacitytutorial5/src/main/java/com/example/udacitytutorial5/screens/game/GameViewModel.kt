package com.example.udacitytutorial5.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.udacitytutorial5.BuzzType
import timber.log.Timber

class GameViewModel : ViewModel() {

    companion object {

        private const val DONE = 0L

        // This is the time when the phone will start buzzing each second
        private const val COUNTDOWN_PANIC_SECONDS = 10L

        private const val ONE_SECOND = 1000L

        private const val COUNTDOWM_TIME = 30000L
    }

    private val timer: CountDownTimer

    // The current word
    private var _word = MutableLiveData<String>("")

    // Encapsulation with backing property (Overriding get method)
    val word: LiveData<String>
        get() = _word

    // The current score
    private var _score = MutableLiveData<Int>()

    // Encapsulation with backing property (Overriding get method)
    val score: LiveData<Int>
        get() = _score

    private var _eventGameFinish = MutableLiveData<Boolean>(false)
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    private var _curentTime = MutableLiveData<Long>(0)
    val curentTime: LiveData<Long>
        get() = _curentTime

    val currentTimeText = Transformations.map(curentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    private var _buzzEvent = MutableLiveData<BuzzType>()
    val buzzEvent: LiveData<BuzzType>
        get() = _buzzEvent

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        Timber.i("GameViewModel created!")
        resetList()
        nextWord()
        _score.value = 0

        timer = object : CountDownTimer(COUNTDOWM_TIME, ONE_SECOND) {
            override fun onFinish() {
                _curentTime.value = DONE
                _buzzEvent.value = BuzzType.GAME_OVER
                _eventGameFinish.value = true
            }

            override fun onTick(millisUntilFinished: Long) {
                _curentTime.value = (millisUntilFinished / ONE_SECOND)
                if (millisUntilFinished / ONE_SECOND <= COUNTDOWN_PANIC_SECONDS) {
                    _buzzEvent.value = BuzzType.COUNTDOWN_PANIC
                }
            }
        }
        timer.start()
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.value = wordList.removeAt(0)

    }

    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value = score.value?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = score.value?.plus(1)
        nextWord()
    }

    fun onGameFinishCompleted() {
        _eventGameFinish.value = false
    }

    fun onBuzzCompleted() {
        _buzzEvent.value = BuzzType.NO_BUZZ
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        Timber.i("GameViewModel destroyed!")
    }
}