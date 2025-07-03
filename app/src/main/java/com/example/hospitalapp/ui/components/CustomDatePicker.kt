package com.example.hospitalapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun CustomDatePickerDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (day: Int, month: Int, year: Int) -> Unit
) {

    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            val years = (1900..2025).toList().reversed()
            val months = listOf(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
            )
            val days = (1..31).toList()

            var selectedYear by remember { mutableStateOf(years.first()) }
            var selectedMonthIndex by remember { mutableStateOf(0) }
            var selectedDay by remember { mutableStateOf(1) }

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DropdownMenuPicker(
                        label = "Year",
                        options = years.map { it.toString() },
                        selectedIndex = years.indexOf(selectedYear),
                        onSelected = { selectedYear = years[it] },

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)

                    )
                    // Month Picker
                    DropdownMenuPicker(
                        label = "Month",
                        options = months,
                        selectedIndex = selectedMonthIndex,
                        onSelected = { selectedMonthIndex = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                    // Day Picker
                    DropdownMenuPicker(
                        label = "Day",
                        options = days.map { it.toString() },
                        selectedIndex = selectedDay - 1,
                        onSelected = { selectedDay = it + 1 },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            onDateSelected(selectedDay, selectedMonthIndex + 1, selectedYear)
                            onDismiss()
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE94D88),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownMenuPicker(
    label: String,
    options: List<String>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        Button(
            onClick = { expanded = true },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFF1F6),
                contentColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(options[selectedIndex])
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelected(index)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomDatePickerDialogPreview() {
    CustomDatePickerDialog(
        onDismiss = {},
        onDateSelected = { _, _, _ -> },
        showDialog = true
    )
}