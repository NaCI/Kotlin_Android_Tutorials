package com.naci.tutorial.dependencyinjectionudemyclass.screens.viewmodel

import androidx.lifecycle.*
import com.naci.tutorial.dependencyinjectionudemyclass.questions.FetchQuestionsUseCase
import com.naci.tutorial.dependencyinjectionudemyclass.questions.Question
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class MyViewModel(
    private val fetchQuestionsUseCase: FetchQuestionsUseCase
) : ViewModel() {

    private val _questions = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>> = _questions

    init {
        viewModelScope.launch {
            val result = fetchQuestionsUseCase.fetchLatestQuestions()
            if (result is FetchQuestionsUseCase.Result.Success) {
                _questions.value = result.questions
            } else {
                throw RuntimeException("fetch failed")
            }
        }
    }

    class Factory @Inject constructor(
        private val fetchQuestionsUseCaseProvider: Provider<FetchQuestionsUseCase>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MyViewModel(fetchQuestionsUseCaseProvider.get()) as T
        }
    }
}