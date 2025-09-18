package com.example.zobaze

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.zobaze.ui.data.model.CategoryReport
import com.example.zobaze.ui.theme.PrimaryColor
import com.example.zobaze.ui.theme.SecondaryColor
import java.io.File

@Composable
fun ExportButton(categories: List<CategoryReport>) {
    val context = LocalContext.current

    Button(
        onClick = {
            // Build CSV from your categories
            val csvData = buildString {
                append("Category,Amount\n")
                categories.forEach { report ->
                    append("${report.category},${report.total}\n")
                }
            }

            // Save CSV file in cache
            val file = File(context.cacheDir, "transactions.csv")
            file.writeText(csvData)

            // Get URI with FileProvider
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )

            // Share intent
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/csv"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(intent, "Share CSV"))
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor)
    ) {
        Text(
            "Export",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryColor
        )
    }
}
