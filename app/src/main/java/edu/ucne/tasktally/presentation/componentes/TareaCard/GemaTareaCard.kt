package edu.ucne.tasktally.presentation.componentes.TareaCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.tasktally.domain.models.TareaGema
import edu.ucne.tasktally.ui.theme.TaskTallyTheme
import edu.ucne.tasktally.utils.ImageResourceUtils

@Composable
fun GemaTareaCard(
    modifier: Modifier = Modifier,
    tarea: TareaGema,
    isProcessing: Boolean = false,
    onIniciarClick: (String) -> Unit = {},
    onCompletarClick: (String) -> Unit = {}
) {
    val imageResId = remember(tarea.nombreImgVector) {
        ImageResourceUtils.getDrawableResourceId(tarea.nombreImgVector)
    }
    val backgroundColor = mapEstadoToColor(tarea.estado)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
    ) {
        GemaTareaImage(imageResId = imageResId)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            GemaTareaCardHeader(tarea = tarea)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = tarea.titulo,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 24.sp,
                maxLines = 2
            )

            if (tarea.descripcion.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = tarea.descripcion,
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp,
                    maxLines = 2
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            GemaTareaCardAction(
                estado = tarea.estado,
                isProcessing = isProcessing,
                onIniciarClick = { onIniciarClick(tarea.tareaId) },
                onCompletarClick = { onCompletarClick(tarea.tareaId) }
            )
        }
    }
}

@Composable
private fun mapEstadoToColor(estado: String): Color {
    return when (estado.lowercase()) {
        "pendiente" -> MaterialTheme.colorScheme.primary
        "iniciada" -> MaterialTheme.colorScheme.secondary
        "completada" -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.primary
    }
}

@Composable
private fun GemaTareaImage(imageResId: Int?) {
    if (imageResId != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Imagen de tarea",
                modifier = Modifier
                    .size(120.dp)
                    .offset(x = 10.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
private fun GemaTareaCardHeader(tarea: TareaGema) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Surface(
            color = Color.White.copy(alpha = 0.25f),
            shape = RoundedCornerShape(6.dp)
        ) {
            Text(
                text = tarea.estado.uppercase(),
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
            )
        }

        Surface(
            color = Color.Black.copy(alpha = 0.3f),
            shape = RoundedCornerShape(6.dp)
        ) {
            Text(
                text = "${tarea.puntos} puntos",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
            )
        }

        Surface(
            color = Color.Black.copy(alpha = 0.3f),
            shape = RoundedCornerShape(6.dp)
        ) {
            Text(
                text = " ✨ ${tarea.gemaCompletoNombre}",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
            )
        }
    }
}

@Composable
private fun GemaTareaCardAction(
    estado: String,
    isProcessing: Boolean,
    onIniciarClick: () -> Unit,
    onCompletarClick: () -> Unit
) {
    when (estado.lowercase()) {
        "pendiente" -> {
            ActionTaskButton(
                text = if (isProcessing) "Iniciando..." else "Iniciar",
                isProcessing = isProcessing,
                onClick = onIniciarClick
            )
        }

        "iniciada" -> {
            ActionTaskButton(
                text = if (isProcessing) "Completando..." else "Completar",
                isProcessing = isProcessing,
                onClick = onCompletarClick
            )
        }

        "completada" -> {
            CompletedTaskStatus()
        }
    }
}

@Composable
private fun ActionTaskButton(
    text: String,
    isProcessing: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = !isProcessing,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White.copy(alpha = 0.3f),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
    ) {
        if (isProcessing) {
            CircularProgressIndicator(
                modifier = Modifier.size(14.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
            Spacer(modifier = Modifier.width(6.dp))
        }
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun CompletedTaskStatus() {
    Surface(
        color = Color.White.copy(alpha = 0.3f),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "¡Completada!",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GemaTareaCardPreview() {
    TaskTallyTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GemaTareaCard(
                tarea = TareaGema(
                    tareaId = "1",
                    titulo = "Arreglar la habitación",
                    estado = "pendiente",
                    puntos = 60,
                    descripcion = "Organizar y limpiar toda la habitación",
                    nombreImgVector = "img0_yellow_tree",
                    gemaCompletoNombre = "Gema Nivel 1"
                )
            )

            // Tarea iniciada
            GemaTareaCard(
                tarea = TareaGema(
                    tareaId = "2",
                    titulo = "Hacer la tarea de matemáticas",
                    estado = "iniciada",
                    puntos = 45,
                    descripcion = "Completar ejercicios del capítulo 5",
                    nombreImgVector = "img25_notebook",
                    gemaCompletoNombre = "Gema Nivel 2"
                )
            )

            // Tarea completada
            GemaTareaCard(
                tarea = TareaGema(
                    tareaId = "3",
                    titulo = "Lavar los platos",
                    estado = "completada",
                    puntos = 30,
                    descripcion = "Lavar todos los platos del desayuno",
                    nombreImgVector = "img16_dishes",
                    gemaCompletoNombre = "Gema Nivel 3"
                )
            )
        }
    }
}