package uk.co.bubblebearapps.domain.model

/**
 * Possible destinations after the splash screen has finished getting the app ready
 */
sealed class SplashDestination {

    object GoToSignIn : SplashDestination()

    object GoToTasksList : SplashDestination()

}
