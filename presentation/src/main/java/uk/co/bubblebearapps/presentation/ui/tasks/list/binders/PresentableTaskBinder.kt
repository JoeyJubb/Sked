package uk.co.bubblebearapps.presentation.ui.tasks.list.binders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uk.co.bubblebearapps.presentation.R
import uk.co.bubblebearapps.presentation.databinding.TasksListFragmentItemBinding
import uk.co.bubblebearapps.presentation.ui.common.FeedItemViewBinder
import uk.co.bubblebearapps.presentation.ui.tasks.list.PresentableTask

class PresentableTaskBinder(private val onClick: (PresentableTask) -> Unit) :
    FeedItemViewBinder<PresentableTask, PresentableTaskViewHolder>(
        PresentableTask::class.java
    ) {

    override fun createViewHolder(parent: ViewGroup): PresentableTaskViewHolder {
        return PresentableTaskViewHolder(
            TasksListFragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick
        )
    }

    override fun bindViewHolder(
        model: PresentableTask,
        viewHolder: PresentableTaskViewHolder
    ) {
        viewHolder.bind(model)
    }

    override fun getFeedItemType(): Int = R.layout.tasks_list_fragment_item

    override fun areItemsTheSame(
        oldItem: PresentableTask,
        newItem: PresentableTask
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: PresentableTask,
        newItem: PresentableTask
    ): Boolean {
        // everything except the id is shown on the UI, so might as well compare the whole object
        return oldItem == newItem
    }

}

class PresentableTaskViewHolder(
    private val binding: TasksListFragmentItemBinding,
    private val onClick: (PresentableTask) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(task: PresentableTask) {

        binding.root.setOnClickListener { onClick(task) }

        with(binding) {
            textName.text = task.name
            textDescription.text = task.description
            imageTaskType.setImageResource(task.iconRes)
        }
    }
}
