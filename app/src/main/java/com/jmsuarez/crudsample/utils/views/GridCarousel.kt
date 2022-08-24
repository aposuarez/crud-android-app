package com.jmsuarez.crudsample.utils.views

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.ModelView

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class GridCarousel(context: Context?) : Carousel(context) {

    override fun createLayoutManager(): LayoutManager {
        return GridLayoutManager(context, SPAN_COUNT, LinearLayoutManager.VERTICAL, false)
    }

    companion object {
        const val SPAN_COUNT = 2
    }
}