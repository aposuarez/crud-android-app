package com.jmsuarez.crudsample.ui.noteDetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jmsuarez.crudsample.CRUDApplication
import com.jmsuarez.crudsample.R
import com.jmsuarez.crudsample.databinding.FragmentNoteDetailsBinding
import com.jmsuarez.crudsample.utils.views.ConfirmationDialog
import com.jmsuarez.crudsample.utils.views.LoadingDialog
import io.uniflow.android.livedata.onEvents
import io.uniflow.android.livedata.onStates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteDetailsFragment: Fragment(R.layout.fragment_note_details), CoroutineScope by MainScope(), ConfirmationDialog.Listener {

    @Inject
    lateinit var noteDetailsViewModel: NoteDetailsViewModel

    private val args: NoteDetailsFragmentArgs by navArgs()
    private var confirmationDialog: ConfirmationDialog? = null
    private var loadingDialog : LoadingDialog? = null

    private var _binding: FragmentNoteDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNoteDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as CRUDApplication).appComponent.noteDetailsComponent().create()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.noteLastUpdateTextView.text = args.noteItem.updatedAt
        binding.noteDetailsTitleEditText.setText(args.noteItem.title)
        binding.noteDetailsDescriptionEditText.setText(args.noteItem.description)

        binding.noteDetailsDeleteImageView.setOnClickListener {
            showConfirmationDialog(
                getString(R.string.text_dialog_delete_title),
                getString(R.string.text_dialog_delete_description),
                getString(R.string.button_no),
                getString(R.string.button_yes)

            )
        }

        binding.noteDetailsSaveButton.setOnClickListener {
            launch {
                noteDetailsViewModel.areInputsValid(
                    binding.noteDetailsTitleEditText.text.toString(),
                    binding.noteDetailsDescriptionEditText.text.toString()
                )
            }
        }

        onStates(noteDetailsViewModel) {
            when (it) {
                is NoteDetailsState.Screen -> {
                    showLoadingDialog(getString(R.string.text_saving_changes))
                    launch {
                        noteDetailsViewModel.editNote(
                            args.noteItem.id,
                            it.title,
                            it.description
                        )
                    }
                }
            }
        }

        onEvents(noteDetailsViewModel) {
            when (it) {
                is NoteDetailsEvent.EditNoteSuccess -> {
                    loadingDialog?.dismiss()
                    findNavController().navigate(R.id.noteDetailsFragment_to_dashboardFragment)
                }
                is NoteDetailsEvent.EditNoteFailed -> {
                    loadingDialog?.dismiss()
                    Toast.makeText(context, getString(R.string.text_error_failed_saving), Toast.LENGTH_LONG).show()
                }
                is NoteDetailsEvent.DeleteNoteSuccess -> {
                    loadingDialog?.dismiss()
                    findNavController().navigate(R.id.noteDetailsFragment_to_dashboardFragment)
                }
                is NoteDetailsEvent.DeleteNoteFailed -> {
                    loadingDialog?.dismiss()
                    Toast.makeText(context, getString(R.string.text_error_failed_deleting), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showLoadingDialog(text: String) {
        loadingDialog = LoadingDialog.newInstance(text)
        loadingDialog?.show(parentFragmentManager,LoadingDialog.TAG_LOADING)
    }

    private fun showConfirmationDialog(
        title: String,
        description: String,
        negativeButtonText: String? = null,
        positiveButtonText: String? = null
    ) {
        confirmationDialog = ConfirmationDialog.newInstance(
            title,
            description,
            negativeButtonText,
            positiveButtonText
        )
        confirmationDialog?.setListener(this)
        confirmationDialog?.show(
            parentFragmentManager,
            ConfirmationDialog.CONFIRMATION_DIALOG_FRAGMENT_TAG
        )
    }

    override fun onPositiveButtonClicked() {
        launch {
            showLoadingDialog(getString(R.string.text_deleting_note))
            noteDetailsViewModel.deleteNote(args.noteItem.id)
        }
    }

}