package com.jmsuarez.crudsample.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.EpoxyController
import com.jmsuarez.crudsample.CRUDApplication
import com.jmsuarez.crudsample.R
import com.jmsuarez.crudsample.data.local.Note
import com.jmsuarez.crudsample.databinding.FragmentDashboardBinding
import com.jmsuarez.crudsample.utils.helpers.withModelsFrom
import com.jmsuarez.crudsample.utils.views.ConfirmationDialog
import com.jmsuarez.crudsample.utils.views.NoteItemModel_
import com.jmsuarez.crudsample.utils.views.emptyPlaceholder
import com.jmsuarez.crudsample.utils.views.gridCarousel
import io.uniflow.android.livedata.onStates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardFragment : Fragment(R.layout.fragment_dashboard), CoroutineScope by MainScope(), ConfirmationDialog.Listener {

    @Inject
    lateinit var dashboardViewModel: DashboardViewModel

    private var confirmationDialog: ConfirmationDialog? = null

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as CRUDApplication).appComponent.dashboardComponent().create()
            .inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.title = getString(R.string.title_dashboard)
        binding.toolbar.inflateMenu(R.menu.menu_dashboard)
        binding.toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.action_add -> {
                    findNavController().navigate(R.id.dashboardFragment_to_addNoteFragment)
                }
                R.id.action_logout -> {
                    showConfirmationDialog(
                        getString(R.string.text_dialog_logout_title),
                        getString(R.string.text_dialog_logout_description),
                        getString(R.string.button_no),
                        getString(R.string.button_yes)
                    )
                }
                else -> {}
            }
            true
        }

        val notesCarouselController = NotesCarouselController()

        binding.notesRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            setController(notesCarouselController)
        }

        onStates(dashboardViewModel) {
            when (it) {
                is DashboardState.Screen -> {
                    notesCarouselController.notesList = it.notesList
                    binding.notesRecyclerView.requestModelBuild()
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        launch {
            dashboardViewModel.getNotes()
        }
    }

    inner class NotesCarouselController : EpoxyController(){

        var notesList: List<Note> = emptyList()

        override fun buildModels() {

            if (notesList.isEmpty()) {
                emptyPlaceholder {
                    id(getString(R.string.id_dashboard_empty_placeholder))
                    onCardClicked {
                        findNavController().navigate(R.id.dashboardFragment_to_addNoteFragment)
                    }
                }
            }
            else {
                gridCarousel {
                    id(getString(R.string.id_dashboard_notes_carousel))
                    onBind { _, view, _ -> view.clipToPadding = false }
                    onUnbind { _, view -> view.removeAllViews() }
                    padding(
                        Carousel.Padding.dp(
                            CAROUSEL_PADDING,
                            CAROUSEL_PADDING,
                            CAROUSEL_PADDING,
                            CAROUSEL_PADDING,
                            CAROUSEL_ITEM_SPACING
                        )
                    )
                    withModelsFrom(this@NotesCarouselController.notesList) { item ->
                        NoteItemModel_()
                            .id(getString(R.string.id_dashboard_note), item.id)
                            .onCardClicked { findNavController().navigate(DashboardFragmentDirections.dashboardFragmentToNoteDetailsFragment(item)) }
                            .title(item.title)
                            .description(item.description)
                            .spanSizeOverride { _, _, _ -> SPAN_SIZE_OVERRIDE }
                    }
                    hasFixedSize(true)
                }
            }
        }
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
            dashboardViewModel.userLogout()
            findNavController().navigate(R.id.dashboardFragment_to_loginFragment)
        }
    }

    companion object {

        private const val CAROUSEL_PADDING: Int = 0

        private const val CAROUSEL_ITEM_SPACING: Int = 10

        private const val SPAN_SIZE_OVERRIDE: Int = 1

    }
}