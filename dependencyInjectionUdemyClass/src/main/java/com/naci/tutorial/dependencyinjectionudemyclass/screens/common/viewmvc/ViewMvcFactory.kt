package com.naci.tutorial.dependencyinjectionudemyclass.screens.common.viewmvc

import android.view.LayoutInflater
import android.view.ViewGroup
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.imageloader.ImageLoader
import com.naci.tutorial.dependencyinjectionudemyclass.screens.questiondetails.QuestionDetailsViewMvc
import com.naci.tutorial.dependencyinjectionudemyclass.screens.questionslist.QuestionsListViewMvc
import javax.inject.Inject
import javax.inject.Provider

/**
 * Creating factories to instantiate objects with runtime parameters is a very general solution
 *
 * If a factory takes in a specific service, then this service should be safe to reused among all
 * the objects that a factory instantiates.
 * Otherwise, a factory should take in another factory and just delegate to another factory
 * whenever it need specific service.
 */
class ViewMvcFactory @Inject constructor(
    private val layoutInflater: LayoutInflater,
    private val imageLoaderProvider: Provider<ImageLoader>
) {

    fun newQuestionListViewMvc(parent: ViewGroup?): QuestionsListViewMvc {
        return QuestionsListViewMvc(layoutInflater, parent)
    }

    fun newQuestionDetailsViewMvc(parent: ViewGroup?): QuestionDetailsViewMvc {
        return QuestionDetailsViewMvc(layoutInflater, imageLoaderProvider.get(), parent)
    }
}