package uk.co.bubblebearapps.presentation.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import uk.co.bubblebearapps.presentation.R
import uk.co.bubblebearapps.presentation.databinding.LayoutErrorBinding
import uk.co.bubblebearapps.presentation.ui.ViewState

class ErrorItemBinder :
    FeedItemViewBinder<ViewState.Error, ErrorViewHolder>(
        ViewState.Error::class.java
    ) {

    override fun createViewHolder(parent: ViewGroup): ErrorViewHolder {
        return ErrorViewHolder(
            LayoutErrorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun bindViewHolder(
        model: ViewState.Error,
        viewHolder: ErrorViewHolder
    ) {
        viewHolder.bind(model)
    }

    override fun getFeedItemType(): Int = R.layout.layout_error

    override fun areItemsTheSame(
        oldItem: ViewState.Error,
        newItem: ViewState.Error
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: ViewState.Error,
        newItem: ViewState.Error
    ): Boolean {
        return oldItem.message == newItem.message
                && oldItem.action?.label.orEmpty() == newItem.action?.label.orEmpty()
    }

}

class ErrorViewHolder(
    private val binding: LayoutErrorBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(error: ViewState.Error) {
        with(binding) {

            errorText.text = error.message

            error.action?.let { action ->

                errorButton.text = action.label
                errorButton.setOnClickListener { action.invokable() }

                errorButton.isVisible = true
            } ?: kotlin.run { errorButton.isVisible = false }

        }
    }
}
