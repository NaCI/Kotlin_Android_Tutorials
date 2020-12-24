package com.test.rxjavaudemyclass

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChangeEvents
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


private const val TAG = "myApp"

class MainActivity : AppCompatActivity() {

    private lateinit var inputText: EditText
    private lateinit var viewText: TextView
    private lateinit var clearButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxbinding)

        inputText = findViewById(R.id.etInputField)
        viewText = findViewById(R.id.tvInput)
        clearButton = findViewById(R.id.btnClear)

//        uiInteractionWithoutRxBinding()

        uiInteractionWithRxBinding()

    }

    private fun uiInteractionWithRxBinding() {
        inputText.textChangeEvents()
            .subscribeOn(Schedulers.io())
            .filter {
                it.text.length > 2
            }
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewText.text = it.text
            }

        clearButton.clicks()
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe {
                inputText.setText(inputText.text.dropLast(1))
                inputText.setSelection(inputText.text.length)
                viewText.text = viewText.text.dropLast(1)
            }
    }

    private fun uiInteractionWithoutRxBinding() {
        inputText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewText.text = s
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        clearButton.setOnClickListener {
            inputText.setText("")
            viewText.text = ""
        }
    }
}