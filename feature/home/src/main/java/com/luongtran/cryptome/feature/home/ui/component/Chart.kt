package com.luongtran.cryptome.feature.home.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.luongtran.cryptome.core.designsystem.theme.CryptomeTheme
import kotlin.random.Random

@Composable
fun FakeChart(
    modifier: Modifier = Modifier,
    lineColor: Color,
    pointCount: Int = 15
) {
    val points = remember(pointCount) {
        List(pointCount + 1) {
            Random.nextFloat()
        }
    }
    val startingPoint = remember {
        Random.nextFloat()
    }
    Canvas(modifier = modifier) {
        val path = Path()
        val widthStep = size.width / pointCount
        val startY = size.height / 2f + startingPoint * size.height / 4f
        path.moveTo(0f, startY)

        for (i in 1..pointCount) {
            val x = i * widthStep
            val y = size.height / 2f + (points[i] - 0.5f) * size.height / 1.5f
            path.lineTo(x, y)
        }

        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = 4f, cap = StrokeCap.Round)
        )
    }
}

@PreviewLightDark
@Composable
fun PreviewFakeCharts() {
    CryptomeTheme {
        FakeChart(
            modifier = Modifier
                .width(50.dp)
                .height(30.dp),
            lineColor = Color.Red
        )
    }
}