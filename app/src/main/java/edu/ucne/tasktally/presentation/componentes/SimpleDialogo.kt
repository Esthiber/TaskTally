package edu.ucne.tasktally.presentation.componentes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

enum class ErrorType {
    ERROR,
    WARNING,
    INFO
}

@Composable
fun SimpleDialogo(
    showDialog: Boolean,
    errorType: ErrorType = ErrorType.ERROR,
    title: String = when (errorType) {
        ErrorType.ERROR -> "Error"
        ErrorType.WARNING -> "Advertencia"
        ErrorType.INFO -> "Información"
    },
    message: String,
    positiveButtonText: String = "Aceptar",
    negativeButtonText: String? = null,
    onPositiveButtonClick: () -> Unit = {},
    onNegativeButtonClick: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    if (showDialog) {
        val icon: ImageVector = when (errorType) {
            ErrorType.ERROR -> Icons.Default.Error
            ErrorType.WARNING -> Icons.Default.Warning
            ErrorType.INFO -> Icons.Default.Info
        }

        val iconTint: Color = when (errorType) {
            ErrorType.ERROR -> MaterialTheme.colorScheme.error
            ErrorType.WARNING -> MaterialTheme.colorScheme.tertiary
            ErrorType.INFO -> MaterialTheme.colorScheme.primary
        }

        AlertDialog(
            onDismissRequest = onDismiss,
            icon = {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconTint
                )
            },
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onPositiveButtonClick()
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when (errorType) {
                            ErrorType.ERROR -> MaterialTheme.colorScheme.error
                            ErrorType.WARNING -> MaterialTheme.colorScheme.tertiary
                            ErrorType.INFO -> MaterialTheme.colorScheme.primary
                        }
                    )
                ) {
                    Text(
                        text = positiveButtonText,
                        color = when (errorType) {
                            ErrorType.ERROR -> MaterialTheme.colorScheme.onError
                            ErrorType.WARNING -> MaterialTheme.colorScheme.onTertiary
                            ErrorType.INFO -> MaterialTheme.colorScheme.onPrimary
                        }
                    )
                }
            },
            dismissButton = negativeButtonText?.let {
                {
                    OutlinedButton(
                        onClick = {
                            onNegativeButtonClick()
                            onDismiss()
                        }
                    ) {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            textContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ErrorDialogo(
    showDialog: Boolean,
    message: String,
    onDismiss: () -> Unit
) {
    SimpleDialogo(
        showDialog = showDialog,
        errorType = ErrorType.ERROR,
        title = "Error",
        message = message,
        onDismiss = onDismiss
    )
}

@Composable
fun WarningDialogo(
    showDialog: Boolean,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    SimpleDialogo(
        showDialog = showDialog,
        errorType = ErrorType.WARNING,
        title = "Advertencia",
        message = message,
        positiveButtonText = "Continuar",
        negativeButtonText = "Cancelar",
        onPositiveButtonClick = onConfirm,
        onDismiss = onDismiss
    )
}

@Composable
fun InfoDialogo(
    showDialog: Boolean,
    title: String = "Información",
    message: String,
    onDismiss: () -> Unit
) {
    SimpleDialogo(
        showDialog = showDialog,
        errorType = ErrorType.INFO,
        title = title,
        message = message,
        onDismiss = onDismiss
    )
}

@Preview(showBackground = true)
@Composable
fun SimpleDialogoPreview() {
    MaterialTheme {
        SimpleDialogo(
            showDialog = true,
            errorType = ErrorType.ERROR,
            title = "Error de Conexión",
            message = "No se pudo conectar con el servidor. Verifique su conexión a internet e intente nuevamente.",
            positiveButtonText = "Reintentar",
            negativeButtonText = "Cancelar",
            onPositiveButtonClick = { },
            onNegativeButtonClick = { },
            onDismiss = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WarningDialogoPreview() {
    MaterialTheme {
        WarningDialogo(
            showDialog = true,
            message = "Esta acción no se puede deshacer. ¿Está seguro que desea continuar?",
            onConfirm = { },
            onDismiss = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InfoDialogoPreview() {
    MaterialTheme {
        InfoDialogo(
            showDialog = true,
            title = "Información",
            message = "La tarea se ha guardado exitosamente en el dispositivo.",
            onDismiss = { }
        )
    }
}