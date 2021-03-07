package uk.co.bubblebearapps.presentation.ext

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadUrl(string: String) {
    Glide.with(this)
            .load(string)
            .into(this)
}
