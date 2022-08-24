package com.jmsuarez.crudsample.utils.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.google.android.material.card.MaterialCardView
import com.jmsuarez.crudsample.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class EmptyPlaceholder @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : FrameLayout(context, attrs, defStyleAttrs) {

    init {
        View.inflate(context, R.layout.layout_empty,this)
    }

    private val placeholderCard: MaterialCardView = findViewById(R.id.placeholderCard)

    private var onCardClicked: ((Boolean) -> Unit)? = null

    @CallbackProp
    fun onCardClicked(onClick: ((Boolean) -> Unit)?) {
        onClick?.let { this.onCardClicked = it }
    }

    @AfterPropsSet
    fun useProps() {
        placeholderCard.setOnClickListener {
            onCardClicked?.invoke(true)
        }
    }

}