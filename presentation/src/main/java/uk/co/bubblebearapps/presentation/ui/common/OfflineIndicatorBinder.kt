package uk.co.bubblebearapps.presentation.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uk.co.bubblebearapps.presentation.R

class OfflineIndicatorBinder :
    FeedItemViewBinder<OfflineIndicator, OfflineIndicatorViewHolder>(
        OfflineIndicator::class.java
    ) {

    override fun createViewHolder(parent: ViewGroup): OfflineIndicatorViewHolder {
        return OfflineIndicatorViewHolder(
            LayoutInflater.from(parent.context).inflate(
                getFeedItemType(), parent, false
            )
        )
    }

    override fun getFeedItemType(): Int = R.layout.layout_offline

    override fun bindViewHolder(model: OfflineIndicator, viewHolder: OfflineIndicatorViewHolder) {
        // no op
    }

    override fun areItemsTheSame(oldItem: OfflineIndicator, newItem: OfflineIndicator): Boolean = true

    override fun areContentsTheSame(oldItem: OfflineIndicator, newItem: OfflineIndicator): Boolean = true
}

object OfflineIndicator


class OfflineIndicatorViewHolder(view: View) :
    RecyclerView.ViewHolder(view)