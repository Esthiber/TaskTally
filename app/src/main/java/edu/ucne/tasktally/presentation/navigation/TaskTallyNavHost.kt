package edu.ucne.tasktally.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.tasktally.presentation.Perfil.PerfilScreen
import edu.ucne.tasktally.presentation.Tareas.TareasScreen
import edu.ucne.tasktally.presentation.Tienda.TiendaScreen
import edu.ucne.tasktally.presentation.auth.LoginScreen
import edu.ucne.tasktally.presentation.auth.LoginViewModel
import edu.ucne.tasktally.presentation.auth.RegisterScreen

@Composable
fun TaskTallyNavHost(
    navHostController: NavHostController
) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val isLoggedIn by loginViewModel.isLoggedIn.collectAsState()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navHostController.navigate(Screen.Tareas) {
                popUpTo(Screen.Login) { inclusive = true }
            }
        } else {
            navHostController.navigate(Screen.Login) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navHostController,
        startDestination = if (isLoggedIn) Screen.Tareas else Screen.Login
    ) {
        composable<Screen.Login> {
            LoginScreen(
                onNavigateToRegister = {
                    navHostController.navigate(Screen.Register)
                },
                onLoginSuccess = {
                    navHostController.navigate(Screen.Tareas) {
                        popUpTo(Screen.Login) { inclusive = true }
                    }
                }
            )
        }

        composable<Screen.Register> {
            RegisterScreen(
                onNavigateBack = {
                    navHostController.popBackStack()
                },
                onRegisterSuccess = {
                    navHostController.popBackStack()
                }
            )
        }

        composable<Screen.Tareas> {
            TareasScreen()
        }

        composable<Screen.Tienda> {
            TiendaScreen()
        }

        composable<Screen.Perfil> {
            PerfilScreen(
                onLogout = {
                    loginViewModel.onLogoutClick()
                }
            )
        }
    }
}