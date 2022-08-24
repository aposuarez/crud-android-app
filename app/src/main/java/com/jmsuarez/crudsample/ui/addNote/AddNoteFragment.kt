package com.jmsuarez.crudsample.ui.addNote

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
import com.jmsuarez.crudsample.databinding.FragmentAddNoteBinding
import com.jmsuarez.crudsample.utils.views.LoadingDialog
import io.uniflow.android.livedata.onEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddNoteFragment : Fragment(R.layout.fragment_add_note), CoroutineScope by MainScope() {

    @Inject
    lateinit var addNoteViewModel: AddNoteViewModel

    private val loadingDialog : LoadingDialog by lazy {
        LoadingDialog.newInstance(getString(R.string.text_adding_note))
    }

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as CRUDApplication).appComponent.addNoteComponent().create()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addNoteBackImageView.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.noteSaveButton.setOnClickListener {
            loadingDialog.show(parentFragmentManager,LoadingDialog.TAG_LOADING)
            launch {
                addNoteViewModel.addNewNote(
                    binding.noteTitleEditText.text.toString(),
                    binding.noteDescriptionEditText.text.toString()
                )
            }
        }

        onEvents(addNoteViewModel){
            when (it) {
                AddNoteEvent.AddNoteSuccess -> {
                    loadingDialog.dismiss()
                    findNavController().navigate(R.id.addNoteFragment_to_dashboardFragment)
                }
                AddNoteEvent.AddNoteFailed -> {
                    loadingDialog.dismiss()
                    Toast.makeText(context,getString(R.string.text_error_failed_adding),Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}