package edu.ucne.tasktally.domain.usecases.auth

import edu.ucne.tasktally.data.repositories.AuthRepository
import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.data.remote.DTOs.auth.RegisterResponse
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String, confirmPassword: String, email: String?): Resource<RegisterResponse> {
        if (username.isBlank()) {
            return Resource.Error("Username cannot be empty")
        }

        if (username.length < 3) {
            return Resource.Error("Username must be at least 3 characters long")
        }

        if (password.isBlank()) {
            return Resource.Error("Password cannot be empty")
        }

        if (password.length < 6) {
            return Resource.Error("Password must be at least 6 characters long")
        }

        if (password != confirmPassword) {
            return Resource.Error("Passwords do not match")
        }

        if (!email.isNullOrBlank() && !isValidEmail(email)) {
            return Resource.Error("Invalid email format")
        }

        return authRepository.register(username.trim(), password, email?.trim())
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}