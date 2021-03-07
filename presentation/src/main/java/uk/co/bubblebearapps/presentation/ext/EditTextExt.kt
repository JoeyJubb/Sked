package uk.co.bubblebearapps.presentation.ext

import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun EditText.onImeActionSearch(callback: (CharSequence) -> Unit) {
    setOnEditorActionListener { textView, actionId, _ ->
        return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            callback.invoke(textView.text)
            textView.hideKeyboard()
            true
        } else {
            false
        }
    }
}

