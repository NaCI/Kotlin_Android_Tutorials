package com.naci.tutorial.dependencyinjectionudemyclass.screens.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naci.tutorial.dependencyinjectionudemyclass.questions.FetchQuestionDetailsUseCase
import com.naci.tutorial.dependencyinjectionudemyclass.questions.QuestionWithBody
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyViewModel2 @Inject constructor(
    private val fetchQuestionDetailsUseCase: FetchQuestionDetailsUseCase
) : ViewModel() {

    private val _questionWithBody = MutableLiveData<QuestionWithBody>()
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