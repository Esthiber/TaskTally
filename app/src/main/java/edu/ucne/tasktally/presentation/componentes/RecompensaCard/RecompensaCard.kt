package edu.ucne.tasktally.presentation.componentes.RecompensaCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.tasktally.R
import edu.ucne.tasktally.ui.theme.TaskTallyTheme

@Composable
fun RecompensaCard(
    modifier: Modifier = Modifier,
    titulo: String = "Cenar pizza",
    puntos: Int = 750,
    imagenId: Int = R.drawable.img0_yellow_tree,
    onCanjearClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .width(200.dp)
            .height(280.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5) // TODO cambiar color a Material
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = imagenId),
                    contentDescription = titulo,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D78), // TODO cambiar color a Material
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Valor: $puntos pts",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }

            BtnRecompensa(
                text = "Canjear",
                onClick = onCanjearClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecompensaCardPreview() {
    TaskTallyTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            RecompensaCard(
                titulo = "Cenar pizza",
                puntos = 750,
                onCanjearClick = { }
            )

            RecompensaCard(
                titulo = "Ver película",
                puntos = 500,
                onCanjearClick = { }
            )

            RecompensaCard(
                titulo = "Comprar helado",
                puntos = 300,
                onCanjearClick = { }
            )
        }
    }
}
