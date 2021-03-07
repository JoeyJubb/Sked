package uk.co.bubblebearapps.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import uk.co.bubblebearapps.presentation.MainActivity
import uk.co.bubblebearapps.presentation.MainActivityModule

@Module
interface ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    fun contributeMainActivity(): MainActivity
}
