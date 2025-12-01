package edu.ucne.tasktally.presentation.componentes.TareaCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import edu.ucne.tasktally.R
import edu.ucne.tasktally.ui.theme.TaskTallyTheme

@Composable
fun TareaCard(
    modifier: Modifier = Modifier,
    numeroTarea: String = "Tarea #1",
    titulo: String = "Arreglar la habitación",
    puntos: Int = 60,
    onIniciarClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF4A9B8F)) // TODO cambiar color a Material
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Image(
                painter = painterResource(id = R.drawable.img0_yellow_tree),
                contentDescription = "Decorative tree",
                modifier = Modifier
                    .size(120.dp)
                    .offset(x = 20.dp),
                contentScale = ContentScale.Fit
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                BtnTarea(
                    text = "Iniciar",
                    icon = Icons.Default.Star,
                    onClick = onIniciarClick,
                    containerColor = Color.White,
                    contentColor = Color(0xFF6B4EFF) // TODO Aplicar a Material
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TareaCardPreview() {
    TaskTallyTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TareaCard(
                numeroTarea = "Tarea #1",
                titulo = "Arreglar la habitación",
                puntos = 60,
                onIniciarClick = { }
            )

            TareaCard(
                numeroTarea = "Tarea #2",
                titulo = "Hacer la tarea de matemáticas",
                puntos = 45,
                onIniciarClick = { }
            )
        }
    }
}