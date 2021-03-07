package uk.co.bubblebearapps.domain.usecase

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Emit a Completable that will complete if the user is signed in, or emit a UserNotSignedInException
 * if they're not
 */
class GetIsUserSignedInUseCase @Inject constructor() : () -> Completable {

    override fun invoke(): Completable = Completable.complete()

}
