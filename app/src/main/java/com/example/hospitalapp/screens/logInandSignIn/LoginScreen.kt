package com.example.hospitalapp.screens.logInandSignIn

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hospitalapp.R
import com.example.hospitalapp.ui.theme.Inter
import com.example.hospitalapp.viewmodels.AuthViewModel


@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {

    var input by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFECE6EB))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, bottom = 0.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Row(
                modifier = Modifier
                    .padding(bottom = 48.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Log In",
                    color = Color(0xFFD53F8C),
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.W600
                )


            }
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = "Welcome Back!",
                color = Color(0xFF111827),
                fontSize = 24.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.W700
            )
            Text(
                text = "Please enter your details to proceed",
                color = Color(0xFF4B5563),
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.W400,
                modifier = Modifier.padding(bottom = 40.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            )
            {
                Text(
                    text = "Phone Number",
                    color = Color(0xFF374151),
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }


            Surface(
                shadowElevation = 8.dp,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)

            ) {
                TextField(
                    value = input,
                    onValueChange = { input = it },
                    textStyle = TextStyle(
                        color = Color(0xFF374151)
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "Enter your phone number",
                            color = Color(0xFF9CA3AF),
                            fontSize = 14.sp,
                            fontFamily = Inter,
                            fontWeight = FontWeight.W500,
                        )
                    },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.phone),
                            contentDescription = null
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                    ),
                    shape = RoundedCornerShape(8.dp),


                    )

            }
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            )
            {
                Text(
                    text = "Password",
                    color = Color(0xFF374151),
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }


            Surface(
                shadowElevation = 8.dp,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)

            ) {
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    textStyle = TextStyle(
                        color = Color(0xFF374151)
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                painter = painterResource(R.drawable.eye_icon),
                                contentDescription = "password toggle"
                            )
                        }
                    },
                    placeholder = {
                        Text(
                            text = "Enter your password",
                            color = Color(0xFF9CA3AF),
                            fontSize = 14.sp,
                            fontFamily = Inter,
                            fontWeight = FontWeight.W500,
                        )
                    },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.lock),
                            contentDescription = null
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                    ),
                    shape = RoundedCornerShape(8.dp),
                )

            }


            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = ButtonDefaults.buttonElevation(8.dp),
                onClick = {
                    authViewModel.login(input, password) { isFirstTimeLogin ->
                        if (isFirstTimeLogin) {
                            navController.navigate("languageScreen")
                        } else {
                            navController.navigate("healthInformation")
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD53F8C),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Login")
            }
            TextButton(
                modifier = Modifier.padding(bottom = 16.dp),
                onClick = {/*TODO*/ }
            ) {
                Text(
                    text = "Forgot Password?",
                    color = Color(0xFF4B5563),
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.W500,
                )
            }
            Text(
                text = "or sign up with",
                color = Color(0xFF6B7280),
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.W400,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center
            )
            {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(color = Color.White)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_apple),
                        contentDescription = null,

                        )
                }
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))


                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(color = Color.White)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_google),
                        contentDescription = null,

                        )
                }
            }
            Row(
                modifier = Modifier.wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account?",
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF4B5563),
                )
                TextButton(
                    onClick = {
                        navController.navigate("signInScreen")
                    },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Sign Up",
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.W600,
                        color = Color(0xFFD53F8C),
                    )
                }


            }


        }
    }


}