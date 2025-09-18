@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.zobaze.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zobaze.ExportButton
import com.example.zobaze.ui.components.TransactionBarGraph
import com.example.zobaze.ui.data.sampleCategoryReports
import com.example.zobaze.ui.data.CategoryReport
import com.example.zobaze.ui.data.dailyMockData
import com.example.zobaze.ui.theme.*

@Composable
fun ExpenseReportScreen(
    categories: List<CategoryReport> = sampleCategoryReports
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGradient)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 20.dp)
    ) {
        // Title
        Text(
            text = "Transaction Report",
            fontSize = 22.sp,
            fontFamily = SourceSansProFamily,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.padding(20.dp)
        )

        // Mock Bar Chart
        TransactionBarGraph(
            dailyData = dailyMockData,
            categoryData = categories.map { it.category to it.total }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subheading
        Text(
            text = "Category",
            fontSize = 18.sp,
            fontFamily = SourceSansProFamily,
            fontWeight = FontWeight.Medium,
            color = TextPrimary,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Category cards grid-like layout
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            categories.forEach { report ->
                Card(
                    modifier = Modifier
                        .width(160.dp) // each card fixed width
                        .shadow(4.dp, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryColor)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = report.category,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = SourceSansProFamily,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "₹ ${report.total}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = SourceSansProFamily,
                            color = SecondaryColor
                        )
                    }
                }
            }
        }
        ExportButton(categories)
    }
}


@Preview(showBackground = true)
@Composable
fun ExpenseReportScreenPreview() {
    MaterialTheme {
        ExpenseReportScreen()
    }
}
