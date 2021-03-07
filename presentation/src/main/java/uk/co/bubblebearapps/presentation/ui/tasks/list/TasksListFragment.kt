package uk.co.bubblebearapps.presentation.ui.tasks.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButtonToggleGroup
import uk.co.bubblebearapps.domain.model.TaskType
import uk.co.bubblebearapps.presentation.R
import uk.co.bubblebearapps.presentation.base.BaseFragment
import uk.co.bubblebearapps.presentation.databinding.TasksListFragmentBinding
import uk.co.bubblebearapps.presentation.ext.observe
import uk.co.bubblebearapps.presentation.ui.ViewState
import uk.co.bubblebearapps.presentation.ui.common.FeedAdapter
import uk.co.bubblebearapps.presentation.ui.common.FeedItemBinder
import uk.co.bubblebearapps.presentation.ui.tasks.list.binders.BusyMarkerBinder
import uk.co.bubblebearapps.presentation.ui.tasks.list.binders.EmptyMarkerBinder
import uk.co.bubblebearapps.presentation.ui.tasks.list.binders.PresentableTaskBinder

class TasksListFragment : BaseFragment() {

    private lateinit var filterCheckedListener: MaterialButtonToggleGroup.OnButtonCheckedListener
    private lateinit var viewModel: TasksListViewModel
    private lateinit var binding: TasksListFragmentBinding
    private lateinit var adapter: FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        adapter =
            FeedAdapter(
                PresentableTaskBinder { viewModel.onTaskClicked(it) } as FeedItemBinder,
                BusyMarkerBinder() as FeedItemBinder,
                EmptyMarkerBinder() as FeedItemBinder,
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TasksListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            recyclerView.adapter = adapter
        }

        filterCheckedListener =
            MaterialButtonToggleGroup.OnButtonCheckedListener { group, _, _ ->
                group
                    .checkedButtonIds
                    .mapNotNull { id ->
                        when (id) {
                            R.id.filter_button_general -> TaskType.GENERAL
                            R.id.filter_button_hydration -> TaskType.HYDRATION
                            R.id.filter_button_medication -> TaskType.MEDICATION
                            R.id.filter_button_nutrition -> TaskType.NUTRITION
                            else -> null
                        }
                    }
                    .toSet()
                    .let { filterSet ->
                        viewModel.onFilterChanged(filterSet)
                    }
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(TasksListViewModel::class.java)
        observe(viewModel.getViewStateLiveData()) { onViewStateChanged(it) }
        if (savedInstanceState == null) {
            viewModel.init()
        }
    }

    private fun onViewStateChanged(viewState: ViewState<TasksListViewState>) {

        binding.filterToggleGroup.isVisible = false

        when (viewState) {
            is ViewState.Ready -> {
                adapter.submitList(viewState.data.tasksList.takeIf { it.isNotEmpty() } ?: listOf(
                    EmptyMarker
                ))
                bindFilterToggles(viewState.data.filters)
            }
            is ViewState.Busy -> {
                adapter.submitList(
                    (0..20).map { BusyMarker }
                )
            }
            is ViewState.Error -> {
                adapter.submitList(
                    listOf(
                        viewState
                    )
                )
            }
            is ViewState.Alert -> showAlert(viewState)
        }
    }

    private fun bindFilterToggles(filters: Set<TaskType>) {

        with(binding.filterToggleGroup) {
            removeOnButtonCheckedListener(filterCheckedListener)

            clearChecked()

            filters.map { taskType ->
                when (taskType) {
                    TaskType.GENERAL -> R.id.filter_button_general
                    TaskType.MEDICATION -> R.id.filter_button_medication
                    TaskType.HYDRATION -> R.id.filter_button_hydration
                    TaskType.NUTRITION -> R.id.filter_button_nutrition
                }
            }.forEach { buttonId ->
                check(buttonId)
            }

            addOnButtonCheckedListener(filterCheckedListener)

            isVisible = true
        }
    }
}
