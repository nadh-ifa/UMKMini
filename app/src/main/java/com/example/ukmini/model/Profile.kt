package com.example.ukmini.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val id: String? = null,

    @SerialName("user_id")
    val userId: String,

    @SerialName("full_name")
    val fullName: String? = "",

    val phone: String? = "",
    val address: String? = "",
    val avatar: String? = "avatar_1",

    @SerialName("created_at")
    val createdAt: String? = null
)