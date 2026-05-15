package com.example.ukmini.model

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val id: String,
    val full_name: String,
    val email: String,
    val avatar_url: String? = null
)