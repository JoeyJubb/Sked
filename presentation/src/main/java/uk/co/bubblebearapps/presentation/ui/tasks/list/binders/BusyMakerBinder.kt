package uk.co.bubblebearapps.presentation.ui.tasks.list.binders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uk.co.bubblebearapps.presentation.R
import uk.co.bubblebearapps.presentation.ui.common.FeedItemViewBinder
import uk.co.bubblebearapps.presentation.ui.tasks.list.BusyMarker

class BusyMarkerBinder :
    FeedItemViewBinder<BusyMarker, BusyMarkerViewHolder>(
        BusyMarker::class.java
    ) {

    override fun createViewHolder(parent: ViewGroup): BusyMarkerViewHolder =
        BusyMarkerViewHolder(
            LayoutInflater.from(parent.context).inflate(getFeedItemType(), parent, false)
        )

    override fun bindViewHolder(
        model: BusyMarker,
        viewHolder: BusyMarkerViewHolder
    ) {
        // no op
    }

    override fun getFeedItemType(): Int = R.layout.tasks_list_fragment_item_loading

    override fun areItemsTheSame(
        oldItem: BusyMarker,
        newItem: BusyMarker
    ): Boolean = true

    override fun areContentsTheSame(
        oldItem: BusyMarker,
        newItem: BusyMarker
    ): Boolean = true

}

class BusyMarkerViewHolder(view: View) : RecyclerView.ViewHolder(view)