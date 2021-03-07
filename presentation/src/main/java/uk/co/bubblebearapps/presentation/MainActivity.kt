package uk.co.bubblebearapps.presentation

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber
import uk.co.bubblebearapps.presentation.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: MainActivityViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        attachNavFragment()
        attachViewModel()
    }

    private fun attachNavFragment() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun attachViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)

        viewModel.getNetworkConnectionLiveData().observe(this) { deviceIsConnected ->
            with(binding.textViewOnlineStatus) {
                if (deviceIsConnected) {
                    if (isVisible) {
                        text = getString(R.string.online_indicator_back_online)
                        postDelayed({ isVisible = false }, 1000)
                    }

                } else {
                    text = getString(R.string.online_indicator_offline)
                    isVisible = true
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
