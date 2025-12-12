package edu.ucne.tasktally.presentation.mentor.perfil

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.tasktally.presentation.componentes.CircularLoadingIndicator
import edu.ucne.tasktally.presentation.componentes.ProfileHeader.ProfileHeader
import edu.ucne.tasktally.ui.theme.TaskTallyTheme

@Composable
fun PerfilScreen(
    viewModel: PerfilViewModel = hiltViewModel(),
    onLogout: () -> Unit = {},
    onNavigateToZona: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.isLoading) {
        CircularLoadingIndicator()
    } else {
        PerfilBody(
            state = state,
            onLogout = onLogout,
            onNavigateToZona = onNavigateToZona
        )
    }
}

@Composable
fun PerfilBody(
    state: PerfilUiState,
    onLogout: () -> Unit,
    onNavigateToZona: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        ProfileHeader(
            userName = state.userName,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onNavigateToZona,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = Icons.Default.Group,
                contentDescription = "Zona",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Mi Zona",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text(
                text = "Cerrar Sesión",
                color = MaterialTheme.colorScheme.onError
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilScreenPreview() {
    TaskTallyTheme {
        PerfilBody(
            state = PerfilUiState(
                isLoading = false,
                userName = "Esthiber",
                completedTasks = 12,
                currentStreak = 5,
                totalPoints = 250
            ),
            onLogout = {},
            onNavigateToZona = {}
        )
    }
}