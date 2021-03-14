package com.naci.tutorial.dependencyinjectionudemyclass.screens.viewmodel

import androidx.lifecycle.*
import com.naci.tutorial.dependencyinjectionudemyclass.questions.FetchQuestionDetailsUseCase
import com.naci.tutorial.dependencyinjectionudemyclass.questions.FetchQuestionsUseCase
import com.naci.tutorial.dependencyinjectionudemyclass.questions.Question
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val fetchQuestionsUseCase: FetchQuestionsUseCase,
    private val fetchQuestionDetailsUseCase: FetchQuestionDetailsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _questions: MutableLiveData<List<Question>> =
        savedStateHandle.getLiveData("questions")
    val questions: LiveData<List<Question>> get() = _questions

    init {
        viewModelScope.launch {
            delay(5000)
            val result = fetchQuestionsUseCase.fetchLatestQuestions()
            if (result is FetchQuestionsUseCase.Result.Success) {
                _questions.value = result.questions
            } else {
                throw RuntimeException("fetch failed")
            }
        }
    }
}