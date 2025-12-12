package edu.ucne.tasktally.presentation.componentes.TareaCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import edu.ucne.tasktally.ui.theme.TaskTallyTheme
import edu.ucne.tasktally.utils.ImageResourceUtils

// =================================================================
// Función Principal Refactorizada (Complejidad Cognitiva Reducida)
// =================================================================

@Composable
fun MentorTareaCard(
    modifier: Modifier = Modifier,
    numeroTarea: String = "Tarea #1",
    titulo: String = "Arreglar la habitación",
    puntos: Int = 60,
    imageName: String? = null,
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    val imageResId = remember(imageName) {
        ImageResourceUtils.getDrawableResourceId(imageName)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        // 1. Composable para la Imagen (Extraído)
        MentorCardImage(imageResId = imageResId)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // 2. Composable para el Contenido Superior (Extraído)
            MentorCardContent(
                numeroTarea = numeroTarea,
                titulo = titulo,
                puntos = puntos
            )

            // 3. Composable para las Acciones de Edición/Eliminación (Extraído)
            MentorCardActions(
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick
            )
        }
    }
}

// =================================================================
// Composables Auxiliares
// =================================================================

/**
 * Muestra la imagen vectorial de la tarea dentro de la tarjeta.
 */
@Composable
private fun MentorCardImage(imageResId: Int?) {
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
                    .offset(x = 20.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

/**
 * Muestra el número, título y puntos de la tarea.
 */
@Composable
private fun MentorCardContent(
    numeroTarea: String,
    titulo: String,
    puntos: Int
) {
    Column {
        Text(
            text = numeroTarea,
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = titulo,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 28.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Surface(
            color = Color.Black.copy(alpha = 0.3f),
            shape = RoundedCornerShape(6.dp)
        ) {
            Text(
                text = "$puntos puntos",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}

/**
 * Muestra los botones de Editar y Eliminar.
 */
@Composable
private fun MentorCardActions(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onEditClick,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = Color.White
            )
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar tarea",
                modifier = Modifier.size(24.dp)
            )
        }

        IconButton(
            onClick = onDeleteClick,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = Color.White
            )
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar tarea",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun MentorTareaCardPreview() {
    TaskTallyTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MentorTareaCard(
                numeroTarea = "Tarea #1",
                titulo = "Arreglar la habitación",
                puntos = 60,
                imageName = "img0_yellow_tree",
                onEditClick = { },
                onDeleteClick = { }
            )

            MentorTareaCard(
                numeroTarea = "Tarea #2",
                titulo = "Barrer la casa",
                puntos = 100,
                imageName = "img5_purple_flower",
                onEditClick = { },
                onDeleteClick = { }
            )
        }
    }
}