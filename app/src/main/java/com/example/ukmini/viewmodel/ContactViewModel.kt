package com.example.ukmini.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ukmini.data.SupabaseClient
import com.example.ukmini.model.SellerContact
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class ContactViewModel : ViewModel() {

    var contacts by mutableStateOf<List<SellerContact>>(emptyList())
    var selectedContact by mutableStateOf<SellerContact?>(null)
    var isLoading by mutableStateOf(false)
    var message by mutableStateOf("")

    private val client = SupabaseClient.client

    fun getContacts() {
        viewModelScope.launch {
            try {
                isLoading = true
                message = ""

                contacts = client
                    .from("seller_contacts")
                    .select()
                    .decodeList<SellerContact>()

            } catch (e: Exception) {
                message = "Gagal mengambil kontak: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun getContactById(contactId: String) {
        selectedContact = contacts.find { it.id == contactId }
    }

    fun addContact(
        storeName: String,
        whatsapp: String,
        address: String,
        instagram: String,
        onSuccess: () -> Unit
    ) {
        if (storeName.isBlank()) {
            message = "Nama toko wajib diisi"
            return
        }

        viewModelScope.launch {
            try {
                isLoading = true
                message = ""

                val user = client.auth.currentUserOrNull()

                if (user == null) {
                    message = "User belum login"
                    return@launch
                }

                val contact = SellerContact(
                    user_id = user.id,
                    store_name = storeName,
                    whatsapp = whatsapp.ifBlank { null },
                    address = address.ifBlank { null },
                    instagram = instagram.ifBlank { null }
                )

                client.from("seller_contacts").insert(contact)

                message = "Kontak berhasil ditambahkan"
                getContacts()
                onSuccess()

            } catch (e: Exception) {
                message = "Gagal menambah kontak: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}