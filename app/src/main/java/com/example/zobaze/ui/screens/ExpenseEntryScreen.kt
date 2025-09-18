@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.zobaze.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import com.example.zobaze.ui.components.CameraButton
import com.example.zobaze.ui.theme.*
import kotlinx.coroutines.delay

val SourceSansProFamily = FontFamily.Default

@Composable
fun ExpenseEntryScreen(viewModel: ExpenseViewModel) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var isCredit by remember { mutableStateOf(false) }
    var showSuccessAnimation by remember { mutableStateOf(false) }

    val categories = listOf("Staff", "Travel", "Food", "Utility")
    val context = LocalContext.current

    val expenses by viewModel.expenses.collectAsState()
    val totalSpent = expenses.filter { !it.isCredit }.sumOf { it.amount }

    Box(modifier = Modifier.fillMaxSize().background(BackgroundGradient)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(16.dp),
                        spotColor = ShadowMedium,
                        ambientColor = ShadowLight
                    ),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = GlassColor)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Total Spent Today",
                        fontSize = 16.sp,
                        fontFamily = SourceSansProFamily,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "₹ ${"%.2f".format(totalSpent)}",
                        fontSize = 32.sp,
                        fontFamily = SourceSansProFamily,
                        fontWeight = FontWeight.Bold,
                        color = SecondaryColor
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryColor)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Add transactions", fontFamily = SourceSansProFamily) },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BorderFocused,
                            unfocusedBorderColor = BorderLight
                        ),
                        shape = RoundedCornerShape(12.dp),
                        textStyle = TextStyle(fontFamily = SourceSansProFamily)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    FloatingActionButton(
                        onClick = { },
                        modifier = Modifier.size(56.dp),
                        containerColor = SecondaryColor,
                        shape = CircleShape
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            tint = PrimaryColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryColor)
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.padding(4.dp)
                ) {
                    OutlinedTextField(
                        value = selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Category", fontFamily = SourceSansProFamily) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BorderFocused,
                            unfocusedBorderColor = BorderLight
                        ),
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        textStyle = TextStyle(fontFamily = SourceSansProFamily)
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .background(color = PrimaryColor, shape = RoundedCornerShape(12.dp))
                            .shadow(elevation = 12.dp, shape = RoundedCornerShape(12.dp), spotColor = ShadowMedium)
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category, fontFamily = SourceSansProFamily, color = TextPrimary, fontWeight = FontWeight.Medium) },
                                onClick = {
                                    selectedCategory = category
                                    expanded = false
                                },
                                colors = MenuDefaults.itemColors(textColor = TextPrimary),
                                modifier = Modifier.background(
                                    color = if (selectedCategory == category) SecondaryColor.copy(alpha = 0.1f) else Transparent,
                                    shape = RoundedCornerShape(8.dp)
                                )
                            )
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryColor)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Debit")
                    Switch(
                        checked = isCredit,
                        onCheckedChange = { isCredit = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = SuccessGreen,
                            uncheckedThumbColor = ErrorRed,
                            checkedTrackColor = SuccessGreen.copy(alpha = 0.5f),
                            uncheckedTrackColor = ErrorRed.copy(alpha = 0.5f)
                        )
                    )
                    Text("Credit")
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("₹", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = SecondaryColor, fontFamily = SourceSansProFamily)
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { newValue ->
                            if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                                amount = newValue
                            }
                        },
                        placeholder = { Text("0", fontFamily = SourceSansProFamily, color = TextHint) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BorderFocused,
                            unfocusedBorderColor = BorderLight
                        ),
                        shape = RoundedCornerShape(12.dp),
                        textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium, fontFamily = SourceSansProFamily)
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceGrey)
            ) {
                OutlinedTextField(
                    value = notes,
                    onValueChange = { if (it.length <= 100) notes = it },
                    placeholder = { Text("Add note (optional)", color = TextTertiary, fontFamily = SourceSansProFamily) },
                    modifier = Modifier.fillMaxWidth().padding(4.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Transparent,
                        unfocusedBorderColor = Transparent,
                        focusedContainerColor = Transparent,
                        unfocusedContainerColor = Transparent
                    ),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = TextStyle(fontFamily = SourceSansProFamily),
                    supportingText = { Text("${notes.length}/100", color = TextTertiary, fontSize = 12.sp, fontFamily = SourceSansProFamily) }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        if (title.isNotEmpty() && amount.isNotEmpty() && selectedCategory.isNotEmpty()) {
                            viewModel.addExpense(Expense(title, selectedCategory, amount.toDouble(), isCredit))
                            showSuccessAnimation = true
                            title = ""; amount = ""; selectedCategory = ""; notes = ""
                        } else {
                            Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.weight(1f).height(56.dp).shadow(8.dp, RoundedCornerShape(14.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("Submit", fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = SourceSansProFamily, color = PrimaryColor)
                }
                CameraButton()
            }
        }

        if (showSuccessAnimation) {
            val scale = remember { Animatable(0f) }
            LaunchedEffect(Unit) {
                scale.animateTo(1.5f, animationSpec = tween(500, easing = FastOutSlowInEasing))
                delay(500)
                scale.animateTo(0f, animationSpec = tween(300))
                showSuccessAnimation = false
            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                // Dark semi-transparent background
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.5f))
                )
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Success",
                    tint = SuccessGreen,
                    modifier = Modifier
                        .size(80.dp)
                        .graphicsLayer(
                            scaleX = scale.value,
                            scaleY = scale.value,
                            alpha = scale.value
                        )
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseEntryScreenPreview() {
    val fakeViewModel = object : ExpenseViewModel() {
        override fun addExpense(expense: Expense) {}
    }
    MaterialTheme { ExpenseEntryScreen(viewModel = fakeViewModel) }
}
