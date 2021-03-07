package uk.co.bubblebearapps.di

import dagger.Binds
import dagger.Module
import uk.co.bubblebearapps.presentation.util.*

@Module(
        includes = [
            FragmentBuilderModule::class,
            ViewModelModule::class
        ]
)
interface SharedActivityModule {

    @Binds
    fun resourcesWrapper(resourcesWrapperImpl: ResourcesWrapperImpl): ResourcesWrapper

    @Binds
    fun errorMessageFactory(errorMessageFactoryImpl: ErrorMessageFactoryImpl): ErrorMessageFactory

    @Binds
    fun datetimeFormatter(dateTimeFormatterImpl: DateTimeFormatterImpl): DateTimeFormatter

}
