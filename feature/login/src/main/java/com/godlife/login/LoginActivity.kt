package com.godlife.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context: Context = this

        setContent {
            LoginUi(context)
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginUi(context: Context){

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(navController = navController, startDestination = LoginScreenRoute.route,
            modifier = Modifier.fillMaxHeight()) {

            composable(LoginScreenRoute.route){
                LoginScreen(context)
            }

            composable(SignUpScreenRoute.route){
                SignUpScreen()
            }

        }
    }


}

object LoginScreenRoute{
    const val route = "LoginScreen"
}

object SignUpScreenRoute{
    const val route = "SignUpScreen"
}