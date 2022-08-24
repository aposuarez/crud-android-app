package com.jmsuarez.crudsample.utils.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.google.android.material.card.MaterialCardView
import com.jmsuarez.crudsample.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class NoteItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : FrameLayout(context, attrs, defStyleAttrs) {

    init {
        View.inflate(context, R.layout.layout_note_item,this)
    }

    private val noteItemCard: MaterialCardView = findViewById(R.id.noteItemCard)
    private val noteItemTitle: TextView = findViewById(R.id.noteTitleTextView)
    private val noteItemDescription: TextView = findViewById(R.id.noteDescriptionTextView)

    private var onCardClicked: ((Boolean) -> Unit)? = null
    private var noteTitle: CharSequence = DEFAULT_VALUE
    private var noteDescription: CharSequence = DEFAULT_VALUE

    @CallbackProp
    fun onCardClicked(onClick: ((Boolean) -> Unit)?) {
        onClick?.let { this.onCardClicked = it }
    }

    @TextProp
    fun setTitle(title: CharSequence) {
        this.noteTitle = title
    }

    @TextProp
    fun setDescription(description: CharSequence) {
        this.noteDescription = description
    }

    @AfterPropsSet
    fun useProps() {
        noteItemCard.setOnClickListener {
            onCardClicked?.invoke(true)
        }

        noteItemTitle.text = noteTitle
        noteItemDescription.text = noteDescription
    }

    companion object {
        private const val DEFAULT_VALUE = ""
    }

}