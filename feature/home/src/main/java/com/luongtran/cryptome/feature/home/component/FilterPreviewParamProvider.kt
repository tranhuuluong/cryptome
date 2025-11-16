package com.luongtran.cryptome.feature.home.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.luongtran.cryptome.feature.home.model.FilterOption
import com.luongtran.cryptome.feature.home.model.FilterUi

class FilterPreviewParamProvider : PreviewParameterProvider<FilterUi> {
    override val values: Sequence<FilterUi>
        get() = sequenceOf(
            FilterPreviewData.filter1,
            FilterPreviewData.filter2,
        )
}

internal object FilterPreviewData {
    val filter1 = FilterUi(
        options = FilterOption.entries.toList(),
        selectedOption = FilterOption.All,
        showPurchasable = false
    )

    val filter2 = filter1.copy(
        showPurchasable = false
    )
}