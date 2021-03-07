package uk.co.bubblebearapps.presentation.ui.tasks.list.binders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uk.co.bubblebearapps.presentation.R
import uk.co.bubblebearapps.presentation.ui.common.FeedItemViewBinder
import uk.co.bubblebearapps.presentation.ui.tasks.list.EmptyMarker

class EmptyMarkerBinder :
    FeedItemViewBinder<EmptyMarker, EmptyMarkerViewHolder>(
        EmptyMarker::class.java
    ) {

    override fun createViewHolder(parent: ViewGroup): EmptyMarkerViewHolder =
        EmptyMarkerViewHolder(
            LayoutInflater.from(parent.context).inflate(getFeedItemType(), parent, false)
        )

    override fun bindViewHolder(
        model: EmptyMarker,
        viewHolder: EmptyMarkerViewHolder
    ) {
        // no op
    }

    override fun getFeedItemType(): Int = R.layout.tasks_list_fragment_item_empty

    override fun areItemsTheSame(
        oldItem: EmptyMarker,
        newItem: EmptyMarker
    ): Boolean = true

    override fun areContentsTheSame(
        oldItem: EmptyMarker,
        newItem: EmptyMarker
    ): Boolean = true

}

class EmptyMarkerViewHolder(view: View) :
    RecyclerView.ViewHolder(view)