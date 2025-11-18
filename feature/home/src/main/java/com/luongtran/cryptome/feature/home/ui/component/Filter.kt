package com.luongtran.cryptome.feature.home.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.luongtran.cryptome.core.designsystem.component.CryptomeCheckbox
import com.luongtran.cryptome.core.designsystem.component.CryptomeFilterChip
import com.luongtran.cryptome.core.designsystem.theme.CryptomeTheme
import com.luongtran.cryptome.feature.home.R
import com.luongtran.cryptome.feature.home.ui.model.FilterOption
import com.luongtran.cryptome.feature.home.ui.model.FilterUi


@Composable
fun Filter(
    uiModel: FilterUi,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onFilterOptionClick: (FilterOption) -> Unit = {},
    onTradableClick: () -> Unit = {},
) {
    val layoutDirection = LocalLayoutDirection.current
    val paddingStart = remember(contentPadding, layoutDirection) {
        contentPadding.calculateLeftPadding(layoutDirection)
    }
    val paddingEnd = remember(contentPadding, layoutDirection) {
        contentPadding.calculateRightPadding(layoutDirection)
    }
    val paddingTop = remember(contentPadding, layoutDirection) {
        contentPadding.calculateTopPadding()
    }
    val paddingBottom = remember(contentPadding, layoutDirection) {
        contentPadding.calculateBottomPadding()
    }
    Row(
        modifier = modifier
            .padding(
                end = paddingEnd,
                top = paddingTop,
                bottom = paddingBottom
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        LazyRow(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(start = paddingStart)
        ) {
            items(
                items = uiModel.options,
                key = { it }
            ) { option ->
                CryptomeFilterChip(
                    selected = option == uiModel.selectedOption,
                    label = stringResource(option.stringRes),
                    onSelectedChange = {
                        onFilterOptionClick(option)
                    }
                )
            }
        }
        CryptomeCheckbox(
            checked = uiModel.showTradable,
            label = stringResource(R.string.tradable),
            onCheckedChange = { onTradableClick() },
        )
    }
}

@PreviewLightDark
@Composable
private fun FilterPreview(
    @PreviewParameter(FilterPreviewParamProvider::class)
    filter: FilterUi
) {
    CryptomeTheme {
        Surface {
            Filter(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                uiModel = filter,
            )
        }
    }
}