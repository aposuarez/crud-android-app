package com.jmsuarez.crudsample.utils.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.DialogFragment
import com.jmsuarez.crudsample.R

private const val ARGS_KEY_TITLE = "key_title"
private const val ARGS_KEY_DESCRIPTION = "key_description"
private const val ARGS_KEY_POSITIVE_BUTTON = "key_positive_button"
private const val ARGS_KEY_NEGATIVE_BUTTON = "key_negative_button"

class ConfirmationDialog : DialogFragment() {

    companion object {
        const val CONFIRMATION_DIALOG_FRAGMENT_TAG = "ConfirmationDialogFragment"
        fun newInstance(
            title: String,
            description: String,
            negativeButtonText: String? = null,
            positiveButtonText: String? = null
        ): ConfirmationDialog {
            val fragment = ConfirmationDialog()
            val args = Bundle()

            args.putString(ARGS_KEY_TITLE, title)
            args.putString(ARGS_KEY_DESCRIPTION, description)
            args.putString(ARGS_KEY_NEGATIVE_BUTTON, negativeButtonText)
            args.putString(ARGS_KEY_POSITIVE_BUTTON, positiveButtonText)

            fragment.arguments = args

            return fragment
        }
    }

    private var title: String? = ""
    private var description: String? = ""
    private var positiveButtonText: String? = ""
    private var negativeButtonText: String? = ""
    private var listener: Listener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString(ARGS_KEY_TITLE, "")
        description = arguments?.getString(ARGS_KEY_DESCRIPTION, "")
        positiveButtonText = arguments?.getString(ARGS_KEY_POSITIVE_BUTTON,"")
        negativeButtonText = arguments?.getString(ARGS_KEY_NEGATIVE_BUTTON,"")

        setStyle(STYLE_NO_TITLE, R.style.WideDialog)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_confirmation, container, false)
        val textTitle = view.findViewById<AppCompatTextView>(R.id.confirmationTitleTextView)
        val textDescription = view.findViewById<AppCompatTextView>(R.id.confirmationDescriptionTextView)
        val negativeButton = view.findViewById<Button>(R.id.negativeButton)
        val positiveButton = view.findViewById<Button>(R.id.positiveButton)

        textTitle.text = title
        textDescription.text = description

        negativeButton.apply{
            if(!negativeButtonText.isNullOrBlank()) {
                text = negativeButtonText
            }
            setOnClickListener {
                listener?.onNegativeButtonClicked()
                dismiss()
            }
        }

        positiveButton.apply{
            if(!positiveButtonText.isNullOrBlank()) {
                text = positiveButtonText
            }
            setOnClickListener {
                listener?.onPositiveButtonClicked()
                dismiss()
            }
        }

        return view
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun onNegativeButtonClicked() {}
        fun onPositiveButtonClicked() {}
    }
}
