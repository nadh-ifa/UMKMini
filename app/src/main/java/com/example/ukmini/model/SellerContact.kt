package com.example.ukmini.model

import kotlinx.serialization.Serializable

@Serializable
data class SellerContact(
    val id: String? = null,
    val user_id: String,
    val store_name: String,
    val whatsapp: String? = null,
    val address: String? = null,
    val instagram: String? = null
)