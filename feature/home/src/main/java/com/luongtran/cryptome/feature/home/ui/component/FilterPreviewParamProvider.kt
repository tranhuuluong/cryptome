package com.luongtran.cryptome.feature.home.ui.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.luongtran.cryptome.feature.home.ui.model.FilterOption
import com.luongtran.cryptome.feature.home.ui.model.FilterUi

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
        selectedOption = FilterOption.Crypto,
        showTradable = false
    )

    val filter2 = filter1.copy(
        selectedOption = FilterOption.Fiat,
        showTradable = true
    )
}