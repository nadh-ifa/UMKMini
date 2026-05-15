package com.example.ukmini.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ukmini.data.SupabaseClient
import com.example.ukmini.model.UserModel
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    var isLoggedIn by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var message by mutableStateOf("")

    private val client = SupabaseClient.client

    init {
        checkSession()
    }

    fun checkSession() {
        viewModelScope.launch {
            val user = client.auth.currentUserOrNull()
            isLoggedIn = user != null
        }
    }

    fun register(name: String, email: String, password: String) {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            message = "Semua field wajib diisi"
            return
        }

        if (password.length < 6) {
            message = "Password minimal 6 karakter"
            return
        }

        viewModelScope.launch {
            try {
                isLoading = true
                message = ""

                client.auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                }

                val user = client.auth.currentUserOrNull()

                if (user != null) {
                    val userData = UserModel(
                        id = user.id,
                        full_name = name,
                        email = email
                    )

                    client.from("users").insert(userData)

                    isLoggedIn = true
                    message = "Register berhasil"
                } else {
                    message = "Register berhasil, silakan login"
                }

            } catch (e: Exception) {
                message = "Register gagal: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            message = "Email dan password wajib diisi"
            return
        }

        viewModelScope.launch {
            try {
                isLoading = true
                message = ""

                client.auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }

                isLoggedIn = true
                message = "Login berhasil"

            } catch (e: Exception) {
                message = "Login gagal: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                client.auth.signOut()
                isLoggedIn = false
                message = "Logout berhasil"
            } catch (e: Exception) {
                message = "Logout gagal: ${e.message}"
            }
        }
    }
}