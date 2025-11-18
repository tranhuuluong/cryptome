package com.luongtran.cryptome.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.luongtran.cryptome.core.designsystem.theme.CryptomeTheme

@Composable
fun CryptomeCheckbox(
    checked: Boolean,
    label: String,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    onCheckedChange: (Boolean) -> Unit = {},
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
        Row(
            modifier = modifier
                .clickable { onCheckedChange(!checked) }
                .padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Checkbox(checked = checked, onCheckedChange = null)
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun CryptomeCheckboxPreview() {
    CryptomeTheme {
        Surface {
            CryptomeCheckbox(checked = true, label = "Tradable")
        }
    }
}