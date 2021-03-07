package com.naci.tutorial.dependencyinjectionudemyclass.screens.questionslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.naci.tutorial.dependencyinjectionudemyclass.R
import com.naci.tutorial.dependencyinjectionudemyclass.questions.Question
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.toolbar.MyToolbar
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.viewmvc.BaseViewMvc

class QuestionsListViewMvc(
    private val layoutInflater: LayoutInflater,
    private val parent: ViewGroup?
) : BaseViewMvc<QuestionsListViewMvc.Listener>(
    layoutInflater,
    parent,
    R.layout.layout_questions_list
) {

    interface Listener {
        fun onRefreshClicked()
        fun onQuestionClicked(question: Question)
        fun onViewModelClicked()
    }

    private val toolbar: MyToolbar
    private val swipeRefresh: SwipeRefreshLayout
    private val recyclerView: RecyclerView
    private val questionsAdapter: QuestionsAdapter

    init {
        toolbar = findViewById(R.id.toolbar)
        toolbar.setViewModelListener {
            for (listener in listeners) {
                listener.onViewModelClicked()
            }
        }

        // init pull-down-to-refresh
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            for (listener in listeners) {
                listener.onRefreshClicked()
            }
        }

        // init recycler view
        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        questionsAdapter = QuestionsAdapter { clickedQuestion ->
            for (listener in listeners) {
                listener.onQuestionClicked(clickedQuestion)
            }
        }
        recyclerView.adapter = questionsAdapter
    }

    fun bindQuestions(questions: List<Question>) {
        questionsAdapter.bindData(questions)
    }

    fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

    fun hideProgressIndication() {
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
    }
}