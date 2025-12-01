package edu.ucne.tasktally.presentation.Tienda

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.ucne.tasktally.presentation.componentes.CircularLoadingIndicator
import edu.ucne.tasktally.presentation.componentes.RecompensaCard.RecompensaCard
import edu.ucne.tasktally.R
import edu.ucne.tasktally.presentation.componentes.PointsHeader.PointsHeader
import edu.ucne.tasktally.ui.theme.TaskTallyTheme
import kotlinx.coroutines.delay

data class RecompensaExample(
    val id: Int,
    val titulo: String,
    val puntos: Int,
    val imagenId: Int = R.drawable.img0_yellow_tree
)

@Composable
fun TiendaScreen(){ // TODO Completar TiendaScreen
    var isLoading by remember { mutableStateOf(true) }

    // TODO TOTO TODO TOOOO Ejemplo de recompensas - esto debe venir de un ViewModel en la implementacion real
    val recompensas = remember {
        listOf(
            RecompensaExample(1, "Cenar pizza", 750),
            RecompensaExample(2, "Ver película", 500),
            RecompensaExample(3, "Comprar helado", 300),
            RecompensaExample(4, "Jugar videojuegos", 400),
            RecompensaExample(5, "Salir con amigos", 600),
            RecompensaExample(6, "Comprar libro", 350)
        )
    }

    LaunchedEffect(Unit) { //TODO logica de carga correcta.
        delay(2000) // 1 segundos
        isLoading = false
    }

    if (isLoading) {
        CircularLoadingIndicator()
    } else {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val totalPoints = 1500 // TODO ELIMINAR VARIABLE LOCAL.
                PointsHeader(totalPoints = totalPoints)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Recompensas",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(recompensas) { recompensa ->
                    RecompensaCard(
                        titulo = recompensa.titulo,
                        puntos = recompensa.puntos,
                        imagenId = recompensa.imagenId,
                        onCanjearClick = {
                            // TODO: Implementar logica de canjeo
                            println("Canjeando recompensa: ${recompensa.titulo}")
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TiendaScreenPreview(){
    TaskTallyTheme {
        TiendaScreen()
    }
}