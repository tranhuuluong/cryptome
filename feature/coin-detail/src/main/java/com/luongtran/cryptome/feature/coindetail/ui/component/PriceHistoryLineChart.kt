package com.luongtran.cryptome.feature.coindetail.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.util.fastMapIndexed
import com.luongtran.cryptome.core.designsystem.theme.AccentGreen
import com.luongtran.cryptome.core.designsystem.theme.AccentRed
import com.luongtran.cryptome.core.designsystem.theme.CryptomeTheme
import com.luongtran.cryptome.core.ui.model.DisplayableNumber
import com.luongtran.cryptome.feature.coindetail.ui.model.CoinPriceChartUiState

private const val PRICE_LABEL_COUNT = 4

@SuppressLint("DefaultLocale")
@Composable
fun PriceHistoryLineChart(
    uiModel: CoinPriceChartUiState.Success,
    modifier: Modifier = Modifier,
    strokeWidth: Float = 4f
) {
    val prices = uiModel.prices
    val (priceChangePercent, priceChangePercentFormatted) = uiModel.priceChangePercent
    val color = if (priceChangePercent.toDouble() >= 0) AccentGreen else AccentRed
    val maxPrice = prices.maxOrNull()?.toFloat() ?: 0f
    val minPrice = prices.minOrNull()?.toFloat() ?: 0f
    val priceRange = maxPrice - minPrice
    val priceLabels = remember(maxPrice, minPrice) {
        (0 until PRICE_LABEL_COUNT).map { i ->
            maxPrice - (priceRange / (PRICE_LABEL_COUNT - 1)) * i
        }
    }

    Column(
        modifier = modifier,
    ) {
        Text(
            text = priceChangePercentFormatted,
            style = MaterialTheme.typography.labelSmall.copy(
                color = color,
            )
        )

        Box {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val chartHeight = size.height
                val widthStep = size.width / (prices.size - 1)
                val heightStep = if (priceRange == 0f) 0f else chartHeight / priceRange

                // Transform prices into chart points
                val points = prices.fastMapIndexed { index, price ->
                    val x = index * widthStep
                    val y = chartHeight - ((price.toFloat() - minPrice) * heightStep)
                    Offset(x, y)
                }

                // Create the line path
                val path = Path().apply {
                    points.fastForEachIndexed { i, (x, y) ->
                        if (i == 0) moveTo(x, y) else lineTo(x, y)
                    }
                }

                // Gradient fill path
                val fillPath = Path().apply {
                    addPath(path)
                    lineTo(points.last().x, size.height)
                    lineTo(points.first().x, size.height)
                    close()
                }

                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            color.copy(alpha = 0.1f),
                            color.copy(alpha = 0.01f)
                        )
                    )
                )

                drawPath(
                    path = path,
                    color = color,
                    style = Stroke(width = strokeWidth)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterEnd),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                priceLabels.forEach { value ->
                    Text(
                        text = String.format("%,.2f", value),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    CryptomeTheme {
        Surface {
            PriceHistoryLineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(16.dp),
                uiModel = CoinPriceChartUiState.Success(
                    prices = listOf(10.0, 12.0, 8.0, 15.0, 20.0, 18.0, 25.0, 22.0, 30.0, 28.0),
                    priceChangePercent = DisplayableNumber(
                        value = 5.67,
                        formatted = "+5.67%"
                    )
                )
            )
        }
    }
}