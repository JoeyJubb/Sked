package uk.co.bubblebearapps.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import org.threeten.bp.Clock
import uk.co.bubblebearapps.sked.App

@Module(
        includes = [
            ProvidesAppModule::class
        ]
)
interface AppModule {

    @Binds
    @ApplicationContext
    fun context(app: App): Context

}

@Module
class ProvidesAppModule {

    @Provides
    @Reusable
    fun provideClock(): Clock = Clock.systemUTC()

}
