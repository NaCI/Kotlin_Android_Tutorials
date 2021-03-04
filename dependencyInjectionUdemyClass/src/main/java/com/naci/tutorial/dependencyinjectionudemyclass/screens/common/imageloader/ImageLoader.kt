package com.naci.tutorial.dependencyinjectionudemyclass.screens.common.imageloader

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.activity.ActivityScope
import javax.inject.Inject

@ActivityScope
class ImageLoader @Inject constructor(private val activity: AppCompatActivity) {

    private val requestOptions = RequestOptions.circleCropTransform()

    fun loadImage(imgUrl: String, target: ImageView) {
        Glide.with(activity).load(imgUrl).apply(requestOptions).into(target)
    }
}