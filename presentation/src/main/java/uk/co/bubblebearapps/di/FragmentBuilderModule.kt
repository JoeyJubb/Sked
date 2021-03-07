package uk.co.bubblebearapps.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import uk.co.bubblebearapps.presentation.ui.splash.SplashFragment
import uk.co.bubblebearapps.presentation.ui.tasks.list.TasksListFragment

@Module
interface FragmentBuilderModule {

    @FragmentScope
    @ContributesAndroidInjector
    fun splashFragment(): SplashFragment

    @FragmentScope
    @ContributesAndroidInjector
    fun taskListFragment(): TasksListFragment

}
