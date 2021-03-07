package uk.co.bubblebearapps.presentation.ext

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

fun <T> Fragment.observe(
        liveData: LiveData<T>,
        function: (T) -> Unit
) {
    liveData.observe(viewLifecycleOwner, { function(it) })
}