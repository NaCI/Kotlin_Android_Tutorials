package com.naci.tutorial.dependencyinjectionudemyclass.screens.questiondetails

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.naci.tutorial.dependencyinjectionudemyclass.R
import com.naci.tutorial.dependencyinjectionudemyclass.questions.QuestionWithBody
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.imageloader.ImageLoader
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.toolbar.MyToolbar
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.viewmvc.BaseViewMvc

class QuestionDetailsViewMvc(
    private val layoutInflater: LayoutInflater,
    private val imageLoader: ImageLoader,
    private val parent: ViewGroup?
) : BaseViewMvc<QuestionDetailsViewMvc.Listener>(
    layoutInflater,
    parent,
    R.layout.layout_question_details
) {

    interface Listener {
        fun onNavigateUpClicked()
    }

    private val toolbar: MyToolbar
    private val swipeRefresh: SwipeRefreshLayout
    private val txtQuestionBody: TextView
    private val txtUserName: TextView
    private val imgUser: ImageView

    init {
        txtQuestionBody = findViewById(R.id.txt_question_body)
        txtUserName = findViewById(R.id.txt_user_name)
        imgUser = findViewById(R.id.img_user)

        // init toolbar
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigateUpListener {
            for (listener in listeners) {
                listener.onNavigateUpClicked()
            }
        }

        // init pull-down-to-refresh (used as a progress indicator)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.isEnabled = false
    }

    fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

    fun hideProgressIndication() {
        swipeRefresh.isRefreshing = false
    }

    fun fillQuestionBodyText(questionBody: QuestionWithBody) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtQuestionBody.text =
                Html.fromHtml(questionBody.body, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            txtQuestionBody.text = Html.fromHtml(questionBody.body)
        }
        imageLoader.loadImage(questionBody.owner.imageUrl, imgUser)
        txtUserName.text = questionBody.owner.name
    }
}