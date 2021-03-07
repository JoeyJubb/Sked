package uk.co.bubblebearapps.presentation.ext

import android.content.res.Resources
import android.graphics.drawable.InsetDrawable
import android.util.LayoutDirection
import androidx.recyclerview.widget.*

val RecyclerView.orientation: Int
    get() {
        with(layoutManager) {
            return when (this) {
                is LinearLayoutManager -> orientation
                is GridLayoutManager -> orientation
                is StaggeredGridLayoutManager -> orientation
                else -> RecyclerView.VERTICAL
            }
        }
    }

val RecyclerView.ViewHolder.resources: Resources
    get() = itemView.resources

fun RecyclerView.addDivider(
        insetStart: Int = 0,
        insetTop: Int = 0,
        insetEnd: Int = 0,
        insetBottom: Int = 0
) {
    addItemDecoration(
            DividerItemDecoration(context, orientation).also {
                if (insetStart > 0 || insetTop > 0 || insetEnd > 0 || insetBottom > 0) {

                    val layoutDirection = resources.configuration.layoutDirection
                    val insetLeft = if (layoutDirection == LayoutDirection.LTR) insetStart else insetEnd
                    val insetRight =
                            if (layoutDirection == LayoutDirection.RTL) insetStart else insetEnd

                    it.setDrawable(
                            InsetDrawable(
                                    it.drawable,
                                    insetLeft,
                                    insetTop,
                                    insetRight,
                                    insetBottom
                            )
                    )
                }
            }
    )
}
