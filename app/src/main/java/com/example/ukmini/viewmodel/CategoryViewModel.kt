package com.example.ukmini.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ukmini.data.SupabaseClient
import com.example.ukmini.model.Category
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    fun getCategories() {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val result = SupabaseClient.client
                    .from("categories")
                    .select()
                    .decodeList<Category>()

                _categories.value = result
            } catch (e: Exception) {
                _message.value = e.message ?: "Gagal mengambil kategori"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addCategory(name: String, description: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val category = Category(
                    name = name,
                    description = description
                )

                SupabaseClient.client
                    .from("categories")
                    .insert(category)

                _message.value = "Kategori berhasil ditambahkan"
                getCategories()
            } catch (e: Exception) {
                _message.value = e.message ?: "Gagal menambahkan kategori"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearMessage() {
        _message.value = ""
    }
}