package com.jmsuarez.crudsample.ui.registration

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
import com.jmsuarez.crudsample.databinding.FragmentRegistrationBinding
import com.jmsuarez.crudsample.utils.views.LoadingDialog
import io.uniflow.android.livedata.onEvents
import io.uniflow.android.livedata.onStates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.ldralighieri.corbind.widget.textChanges
import javax.inject.Inject

class RegistrationFragment : Fragment(R.layout.fragment_registration), CoroutineScope by MainScope() {

    @Inject
    lateinit var registrationViewModel: RegistrationViewModel

    private val loadingDialog : LoadingDialog by lazy {
        LoadingDialog.newInstance(getString(R.string.text_registering))
    }

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as CRUDApplication).appComponent.registrationComponent().create()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch {
            bindViews()
        }

        binding.registrationSubmitButton.setOnClickListener {
            loadingDialog.show(parentFragmentManager,LoadingDialog.TAG_LOADING)
            launch {
                registrationViewModel.registerUserEmail(
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                )
            }
        }

        binding.registerBackImageView.setOnClickListener {
            findNavController().navigateUp()
        }

        onStates(registrationViewModel) {
            when (it) {
                is RegistrationState.Screen -> {
                    binding.registrationSubmitButton.isEnabled = it.isSubmitButtonEnabled
                }
            }
        }

        onEvents(registrationViewModel) {
            when (it) {
                is RegistrationEvent.EmailInputValid -> {
                    binding.emailInputLayout.error = null
                }
                is RegistrationEvent.EmailInputInvalid -> {
                    binding.emailInputLayout.error = getString(R.string.text_error_invalid_email)
                }
                is RegistrationEvent.PasswordInputValid -> {
                    binding.emailInputLayout.error = null
                }
                is RegistrationEvent.PasswordInputInvalid -> {
                    binding.passwordInputLayout.error = getString(R.string.text_error_invalid_password)
                }
                is RegistrationEvent.RegistrationSuccess -> {
                    loadingDialog.dismiss()
                    findNavController().navigate(R.id.registrationFragment_to_dashboardFragment)
                }
                is RegistrationEvent.RegistrationFailed -> {
                    loadingDialog.dismiss()
                    Toast.makeText(context,getString(R.string.text_error_failed_registration),Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private suspend fun bindViews() {
        combine(
            binding.emailEditText
                .textChanges()
                .drop(1)
                .onEach { registrationViewModel.isEmailInputValid(it.toString()) }
                .map { it.toString() },
            binding.passwordEditText
                .textChanges()
                .drop(1)
                .onEach { registrationViewModel.isPasswordInputValid(it.toString()) }
                .map { it.toString() }
        ){ email, password ->
            RegistrationViewModel.Registration.Params(
                email,
                password
            )
        }
            .onEach { registrationViewModel.verifyInputs(it) }
            .launchIn(this)

    }

}