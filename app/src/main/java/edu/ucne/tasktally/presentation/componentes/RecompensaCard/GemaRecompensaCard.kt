package edu.ucne.tasktally.presentation.componentes.RecompensaCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.tasktally.R
import edu.ucne.tasktally.ui.theme.TaskTallyTheme

data class RecompensaCardState(
    val titulo: String = "Cenar pizza",
    val precio: Int = 750,
    val imageName: String? = null,
    val puedeComprar: Boolean = true,
    val isProcessing: Boolean = false
)

@Composable
fun GemaRecompensaCard(
    modifier: Modifier = Modifier,
    state: RecompensaCardState,
    onCanjearClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RecompensaImage(imageName = state.imageName, titulo = state.titulo)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = state.titulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            PrecioText(precio = state.precio, puedeComprar = state.puedeComprar)

            if (!state.puedeComprar) {
                Text(
                    text = "Puntos insuficientes",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            CanjearButton(
                puedeComprar = state.puedeComprar,
                isProcessing = state.isProcessing,
                onCanjearClick = onCanjearClick
            )
        }
    }
}

@Composable
private fun RecompensaImage(imageName: String?, titulo: String) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        val imageResId = getDrawableResourceId(imageName)
        if (imageResId != null) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = titulo,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        } else {
            Icon(
                imageVector = Icons.Default.CardGiftcard,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun PrecioText(precio: Int, puedeComprar: Boolean) {
    Text(
        text = "Valor: $precio pts",
        style = MaterialTheme.typography.bodyMedium,
        color = if (puedeComprar) Color.Black else MaterialTheme.colorScheme.error,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
}

@Composable
private fun CanjearButton(
    puedeComprar: Boolean,
    isProcessing: Boolean,
    onCanjearClick: () -> Unit
) {
    Button(
        onClick = onCanjearClick,
        enabled = puedeComprar && !isProcessing,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        if (isProcessing) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = if (puedeComprar) "Canjear" else "Sin puntos",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}

private fun getDrawableResourceId(imageName: String?): Int? {
    if (imageName == null) return null

    return getPlantDrawableId(imageName) ?: getObjectDrawableId(imageName)
}

private fun getPlantDrawableId(imageName: String): Int? {
    return when (imageName) {
        "img0_yellow_tree" -> R.drawable.img0_yellow_tree
        "img1_purple_vines" -> R.drawable.img1_purple_vines
        "img2_little_bush" -> R.drawable.img2_little_bush
        "img3_little_plant" -> R.drawable.img3_little_plant
        "img5_purple_flower" -> R.drawable.img5_purple_flower
        "img6_purple_plant" -> R.drawable.img6_purple_plant
        "img8_green_leaves" -> R.drawable.img8_green_leaves
        "img9_color_leaves" -> R.drawable.img9_color_leaves
        else -> null
    }
}

private fun getObjectDrawableId(imageName: String): Int? {
    return when (imageName) {
        "img10_batteries" -> R.drawable.img10_batteries
        "img11_boxes" -> R.drawable.img11_boxes
        "img12_calendar" -> R.drawable.img12_calendar
        "img13_chocolate" -> R.drawable.img13_chocolate
        "img14_clock" -> R.drawable.img14_clock
        "img15_coffee_cup" -> R.drawable.img15_coffee_cup
        "img16_coffee_machine" -> R.drawable.img16_coffee_machine
        "img16_dishes" -> R.drawable.img16_dishes
        "img17_doughnut" -> R.drawable.img17_doughnut
        "img18_doughnut" -> R.drawable.img18_doughnut
        "img19_files" -> R.drawable.img19_files
        "img20_folder" -> R.drawable.img20_folder
        "img21_food" -> R.drawable.img21_food
        "img22_hamburguer" -> R.drawable.img22_hamburguer
        "img23_ice_cream" -> R.drawable.img23_ice_cream
        "img24_mobile_phone" -> R.drawable.img24_mobile_phone
        "img25_notebook" -> R.drawable.img25_notebook
        "img26_pancakes" -> R.drawable.img26_pancakes
        "img27_pizza" -> R.drawable.img27_pizza
        "img28_pizza_slice" -> R.drawable.img28_pizza_slice
        "img29_pudding" -> R.drawable.img29_pudding
        "img30_recycle_bin" -> R.drawable.img30_recycle_bin
        else -> null
    }
}

@Preview(showBackground = true)
@Composable
fun GemaRecompensaCardPreview() {
    TaskTallyTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GemaRecompensaCard(
                modifier = Modifier.weight(1f),
                state = RecompensaCardState(
                    titulo = "Cenar pizza",
                    precio = 750,
                    imageName = "img27_pizza",
                    puedeComprar = true
                ),
                onCanjearClick = { }
            )

            GemaRecompensaCard(
                modifier = Modifier.weight(1f),
                state = RecompensaCardState(
                    titulo = "Helado de chocolate",
                    precio = 300,
                    imageName = "img23_ice_cream",
                    puedeComprar = false
                ),
                onCanjearClick = { }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GemaRecompensaCardProcessingPreview() {
    TaskTallyTheme {
        GemaRecompensaCard(
            modifier = Modifier.width(180.dp),
            state = RecompensaCardState(
                titulo = "Pizza",
                precio = 500,
                imageName = "img27_pizza",
                puedeComprar = true,
                isProcessing = true
            ),
            onCanjearClick = { }
        )
    }
}