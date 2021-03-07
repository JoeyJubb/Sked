package uk.co.bubblebearapps.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uk.co.bubblebearapps.presentation.MainActivityViewModel
import uk.co.bubblebearapps.presentation.ui.splash.SplashViewModel
import uk.co.bubblebearapps.presentation.ui.tasks.list.TasksListViewModel

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    fun mainActivity(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    fun splash(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TasksListViewModel::class)
    fun tasksList(viewModel: TasksListViewModel): ViewModel

}