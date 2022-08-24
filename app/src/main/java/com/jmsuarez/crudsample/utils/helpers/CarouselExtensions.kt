package com.jmsuarez.crudsample.utils.helpers

import com.airbnb.epoxy.CarouselModelBuilder
import com.airbnb.epoxy.EpoxyModel
import com.jmsuarez.crudsample.utils.views.GridCarouselModelBuilder

inline fun CarouselModelBuilder.withModelsFrom(
    items: Map<String, Int>,
    modelBuilder: (String, Int) -> EpoxyModel<*>
) {
    models(items.map { modelBuilder(it.key, it.value) })
}

inline fun <T> GridCarouselModelBuilder.withModelsFrom(
    items: List<T>,
    modelBuilder: (T) -> EpoxyModel<*>
) {
    models(items.map { modelBuilder(it) })
}