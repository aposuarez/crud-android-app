package com.jmsuarez.crudsample.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jmsuarez.crudsample.CRUDApplication
import com.jmsuarez.crudsample.R
import com.jmsuarez.crudsample.databinding.FragmentLoginBinding
import com.jmsuarez.crudsample.ui.registration.RegistrationState
import io.uniflow.android.livedata.onEvents
import io.uniflow.android.livedata.onStates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.ldralighieri.corbind.widget.textChanges
import javax.inject.Inject

class LoginFragment : Fragment(R.layout.fragment_login), CoroutineScope by MainScope() {

    @Inject
    lateinit var loginViewModel : LoginViewModel

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as CRUDApplication).appComponent.loginComponent().create()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch {
            bindViews()
        }

        binding.loginButton.setOnClickListener {
            launch {
                loginViewModel.loginEmail(binding.emailEditText.text.toString(),binding.passwordEditText.text.toString())
            }
        }

        binding.registrationTextView.setOnClickListener {
            findNavController().navigate(R.id.loginFragment_to_registrationFragment)
        }

        onStates(loginViewModel) {
            when (it) {
                is RegistrationState.Screen -> {
                    binding.loginButton.isEnabled = it.isSubmitButtonEnabled
                }
            }
        }

        onEvents(loginViewModel) {
            when (it) {
                is LoginEvent.EmailInputValid -> {
                    binding.emailInputLayout.error = null
                }
                is LoginEvent.EmailInputInvalid -> {
                    binding.emailInputLayout.error = getString(R.string.text_error_invalid_email)
                }
                is LoginEvent.PasswordInputValid -> {
                    binding.emailInputLayout.error = null
                }
                is LoginEvent.PasswordInputInvalid -> {
                    binding.passwordInputLayout.error = getString(R.string.text_error_invalid_password)
                }
                is LoginEvent.LoginSuccess -> {
                    findNavController().navigate(R.id.loginFragment_to_dashboardFragment)
                }
                is LoginEvent.LoginFailed -> {
                    Toast.makeText(context, getString(R.string.text_error_failed_login), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private suspend fun bindViews() {
        combine(
            binding.emailEditText
                .textChanges()
                .drop(1)
                .onEach { loginViewModel.isEmailInputValid(it.toString()) }
                .map { it.toString() },
            binding.passwordEditText
                .textChanges()
                .drop(1)
                .onEach { loginViewModel.isPasswordInputValid(it.toString()) }
                .map { it.toString() }
        ){ email, password ->
            LoginViewModel.Login.Params(
                email,
                password
            )
        }
            .onEach { loginViewModel.verifyInputs(it) }
            .launchIn(this)

    }

}