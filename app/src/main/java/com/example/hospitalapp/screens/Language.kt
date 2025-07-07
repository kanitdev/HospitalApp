package com.example.hospitalapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hospitalapp.R

data class LanguageOption(
    val code: String,
    val englishName: String,
    val nativeName: String,
    val iconRes: Int
)

@Composable
fun Language(navController: NavController) {
    var selectedLanguage by remember { mutableStateOf<String?>(null) }

    val languages = listOf(
        LanguageOption("en", "English", "English", R.drawable.english_icon),
        LanguageOption("hi", "Hindi", "हिंदी", R.drawable.hindi_icon),
        LanguageOption("bn", "Bengali", "বাংলা", R.drawable.hindi_icon), // fallback
        LanguageOption("pa", "Punjabi", "ਪੰਜਾਬੀ", R.drawable.hindi_icon), // fallback
        LanguageOption("ta", "Tamil", "தமிழ்", R.drawable.hindi_icon), // fallback
        LanguageOption("te", "Telugu", "తెలుగు", R.drawable.hindi_icon) // fallback
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header with back button and title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.back_icon),
                        contentDescription = "Back",
                        tint = Color(0xFFE94D88)
                    )
                }

                Text(
                    text = "Select Language",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFE94D88),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Title and subtitle
            Text(
                text = "Choose Your Language",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Select your preferred language to continue",
                fontSize = 14.sp,
                color = Color(0xFF6B7280),
                modifier = Modifier.padding(bottom = 40.dp)
            )

            // Language grid
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                for (i in 0 until 3) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        for (j in 0 until 2) {
                            val index = i * 2 + j
                            LanguageCard(
                                language = languages[index],
                                isSelected = selectedLanguage == languages[index].code,
                                onSelect = { selectedLanguage = languages[index].code },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // Continue button
            Button(
                onClick = {
                    if (selectedLanguage != null) {
                        navController.navigate("userType")
                    }
                },
                enabled = selectedLanguage != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(top = 24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE94D88),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFE5E7EB),
                    disabledContentColor = Color(0xFF9CA3AF)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Continue",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun LanguageCard(
    language: LanguageOption,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { onSelect() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFFDF2F8) else Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (isSelected) {
                        Modifier.border(
                            2.dp,
                            Color(0xFFE94D88),
                            RoundedCornerShape(12.dp)
                        )
                    } else Modifier
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(language.iconRes),
                    contentDescription = language.englishName,
                    modifier = Modifier.size(40.dp),
                    tint = if (isSelected) Color(0xFFE94D88) else Color(0xFF6B7280)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = language.englishName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF111827),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = language.nativeName,
                    fontSize = 12.sp,
                    color = Color(0xFF6B7280),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageScreenPreview() {
    Language(androidx.navigation.compose.rememberNavController())
}
