package com.luongtran.cryptome.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.luongtran.cryptome.core.designsystem.theme.CryptomeTheme

@Composable
fun CryptomeActionButton(
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    icon: @Composable RowScope.() -> Unit = {},
    onClick: () -> Unit = {},
) {
    Button(
        modifier = modifier,
        colors = colors,
        shape = shape,
        onClick = onClick
    ) {
        icon()
        Text(text = text)
    }
}

@PreviewLightDark
@Composable
private fun CryptomeActionButtonPreview() {
    CryptomeTheme {
        Surface {
            CryptomeActionButton(
                text = " Clear Data",
                icon = {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            )
        }
    }
}