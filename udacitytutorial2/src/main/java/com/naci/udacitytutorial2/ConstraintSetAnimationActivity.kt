package com.naci.udacitytutorial2

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.animation.AnticipateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class ConstraintSetAnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_set_animation_start)
        startAnimationAfterInit()
    }

    private fun startAnimationAfterInit() {
        val mainThreadHandler = Handler(Looper.getMainLooper())

        mainThreadHandler.postDelayed({
            val rootLayout = findViewById<ConstraintLayout>(R.id.rootLayout)

            val transition = ChangeBounds()
            transition.interpolator = AnticipateInterpolator(1.0f)

            val constraintSet = ConstraintSet()
            constraintSet.clone(baseContext, R.layout.activity_constraint_set_animation)
            TransitionManager.beginDelayedTransition(rootLayout, transition)
            constraintSet.applyTo(rootLayout)
        }, 1000)
    }
}
