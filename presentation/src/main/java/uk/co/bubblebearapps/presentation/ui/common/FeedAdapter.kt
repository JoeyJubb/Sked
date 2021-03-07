package uk.co.bubblebearapps.presentation.ui.common

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

internal typealias FeedItemClass = Class<out Any>
internal typealias FeedItemBinder = FeedItemViewBinder<Any, RecyclerView.ViewHolder>


/**
 * Implement multiple view-types in the recyclerview in a reusable way
 *
 * @see https://medium.com/android-dev-hacks/building-a-reactive-heterogeneous-adapter-in-kotlin-eed9487df29b
 */
class FeedAdapter(private val viewBinders: Map<FeedItemClass, FeedItemBinder>) :
    ListAdapter<Any, RecyclerView.ViewHolder>(FeedDiffCallback(viewBinders)) {

    companion object {

        @Suppress("UNCHECKED_CAST")
        private val COMMON_BINDERS: List<FeedItemBinder> = listOf(
            ErrorItemBinder() as FeedItemBinder,
            OfflineIndicatorBinder() as FeedItemBinder
        )
    }

    constructor(vararg viewBinders: FeedItemBinder) : this(
        viewBinders
            .asList()
            .plus(COMMON_BINDERS)
            .groupBy { it.modelClass }
            .mapValues { it.value.first() }
    )


    private val viewTypeToBinders = viewBinders.mapKeys { it.value.getFeedItemType() }

    private fun getViewBinder(viewType: Int): FeedItemBinder = viewTypeToBinders.getValue(viewType)

    override fun getItemViewType(position: Int): Int =
        viewBinders.getValue(super.getItem(position).javaClass).getFeedItemType()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewBinder(viewType).createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return getViewBinder(getItemViewType(position)).bindViewHolder(getItem(position), holder)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        getViewBinder(holder.itemViewType).onViewRecycled(holder)
        super.onViewRecycled(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        getViewBinder(holder.itemViewType).onViewDetachedFromWindow(holder)
        super.onViewDetachedFromWindow(holder)
    }

}

abstract class FeedItemViewBinder<M, in VH : RecyclerView.ViewHolder>(
    val modelClass: Class<out M>
) : DiffUtil.ItemCallback<M>() {

    abstract fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    abstract fun bindViewHolder(model: M, viewHolder: VH)
    abstract fun getFeedItemType(): Int

    // Having these as non abstract because not all the viewBinders are required to implement them.
    open fun onViewRecycled(viewHolder: VH) = Unit
    open fun onViewDetachedFromWindow(viewHolder: VH) = Unit
}

class FeedDiffCallback(
    private val viewBinders: Map<FeedItemClass, FeedItemBinder>
) : DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem::class != newItem::class) {
            return false
        }
        return viewBinders[oldItem::class.java]?.areItemsTheSame(oldItem, newItem) ?: false
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return viewBinders[oldItem::class.java]?.areContentsTheSame(oldItem, newItem) ?: false
    }
}