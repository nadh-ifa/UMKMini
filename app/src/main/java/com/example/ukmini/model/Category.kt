package com.example.ukmini.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Long? = null,
    val name: String,
    val description: String? = null,
    val created_at: String? = null
)