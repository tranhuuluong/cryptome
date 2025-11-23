package com.luongtran.cryptome.feature.coindetail.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.luongtran.cryptome.core.designsystem.theme.CryptomeTheme

@Composable
fun PriceHistoryLineChart(
    prices: List<Double>,
    modifier: Modifier = Modifier,
    lineColor: Color = Color(0xFF4CAF50),
    strokeWidth: Float = 4f
) {
    val maxPrice = prices.maxOrNull()?.toFloat() ?: 0f
    val minPrice = prices.minOrNull()?.toFloat() ?: 0f
    val priceRange = maxPrice - minPrice

    Canvas(modifier = modifier) {

        val widthStep = size.width / (prices.size - 1)

        // Transform prices into chart points
        val points = prices.mapIndexed { index, price ->
            val x = index * widthStep
            val normalized = (price.toFloat() - minPrice) / priceRange
            val y = size.height - (normalized * size.height)
            Offset(x, y)
        }

        // Draw line
        drawPath(
            path = Path().apply {
                points.forEachIndexed { i, point ->
                    if (i == 0) moveTo(point.x, point.y)
                    else lineTo(point.x, point.y)
                }
            },
            color = lineColor,
            style = Stroke(width = strokeWidth)
        )
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
                prices = listOf(
                    96596.26748932256,
                    96456.09351130111,
                    96093.55844512114,
                    96093.08554930383,
                    96094.77509106293,
                    96414.32704575720,
                    96740.72367213018,
                    96795.14537605634,
                    96163.66515890630,
                    96280.95659551470,
                    96132.57875013179,
                    95638.84239810033,
                    95345.67539493149,
                    95408.10578161350,
                    95563.16330306425,
                    95455.73575408528,
                    95338.37491430218,
                    95765.98534879953,
                    95887.65311062265,
                    95970.53978502872,
                    95901.24206238744,
                    95770.69010049747,
                    95924.85394402785,
                    96218.24041912635,
                    96363.83596651317,
                    95533.27758908504,
                    95679.77614693345,
                    95354.25150008230,
                    95339.12988520236,
                    94222.38094010827,
                    93961.39000268752,
                    94149.48631143362,
                    93894.30574937458,
                    94088.27736234758,
                    93418.28685839157,
                    93724.16735983620,
                    94447.56649239272,
                    94943.30337496543,
                    95007.20566275727,
                    95232.17613259320,
                    95254.67794029391,
                    94993.14924633299,
                    95153.23564959208,
                    95606.60743383903,
                    95397.86689371291,
                    95606.20640199064,
                    95686.25510259028,
                    95379.36071978675,
                    93796.88994586666,
                    94653.58382649544,
                    93922.87486866680,
                    93842.81769926088,
                    92741.26731841980,
                    92100.63237839056,
                    91596.53006366558,
                    91693.35461646627,
                    92227.59523623077,
                    91737.70686988925,
                    91980.26045863712,
                    91610.80627720905,
                    90255.22078808662,
                    90163.31278373566,
                    90065.41808353463,
                    89940.88303797417,
                    90376.78831209745,
                    91247.20065880953,
                    91390.14934491530,
                    91491.32068512319,
                    91101.27957832650,
                    91286.59585840360,
                    91755.36554718122,
                    92662.30632481481,
                    93204.81982634002,
                    93162.07336066250,
                    93325.92713121568,
                    93122.09814015672,
                    92716.84160443030,
                    93044.65734346136,
                    92694.55870350763,
                    92138.14036005416,
                    92427.69170514931,
                    92507.41084533423,
                    92346.35749695773,
                    91217.60245946673,
                    90757.23739386712,
                    91306.69978099474,
                    91756.74992757434,
                    91253.33816148552,
                    91470.31108260156,
                    91548.01493856287,
                    91449.53604185913,
                    91357.07449318390,
                    91717.41061740446,
                    90132.49571363469,
                    89360.92781846721,
                    89626.74292528489,
                    88818.47297460062,
                    89199.17099702486,
                    90378.66894083007,
                    90503.26750887478,
                    91456.54597418207,
                    91731.44738667835,
                    92452.90118359654,
                    92340.64019107407,
                    92200.12120462913,
                    92584.25355764051,
                    91711.56187440915,
                    92022.77247785960,
                    91904.56393806005,
                    91758.44867344385,
                    91713.88441318647,
                    91609.37568158247,
                    91859.19071511702,
                    91315.72447880712,
                    90551.69791483090,
                    88137.89293271797,
                    86693.58356348285,
                    86342.09749393745,
                    86800.83306808230,
                    86761.20205548570,
                    87788.10374227915,
                    87201.14358848673,
                    87222.15564440849,
                    87148.89064644047,
                    85953.13412436966,
                    85767.26725675236,
                    85931.70108765477,
                    85925.79964182339,
                    85392.71313690203,
                    84120.56581879629,
                    82981.45138695452,
                    82841.23602992203,
                    82528.55533695265,
                    82973.64482179837,
                    84051.78650233845,
                    84941.68698550288,
                    82869.03860231553,
                    85085.19099216028,
                    83911.10163860504,
                    84820.82939164332,
                    84452.52903690241,
                    85507.94465034025,
                    84543.03452474878,
                    85074.85290618234,
                    84664.15518883192,
                    84671.53026467693,
                    84458.97007047643,
                    83974.52428955567,
                    83855.23592828802,
                    84580.73868684813,
                    84533.48616607075
                )
            )
        }
    }
}