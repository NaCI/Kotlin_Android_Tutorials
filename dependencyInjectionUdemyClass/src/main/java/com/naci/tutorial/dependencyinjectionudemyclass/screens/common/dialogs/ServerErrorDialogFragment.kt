package com.naci.tutorial.dependencyinjectionudemyclass.screens.common.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import com.naci.tutorial.dependencyinjectionudemyclass.R
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.ScreensNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ServerErrorDialogFragment : BaseDialog() {

    @Inject
    lateinit var screensNavigator: ScreensNavigator

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d("TAG", "onCreateDialog: $screensNavigator")
        return AlertDialog.Builder(activity).let {
            it.setTitle(R.string.server_error_dialog_title)
            it.setMessage(getString(R.string.server_error_dialog_message))
            it.setPositiveButton(R.string.server_error_dialog_button_caption) { _, _ -> dismiss() }
            it.create()
        }
    }

    companion object {
        fun newInstance(): ServerErrorDialogFragment {
            return ServerErrorDialogFragment()
        }
    }
}