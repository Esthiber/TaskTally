package edu.ucne.tasktally.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.tasktally.presentation.auth.login.LoginScreen
import edu.ucne.tasktally.presentation.auth.login.LoginUiEvent
import edu.ucne.tasktally.presentation.auth.login.LoginViewModel
import edu.ucne.tasktally.presentation.auth.register.RegisterScreen
import edu.ucne.tasktally.presentation.gema.tareas.GemaTareasScreen
import edu.ucne.tasktally.presentation.gema.tienda.GemaTiendaScreen
import edu.ucne.tasktally.presentation.mentor.perfil.PerfilScreen
import edu.ucne.tasktally.presentation.mentor.recompensas.CreateRecompensaScreen
import edu.ucne.tasktally.presentation.mentor.recompensas.list.ListRecompensaScreen
import edu.ucne.tasktally.presentation.mentor.tareas.CreateTareaScreen
import edu.ucne.tasktally.presentation.mentor.tareas.list.ListTareaScreen
import edu.ucne.tasktally.presentation.zona.ZonaScreen
import edu.ucne.tasktally.presentation.zona.ZoneAccessScreen

@Composable
fun TaskTallyNavHost(
    navHostController: NavHostController
) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val isLoggedIn by loginViewModel.isLoggedIn.collectAsState()
    val currentUser by loginViewModel.uiState.collectAsState()

    LaunchedEffect(isLoggedIn, currentUser.currentUser?.role, currentUser.hasZoneAccess) {
        if (isLoggedIn && currentUser.currentUser != null) {

            val user = currentUser.currentUser

            val targetDestination = when (user?.role) {
                "mentor" -> Screen.MentorTareas
                "gema" -> if (currentUser.hasZoneAccess) Screen.Tareas else Screen.ZoneAccess
                else -> {
                    loginViewModel.onEvent(LoginUiEvent.LogoutClick)
                    Screen.Login
                }
            }

            navHostController.navigate(targetDestination) {
                popUpTo(Screen.Login) { inclusive = true }
            }
        } else if (!isLoggedIn) {
            navHostController.navigate(Screen.Login) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navHostController,
        startDestination = Screen.Login
    ) {

        composable<Screen.Login> {
            LoginScreen(
                onNavigateToRegister = {
                    navHostController.navigate(Screen.Register)
                },
                onLoginSuccess = {}
            )
        }

        composable<Screen.Register> {
            RegisterScreen(
                onNavigateBack = { navHostController.popBackStack() },
                onRegisterSuccess = { navHostController.popBackStack() }
            )
        }

        composable<Screen.ZoneAccess> {
            ZoneAccessScreen(
                onZoneAccessGranted = { loginViewModel.onEvent(LoginUiEvent.RefreshZoneAccess) },
                onLogout = { loginViewModel.onEvent(LoginUiEvent.LogoutClick) }
            )
        }

        composable<Screen.Tareas> {
            GemaTareasScreen()
        }

        composable<Screen.Tienda> {
            NavigationGuard(
                navController = navHostController,
                requiredRole = "gema"
            ) {
                GemaTiendaScreen()
            }
        }

        composable<Screen.Perfil> {
            NavigationGuard(
                navController = navHostController,
                requiredRole = "gema"
            ) {
                PerfilScreen(
                    onLogout = { loginViewModel.onEvent(LoginUiEvent.LogoutClick) },
                    onNavigateToZona = {
                        navHostController.navigate(Screen.Zona)
                    }
                )
            }
        }

        composable<Screen.Zona> {
            ZonaScreen()
        }

        composable<Screen.MentorTareas> {
            ListTareaScreen(
                onNavigateToCreate = {
                    navHostController.navigate(Screen.CreateTarea)
                },
                onNavigateToEdit = { tareaId ->
                    navHostController.navigate(Screen.EditTarea(tareaId))
                }
            )
        }

        composable<Screen.ListTareas> {
            ListTareaScreen(
                onNavigateToCreate = {
                    navHostController.navigate(Screen.CreateTarea)
                },
                onNavigateToEdit = { tareaId ->
                    navHostController.navigate(Screen.EditTarea(tareaId))
                }
            )
        }

        composable<Screen.CreateTarea> {
            CreateTareaScreen(
                tareaId = null,
                onNavigateBack = { navHostController.popBackStack() }
            )
        }

        composable<Screen.EditTarea> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.EditTarea>()
            CreateTareaScreen(
                tareaId = args.tareaId,
                onNavigateBack = { navHostController.popBackStack() }
            )
        }

        composable<Screen.MentorTienda> {
            ListRecompensaScreen(
                onNavigateToCreate = {
                    navHostController.navigate(Screen.CreateRecompensa)
                },
                onNavigateToEdit = { recompensaId ->
                    navHostController.navigate(Screen.EditRecompensa(recompensaId))
                }
            )
        }

        composable<Screen.ListRecompensas> {
            ListRecompensaScreen(
                onNavigateToCreate = {
                    navHostController.navigate(Screen.CreateRecompensa)
                },
                onNavigateToEdit = { recompensaId ->
                    navHostController.navigate(Screen.EditRecompensa(recompensaId))
                }
            )
        }

        composable<Screen.CreateRecompensa> {
            CreateRecompensaScreen(
                recompensaId = null,
                onNavigateBack = { navHostController.popBackStack() }
            )
        }

        composable<Screen.EditRecompensa> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.EditRecompensa>()
            CreateRecompensaScreen(
                recompensaId = args.recompensaId,
                onNavigateBack = { navHostController.popBackStack() }
            )
        }

        composable<Screen.MentorPerfil> {
            PerfilScreen(
                onLogout = { loginViewModel.onEvent(LoginUiEvent.LogoutClick) },
                onNavigateToZona = {
                    navHostController.navigate(Screen.Zona)
                }
            )
        }
    }
}