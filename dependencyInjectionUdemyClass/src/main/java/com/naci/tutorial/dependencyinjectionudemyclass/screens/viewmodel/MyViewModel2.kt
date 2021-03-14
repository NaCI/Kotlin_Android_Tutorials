package com.naci.tutorial.dependencyinjectionudemyclass.screens.viewmodel

import androidx.lifecycle.*
import com.naci.tutorial.dependencyinjectionudemyclass.questions.FetchQuestionDetailsUseCase
import com.naci.tutorial.dependencyinjectionudemyclass.questions.QuestionWithBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel2 @Inject constructor(
    private val fetchQuestionDetailsUseCase: FetchQuestionDetailsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _questionWithBody: MutableLiveData<QuestionWithBody> =
        savedStateHandle.getLiveData("questionWithBody")
    val questionWithBody: LiveData<QuestionWithBody> = _questionWithBody

    fun getQuestionDetail(questionId: String) {
        viewModelScope.launch {
            val result = fetchQuestionDetailsUseCase.fetchQuestionDetails(questionId = questionId)
            if (result is FetchQuestionDetailsUseCase.Result.Success) {
                _questionWithBody.value = result.questionWithBody
            } else {
                throw RuntimeException("fetch failed")
            }
        }
    }
}