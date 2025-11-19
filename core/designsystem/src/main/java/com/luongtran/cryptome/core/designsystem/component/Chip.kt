package com.luongtran.cryptome.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.luongtran.cryptome.core.designsystem.theme.CryptomeTheme

@Composable
fun CryptomeFilterChip(
    selected: Boolean,
    label: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(32.dp),
    onSelectedChange: (Boolean) -> Unit = {},
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
        FilterChip(
            modifier = modifier,
            selected = selected,
            onClick = { onSelectedChange(!selected) },
            shape = shape,
            label = {
                Text(label)
            },
        )
    }
}

@Composable
fun CryptomeChip(
    label: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(32.dp),
    onClick: () -> Unit = { }
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
        SuggestionChip(
            modifier = modifier.minimumInteractiveComponentSize(),
            onClick = onClick,
            shape = shape,
            label = {
                Text(label)
            },
        )
    }
}

@PreviewLightDark
@Composable
private fun CryptomeFilterChipPreview() {
    CryptomeTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                CryptomeFilterChip(
                    selected = true,
                    label = "Crypto"
                )
                CryptomeChip(label = "BTC")
                CryptomeChip(label = "BTC")
            }

        }
    }
}