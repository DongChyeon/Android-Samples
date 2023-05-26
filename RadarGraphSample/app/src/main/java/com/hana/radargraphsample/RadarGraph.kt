package com.hana.radargraphsample

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RadarChart(
    data: List<RadarData>,
    modifier: Modifier = Modifier,
    strokeColor: Color = Color.Black,
    fillColor: Color = Color.Black.copy(alpha = 0.3f),
    strokeWidth: Dp = 1.dp,
    maxValue: Float = 5f,
    labelColor: Color = Color.Black,
    labelSize: Int = 16
) {
    Box(modifier = modifier) {
        Canvas(modifier = modifier.size(300.dp)) {
            drawRadarChart(data, strokeColor, fillColor, strokeWidth, maxValue)
            drawLabels(data, labelColor, labelSize)
        }
    }
}

private fun DrawScope.drawLabels(data: List<RadarData>, textColor: Color, textSize: Int) {
    val centerX = size.width / 2
    val centerY = size.height / 2
    val radius = (size.width / 2) * 0.95
    val angleStep = (2 * Math.PI / data.size).toFloat()

    for (i in data.indices) {
        // (-2 * Math.PI / 4) 를 해주는 이유 : canvas에 그릴때 90도부터 시작하는데 이를 0도 부터 시작하기 위함
        val angle = angleStep * i + (-2 * Math.PI / 4).toFloat()
        val labelX = centerX + radius * cos(angle)
        val labelY = centerY + radius * sin(angle) + (textSize / 2).sp.toPx()
        val label = data[i].label

        drawContext.canvas.nativeCanvas.drawText(
            label,
            labelX.toFloat(),
            labelY.toFloat(),
            Paint().apply {
                this.color = textColor.toArgb()
                this.textSize = textSize.sp.toPx()
                textAlign = Paint.Align.CENTER
                typeface = Typeface.DEFAULT_BOLD
            }
        )
    }
}

private fun DrawScope.drawRadarChart(
    data: List<RadarData>,
    strokeColor: Color,
    fillColor: Color,
    strokeWidth: Dp,
    maxValue: Float,
) {
    val centerX = size.width / 2
    val centerY = size.height / 2
    val radius = (size.width / 2) * 0.8f

    /* 중앙에서 각 라벨로 뻗어나가는 경계선
    val angleStep = (2 * Math.PI / data.size).toFloat()

    for (i in data.indices) {
        // (-2 * Math.PI / 4) 를 해주는 이유 : canvas에 그릴때 90도부터 시작하는데 이를 0도 부터 시작하기 위함
        val angle = angleStep * i + (-2 * Math.PI / 4).toFloat()
        val startX = centerX + radius * cos(angle)
        val startY = centerY + radius * sin(angle)
        drawLine(
            color = strokeColor,0
            start = Offset(centerX, centerY),
            end = Offset(startX, startY),
            strokeWidth = strokeWidth.toPx()
        )
    }
     */

    for (i: Int in 1..maxValue.toInt()) {
        drawPolygon(centerX, centerY, radius, i.toFloat(), maxValue, data.size, strokeColor, strokeWidth)
    }

    drawPath(
        path = getPath(data, centerX, centerY, radius, maxValue),
        color = fillColor
    )
}

private fun DrawScope.drawPolygon(
    centerX: Float,
    centerY: Float,
    radius: Float,
    value: Float,
    maxValue: Float,
    sides: Int,
    color: Color,
    strokeWidth: Dp
) {
    val angleStep = (2 * Math.PI / sides).toFloat()

    val path = Path()
    var x = centerX + radius * value / maxValue * cos(-2 * Math.PI / 4).toFloat()
    var y = centerY + radius * value / maxValue * sin(-2 * Math.PI / 4).toFloat()
    path.moveTo(x, y)

    for (i in 1 until sides) {
        val angle = angleStep * i + (-2 * Math.PI / 4).toFloat()
        x = centerX + radius * value / maxValue * cos(angle)
        y = centerY + radius * value / maxValue * sin(angle)
        path.lineTo(x, y)
    }

    path.close()
    drawPath(path = path, color = color, style = Stroke(width = strokeWidth.toPx()))
}

private fun getPath(
    data: List<RadarData>,
    centerX: Float,
    centerY: Float,
    radius: Float,
    maxValue: Float
): Path {
    val path = Path()

    data.forEachIndexed { index, radarData ->
        val angle = (2 * Math.PI / data.size) * index + (-2 * Math.PI / 4).toFloat()
        val value = radarData.value.coerceIn(0f, maxValue)
        // value / maxValue 만큼 거리 이동
        val x = centerX + radius * value / maxValue * cos(angle).toFloat()
        val y = centerY + radius * value / maxValue * sin(angle).toFloat()

        if (index == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }

    path.close()
    return path
}

@Preview
@Composable
fun PreviewRadarGraph() {
    RadarChart(
        data = listOf(
        RadarData("당도", 2f),
        RadarData("여운", 3f),
        RadarData("알코올", 4f),
        RadarData("탄닌", 3f),
        RadarData("바디", 2f),
        RadarData("산도", 5f)
    ))
}