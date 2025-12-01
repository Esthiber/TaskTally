package edu.ucne.tasktally.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.tasktally.presentation.Perfil.PerfilScreen
import edu.ucne.tasktally.presentation.Tareas.TareasScreen
import edu.ucne.tasktally.presentation.Tienda.TiendaScreen

@Composable
fun TaskTallyNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Tareas
    ) {
        composable<Screen.Login> { // TODO Agregar Componente Login
        }

        composable<Screen.Tareas> { TareasScreen() }

        composable<Screen.Tienda> { TiendaScreen() }

        composable<Screen.Perfil> { PerfilScreen() }
    }
}