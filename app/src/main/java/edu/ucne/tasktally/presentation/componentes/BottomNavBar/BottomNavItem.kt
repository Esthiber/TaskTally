package edu.ucne.tasktally.presentation.componentes.BottomNavBar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShortNavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import edu.ucne.tasktally.presentation.navigation.Screen

@Composable
fun BottomNavItem(
    label: String,
    icon: ImageVector,
    currentRoute: String?,
    navigateTo: (Screen) -> Unit,
    route: Screen
) {
    val isSelected = currentRoute == route::class.qualifiedName
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val iconColor by animateColorAsState(
        targetValue = when {
            isSelected -> MaterialTheme.colorScheme.onSecondaryContainer
            else -> MaterialTheme.colorScheme.onSurfaceVariant
        },
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 0
        ),
        label = "iconColor"
    )

    val labelColor by animateColorAsState(
        targetValue = when {
            isSelected -> MaterialTheme.colorScheme.onSurface
            else -> MaterialTheme.colorScheme.onSurfaceVariant
        },
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 0
        ),
        label = "labelColor"
    )

    val scale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.85f
            isSelected -> 1.1f
            else -> 1.0f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    val fontWeight by animateFloatAsState(
        targetValue = if (isSelected) 700f else 400f,
        animationSpec = tween(durationMillis = 300),
        label = "fontWeight"
    )

    ShortNavigationBarItem(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconColor,
                modifier = Modifier.graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
            )
        },
        label = {
            Text(
                text = label,
                color = labelColor,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight(fontWeight.toInt()),
                modifier = Modifier.graphicsLayer {
                    scaleX = if (isSelected) 1.05f else 1.0f
                    scaleY = if (isSelected) 1.05f else 1.0f
                }
            )
        },
        selected = isSelected,
        onClick = { navigateTo(route) },
        interactionSource = interactionSource
    )
}
