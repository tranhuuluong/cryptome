package com.luongtran.cryptome.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddChart
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.luongtran.cryptome.core.designsystem.component.CryptomeActionButton
import com.luongtran.cryptome.core.designsystem.theme.CryptomeTheme
import com.luongtran.cryptome.feature.home.R

@Composable
fun ControlPanel(
    modifier: Modifier = Modifier,
    onClearDataClick: () -> Unit = {},
    onInsertDataClick: () -> Unit = {},
) {
    val shape = MaterialTheme.shapes.small
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                shape = shape,
                color = MaterialTheme.colorScheme.outlineVariant
            )
            .shadow(
                elevation = 10.dp,
                shape = shape,
                ambientColor = MaterialTheme.colorScheme.tertiary,
                spotColor = MaterialTheme.colorScheme.tertiaryContainer,
            )
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            stringResource(R.string.control_panel),
            style = MaterialTheme.typography.labelLarge
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            CryptomeActionButton(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.clear_data),
                icon = {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.clear_data),
                    )
                    Spacer(Modifier.width(4.dp))
                },
                onClick = onClearDataClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onError,
                ),
            )
            CryptomeActionButton(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.insert_data),
                onClick = onInsertDataClick,
                icon = {
                    Icon(
                        imageVector = Icons.Default.AddChart,
                        contentDescription = stringResource(R.string.insert_data),
                    )
                    Spacer(Modifier.width(4.dp))
                },
            )
        }

    }
}

@PreviewLightDark
@Composable
private fun ControlPanelPreview() {
    CryptomeTheme {
        Surface {
            ControlPanel(
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}