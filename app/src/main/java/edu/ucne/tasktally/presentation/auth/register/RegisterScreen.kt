package edu.ucne.tasktally.presentation.auth.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.tasktally.R
import edu.ucne.tasktally.ui.theme.TaskTallyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    onRegisterSuccess: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.registrationSuccess) {
        if (uiState.registrationSuccess) {
            onRegisterSuccess()
            viewModel.onEvent(RegisterUiEvent.ResetForm)
        }
    }

    RegisterScreenBody(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateBack = onNavigateBack
    )
}

@Composable
fun RegisterScreenBody(
    uiState: RegisterUiState,
    onEvent: (RegisterUiEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    val showUserForm = uiState.role.isNotBlank()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        RegisterHeaderArea(
            showUserForm = showUserForm,
            roleName = uiState.role,
            onNavigateBack = {
                if (showUserForm) {
                    onEvent(RegisterUiEvent.RoleChanged(""))
                } else {
                    onNavigateBack()
                }
            }
        )

        RegisterContentCard(
            showUserForm = showUserForm,
            uiState = uiState,
            onEvent = onEvent
        )
    }
}


@Composable
private fun RegisterHeaderArea(
    showUserForm: Boolean,
    roleName: String,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(if (showUserForm) 0.15f else 0.35f)
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }
            Text(
                text = "Volver al inicio",
                color = Color.White,
                fontSize = 14.sp
            )
        }

        if (!showUserForm) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Únete a TaskTally",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.img_inicio),
                contentDescription = "Logo",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
private fun BoxScope.RegisterContentCard(
    showUserForm: Boolean,
    uiState: RegisterUiState,
    onEvent: (RegisterUiEvent) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(if (showUserForm) 0.85f else 0.65f)
            .align(Alignment.BottomCenter),
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        if (!showUserForm) {
            RoleSelectionContent(
                onRoleSelected = { onEvent(RegisterUiEvent.RoleChanged(it)) }
            )
        } else {
            UserInformationFormContent(
                uiState = uiState,
                onEvent = onEvent
            )
        }
    }
}

@Composable
private fun UserInformationFormContent(
    uiState: RegisterUiState,
    onEvent: (RegisterUiEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 32.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RegisterHeader(roleName = uiState.role)

        Spacer(modifier = Modifier.height(24.dp))

        RegistrationFormFields(
            uiState = uiState,
            onEvent = onEvent,
            focusManager = focusManager,
            isPasswordVisible = isPasswordVisible,
            onPasswordVisibilityChanged = { isPasswordVisible = !isPasswordVisible },
            isConfirmPasswordVisible = isConfirmPasswordVisible,
            onConfirmPasswordVisibilityChanged = {
                isConfirmPasswordVisible = !isConfirmPasswordVisible
            }
        )

        RegistrationSubmitArea(
            uiState = uiState,
            onRegisterClick = { onEvent(RegisterUiEvent.RegisterClick) }
        )
    }
}

@Composable
private fun RegisterHeader(roleName: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Registro",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        ) {
            Text(
                text = roleName.replaceFirstChar { it.uppercase() },
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun RegistrationFormFields(
    uiState: RegisterUiState,
    onEvent: (RegisterUiEvent) -> Unit,
    focusManager: FocusManager,
    isPasswordVisible: Boolean,
    onPasswordVisibilityChanged: () -> Unit,
    isConfirmPasswordVisible: Boolean,
    onConfirmPasswordVisibilityChanged: () -> Unit
) {
    OutlinedTextField(
        value = uiState.username,
        onValueChange = { onEvent(RegisterUiEvent.UsernameChanged(it)) },
        label = { Text("Usuario") },
        leadingIcon = { Icon(Icons.Default.Person, null, tint = Color.Gray) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.LightGray,
            focusedLabelColor = MaterialTheme.colorScheme.primary
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
        enabled = !uiState.isLoading
    )

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = uiState.email,
        onValueChange = { onEvent(RegisterUiEvent.EmailChanged(it)) },
        label = { Text("Correo") },
        leadingIcon = { Icon(Icons.Default.Email, null, tint = Color.Gray) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.LightGray,
            focusedLabelColor = MaterialTheme.colorScheme.primary
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
        enabled = !uiState.isLoading
    )

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = uiState.password,
        onValueChange = { onEvent(RegisterUiEvent.PasswordChanged(it)) },
        label = { Text("Contraseña") },
        leadingIcon = { Icon(Icons.Default.Lock, null, tint = Color.Gray) },
        trailingIcon = {
            IconButton(onClick = onPasswordVisibilityChanged) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = if (isPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                    tint = Color.Gray
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.LightGray,
            focusedLabelColor = MaterialTheme.colorScheme.primary
        ),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
        enabled = !uiState.isLoading
    )

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = uiState.confirmPassword,
        onValueChange = { onEvent(RegisterUiEvent.ConfirmPasswordChanged(it)) },
        label = { Text("Confirmar Contraseña") },
        leadingIcon = { Icon(Icons.Default.Lock, null, tint = Color.Gray) },
        trailingIcon = {
            IconButton(onClick = onConfirmPasswordVisibilityChanged) {
                Icon(
                    imageVector = if (isConfirmPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = if (isConfirmPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                    tint = Color.Gray
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.LightGray,
            focusedLabelColor = MaterialTheme.colorScheme.primary
        ),
        visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus(); onEvent(
            RegisterUiEvent.RegisterClick
        )
        }),
        enabled = !uiState.isLoading
    )
}

@Composable
private fun RegistrationSubmitArea(
    uiState: RegisterUiState,
    onRegisterClick: () -> Unit
) {
    if (uiState.error != null) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = uiState.error,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
        )
    }

    if (uiState.successMessage != null) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = uiState.successMessage,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodySmall
        )
    }

    Spacer(modifier = Modifier.height(32.dp))

    val isButtonEnabled = !uiState.isLoading &&
            uiState.username.isNotBlank() &&
            uiState.password.isNotBlank() &&
            uiState.confirmPassword.isNotBlank() &&
            uiState.role.isNotBlank()

    Button(
        onClick = onRegisterClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        enabled = isButtonEnabled
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = Color.White
            )
        } else {
            Text(
                text = "Registrarse",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun RoleSelectionContent(
    onRoleSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Selecciona tu Rol",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Elige cómo quieres usar TaskTally",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                onClick = { onRoleSelected("gema") },
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Gema",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Card(
                onClick = { onRoleSelected("mentor") },
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Mentor",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegisterScreenBodyRoleSelectionPreview() {
    TaskTallyTheme {
        RegisterScreenBody(
            uiState = RegisterUiState(),
            onEvent = {},
            onNavigateBack = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegisterScreenBodyFormPreview() {
    TaskTallyTheme {
        RegisterScreenBody(
            uiState = RegisterUiState(
                role = "gema",
                username = "testuser",
                email = "test@example.com",
                password = "password123",
                confirmPassword = "password123"
            ),
            onEvent = {},
            onNavigateBack = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegisterScreenBodyLoadingPreview() {
    TaskTallyTheme {
        RegisterScreenBody(
            uiState = RegisterUiState(
                role = "mentor",
                username = "testuser",
                email = "test@example.com",
                password = "password123",
                confirmPassword = "password123",
                isLoading = true
            ),
            onEvent = {},
            onNavigateBack = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegisterScreenBodyErrorPreview() {
    TaskTallyTheme {
        RegisterScreenBody(
            uiState = RegisterUiState(
                role = "gema",
                username = "testuser",
                email = "test@example.com",
                password = "password123",
                confirmPassword = "password123",
                error = "Las contraseñas no coinciden"
            ),
            onEvent = {},
            onNavigateBack = {}
        )
    }
}

