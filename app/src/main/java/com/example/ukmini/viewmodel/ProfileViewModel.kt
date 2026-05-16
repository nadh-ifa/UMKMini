package com.example.ukmini.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ukmini.data.SupabaseClient
import com.example.ukmini.model.Profile
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _profile = MutableStateFlow<Profile?>(null)
    val profile: StateFlow<Profile?> = _profile

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    fun getProfile() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _message.value = ""

                val userId = SupabaseClient.client.auth.currentUserOrNull()?.id

                if (userId == null) {
                    _message.value = "User belum login"
                    return@launch
                }

                val result = SupabaseClient.client
                    .from("profiles")
                    .select {
                        filter {
                            eq("user_id", userId)
                        }
                    }
                    .decodeSingleOrNull<Profile>()

                _profile.value = result

            } catch (e: Exception) {
                _message.value = e.message ?: "Gagal mengambil profil"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveProfile(
        fullName: String,
        phone: String,
        address: String,
        avatar: String,
        onSuccess: () -> Unit = {}
    ) {
        if (fullName.isBlank()) {
            _message.value = "Nama lengkap wajib diisi"
            return
        }

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _message.value = ""

                val userId = SupabaseClient.client.auth.currentUserOrNull()?.id

                if (userId == null) {
                    _message.value = "User belum login"
                    return@launch
                }

                val existingProfile = SupabaseClient.client
                    .from("profiles")
                    .select {
                        filter {
                            eq("user_id", userId)
                        }
                    }
                    .decodeSingleOrNull<Profile>()

                val newProfile = Profile(
                    id = existingProfile?.id,
                    userId = userId,
                    fullName = fullName,
                    phone = phone,
                    address = address,
                    avatar = avatar
                )

                if (existingProfile == null) {
                    SupabaseClient.client
                        .from("profiles")
                        .insert(newProfile)
                } else {
                    SupabaseClient.client
                        .from("profiles")
                        .update({
                            set("full_name", fullName)
                            set("phone", phone)
                            set("address", address)
                            set("avatar", avatar)
                        }) {
                            filter {
                                eq("user_id", userId)
                            }
                        }
                }

                _profile.value = newProfile
                _message.value = "Profil berhasil disimpan"
                onSuccess()

            } catch (e: Exception) {
                _message.value = e.message ?: "Gagal menyimpan profil"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearMessage() {
        _message.value = ""
    }
}