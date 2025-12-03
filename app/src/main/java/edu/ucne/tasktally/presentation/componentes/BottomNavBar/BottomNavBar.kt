package edu.ucne.tasktally.presentation.componentes.BottomNavBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FormatListNumberedRtl
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FlexibleBottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import edu.ucne.tasktally.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalMaterial3ExpressiveApi
@Composable
fun BottomNavBar(
    navController: NavController,
    isMentor: Boolean = false
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navigateTo: (Screen) -> Unit = { screen ->
        navController.navigate(screen) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    FlexibleBottomAppBar {
        if (isMentor) {
            BottomNavItem(
                label = "Tareas",
                icon = Icons.Default.FormatListNumberedRtl,
                currentRoute = currentRoute,
                navigateTo = navigateTo,
                route = Screen.MentorTareas
            )

            BottomNavItem(
                label = "Tienda",
                icon = Icons.Default.ShoppingCart,
                currentRoute = currentRoute,
                navigateTo = navigateTo,
                route = Screen.MentorTienda
            )

            BottomNavItem(
                label = "Perfil",
                icon = Icons.Default.AccountCircle,
                currentRoute = currentRoute,
                navigateTo = navigateTo,
                route = Screen.MentorPerfil
            )
        } else {
            BottomNavItem(
                label = "Tareas",
                icon = Icons.Default.FormatListNumberedRtl,
                currentRoute = currentRoute,
                navigateTo = navigateTo,
                route = Screen.Tareas
            )

            BottomNavItem(
                label = "Tienda",
                icon = Icons.Default.ShoppingCart,
                currentRoute = currentRoute,
                navigateTo = navigateTo,
                route = Screen.Tienda
            )

            BottomNavItem(
                label = "Perfil",
                icon = Icons.Default.AccountCircle,
                currentRoute = currentRoute,
                navigateTo = navigateTo,
                route = Screen.Perfil
            )
        }
    }
}