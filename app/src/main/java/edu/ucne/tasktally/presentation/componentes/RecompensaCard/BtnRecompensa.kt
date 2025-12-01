package edu.ucne.tasktally.presentation.componentes.RecompensaCard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.ucne.tasktally.ui.theme.TaskTallyTheme

@Composable
fun BtnRecompensa( // TODO Por  decidir tema de colores
    modifier: Modifier = Modifier,
    text: String = "Canjear",
    icon: ImageVector = Icons.Default.Star,
    onClick: () -> Unit = {},
    containerColor: Color = Color(0xFFE1BEE7), // TODO cambiar color a Material
    contentColor: Color = Color.Black
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = contentColor
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BtnRecompensaPreview() {
    TaskTallyTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BtnRecompensa(
                text = "Canjear",
                onClick = { }
            )

            BtnRecompensa(
                text = "Canjeado",
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White,
                onClick = { }
            )

            BtnRecompensa(
                text = "Sin puntos",
                containerColor = Color.Gray,
                contentColor = Color.White,
                onClick = { }
            )
        }
    }
}
