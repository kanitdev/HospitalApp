package com.example.hospitalapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hospitalapp.screens.HealthInformation
import com.example.hospitalapp.screens.Language
import com.example.hospitalapp.screens.UserType
import com.example.hospitalapp.screens.logInandSignIn.LoginScreen
import com.example.hospitalapp.screens.logInandSignIn.SignInScreen
import com.example.hospitalapp.viewmodels.AuthViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "LoginScreen") {
        composable("loginScreen") { LoginScreen(navController, authViewModel) }
        composable("signInScreen") { SignInScreen(modifier, navController, authViewModel) }
        composable("language") { Language(navController = navController) }
        composable("healthInformation") { HealthInformation() }
        composable ("usertype"){ UserType(modifier, navController) }
    }

}