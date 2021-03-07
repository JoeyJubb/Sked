package uk.co.bubblebearapps.presentation.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import uk.co.bubblebearapps.domain.model.SplashDestination
import uk.co.bubblebearapps.presentation.base.BaseFragment
import uk.co.bubblebearapps.presentation.databinding.SplashFragmentBinding
import uk.co.bubblebearapps.presentation.ext.observe
import uk.co.bubblebearapps.presentation.ui.ViewState

class SplashFragment : BaseFragment() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    private lateinit var viewModel: SplashViewModel
    private lateinit var viewBinding: SplashFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = SplashFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SplashViewModel::class.java)
        observe(viewModel.getViewStateLiveData()) {
            onViewStateChanged(it)
        }
    }

    private fun onViewStateChanged(viewState: ViewState<SplashDestination>) {

        viewBinding.progress.hide()

        when (viewState) {
            is ViewState.Alert -> showAlert(viewState)
            is ViewState.Busy -> viewBinding.progress.show()
            is ViewState.Error -> showError(viewState)
            is ViewState.Ready -> onReady(viewState.data)
        }
    }

    private fun onReady(destination: SplashDestination) {
        when (destination) {
            SplashDestination.GoToSignIn -> TODO()
            SplashDestination.GoToTasksList -> findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToUserList())
        }
    }

}