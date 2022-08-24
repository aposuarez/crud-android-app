package com.jmsuarez.crudsample.ui.splash

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jmsuarez.crudsample.CRUDApplication
import com.jmsuarez.crudsample.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashFragment : Fragment(R.layout.fragment_splash), CoroutineScope by MainScope() {

    @Inject
    lateinit var viewModel : SplashViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as CRUDApplication).appComponent.splashComponent().create()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch {
            navigate(viewModel.checkActiveSession())
        }
    }

    private suspend fun navigate(hasActiveSession:Boolean) {
        delay(viewModel.splashScreenDelay)
        if(hasActiveSession) findNavController().navigate(R.id.splashFragment_to_dashboardFragment)
        else findNavController().navigate(R.id.splashFragment_to_loginFragment)
    }
}