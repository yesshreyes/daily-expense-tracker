package com.example.zobaze.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zobaze.R
import com.example.zobaze.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZobazeTopBar(
    title: String = "Zobaze",
    isDarkTheme: Boolean, // current theme state
    onThemeToggle: () -> Unit // called when user clicks moon/sun
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.zobaze_logo),
                    contentDescription = "Zobaze Logo",
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = PrimaryColor
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = SecondaryColor
        ),
        actions = {
            IconButton(onClick = onThemeToggle) {
                Icon(
                    imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = "Toggle Theme",
                    tint = PrimaryColor
                )
            }
        },
        modifier = Modifier.shadow(
            elevation = 8.dp,
            spotColor = ShadowMedium,
            ambientColor = ShadowLight
        )
    )
}
