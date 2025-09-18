package com.example.zobaze.ui.components

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.zobaze.ui.theme.PrimaryColor
import com.example.zobaze.ui.theme.SecondaryColor

@Composable
fun CameraButton() {
    val context = LocalContext.current

    // Launcher to pick an image
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        // uri is the selected image
        if (uri != null) {
            Toast.makeText(context, "Image uploaded", Toast.LENGTH_SHORT).show()
        }
    }

    Card(
        modifier = Modifier
            .size(56.dp)
            .shadow(8.dp, RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryColor),
        onClick = {
            // Open gallery to pick an image
            pickImageLauncher.launch("image/*")
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "Camera",
                tint = SecondaryColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
