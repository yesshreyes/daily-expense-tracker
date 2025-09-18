package com.example.zobaze.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zobaze.ui.theme.PrimaryColor
import com.example.zobaze.ui.theme.SecondaryColor
import com.example.zobaze.ui.theme.TextSecondary

@Composable
fun TransactionBarGraph(
    dailyData: List<Pair<String, Double>>,
    categoryData: List<Pair<String, Double>>
) {
    var selectedGraph by remember { mutableStateOf("Daily") }

    val graphData = if (selectedGraph == "Daily") dailyData else categoryData
    val maxValue = graphData.maxOf { it.second }
    val ySteps = 4

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        // Toggle Buttons
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = { selectedGraph = "Daily" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedGraph == "Daily") SecondaryColor else PrimaryColor
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Daily",
                    color = if (selectedGraph == "Daily") PrimaryColor else SecondaryColor
                )
            }

            Button(
                onClick = { selectedGraph = "Category" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedGraph == "Category") SecondaryColor else PrimaryColor
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Category",
                    color = if (selectedGraph == "Category") PrimaryColor else SecondaryColor
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Graph Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .shadow(6.dp, RoundedCornerShape(12.dp))
                .background(PrimaryColor, RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.weight(1f)) {
                    // Y-axis labels
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        horizontalAlignment = Alignment.End
                    ) {
                        for (i in ySteps downTo 0) {
                            val labelValue = (maxValue / ySteps) * i
                            Text(
                                text = labelValue.toInt().toString(),
                                fontSize = 10.sp,
                                color = TextSecondary,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Bars
                    Canvas(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        val barWidth = size.width / (graphData.size * 2)

                        // 🔹 Draw horizontal grid lines
                        for (i in 0..ySteps) {
                            val y = size.height - (i / ySteps.toFloat()) * size.height
                            drawLine(
                                color = TextSecondary.copy(alpha = 0.3f), // light gray-ish line
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = 1f
                            )
                        }

                        // 🔹 Optional: Vertical grid lines for each bar
                        graphData.forEachIndexed { index, _ ->
                            val x = index * barWidth * 2 + barWidth
                            drawLine(
                                color = TextSecondary.copy(alpha = 0.15f),
                                start = Offset(x, 0f),
                                end = Offset(x, size.height),
                                strokeWidth = 1f
                            )
                        }

                        // 🔹 Draw bars
                        graphData.forEachIndexed { index, (_, value) ->
                            val barHeight = (value.toFloat() / maxValue.toFloat()) * size.height * 0.8f
                            val left = index * barWidth * 2 + barWidth / 2
                            val top = size.height - barHeight

                            drawRect(
                                color = SecondaryColor,
                                topLeft = Offset(x = left, y = top),
                                size = Size(width = barWidth, height = barHeight)
                            )
                        }
                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                // X-axis labels
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    graphData.forEach { (label, _) ->
                        Text(
                            text = label,
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }
            }
        }
    }
}
