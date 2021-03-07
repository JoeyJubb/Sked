package uk.co.bubblebearapps.presentation.util

import android.content.Context
import androidx.core.content.ContextCompat
import uk.co.bubblebearapps.di.ActivityContext
import javax.inject.Inject


class ResourcesWrapperImpl @Inject constructor(@ActivityContext val context: Context) :
        ResourcesWrapper {

    private val resources = context.resources

    override fun getString(ref: Int): String = resources.getString(ref)

    override fun getColor(colorRes: Int): Int = ContextCompat.getColor(context, colorRes)

}
