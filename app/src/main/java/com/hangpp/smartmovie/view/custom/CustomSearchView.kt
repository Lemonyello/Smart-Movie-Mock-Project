package com.hangpp.smartmovie.view.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.view.KeyEvent
import com.hangpp.smartmovie.R

class CustomSearchView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var textView: TextView
    private var editText: EditText
    var onEnterPressed: (searchWord: String) -> Unit = {}

    init {
        val view = inflate(context, R.layout.custom_search_view, this)

        textView = view.findViewById(R.id.btnCancel)
        editText = view.findViewById(R.id.etSearch)

        textView.setOnClickListener { editText.text = null }

        editText.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER)

        editText.setOnKeyListener { view, i, keyEvent ->
            if(keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER)
            {
                onEnterPressed(editText.text.toString().trim())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }
}