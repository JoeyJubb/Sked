package uk.co.bubblebearapps.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import uk.co.bubblebearapps.sked.App
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AppModule::class,
            DataModule::class,
            ActivityBuilderModule::class,
            AndroidSupportInjectionModule::class
        ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }
}
