package edu.ucne.tasktally.presentation.Tareas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.ucne.tasktally.presentation.componentes.CircularLoadingIndicator
import edu.ucne.tasktally.presentation.componentes.DateFilter.DateFilter
import edu.ucne.tasktally.presentation.componentes.TareaCard.TareaCard
import edu.ucne.tasktally.ui.theme.TaskTallyTheme
import kotlinx.coroutines.delay
import java.time.LocalDate

data class TareaExample(
    val id: Int,
    val numero: String,
    val titulo: String,
    val puntos: Int
)

@Composable
fun TareasScreen() { //  TODO Completar TareasScreen
    var isLoading by remember { mutableStateOf(true) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    // TODO superImportante!!!!! Ejemplo de tareas - esto debe venir de un ViewModel en la implementacion real
    val tareas = remember {
        listOf(
            TareaExample(1, "Tarea #1", "Arreglar la habitación", 60),
            TareaExample(2, "Tarea #2", "Hacer la tarea de matemáticas", 45),
            TareaExample(3, "Tarea #3", "Lavar los platos", 30),
            TareaExample(4, "Tarea #4", "Organizar el escritorio", 25),
            TareaExample(5, "Tarea #5", "Estudiar para el examen", 80)
        )
    }

    LaunchedEffect(Unit) { //TODO logica de carga correcta.
        delay(2000) // 2 segundos
        isLoading = false
    }

    if (isLoading) {
        CircularLoadingIndicator()
    } else {
        Scaffold() { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DateFilter(
                        selectedDate = selectedDate,
                        onDateChanged = { newDate ->
                            selectedDate = newDate
                            // TODO: Implementar logica para filtrar tareas por fecha seleccionada
                            // TODO: Recargar tareas desde ViewModel basado en la nueva fecha
                            println("Fecha cambiada a: $newDate")
                        }
                    )
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(tareas) { tarea ->
                        TareaCard(
                            numeroTarea = tarea.numero,
                            titulo = tarea.titulo,
                            puntos = tarea.puntos,
                            onIniciarClick = {
                                // TODO: Implementar navegacion o logica para iniciar tarea
                                println("Iniciando tarea: ${tarea.titulo}")
                            }
                        )
                    }
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TareasScreenPreview(){
    TaskTallyTheme {
        TareasScreen()
    }
}