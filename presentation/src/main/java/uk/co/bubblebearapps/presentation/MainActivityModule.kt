package uk.co.bubblebearapps.presentation

import android.content.Context
import dagger.Binds
import dagger.Module
import uk.co.bubblebearapps.di.ActivityContext
import uk.co.bubblebearapps.di.SharedActivityModule

@Module(
        includes = [SharedActivityModule::class]
)
abstract class MainActivityModule {

    @Binds
    @ActivityContext
    abstract fun context(mainActivity: MainActivity): Context
}
