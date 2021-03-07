package uk.co.bubblebearapps.presentation.util

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes

interface ResourcesWrapper {

    fun getString(@StringRes ref: Int): String

    @ColorInt
    fun getColor(@ColorRes colorRes: Int): Int

}
