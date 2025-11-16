package com.luongtran.cryptome.core.designsystem.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@PreviewLightDark
@Composable
private fun CryptomeFilterChipPreview() {
    CryptomeTheme {
        Surface {
            CryptomeFilterChip(
                selected = true,
                label = "Crypto"
            )
        }
    }
}